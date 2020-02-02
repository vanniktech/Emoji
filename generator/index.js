const commandLineArgs = require("command-line-args");
const fs = require("fs-extra");
const stable = require("stable");
const _ = require("underscore");
const imagemin = require("imagemin");
const imageminZopfli = require("imagemin-zopfli");
const imageminPngquant = require("imagemin-pngquant");
const emojiData = require("./node_modules/emoji-datasource/emoji.json");
const Jimp = require("jimp");

/**
 * The targets for generating. Extend these for adding more emoji variants.
 * @type {*[]} An Array of target-objects.
 */
const targets = [{
    package: "ios",
    name: "IosEmoji",
    dataSource: "apple",
    dataAttribute: "has_img_apple",
}, {
    package: "google",
    name: "GoogleEmoji",
    dataSource: "google",
    dataAttribute: "has_img_google",
}, {
    package: "twitter",
    name: "TwitterEmoji",
    dataSource: "twitter",
    dataAttribute: "has_img_twitter",
}];

/**
 * Emoji codepoints which are duplicates. These are marked as such in the generated code.
 * @type {string[]}
 */
const duplicates = ["1F926", "1F937", "1F938", "1F93C", "1F93D", "1F93E", "1F939"];

/**
 * The order of the categories.
 * @type {string[]}
 */
const categoryOrder = ["SmileysAndPeople", "AnimalsAndNature", "FoodAndDrink", "Activities", "TravelAndPlaces",
    "Objects", "Symbols", "Flags"];

/**
 * The amount of emojis to put in a chunk.
 * @type {number}
 */
const chunkSize = 250;

/**
 * Helper function to be used by {@link #copyImages} for copying (and optimizing) the images of a single target
 * to their destinations.
 * @param map The map.
 * @param target The target.
 * @param shouldOptimize If optimization should be performed.
 * @returns {Promise.<void>} Empty Promise.
 */
async function copyTargetImages(map, target, shouldOptimize) {
    await fs.emptyDir(`../emoji-${target.package}/src/main/res/drawable-nodpi`);

    const allEmoji = emojiData.reduce((all, it) => {
        all.push(it);
        if (it.skin_variations) {
            all.push(...Object.values(it.skin_variations));
        }
        return all;
    }, []);

    const emojiByStrip = [];
    allEmoji.forEach(it => {
        if (emojiByStrip[it.sheet_x]) {
            emojiByStrip[it.sheet_x].push(it);
        } else {
            emojiByStrip[it.sheet_x] = new Array(it);
        }
    });

    const src = `node_modules/emoji-datasource-${target.dataSource}/img/${target.dataSource}/sheets-clean/64.png`;
    const sheet = await Jimp.read(src);
    const strips = sheet.bitmap.width / 66 - 1;

    for (let i = 0; i < strips; i++) {
        const dest = `../emoji-${target.package}/src/main/res/drawable-nodpi/emoji_${target.package}_sheet_${i}.png`;
        const maxY = emojiByStrip[i].map(it => it.sheet_y).reduce((a, b) => Math.max(a, b), 0);
        const height = (maxY + 1) * 66;

        const strip = await sheet.clone().crop(i * 66, 0, 66, height)

        if (shouldOptimize) {
            const buffer = await strip.getBufferAsync('image/png');
            const optimizedStrip = await imagemin.buffer(buffer, {
                plugins: [
                    imageminPngquant(),
                    imageminZopfli(),
                ],
            });
            await fs.writeFile(dest, optimizedStrip);
        } else {
            await strip.writeAsync(dest);
        }
    }

    for (const [category] of map) {
        const dest = `../emoji-${target.package}/src/main/res/drawable-nodpi/emoji_${target.package}_category_${category.toLowerCase()}.png`

        await fs.copy(`img/${category.toLowerCase()}.png`, dest);
    }
}

/**
 * Generates a list of code chunks for the given list of emojis with their variants if present.
 * @param target The target to generate for. It is checked if the target has support for the emoji before generating.
 * @param emojis The emojis.
 * @returns {string[]} List of generated code chunks
 */
function generateChunkedEmojiCode(target, emojis) {
    const list = generateEmojiCode(target, emojis)
    const chunked = _.chunk(list, chunkSize)

    return chunked.map(chunk => chunk.join(`,\n      `))
}

/**
 * Generates the code for a list of emoji with their variants if present.
 * @param target The target to generate for. It is checked if the target has support for the emoji before generating.
 * @param emojis The emojis.
 * @param indent The indent to use. Defaults to 6.
 * @returns {string} The generated code.
 */
function generateEmojiCode(target, emojis, indent = 6) {
    let indentString = "";

    for (let i = 0; i < indent; i++) {
        indentString += " ";
    }

    return emojis.filter(it => it[target.package]).map((it) => {
        const unicodeParts = it.unicode.split("-");
        let result = "";

        if (unicodeParts.length === 1) {
            result = `new ${target.name}(0x${unicodeParts[0]}, ${it.x}, ${it.y}, ${it.isDuplicate}`;
        } else {
            result = `new ${target.name}(new int[] { ${unicodeParts.map(it => "0x" + it).join(", ")} }, ${it.x}, ${it.y}, ${it.isDuplicate}`;
        }

        if (it.variants.filter(it => it[target.package]).length > 0) {
            const generatedVariants = generateEmojiCode(target, it.variants, indent + 2).join(`,\n${indentString}  `)

            return `${result},\n${indentString}  ${generatedVariants}\n${indentString})`;
        } else {
            return `${result})`;
        }
    })
}

/**
 * Parses the files and creates a map of categories to emojis, specified by the passed targets.
 * @returns {Promise.<Map>} Promise returning the map.
 */
async function parse() {
    console.log("Parsing files...");

    const result = new Map();
    const filteredEmojiData = emojiData.filter(it => it.category !== "Skin Tones");
    const preparedEmojiData = stable(filteredEmojiData, (first, second) => first.sort_order - second.sort_order);

    for (const dataEntry of preparedEmojiData) {
        const category = dataEntry.category.replace(" & ", "And");
        const isDuplicate = !!dataEntry.obsoleted_by || duplicates.includes(dataEntry.unified);

        const emoji = {
            unicode: dataEntry.unified,
            x: dataEntry.sheet_x,
            y: dataEntry.sheet_y,
            isDuplicate: isDuplicate,
            variants: [],
        };

        if (dataEntry.skin_variations) {
            for (const variantDataEntry of Object.values(dataEntry.skin_variations)) {
                const isDuplicate = !!variantDataEntry.obsoleted_by || duplicates.includes(variantDataEntry.unified);

                const variantEmoji = {
                    unicode: variantDataEntry.unified,
                    x: variantDataEntry.sheet_x,
                    y: variantDataEntry.sheet_y,
                    isDuplicate: isDuplicate,
                    variants: [],
                };

                for (const target of targets) {
                    if (variantDataEntry[target.dataAttribute] === true) {
                        variantEmoji[target.package] = true
                    }
                }

                emoji.variants.push(variantEmoji)
            }
        }

        for (const target of targets) {
            if (dataEntry[target.dataAttribute] === true) {
                emoji[target.package] = true
            }
        }

        if (result.has(category)) {
            result.get(category).push(emoji);
        } else {
            result.set(category, new Array(emoji));
        }
    }

    // Normalize the new "Smileys & Emotion" and "People & Body" categories to the ones we have.
    const smileysAndEmotion = result.get("SmileysAndEmotion")
    const peopleAndBody = result.get("PeopleAndBody")

    result.set("SmileysAndPeople", smileysAndEmotion.concat(peopleAndBody))
    result.delete("SmileysAndEmotion")
    result.delete("PeopleAndBody")

    return result;
}

/**
 * Copies the images from the previously parsed map into the respective directories, based on the passed targets.
 * @param map The map.
 * @param targets The targets.
 * @param shouldOptimize If optimization should be performed.
 * @returns {Promise.<void>} Empty Promise.
 */
async function copyImages(map, targets, shouldOptimize) {
    console.log("Optimizing and copying images...");

    const promises = [];

    for (const target of targets) {
        promises.push(copyTargetImages(map, target, shouldOptimize));
    }

    await Promise.all(promises);
}

/**
 * Generates the relevant java code and saves it to the destinations, specified by the targets. Code generated are the
 * categories, the provider and the specific emoji class.
 * @param map The previously created map.
 * @param targets The targets, providing destination for the code files.
 * @returns {Promise.<void>} Empty Promise.
 */
async function generateCode(map, targets) {
    console.log("Generating java code...");

    const emojiTemplate = await fs.readFile("template/Emoji.java", "utf-8");
    const categoryTemplate = await fs.readFile("template/Category.java", "utf-8");
    const categoryChunkTemplate = await fs.readFile("template/CategoryChunk.java", "utf-8");
    const emojiProviderTemplate = await fs.readFile("template/EmojiProvider.java", "utf-8");

    const entries = stable([...map.entries()], (first, second) => {
        return categoryOrder.indexOf(first[0]) - categoryOrder.indexOf(second[0]);
    });

    for (const target of targets) {
        const dir = `../emoji-${target.package}/src/main/java/com/vanniktech/emoji/${target.package}`;

        await fs.emptyDir(dir);
        await fs.mkdir(`${dir}/category`);

        let strips = 0;
        for (const [category, emojis] of entries) {
            emojis.forEach(emoji => strips = Math.max(strips, emoji.x + 1));

            const dataChunks = generateChunkedEmojiCode(target, emojis);
            const chunkClasses = [];

            for (let index = 0; index < dataChunks.length; index++) {
                const chunk = dataChunks[index];
                const chunkClass = `${category}CategoryChunk${index}`

                chunkClasses.push(chunkClass)

                await fs.writeFile(`${dir}/category/${chunkClass}.java`,
                    _(categoryChunkTemplate).template()({
                        package: target.package,
                        name: target.name,
                        category: category,
                        index: index,
                        data: chunk,
                    }));
            }

            await fs.writeFile(`${dir}/category/${category}Category.java`,
                _(categoryTemplate).template()({
                    package: target.package,
                    name: target.name,
                    category: category,
                    chunks: chunkClasses.map(it => `${it}.get()`).join(", "),
                    icon: category.toLowerCase(),
                }));
        }

        const imports = [...map.keys()].sort().map((category) => {
            return `import com.vanniktech.emoji.${target.package}.category.${category}Category;`
        }).join("\n");

        const categories = entries.map(entry => {
            const [category] = entry;

            return `new ${category}Category()`
        }).join(",\n      ");

        await fs.writeFile(`${dir}/${target.name}Provider.java`, _(emojiProviderTemplate).template()({
            package: target.package,
            imports: imports,
            name: target.name,
            categories: categories,
        }));

        await fs.writeFile(`${dir}/${target.name}.java`, _(emojiTemplate).template()({
            package: target.package,
            name: target.name,
            strips: strips,
        }));
    }
}

/**
 * Runs the script.
 * This is separated into three parts:
 * - Parsing the files.
 * - Copying (and optimizing) the images into the respective directories.
 * - Generating the java code and copying it into the respective directories.
 * All tasks apart from the parsing can be disabled through a command line parameter. If you want to skip the
 * optimization of the required files (It is assumed they are in place then) for example, you can pass --no-optimize to
 * skip the optimization step.
 * @returns {Promise.<void>} Empty Promise.
 */
async function run() {
    const options = commandLineArgs([
        {name: 'no-copy', type: Boolean},
        {name: 'no-optimize', type: Boolean},
        {name: 'no-generate', type: Boolean},
    ]);

    const map = await parse();

    if (!options["no-copy"]) {
        await copyImages(map, targets, !options["no-optimize"]);
    }

    if (!options["no-generate"]) {
        await generateCode(map, targets);
    }
}

run().then()
    .catch(err => {
        console.error(err);
    });
