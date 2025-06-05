import mysql from 'mysql2/promise'
import { config } from './config.js'

/**
 * Initialize a connection between the backend and the MySQL database.
 */

/**
 * Connect to the database. Here, the [connect] function accepts a callback
 * function, which is called after the connection succeeds or fails.
 *
 * The callback function accepts one parameter, which represents the error that
 * encountered during the connection process. If no error is encountered, it
 * would be [null].
 *
 * If any error is caught, it prints the error message on the console, and
 * exit the program using [process.exit].
 *
 * @returns {Promise<mysql.Connection>}
 */
async function connectToDatabase() {
    let connection
    try {
        connection = await mysql.createConnection({
            host: config.db.host,
            user: config.db.user,
            password: config.db.password,
            database: config.db.database,
        })
    } catch (error) {
        console.error(`Error connecting: ${error.stack}`)
        process.exit(1)
    }

    console.log(
        `Connected to the database: ${config.db.host}@${config.db.database}.`
    )

    return connection
}

/**
 * The connection instance to the database. This project will use this instance
 * throughout the application lifecycle.
 *
 * @type {mysql.Connection}
 */
export const connection = await connectToDatabase()

/**
 * Executes a SQL query on the database.
 *
 * @param {string} sql - The SQL query to execute.
 * @param {Array} params - The parameters to bind to the query.
 * @return {Promise<Array>} - A promise that resolves to an array containing
 *     the results and fields of the query.
 */
export async function query(sql, params = []) {
    const [results] = await connection.query(sql, params)
    return results
}
