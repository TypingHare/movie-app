package edu.uml.movie_app;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class QueryRequest extends StringRequest {
    private static final String TAG = "QueryRequest";

    private final Integer method;
    private final Map<String, String> params = new HashMap<>();

    public QueryRequest(
        final Map<String, ?> params,
        final Integer method,
        final String baseUrl,
        final Response.Listener<String> listener
    ) {
        super(
            method,
            method == Method.GET ? buildUrl(baseUrl, params) : baseUrl,
            listener,
            error -> Log.e(TAG, "Volley error", error)
        );

        this.method = method;
        for (Map.Entry<String, ?> entry : params.entrySet()) {
            this.params.put(entry.getKey(), entry.getValue().toString());
        }
    }

    private static String buildUrl(String baseUrl, Map<String, ?> params) {
        Uri.Builder builder = Uri.parse(baseUrl).buildUpon();
        for (final var entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue().toString());
        }

        return builder.build().toString();
    }

    @Override
    protected Map<String, String> getParams() {
        return method == Method.GET ? Map.of() : params;
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }
}
