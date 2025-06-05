/**
 * Utility functions for creating standardized success response objects.
 *
 * @param {string} message - The message to include in the response.
 * @param {any} [data] - Optional data to include in the response.
 */
export function success(message, data = null) {
    return { success: true, message, data }
}

/**
 * Utility functions for creating standardized error response objects.
 *
 * @param {string} message - The message to include in the response.
 * @param {any} [data] - Optional data to include in the response.
 */
export function error(message, data = null) {
    return { success: false, message, data }
}
