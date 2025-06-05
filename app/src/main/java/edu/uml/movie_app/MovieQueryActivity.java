package edu.uml.movie_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

public class MovieQueryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        final Button button = findViewById(R.id.submitQuery);
        button.setOnClickListener(view -> {
            final EditText yearEditText = findViewById(R.id.year);
            try {
                final int year = Integer.parseInt(yearEditText.getText().toString());
                sendQueryRequest(year);
            } catch (NumberFormatException ignore) {
                new AlertDialog.Builder(MovieQueryActivity.this)
                    .setMessage("Please input a year (YYYY).")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            }
        });
    }

    private void sendQueryRequest(final int year) {
        Response.Listener<String> responseListener = response -> {
            try {
                Log.d("RESPONSE", response);
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");

                if (!success) {
                    new AlertDialog.Builder(MovieQueryActivity.this)
                        .setMessage(jsonObject.getString("message"))
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();
                }

                final var data = jsonObject.getJSONObject("data");
                Intent intent = new Intent(MovieQueryActivity.this, MovieActivity.class);
                intent.putExtra("title", data.getString("title"));
                intent.putExtra("year", data.getInt("year"));
                intent.putExtra("length", data.getInt("length"));
                intent.putExtra("genre", data.getString("genre"));

                MovieQueryActivity.this.startActivity(intent);
            } catch (JSONException ex) {
                Log.e("JSON_PARSE", Objects.requireNonNull(ex.getMessage()));
            }
        };

        final var requestParams = Map.of("year", year);
        final var url = getString(R.string.base_url) + "/movie";

        QueryRequest queryRequest = new QueryRequest(requestParams, url, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MovieQueryActivity.this);
        queue.add(queryRequest);
    }
}
