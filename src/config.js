/**
 * Represents the configuration of the backend. It is a good practice to store
 * all the configurations in a separate file, just like this one.
 *
 * In ES6, you can export a variable using the keyword `export`. Then, you can
 * import it in another file using the keyword `import`.
 *
 * @link https://www.geeksforgeeks.org/es6-modules/
 */
export const config = {
    // Database configuration
    db: {
        host: 'localhost',
        user: 'root',
        password: '',
        database: 'movie_test',
    },

    // Server configuration
    server: {
        port: 3000,
    },
}
