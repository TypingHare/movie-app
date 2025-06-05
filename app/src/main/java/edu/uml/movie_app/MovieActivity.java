package edu.uml.movie_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MovieActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        final var intent = getIntent();
        final var movieTitle = intent.getStringExtra("title");
        final var length = intent.getIntExtra("length", -1);

        TextView tvMovieTitle = findViewById(R.id.tvMovieTitle);
        TextView tvLength = findViewById(R.id.tvLength);
        tvMovieTitle.setText(movieTitle);
        tvLength.setText(length + " min");
    }
}
