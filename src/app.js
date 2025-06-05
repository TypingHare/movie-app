import express from 'express'
import { config } from './config.js'
import { query } from './database.js'
import chalk from 'chalk'

/**
 * Create an Express application instance.
 *
 * @link https://expressjs.com/en/starter/hello-world.html
 * @link https://www.w3schools.com/whatis/whatis_http.asp
 */
export const app = express()

/**
 * Register a middleware that logs the request method and URL to the console.
 * This middleware is called for every incoming request, which is useful for
 * debugging and monitoring purposes.
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
 * This serves an endpoint that handles the GET request to `/movies`. When
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
    res.status(200).send(rows)
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
app.use((req, res) => {
    res.status(404).send({
        error: 'Resource Not Found.',
        url: req.url,
    })
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
