package edu.uml.controller;

import edu.uml.model.Movie;
import edu.uml.service.MovieService;
import edu.uml.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public Response<List<Movie>> getMovies() {
        return Response.success(
            "Retrieved all movies.",
            movieService.getMovies()
        );
    }

    /**
     * This must be written before `getMoviesByActor`, otherwise a 405 HTTP
     * status code would be returned. Why?
     */
    @PutMapping(
        value = "/{movieId}",
        consumes = "application/x-www-form-urlencoded;charset=UTF-8"
    )
    public Response<Object> updateMovieById(
        @PathVariable("movieId") final Long movieId,
        @RequestParam(value = "length", required = false) Integer length,
        @RequestParam(value = "genre", required = false) String genre
    ) {
        final var movie = new Movie();
        movie.setId(movieId);

        if (length != null) movie.setLength(length);
        if (genre != null) movie.setGenre(genre);

        try {
            movieService.updateMovie(movie);
            return Response.success(
                String.format("Updated movie with ID %d.", movieId));
        } catch (Exception ex) {
            return Response.fail(
                String.format("Failed to update movie with ID %d.", movieId)
            );
        }
    }

    @GetMapping("/{actor}")
    public Response<List<Movie>> getMoviesByActor(
        @PathVariable("actor") final String actorName
    ) {
        final var movieList = movieService.getMoviesByActor(actorName);

        if (movieList.isEmpty()) {
            return Response.fail(
                String.format("No movies found for the actor %s.", actorName)
            );
        }

        return Response.success(
            "Retrieved all movies where the actor is in.",
            movieList
        );
    }
}
