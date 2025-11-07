/*
 * Copyright (C) 2016 - Niklas Baudy, Ruben Gees, Mario Đanić and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import commandLineArgs from "command-line-args"
import fs from "fs-extra"
import chunk from "lodash.chunk";
import template from "lodash.template";
import imagemin from "imagemin";
import imageminZopfli from "imagemin-zopfli"
import imageminPngquant from "imagemin-pngquant"
import Jimp from "jimp"

const emojiData = await fs.readJson("./node_modules/emoji-datasource/emoji.json");

/**
 * The targets for generating. Extend these for adding more emoji variants.
 * @type {*[]} An Array of target-objects.
 */
const targets = [{
    package: "ios",
    module: "ios",
    name: "IosEmoji",
    dataSource: "apple",
    dataAttribute: "has_img_apple",
}, {
    package: "google",
    module: "google",
    name: "GoogleEmoji",
    dataSource: "google",
    dataAttribute: "has_img_google",
}, {
    package: "googlecompat",
    module: "google-compat",
    name: "GoogleCompatEmoji",
    dataSource: "google",
    dataAttribute: "has_img_google",
}, {
    package: "androidxemoji2",
    module: "androidx-emoji2",
    name: "AndroidxEmoji2",
    dataSource: "google",
    dataAttribute: "has_img_google",
}, {
    package: "twitter",
    module: "twitter",
    name: "TwitterEmoji",
    dataSource: "twitter",
    dataAttribute: "has_img_twitter",
}, {
    package: "facebook",
    module: "facebook",
    name: "FacebookEmoji",
    dataSource: "facebook",
    dataAttribute: "has_img_facebook",
}];

/**
 * Metadata about the categories.
 * @type {{name: string, i18n: [{{key: string, value: string}}]}[]}
 */
const categoryInfo = [
    {
      "name": "SmileysAndPeople",
      "i18n": [
        { "key": "en", "value": "Faces" },
        { "key": "de", "value": "Gesichter" }
      ]
    },
    {
      "name": "AnimalsAndNature",
      "i18n": [
        { "key": "en", "value": "Nature" },
        { "key": "de", "value": "Natur" }
      ]
    },
    {
      "name": "FoodAndDrink",
      "i18n": [
        { "key": "en", "value": "Food" },
        { "key": "de", "value": "Essen" }
      ]
    },
    {
      "name": "Activities",
      "i18n": [
        { "key": "en", "value": "Activities" },
        { "key": "de", "value": "Aktivitäten" }
      ]
    },
    {
      "name": "TravelAndPlaces",
      "i18n": [
        { "key": "en", "value": "Places" },
        { "key": "de", "value": "Orte" }
      ]
    },
    {
      "name": "Objects",
      "i18n": [
        { "key": "en", "value": "Objects" },
        { "key": "de", "value": "Objekte" }
      ]
    },
    {
      "name": "Symbols",
      "i18n": [
        { "key": "en", "value": "Symbols" },
        { "key": "de", "value": "Symbole" }
      ]
    },
    {
      "name": "Flags",
      "i18n": [
        { "key": "en", "value": "Flags" },
        { "key": "de", "value": "Flaggen" }
      ]
    },
];

/**
 * The amount of emojis to put in a chunk.
 * @type {number}
 */
const chunkSize = 100;

/**
 * Helper function to be used by {@link #copyImages} for copying (and optimizing) the images of a single target
 * to their destinations.
 * @param map The map.
 * @param target The target.
 * @param shouldOptimize If optimization should be performed.
 * @returns {Promise.<void>} Empty Promise.
 */
async function copyTargetImages(map, target, shouldOptimize) {
    await fs.emptyDir(`../emoji-${target.module}/src/androidMain/res/drawable-nodpi`);

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

    if (target.module !== "google-compat" && target.module !== "androidx-emoji2") {
        const src = `node_modules/emoji-datasource-${target.dataSource}/img/${target.dataSource}/sheets-clean/64.png`;
        const sheet = await Jimp.read(src);
        const strips = Math.max(...allEmoji.map(it => it.sheet_x)) + 1

        for (let i = 0; i < strips; i++) {
            const dest = `../emoji-${target.module}/src/androidMain/res/drawable-nodpi/emoji_${target.module}_sheet_${i}.png`;
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
    }

    for (const [category] of map) {
        const dest = `../emoji-${target.module}/src/androidMain/res/drawable-nodpi/emoji_${target.package}_category_${category.toLowerCase()}.png`

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
    const chunked = chunk(list, chunkSize)

    return chunked.map(chunk => chunk.join(`\n    `))
}

/**
 /**
 * Generates the code for a list of emoji with their variants if present.
 * @param target The target to generate for. It is checked if the target has support for the emoji before generating.
 * @param emojis The emojis.
 * @param indent The indent to use. Defaults to 4.
 * @returns {string[]} The list of generated code parts.
 */
function generateEmojiCode(target, emojis, indent = 4, isVariant = false) {
    let indentString = "";

    for (let i = 0; i < indent; i++) {
        indentString += " ";
    }

    return emojis.filter(it => it[target.package]).map((it) => {
        const unicodeParts = it.unicode.split("-");
        const hasVariants = it.variants.filter(it => it[target.package]).length > 0;
        const newLinePrefix = `\n${indentString}  `
        const separator = hasVariants ? newLinePrefix : ""
        const useNamedArguments = !isVariant && hasVariants
        const conditionalNewLinePrefix = useNamedArguments ? newLinePrefix : " "
        const transformedUnicodeParts = unicodeParts
            .map(unicodePart => parseInt(unicodePart, 16))
            .map(codePoint=> String.fromCodePoint(codePoint))
            .join('')
            .split('')
            .map(char => char.charCodeAt(0))
            .map(charCode => "\\u" + charCode.toString(16).padStart(4, "0"))
            .join('')
        const usesSprites = target.module !== "google-compat" && target.module !== "androidx-emoji2"

        const result = `${target.name}(${separator}` + [
            (useNamedArguments ? `unicode = ` : "") + `"${transformedUnicodeParts}"`,
            (useNamedArguments ? `${newLinePrefix}shortcodes = ` : " ") + `${generateShortcodeCode(it)}`,
            usesSprites ? ((useNamedArguments ? `${newLinePrefix}x = ` : " ") + `${it.x}`) : null,
            usesSprites ? ((useNamedArguments ? `${newLinePrefix}y = ` : " ") + `${it.y}`) : null
        ]
        .filter(Boolean)
        .join(',')

        if (hasVariants) {
            const generatedVariants = generateEmojiCode(target, it.variants, indent + 2, true).join(`\n${indentString}    `)
            return `${result},${newLinePrefix}variants = listOf(${newLinePrefix}  ${generatedVariants}${newLinePrefix}),\n${indentString}),`;
        } else {
            return `${result}),`;
        }
    })
}

function generateShortcodeCode(emoji) {
    if (!emoji.shortcodes || emoji.shortcodes.length === 0) {
        return 'emptyList()'
    } else {
        return `listOf("${emoji.shortcodes.join(`", "`)}")`
    }
}

/**
 * Parses the files and creates a map of categories to emojis, specified by the passed targets.
 * @returns {Promise.<Map>} Promise returning the map.
 */
async function parse() {
    console.log("Parsing files...");

    const result = new Map();
    const filteredEmojiData = emojiData.filter(it => it.category !== "Component");
    const preparedEmojiData = [...filteredEmojiData].sort((first, second) => first.sort_order - second.sort_order);

    for (const dataEntry of preparedEmojiData) {
        const category = dataEntry.category.replace(" & ", "And");

        const emoji = {
            unicode: dataEntry.unified,
            shortcodes: dataEntry.short_names,
            x: dataEntry.sheet_x,
            y: dataEntry.sheet_y,
            variants: [],
        };

        if (dataEntry.skin_variations) {
            for (const variantDataEntry of Object.values(dataEntry.skin_variations)) {
                const variantEmoji = {
                    unicode: variantDataEntry.unified,
                    x: variantDataEntry.sheet_x,
                    y: variantDataEntry.sheet_y,
                    variants: [],
                };

                for (const target of targets) {
                    if (variantDataEntry[target.dataAttribute] === true) {
                        variantEmoji[target.package] = true
                    }
                }

                emoji.variants.push(variantEmoji)
            }
        } else if (dataEntry.non_qualified) {
            // Sneaky, but we change it to get proper support for emojis with variant selectors.
            emoji.unicode = dataEntry.non_qualified

            const variantEmoji = {
                unicode: dataEntry.unified,
                x: dataEntry.sheet_x,
                y: dataEntry.sheet_y,
                variants: [],
            };

            for (const target of targets) {
                variantEmoji[target.package] = true
            }

            emoji.variants.push(variantEmoji)
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
    console.log("Generating code...");

    const emojiTemplate = await fs.readFile("template/Emoji.kt", "utf-8");
    const emojiGoogleCompatTemplate = await fs.readFile("template/EmojiCompat.kt", "utf-8");
    const emojiAndroidx2Template = await fs.readFile("template/EmojiAndroidx2.kt", "utf-8");
    const categoryTemplate = await fs.readFile("template/Category.kt", "utf-8");
    const categoryChunkTemplate = await fs.readFile("template/CategoryChunk.kt", "utf-8");
    const emojiProviderAndroid = await fs.readFile("template/EmojiProviderAndroid.kt", "utf-8");
    const emojiProviderGoogleCompatTemplate = await fs.readFile("template/EmojiProviderCompat.kt", "utf-8");
    const emojiProviderAndroidx2Template = await fs.readFile("template/EmojiProviderAndroidx2.kt", "utf-8");
    const emojiProviderJvm = await fs.readFile("template/EmojiProviderJvm.kt", "utf-8");

    const entries = [...map.entries()].sort((first, second) => {
        return categoryInfo.findIndex(it => it.name === first[0]) - categoryInfo.findIndex(it => it.name === second[0]);
    });

    for (const target of targets) {
        const srcDir = `../emoji-${target.module}/src/androidMain/kotlin/com/vanniktech/emoji/${target.package}`;
        const commonSrcDir = `../emoji-${target.module}/src/commonMain/kotlin/com/vanniktech/emoji/${target.package}`;
        const jvmSrcDir = `../emoji-${target.module}/src/jvmMain/kotlin/com/vanniktech/emoji/${target.package}`;

        const isGoogleCompat = target.module === "google-compat"
        const isAndroidxEmoji2 = target.module === "androidx-emoji2"

        if (isGoogleCompat || isAndroidxEmoji2) {
            await fs.emptyDir(`${commonSrcDir}/category`)
        } else {
            await fs.emptyDir(commonSrcDir);
            await fs.mkdir(`${commonSrcDir}/category`);
        }

        await fs.emptyDir(jvmSrcDir);

        let strips = 0;
        for (const [category, emojis] of entries) {
            emojis.forEach(emoji => strips = Math.max(strips, emoji.x + 1));

            const dataChunks = generateChunkedEmojiCode(target, emojis);
            const chunkClasses = [];

            for (let index = 0; index < dataChunks.length; index++) {
                const chunk = dataChunks[index];
                const chunkClass = `${category}CategoryChunk${index}`

                chunkClasses.push(chunkClass)

                await fs.writeFile(`${commonSrcDir}/category/${chunkClass}.kt`,
                    template(categoryChunkTemplate)({
                        package: target.package,
                        name: target.name,
                        category: category,
                        index: index,
                        data: chunk,
                    }),
                );
            }

            await fs.writeFile(`${commonSrcDir}/category/${category}Category.kt`,
                template(categoryTemplate)({
                    package: target.package,
                    name: target.name,
                    category: category,
                    chunks: chunkClasses.map(it => `${it}.EMOJIS`).join(" + "),
                    categoryNames: categoryInfo.filter(it => it.name == category).flatMap(category => category.i18n.map(it => Object.assign({}, {key: it.key, value: it.value}))),
                }),
            );
        }

        const imports = [...map.keys()].sort().map((category) => {
            return `import com.vanniktech.emoji.${target.package}.category.${category}Category`
        }).join("\n");

        const categories = entries.map(entry => {
            const [category] = entry;

            return Object.assign({}, {name: `${category}Category`, icon: category.toLowerCase()})
        })

        if (isGoogleCompat) {
            await fs.writeFile(`${srcDir}/${target.name}Provider.kt`, template(emojiProviderGoogleCompatTemplate)({
                package: target.package,
                imports: imports,
                name: target.name,
                categories: categories,
            }));
        } else if (isAndroidxEmoji2) {
            await fs.writeFile(`${srcDir}/${target.name}Provider.kt`, template(emojiProviderAndroidx2Template)({
                package: target.package,
                imports: imports,
                name: target.name,
                categories: categories,
            }));
        } else {
            await fs.writeFile(`${srcDir}/${target.name}Provider.kt`, template(emojiProviderAndroid)({
                package: target.package,
                imports: imports,
                name: target.name,
                categories: categories,
                strips: strips,
            }));
        }

        await fs.writeFile(`${jvmSrcDir}/${target.name}Provider.kt`, template(emojiProviderJvm)({
            package: target.package,
            imports: imports,
            name: target.name,
            categories: categories,
        }));

        if (isGoogleCompat) {
            await fs.writeFile(`${commonSrcDir}/${target.name}.kt`, template(emojiGoogleCompatTemplate)({
                package: target.package,
                name: target.name,
            }));
        } else if (isAndroidxEmoji2) {
            await fs.writeFile(`${commonSrcDir}/${target.name}.kt`, template(emojiAndroidx2Template)({
                package: target.package,
                name: target.name,
            }));
        } else {
            await fs.writeFile(`${commonSrcDir}/${target.name}.kt`, template(emojiTemplate)({
                package: target.package,
                name: target.name,
            }));
        }
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
