# Updating

This library features a NodeJS script for updating the Emoji images and the generated Java code.

## Contents of the script

The script does five things:

- Downloading the required files
- Extract the images and meta info
- Optimize the images
- Copy the images to the respective locations
- Generate the Java code

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

Not all steps are always required. If the files were already downloaded for example, you would not need to re-download them. The following parameters are available:

```
--no-download
```

Skips the download.

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
npm start -- --no-download --no-copy
```

or

```
yarn start -- --no-download --no-copy
```
