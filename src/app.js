import express from 'express'
import chalk from 'chalk'
import { config } from './config.js'
import { query } from './database.js'
import { error, success } from './response.js'

/**
 * Create an Express application instance.
 *
 * @link https://expressjs.com/en/starter/hello-world.html
 * @link https://www.w3schools.com/whatis/whatis_http.asp
 */
export const app = express()

/**
 * Register a middleware that accepts `x-www-form-urlencoded` data in the HTTP
 * request body.
 */
app.use(express.urlencoded({ extended: true }))

/**
 * Register a middleware that logs the request method and URL to the console.
 * This middleware is called for every incoming request, which is useful for
 * debugging and monitoring purposes.
 *
 * NOTE: Logging is very important in software development. You may want to
 * develop a more robust and informative logging system in the future.
 *
 * @link https://expressjs.com/en/guide/using-middleware.html
 */
app.use((req, _, next) => {
    // Log the request method and URL to the console
    const methodStr = chalk.green(`[${req.method}]`.padEnd(8, ' '))
    const urlStr = chalk.cyan(req.url.padEnd(30, ' '))
    console.log(`RECEViED: ${methodStr} ${urlStr}`)

    // Call the next middleware in the stack
    next()
})

/**
 * Define a route that handles GET requests to the `/movies` endpoint.
 *
 * This serves as an endpoint that handles the GET request to `/movies`. When
 * Express receives a request, it looks for a matching route. For example,
 * if the request is a GET request to `/movies`, it will fire the callback
 * function below.
 *
 * The [res.send] sets the body of the response. If the argument is an object
 * (Note that in JavaScript, arrays are also objects), Express will convert it
 * into JSON format before sending it to the client.
 *
 * The HTTP status code is set to 200, which means the request was successful.
 *
 * [NOTE] In programming, "firing" a function literally means calling it. It is
 * often used in the context of event handling or callbacks. A callback function
 * refers to a function that is passed as an argument to another function and
 * is meant to be called later. It is often simplified to "callback", and "call
 * the callback" seems to be repetitious, so people often say "fire the
 * callback" instead.
 *
 * @example The [rows] should look like this:
 *
 *   [
 *     {
 *       "id": 1,
 *       "title": "Gone With the Wind",
 *       "year": 1939,
 *       "length": 231,
 *       "genre": "drama"
 *     },
 *     {
 *       "id": 2,
 *       "title": "Star Wars",
 *       "year": 1977,
 *       "length": 124,
 *       "genre": "sciFi"
 *     }
 *   ]
 *
 * @link https://www.geeksforgeeks.org/javascript-callbacks
 * @link https://expressjs.com/en/starter/basic-routing.html
 * @link https://www.w3schools.com/whatis/whatis_json.asp
 * @link https://www.geeksforgeeks.org/what-are-http-status-codes/
 */
app.get('/movies', async (_, res) => {
    // [rows] contain all the resulting records from the database
    const rows = await query('SELECT * FROM movies')

    // Set the response code to 200 and send the resulting records to the client
    res.status(200).send(success('Retrieved all movies.', rows))
})

/**
 * Retrieves a movie by its year.
 *
 * This endpoint handles GET requests to `/movie?year=YYYY`, where `YYYY` is the
 * year of the movie you want to retrieve. It queries the database for a movie
 * with the specified year and returns it if found. If no movie is found for
 * the specified year, it returns a 404 Not Found error.
 *
 * NOTE: The URL this endpoint matches is `/movie`, which does not comply with
 * RESTful conventions. Check out the following link for more information about
 * RESTful API design:
 *
 * @link https://www.geeksforgeeks.org/node-js/rest-api-introduction/
 */
app.get('/movie', async (req, res) => {
    const year = req.query.year
    const rows = await query(
        `
            SELECT * FROM movies
            WHERE year = ?
            LIMIT 1
        `,
        [year]
    )

    if (rows.length > 0) {
        res.send(success(`Retrieved movie in ${year}.`, rows[0]))
    } else {
        res.send(error(`No movies found for the year ${year}.`))
    }
})

/**
 * Retrieves all movies where an actor is in.
 *
 * This endpoint handles GET requests to `/movie/:actor`, where `:actor` is a
 * placeholder for the name of the actor. For instance, the following URL would
 * match this route:
 *
 *     /movie/Vivien%20Leigh
 *
 * Here, `%20` represents a space character in the URL.
 *
 * The `req.params` object contains the parameters from the URL. In this case,
 * `req.params.actor` will contain the name of the actor.
 */
app.get('/movies/:actor', async (req, res) => {
    const actor = req.params.actor
    const rows = await query(
        `
            SELECT movies.* FROM movies
            JOIN acts ON acts.movie_id = movies.id
            JOIN actors ON acts.actor_id = actors.id
            WHERE actors.name = ?
        `,
        [actor]
    )

    if (rows.length > 0) {
        res.send(success('Retrieved all movies where an actor is in.', rows))
    } else {
        res.send(error(`No movies are found with the actor: ${actor}.`))
    }
})

/**
 * Updates a movie's information.
 *
 * This endpoint handles PUT requests to `/movies/:id`, where the request body
 * is a JSON object containing the movie's length and genre. It updates the
 * length and genre of the movie with the specified ID in the database.
 *
 * NOTE: In RESTful API design, the PUT method is used to update an existing
 * resource.
 *
 * NOTE: Unlike the GET method, where the parameters are passed in the URL,
 * the PUT method (and other methods other than GET) typically sends the
 * parameters in the request body (especially credential data).
 *
 * @link https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Overview
 */
app.put('/movies/:id', async (req, res) => {
    const id = req.params.id
    const { length, genre } = req.body

    // Update the movie in the database
    try {
        await query(
            `
            UPDATE movies
            SET length = ?, genre = ?
            WHERE id = ?
        `,
            [length, genre, id]
        )

        // Send a success response
        res.send(success(`Updated movie with ID ${id}.`))
    } catch (error) {
        const message = error.message
        res.send(success(`Failed to update movie with ID ${id}: ${message}`))
    }
})

/**
 * Define a middleware that handles the case where the requested resource is not
 * found (in other words, the route the matches the request URL and and HTTP
 * method is not defined).
 *
 * This middleware is defined after all the routes, so it will only be called
 * if no other route matches the request. If a route matches, the middleware
 * will not be called.
 */
app.use((_, res) => {
    res.status(404).send(error('Resource Not Found.'))
})

/**
 * Let the Express application listen for incoming requests. Here's how it works
 * behind the scenes:
 *
 * 1. The Express application occupies a port on the local machine. In this
 *    example, it occupies the port 3000. You can change the port in the
 *    configuration file. The common port for HTTP servers is 80. When you
 *    access a page in your browser using HTTP, the browser will use port 80.
 *    If you want to use a different port in the browser, you need to specify
 *    the port number in the URL:
 *
 *        http://localhost:3000/movies
 *
 * 2. When the Express application receives an HTTP request, it will parse it
 *    and extract the URL and the HTTP method (GET, POST, etc.). It will then
 *    match the URL and the HTTP method with the routes pre-defined.
 *
 * 3. If a match is found, it will fire the callback function associated.
 *    Otherwise, it will return a 404 Not Found error.
 */
app.listen(config.server.port, () => {
    console.log(`Server is running on ${config.server.port}...`)
    console.log(`Press Ctrl+C to stop.`)
})
