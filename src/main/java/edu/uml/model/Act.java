package edu.uml.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "acts")
public class Act {
    @EmbeddedId
    private ActId id;

    @ManyToOne
    @MapsId("actorId")
    @JoinColumn(name = "actor_id", nullable = false)
    private Actor actor;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(length = 100)
    private String role;

    public ActId getId() {
        return id;
    }

    public void setId(ActId id) {
        this.id = id;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Embeddable
    public static class ActId implements Serializable {
        private Integer actorId;
        private Integer movieId;

        public ActId() {
        }

        public ActId(Integer actorId, Integer movieId) {
            this.actorId = actorId;
            this.movieId = movieId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ActId actId = (ActId) o;
            return Objects.equals(actorId, actId.actorId) && Objects.equals(
                movieId, actId.movieId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(actorId, movieId);
        }
    }
}
