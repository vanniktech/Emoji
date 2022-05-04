# Updating

This library features a NodeJS script for updating the Emoji images and the generated Java code.

## Contents of the script

The script does three things:

- Parsing and organizing the meta data.
- Copy (and optimize) the images to the respective locations.
- Generate the Java code.

## Prerequisites

[NodeJS 8](https://nodejs.org)<br>
[Npm](https://www.npmjs.com/) or [Yarn](https://yarnpkg.com/)

## Running the script

Before running the script, you need to install the required dependencies.

```bash
cd generator
npm install
```

or

```bash
yarn
```

After that you can start the script with:

```bash
npm start
```

or

```bash
yarn start
```

## Parameters

Not all steps are always required. If you want to skip the time-consuming optimization for example, you could pass `--no-optimize`. The following parameters are available:

```bash
--no-optimize
```

Does not optimize the images.

```bash
--no-copy
```

Does not copy the images and also implicitly not optimize them.

```bash
--no-generate
```

Does not regenerate the code.

### Running with parameters

Parameters are passed to the script like this:

```bash
npm start -- --no-optimize --no-copy
```

or

```bash
yarn start --no-optimize --no-copy
```
