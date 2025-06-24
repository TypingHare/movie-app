package edu.uml.movie_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class MovieQueryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        final Button getMovieByYearButton = findViewById(R.id.getMovieByYearButton);
        getMovieByYearButton.setOnClickListener(view -> {
            final EditText yearEditText = findViewById(R.id.year);
            try {
                final int year = Integer.parseInt(yearEditText.getText().toString());
                getMovieByYear(year);
            } catch (NumberFormatException ignore) {
                new AlertDialog.Builder(MovieQueryActivity.this).setMessage(
                    "Please input a year (YYYY).").setNegativeButton("Retry", null).create().show();
            }
        });

        final Button getMoviesByAuthorButton = findViewById(R.id.getMoviesByAuthorButton);
        getMoviesByAuthorButton.setOnClickListener(view -> {
            final EditText actorEditText = findViewById(R.id.actor);
            final var actor = actorEditText.getText().toString();
            getMoviesByActor(actor);
        });

        final Button updateMovieLengthButton = findViewById(R.id.updateMovieLengthButton);
        updateMovieLengthButton.setOnClickListener(view -> {
            final EditText movieLengthEditText = findViewById(R.id.movieLength);
            try {
                final int length = Integer.parseInt(movieLengthEditText.getText().toString());
                updateMovieLength(2, length);
            } catch (NumberFormatException ignore) {
                new AlertDialog.Builder(MovieQueryActivity.this).setMessage(
                    "Please input an integer.").setNegativeButton("Retry", null).create().show();
            }
        });
    }

    private void getMovieByYear(final int year) {
        Response.Listener<String> responseListener = response -> {
            final var data = (JSONObject) parseJsonObjectAndGetData(response, false);
            if (data == null) {
                return;
            }

            final var movieDto = parseMovieRecordDto(data);
            final var intent = new Intent(MovieQueryActivity.this, MovieActivity.class);
            intent.putExtra("movieDto", movieDto);
            MovieQueryActivity.this.startActivity(intent);
        };

        final var requestParams = Map.of("year", year);
        final var url = getString(R.string.base_url) + "/movie";

        QueryRequest queryRequest =
            new QueryRequest(requestParams, Request.Method.GET, url, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MovieQueryActivity.this);
        queue.add(queryRequest);
    }

    private void getMoviesByActor(final String actor) {
        Response.Listener<String> responseListener = response -> {
            final var data = (JSONArray) parseJsonObjectAndGetData(response, true);
            final MovieDto[] movieDtoArray =
                IntStream.range(0, data.length()).mapToObj(data::optJSONObject)
                    .filter(Objects::nonNull).map(this::parseMovieRecordDto)
                    .toArray(MovieDto[]::new);
            final var intent = new Intent(MovieQueryActivity.this, MoviesActivity.class);
            intent.putExtra("movieDtoArray", movieDtoArray);
            MovieQueryActivity.this.startActivity(intent);
        };

        final var url = getString(R.string.base_url) + "/movies/" + actor.replaceAll(" ", "%20");
        sendQueryRequest(new QueryRequest(Map.of(), Request.Method.GET, url, responseListener));
    }

    /**
     * @noinspection SameParameterValue
     */
    private void updateMovieLength(final Integer movieId, final Integer length) {
        Response.Listener<String> responseListener = response -> {
            new AlertDialog.Builder(MovieQueryActivity.this).setMessage("Updated movie length.")
                .setNegativeButton("Ok", null).create().show();
        };

        final var url = getString(R.string.base_url) + "/movies/" + movieId.toString();
        sendQueryRequest(
            new QueryRequest(Map.of("length", length), Request.Method.PUT, url, responseListener));
    }

    /**
     * Parse the response string into a JSON object and checks the "success" field. If it is true,
     * returns the value associated with the "data" key; otherwise, display an alert dialog that
     * shows the error message.
     *
     * @param response A JSON string to parse.
     * @return A JSON object (or array) or null.
     */
    private <T> T parseJsonObjectAndGetData(final String response, final Boolean isArray) {
        try {
            Log.d("RESPONSE", response);

            JSONObject jsonObject = new JSONObject(response);
            boolean success = jsonObject.getBoolean("success");
            if (!success) {
                new AlertDialog.Builder(MovieQueryActivity.this).setMessage(
                        jsonObject.getString("message")).setNegativeButton("Retry", null).create()
                    .show();
                return null;
            }

            //noinspection unchecked
            return (T) (isArray ? jsonObject.getJSONArray("data") :
                jsonObject.getJSONObject("data"));
        } catch (JSONException ex) {
            Log.e("JSON_PARSE", Objects.requireNonNull(ex.getMessage()));
            throw new RuntimeException("Failed to parse JSON", ex);
        }
    }

    /**
     * Creates a Volley request queue and send the query request to the server.
     *
     * @param queryRequest The query request to send.
     */
    private void sendQueryRequest(final QueryRequest queryRequest) {
        RequestQueue queue = Volley.newRequestQueue(MovieQueryActivity.this);
        queue.add(queryRequest);
    }

    private MovieDto parseMovieRecordDto(JSONObject data) {
        final var dto = new MovieDto();

        try {
            if (data.has("id")) dto.id = data.getInt("id");
            if (data.has("title")) dto.title = data.getString("title");
            if (data.has("year")) dto.year = data.getInt("year");
            if (data.has("length")) dto.length = data.getInt("length");
            if (data.has("genre")) dto.genre = data.getString("genre");
        } catch (JSONException ex) {
            Log.e("JSON_PARSE", Objects.requireNonNull(ex.getMessage()));
        }

        return dto;
    }
}
