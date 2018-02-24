package org.glassfish.movieplex7.booking;

import java.io.Serializable;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import javax.persistence.*;

import org.glassfish.movieplex7.entities.*;

@Named
@FlowScoped("booking")
public class Booking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int movieId;

	String startTime;
	int startTimeId;

	@PersistenceContext
	EntityManager entityManager;

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		try {
			return entityManager.createNamedQuery("Movie.findById", Movie.class).setParameter("id", movieId).getSingleResult().getName();
		} catch (NoResultException e) {
			return "";
		}
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		StringTokenizer tokens = new StringTokenizer(startTime, ",");
		startTimeId = Integer.parseInt(tokens.nextToken());
		this.startTime = tokens.nextToken();
	}

	public int getStartTimeId() {
		return startTimeId;
	}

	public String getTheater() {
		// for a movie and show
		try {
			// Always return the first theater
			List<ShowTiming> list = entityManager.createNamedQuery(
					"ShowTiming.findByMovieAndTimeslotId",
					ShowTiming.class)
					.setParameter("movieId", movieId)
					.setParameter("timeslotId", startTimeId)
					.getResultList();
			if (list.isEmpty()) {
				return "none";
			}
			return list.get(0).getTheater().getId().toString();
		} catch (NoResultException e) {
			return "none";
		}
	}
}

