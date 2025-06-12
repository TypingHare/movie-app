package edu.uml.movie_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MovieActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        final var intent = getIntent();
        final var movieDto = intent.getParcelableExtra("movieDto", MovieDto.class);
        assert movieDto != null;

        TextView tvMovieTitle = findViewById(R.id.movieTitle);
        TextView tvLength = findViewById(R.id.movieLength);
        tvMovieTitle.setText(movieDto.title);
        tvLength.setText(movieDto.length + " min");

        Button button = findViewById(R.id.movieButton);
        button.setOnClickListener(view -> {
            startActivity(new Intent(this, MovieQueryActivity.class));
        });
    }
}
