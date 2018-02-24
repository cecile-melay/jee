package org.glassfish.movieplex7.client;

import javax.annotation.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

import org.glassfish.movieplex7.entities.*;
import org.glassfish.movieplex7.json.MovieWriter;

@Named
@RequestScoped
public class MovieClientBean {
	Client client;
	WebTarget target;

	@Inject
	MovieBackingBean bean;

	@PostConstruct
	public void init() {
		client = ClientBuilder.newClient();
		target = client.target(
				"http://localhost:8080/movieplex7/webresources/movies/");
	}

	@PreDestroy
	public void destroy() {
		client.close();
	}

	public Movie[] getMovies() {
		return target.request().get(Movie[].class);
	}

	public Movie getMovie() {
		Movie movie = target
				.path("{movieId}")
				.resolveTemplate("movieId", bean.getMovieId())
				.request()
				.get(Movie.class);
		return movie;
	}

	public void deleteMovie() {
		target.path("{movieId}").resolveTemplate("movieId", bean.getMovieId()).request().delete();
	}

	public void addMovie() {
		Movie movie = new Movie();
		movie.setId(bean.getMovieId());
		movie.setName(bean.getMovieName());
		movie.setActors(bean.getActors());
		target
		.register(MovieWriter.class)
		.request()
		.post(Entity.entity(movie, MediaType.APPLICATION_JSON));
	}
}
