const commandLineArgs = require("command-line-args");
const fs = require("fs-promise");
const stable = require("stable");
const cheerio = require("cheerio");
const _ = require("underscore");
const imagemin = require("imagemin");
const imageminOptipng = require("imagemin-optipng");
const download = require("download");

/**
 * Calculates the length of a nested Array by summing the lengths of all Arrays in this Array.
 * Note that this works only for the first level of nesting.
 * @returns {Number} The nested length of this Array.
 */
Array.prototype.nestedLength = function flatten() {
    return this.reduce((sum, toSum) => sum + toSum.length, 0);
};

/**
 * Flattens a nested Array to a single one.
 * @returns {Array} The flattened Array.
 */
Array.prototype.flatten = function flatten() {
    return this.reduce((flat, toFlatten) => flat.concat(Array.isArray(toFlatten) ? toFlatten.flatten() : toFlatten), []);
};

/**
 * Returns the description, made usable for searching in the emoji.json. This means stripping the skin tone and special
 * characters.
 * @param description The description to adjust.
 * @returns {string} The adjusted description.
 */
function getDescriptionForFinding(description) {
    return description.includes("skin tone") ? description.substring(0, description.indexOf(":")) : description
}

/**
 * Generates the code for a list of emoji with their variants if present.
 * @param target The target to generate for. It is checked if the target has support for the emoji before generating.
 * @param emojis The emojis.
 * @param indent The indent to use. Defaults to 4.
 * @returns {string} The generated code.
 */
function generateEmojiCode(target, emojis, indent = 4) {
    let indentString = "";

    for (let i = 0; i < indent; i++) {
        indentString += " ";
    }

    return emojis.filter(it => it[target.package] && !target.ignore.includes(it.unicode)).map((it) => {
        const unicodeParts = it.unicode.split("_");
        let result = "";

        if (unicodeParts.length == 1) {
            result = `new Emoji(0x${unicodeParts[0]}, R.drawable.emoji_${target.package}_${it.unicode}`;
        } else {
            result = `new Emoji(new int[] { ${unicodeParts.map(it => "0x" + it).join(", ") } }, R.drawable.emoji_${target.package}_${it.unicode}`;
        }

        if (it.variants.filter(it => it[target.package]).length > 0) {
            return `${result},\n${indentString}  ${generateEmojiCode(target, it.variants, indent + 2)}\n${indentString})`;
        } else {
            return `${result})`;
        }
    }).join(`,\n${indentString}`);
}

/**
 * Optimizes the image of a single emoji in place.
 * @param target The target to optimize for. It is checked if the target has support for the emoji before optimizing.
 * @param emoji The emoji.
 * @returns {Promise.<void>} Empty Promise.
 */
async function optimizeEmojiImage(target, emoji) {
    if (emoji[target.package] && !target.ignore.includes(emoji.unicode)) {
        emoji[target.package] = await imagemin.buffer(emoji[target.package], {
            plugins: [
                imageminOptipng({more: true})
            ]
        });

        for (const variant of emoji.variants) {
            await optimizeEmojiImage(target, variant);
        }
    }
}

/**
 * Copies an emoji to the folder, specified by the target.
 * @param target The target with info on where to copy the image. It is checked if the target has support for the emoji before copying.
 * @param emoji The emoji.
 * @returns {Promise.<void>} Empty Promise.
 */
async function copyEmojiImage(target, emoji) {
    if (emoji[target.package] && !target.ignore.includes(emoji.unicode)) {
        await fs.writeFile(`../emoji-${target.package}/src/main/res/drawable-nodpi/emoji_${target.package}_${emoji.unicode}.png`, emoji[target.package]);

        for (const variant of emoji.variants) {
            await copyEmojiImage(target, variant);
        }
    }
}

/**
 * The targets for generating. Extend these for adding more emoji variants.
 * @type {[*]} An array of target-objects.
 */
const targets = [{
    package: "ios",
    name: "IosEmoji",
    imagePosition: 4,
    ignore: []
}, {
    package: "google",
    name: "GoogleEmoji",
    imagePosition: 5,
    ignore: [
        "1f1e7_1f1f1", "1f1e7_1f1f6", "1f1ea_1f1ed", "1f1eb_1f1f0", "1f1ec_1f1eb", "1f1ec_1f1f5", "1f1ec_1f1f8",
        "1f1f2_1f1f6", "1f1f3_1f1e8", "1f1f5_1f1f2", "1f1f7_1f1ea", "1f1f9_1f1eb", "1f1fc_1f1eb", "1f1fd_1f1f0",
        "1f1fe_1f1f9"
    ]
}, {
    package: "one",
    name: "EmojiOne",
    imagePosition: 7,
    ignore: []
}];

/**
 * Downloads a single file and shows progress on the console.
 * @param url The file to download.
 * @param dest The destination.
 * @returns {Promise.<void>} Empty Promise.
 */
async function downloadFile(url, dest) {
    await download(url, dest)
        .on('response', res => {
            let current = 0;

            res.on('data', data => {
                current += data.length;

                process.stdout.write("\r" + parseFloat(current / 1024 / 1024).toFixed(2) + "MB")
            });
        });

    console.log("");
}

/**
 * Downloads the required files.
 * @returns {Promise.<void>} Empty promise.
 */
async function downloadFiles() {
    console.log("Downloading files...");

    await fs.emptyDir("build");
    await downloadFile("http://unicode.org/emoji/charts/full-emoji-list.html", "build");
    await downloadFile("https://raw.githubusercontent.com/github/gemoji/master/db/emoji.json", "build");
}

/**
 * Parses the files and creates a map of categories to emojis, specified by the passed targets.
 * @returns {Promise.<Map>} Promise returning the map.
 */
async function parse() {
    console.log("Parsing files...");

    const map = new Map();
    const $ = cheerio.load(await fs.readFile("build/full-emoji-list.html"));
    const emojiInfo = JSON.parse(await fs.readFile("build/emoji.json"));

    const rows = $("tr").get()
        .map(it => it.children.filter(it => it.name === "td"))
        .filter(it => it.length === 19 && it[1].attribs.class === "code");

    const sortedRows = stable(rows, (first, second) => {
        return emojiInfo.findIndex(it => it.description === getDescriptionForFinding(first[16].children[0].data)) -
            emojiInfo.findIndex(it => it.description === getDescriptionForFinding(second[16].children[0].data))
    });

    for (const row of sortedRows) {
        const foundInfo = emojiInfo.find(it => it.description === getDescriptionForFinding(row[16].children[0].data));
        const category = foundInfo ? foundInfo.category : null;

        if (category) {
            const code = row[1].children[0].attribs.name;
            const isVariant = row[16].children[0].data.includes("skin tone");

            const emoji = {
                unicode: code,
                variants: []
            };

            for (const target of targets) {
                const image = row[target.imagePosition].children[0].name === "img" ?
                    new Buffer(row[target.imagePosition].children[0].attribs.src.replace(/^data:image\/png;base64,/, ""), "base64") : null;

                if (image) {
                    emoji[target.package] = image;
                }
            }

            if (isVariant) {
                const array = map.get(category);

                array[array.length - 1].variants.push(emoji);
            } else {
                if (map.has(category)) {
                    map.get(category).push(emoji);
                } else {
                    map.set(category, new Array(emoji));
                }
            }
        }
    }

    return map;
}

/**
 * Optimizes the buffered images in the previously parsed map, based on the passed targets.
 * @param map The map.
 * @param targets The targets.
 * @returns {Promise.<void>} Empty Promise.
 */
async function optimizeImages(map, targets) {
    console.log("Optimizing images...");

    const emojiAmount = [...map.values()].nestedLength();
    let i = 0;

    for (const emoji of [...map.values()].flatten()) {
        process.stdout.write("\r" + (i + 1) + "/" + emojiAmount);

        for (const target of targets) {
            await optimizeEmojiImage(target, emoji);
        }

        i++
    }

    console.log("");
}

/**
 * Copies the images from the previously parsed map into the respective directories, based on the passed targets.
 * @param map The map.
 * @param targets The targets.
 * @returns {Promise.<void>} Empty Promise.
 */
async function copyImages(map, targets) {
    console.log("Copying images...");

    for (const target of targets) {
        await fs.emptyDir(`../emoji-${target.package}/src/main/res/drawable-nodpi`);
    }

    const emojiAmount = [...map.values()].nestedLength();
    let i = 0;

    for (const [category, emojis] of map) {
        for (const target of targets) {
            await fs.copy(`img/${category.toLowerCase()}.png`,
                `../emoji-${target.package}/src/main/res/drawable-nodpi/emoji_${target.package}_category_${category.toLowerCase()}.png`);
        }

        for (const emoji of emojis) {
            process.stdout.write("\r" + (i + 1) + "/" + emojiAmount);

            for (const target of targets) {
                await copyEmojiImage(target, emoji);
            }

            i++;
        }
    }

    console.log("");
}

/**
 * Generates the relevant java code and saves it to the destinations, specified by the targets. Code generated are the
 * categories and the provider.
 * @param map The previously created map.
 * @param targets The targets, providing destination for the code files.
 * @returns {Promise.<void>} Empty Promise.
 */
async function generateCode(map, targets) {
    console.log("Generating java code...");

    const categoryTemplate = await fs.readFile("template/Category.java", "utf-8");
    const emojiProviderTemplate = await fs.readFile("template/EmojiProvider.java", "utf-8");

    for (const target of targets) {
        const dir = `../emoji-${target.package}/src/main/java/com/vanniktech/emoji/${target.package}/category/`;

        await fs.emptyDir(dir);

        for (const [category, emojis] of map.entries()) {
            const data = generateEmojiCode(target, emojis);

            await fs.writeFile(`${dir + category}Category.java`,
                _(categoryTemplate).template()({
                    package: target.package,
                    name: category,
                    data: data,
                    icon: category.toLowerCase()
                }));
        }

        const imports = [...map.keys()].sort().map((category) => {
            return `import com.vanniktech.emoji.${target.package}.category.${category}Category;`
        }).join("\n");

        const categoryMapping = [...map.keys()].map((category) => {
            return `new ${category}Category()`
        }).join(",\n      ");

        await fs.writeFile(`../emoji-${target.package}/src/main/java/com/vanniktech/emoji/${target.package}/${target.name}Provider.java`, _(emojiProviderTemplate).template()({
            package: target.package,
            imports: imports,
            name: target.name,
            categoryMapping: categoryMapping
        }));
    }
}

/**
 * Runs the script.
 * This is separated into five parts:
 * - Downloading the necessary files.
 * - Parsing the files.
 * - Optimizing the images.
 * - Copying the images into the respective directories
 * - Generating the java code and copying it into the respective directories.
 * All tasks apart from the parsing can be disabled through a command line parameter. If you want to skip the download
 * of the required files (It is assumed they are in place then) for example, you can pass --no-download.
 * @returns {Promise.<void>} Empty Promise.
 */
async function run() {
    const options = commandLineArgs([
        {name: 'no-download', type: Boolean},
        {name: 'no-copy', type: Boolean},
        {name: 'no-optimize', type: Boolean,},
        {name: 'no-generate', type: Boolean}
    ]);

    if (!options["no-download"]) {
        await downloadFiles();
    }

    const map = await parse();

    if (!options["no-copy"]) {
        if (!options["no-optimize"]) {
            await optimizeImages(map, targets);
        }

        await copyImages(map, targets);
    }

    if (!options["no-generate"]) {
        await generateCode(map, targets);
    }
}

run().then()
    .catch(err => {
        console.error(err);
    });