package edu.uml.controller;

import edu.uml.model.Movie;
import edu.uml.service.MovieService;
import edu.uml.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class IncorrectMovieController {
    private final MovieService movieService;

    @Autowired
    public IncorrectMovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping()
    public Response<Movie> getMovieByYear(
        @RequestParam("year") Integer year
    ) {
        final var movie = movieService.getMovieByYear(year);

        if (movie == null) {
            return Response.fail(
                String.format("No movies found for the year %d.", year)
            );
        }

        return Response.success(
            String.format("Retrieved movie in %d.", year),
            movie
        );
    }
}
