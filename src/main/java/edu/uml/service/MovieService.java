package edu.uml.service;

import edu.uml.model.Movie;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.intellij.lang.annotations.Language;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    public final EntityManager entityManager;

    public MovieService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<Movie> getMovies() {
        @Language("SQL") final String sql = """
                SELECT * FROM movies
            """;

        return (List<Movie>) entityManager
            .createNativeQuery(sql, Movie.class)
            .getResultList();
    }

    @SuppressWarnings("unchecked")
    public Movie getMovieByYear(final Integer year) {
        @Language("SQL") final String sql = """
                SELECT * FROM movies
                WHERE year = :year
                LIMIT 1
            """;

        final var list = (List<Movie>) entityManager
            .createNativeQuery(sql, Movie.class)
            .setParameter("year", year)
            .getResultList();

        return list.isEmpty() ? null : list.getFirst();
    }

    @SuppressWarnings("unchecked")
    public List<Movie> getMoviesByActor(final String actorName) {
        @Language("SQL") final String sql = """
                SELECT movies.* FROM movies
                JOIN acts ON acts.movie_id = movies.id
                JOIN actors ON acts.actor_id = actors.id
                WHERE actors.name = :actorName
            """;

        return (List<Movie>) entityManager
            .createNativeQuery(sql, Movie.class)
            .setParameter("actorName", actorName)
            .getResultList();
    }

    @Transactional
    public void updateMovie(final Movie movie) {
        @Language("SQL") final String sql = """
                UPDATE movies
                SET length = :length, genre = :genre
                WHERE id = :id
            """;

        entityManager
            .createNativeQuery(sql, Movie.class)
            .setParameter("length", movie.getLength())
            .setParameter("genre", movie.getGenre())
            .setParameter("id", movie.getId())
            .executeUpdate();
    }
}
