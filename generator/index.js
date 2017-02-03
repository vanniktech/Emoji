const fs = require("fs-promise");
const cheerio = require("cheerio");
const _ = require("underscore");
const download = require("download");

/**
 * The targets for generating. Extend these for adding more emoji variants.
 * @type {[*]} An array of target-objects.
 */
const targets = [{
    package: "ios",
    name: "IosEmoji",
    imagePosition: 4
}, {
    package: "one",
    name: "EmojiOne",
    imagePosition: 7
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
 * Parses the files, creates a map of categories to emojis and copies the images (including the category images) to the
 * destinations, specified by the passed targets.
 * @param targets The targets, providing the destination for copying.
 * @returns {Promise.<Map>} Promise returning the map.
 */
async function parseAndCopyImages(targets) {
    console.log("Parsing files and extracting images...");

    for (const target of targets) {
        await fs.emptyDir(`../emoji-${target.package}/src/main/res/drawable`);
        await fs.emptyDir(`../emoji-${target.package}/src/main/res/drawable-nodpi`);
    }

    const map = new Map();
    const $ = cheerio.load(await fs.readFile("build/full-emoji-list.html"));
    const emojiInfo = JSON.parse(await fs.readFile("build/emoji.json"));

    const rows = $("tr").get()
        .map(it => it.children.filter(it => it.name === "td"))
        .filter(it => it.length === 19 && it[1].attribs.class === "code");

    for (const row of rows) {
        const code = row[1].children[0].attribs.name;
        const description = row[16].children[0].data;
        const foundInfo = emojiInfo.find(it => it.description === (description.includes("skin tone") ?
            description.substring(0, description.indexOf(":")) : description));
        const category = foundInfo ? foundInfo.category : null;

        if (category) {
            const emoji = {
                unicode: code
            };

            for (const target of targets) {
                const image = row[target.imagePosition].children[0].name === "img" ?
                    row[target.imagePosition].children[0].attribs.src.replace(/^data:image\/png;base64,/, "") : null;

                if (image) {
                    await fs.writeFile(`../emoji-${target.package}/src/main/res/drawable-nodpi/emoji_${target.package}_${code}.png`, image, "base64");

                    emoji[target.package] = true;
                }
            }

            if (map.has(category)) {
                map.get(category).push(emoji);
            } else {
                map.set(category, new Array(emoji));

                for (const target of targets) {
                    await fs.copy(`img/${category.toLowerCase()}.xml`,
                        `../emoji-${target.package}/src/main/res/drawable/emoji_${target.package}_category_${category.toLowerCase()}.xml`);
                }
            }
        }
    }

    return map;
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
            const data = emojis.filter(it => it[target.package]).map(it => it.unicode).map((it) => {
                const unicodeParts = it.split("_");

                if (unicodeParts.length == 1) {
                    return `new Emoji(0x${unicodeParts[0]}, R.drawable.emoji_${target.package}_${it})`;
                } else {
                    return `new Emoji(new int[]{${unicodeParts.map(it => "0x" + it).join(", ")}}, R.drawable.emoji_${target.package}_${it})`;
                }
            }).join(",\n            ");

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
        }).join(",\n                ");

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
 * This is separated into three parts:
 * - Downloading the necessary files.
 * - Parsing the files and copying the images into the respective directories.
 * - Generating the java code and copying it into the respective directories.
 * If the no-download command line option is passed, the download step is skipped. In that case the presence of the
 * relevant files is assumed.
 * @returns {Promise.<void>} Empty Promise.
 */
async function run() {
    if (!(process.argv.length >= 3 && process.argv.includes("-no-download"))) {
        await downloadFiles();
    }

    const map = await parseAndCopyImages(targets);
    await generateCode(map, targets);
}

run().then()
    .catch(err => {
        console.error(err);
    });