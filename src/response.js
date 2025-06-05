/**
 * Creates a standardized success response object.
 *
 * @param {string} message - The message to include in the response.
 * @param {any} [data] - Optional data to include in the response.
 * @return {{ success: false, message: string, data: any }}
 * @example
 *
 *   {
 *     "success": true,
 *     "message": "This is a success message.",
 *     "data": {
 *       "foo": "bar"
 *     }
 *   }
 */
export function success(message, data = null) {
    return { success: true, message, data }
}

/**
 * Creates a standardized error response object.
 *
 * @param {string} message - The message to include in the response.
 * @param {any} [data] - Optional data to include in the response.
 * @return {{ success: false, message: string, data: any }}
 * @example
 *
 *   {
 *     "success": false,
 *     "message": "This is an error message.",
 *   }
 */
export function error(message, data = null) {
    return { success: false, message, data }
}
