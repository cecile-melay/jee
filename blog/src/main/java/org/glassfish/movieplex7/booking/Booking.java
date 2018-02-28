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

	int articleId;

	String startTime;
	int startTimeId;

	@PersistenceContext
	EntityManager entityManager;

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getArticleName() {
		try {
			return entityManager.createNamedQuery("Article.findById", Article.class).setParameter("id", articleId).getSingleResult().getName();
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

	
}

