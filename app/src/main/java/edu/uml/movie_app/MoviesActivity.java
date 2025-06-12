package edu.uml.movie_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

/**
 * Activity to display a list of movies using a RecyclerView.
 */
public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        final var movieDtoArray =
            getIntent().getParcelableArrayExtra("movieDtoArray", MovieDto.class);
        assert movieDtoArray != null;
        final var movieDtoList = Arrays.asList(movieDtoArray);

        RecyclerView recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));

        MovieAdapter movieAdapter = new MovieAdapter(movieDtoList);
        recyclerViewMovies.setAdapter(movieAdapter);
    }
}