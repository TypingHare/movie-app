# [Movie App](https://github.com/TypingHare/movie-app)

> [!NOTE]
> This branch is the backend (server) of Movie. For the Android app (client) see the [android branch](https://github.com/TypingHare/movie-app/tree/android).

## Installation

[Node.js](https://nodejs.org/en/download/), a popular Common JavaScript runtime environment, is required to run this project. To install Node.js, please check out the following links:

- [Download Node.js](https://nodejs.org/en/download)
- [Downloading and installing Node.js and npm](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)

The Node.js package manager (npm) is included with Node.js. To verify that Noode.js and npm are installed correctly, run the following commands in your terminal:

```bash
$ node --version
$ npm --version
```

> [!TIP]
> If you are using macOS, I highly recommend you using [Homebrew](https://brew.sh/) to install Node.js. Also see [Node.js formula](https://formulae.brew.sh/formula/node).

## Install

Use git to clone this repository to a proper directory on your computer:

```bash
$ git clone git@github.com:TypingHare/movie-app.git
```

Then, navigate to the `movie-app` directory:

```bash
$ cd movie-app
```

Next, install the dependencies using npm:

```bash
$ npm install
```

> [!TIP]
> Node.js is just a JavaScript runtime environment. It does not include any libraries or frameworks. Fortunately, it has a vast community ecosystem, and you can check the dependencies on [npmjs](https://www.npmjs.com).
>
> The `package.json` file in the root directory contains the information about this project that is managed by npm. It includes the dependencies, scripts, and other metadata about the project. When you run `npm install`, npm reads the `package.json` file and installs the dependencies (both `dependencies` and `devDependencies`) listed in it.

## Dependencies

We are going to use the following dependencies in this project. You can also add more.

- [express](https://www.npmjs.com/package/express): A popular and lightweight backend framework for handling HTTP requests.
- [mysql2](https://www.npmjs.com/package/mysql2): A popular library for connecting to MySQL database.
- [chalk](https://www.npmjs.com/package/chalk): A library that color strings in the terminal.

## Database

You need to set up a MySQL database to run this project. You can use any MySQL clients.

- On macOS, I recommend using Homebrew to install [MySQL 8](https://formulae.brew.sh/formula/mysql).
- On Windows, you can use [XAMPP](https://www.apachefriends.org/index.html).
- If you are using Linux, you should be able to install it on your own. Good luck, Linux bro.

After installing MySQL, import the `sql/movie.sql` file into your MySQL database. This file contains the schema and sample data for the Movie app.

## Run

After installing the dependencies, you can start the project using the following command:

```bash
$ npm start
```

This will run the script in the `package.json` (See the `scripts` section). Basically, it would run the following command:

```bash
$ node src/app.js
```

> [!IMPORTANT]
> Please read the JavaScript files in `src` carefully in the following order:
>
> - [src/config.js](src/config.js)
> - [src/database.js](src/database.js)
> - [src/app.js](src/app.js)
> - [src/response.js](src/response.js)
