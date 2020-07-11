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

Before running the script, you need to install the required dependencies. Navigate into the `generator` folder and run:

```
npm install
```

or

```
yarn
```

After that you can start the script with:

```
npm start
```

or

```
yarn start
```

## Parameters

Not all steps are always required. If you want to skip the time-consuming optimization for example, you could pass `--no-optimize`. The following parameters are available:

```
--no-optimize
```

Does not optimize the images.

```
--no-copy
```

Does not copy the images and also implicitly not optimize them.

```
--no-generate
```

Does not regenerate the Java code.

### Running with parameters

Parameters are passed to the script like this:

```
npm start -- --no-optimize --no-copy
```

or

```
yarn start --no-optimize --no-copy
```
