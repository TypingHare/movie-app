package edu.uml.movie_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<MovieDto> movies;

    public MovieAdapter(List<MovieDto> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieDto movie = movies.get(position);
        holder.tvMovieTitle.setText(movie.title != null ? movie.title : "");
        holder.tvMovieYear.setText(movie.year != null ? "Year: " + movie.year : "Year: N/A");
        holder.tvMovieLength.setText(movie.length != null ? "Length: " + movie.length + " min" : "Length: N/A");
        holder.tvMovieGenre.setText(movie.genre != null ? "Genre: " + movie.genre : "Genre: N/A");
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvMovieYear, tvMovieLength, tvMovieGenre;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.movieTitle);
            tvMovieYear = itemView.findViewById(R.id.tvMovieYear);
            tvMovieLength = itemView.findViewById(R.id.tvMovieLength);
            tvMovieGenre = itemView.findViewById(R.id.tvMovieGenre);
        }
    }
}