package edu.uml.movie_app;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class QueryRequest extends StringRequest {
    private static final String TAG = "QueryRequest";

    public QueryRequest(
        final Map<String, ?> params,
        final String baseUrl,
        final Response.Listener<String> listener
    ) {
        super(
            Method.GET,
            buildUrl(baseUrl, params),
            listener,
            error -> Log.e(TAG, "Volley error", error)
        );
    }

    private static String buildUrl(String baseUrl, Map<String, ?> params) {
        Uri.Builder builder = Uri.parse(baseUrl).buildUpon();
        for (final var entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue().toString());
        }

        return builder.build().toString();
    }
}
