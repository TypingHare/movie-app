package edu.uml.utils;

@SuppressWarnings("FieldCanBeLocal")
public record Response<T>(Boolean success, String message, T data) {
    public static <T> Response<T> success(final String message, final T data) {
        return new Response<>(true, message, data);
    }

    public static <T> Response<T> success(final String message) {
        return new Response<>(true, message, null);
    }

    public static <T> Response<T> fail(final String message, final T data) {
        return new Response<>(false, message, data);
    }

    public static <T> Response<T> fail(final String message) {
        return new Response<>(false, message, null);
    }
}
