package org.glassfish.movieplex7.client;

import javax.annotation.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

import org.glassfish.movieplex7.entities.*;
import org.glassfish.movieplex7.json.ArticleWriter;

@Named
@RequestScoped
public class ArticleClientBean {
	Client client;
	WebTarget target;

	@Inject
	ArticleBackingBean bean;

	@PostConstruct
	public void init() {
		client = ClientBuilder.newClient();
		target = client.target(
				"http://localhost:8080/movieplex7/webresources/articles/");
	}

	@PreDestroy
	public void destroy() {
		client.close();
	}

	public Article[] getArticles() {
		return target.request().get(Article[].class);
	}

	public Article getArticle() {
		Article article = target
				.path("{articleId}")
				.resolveTemplate("articleId", bean.getArticleId())
				.request()
				.get(Article.class);
		return article;
	}

	public void deleteArticle() {
		target.path("{articleId}").resolveTemplate("articleId", bean.getArticleId()).request().delete();
	}

	public void addArticle() {
		Article article = new Article();
		article.setId(bean.getArticleId());
		article.setName(bean.getArticleName());
		article.setActors(bean.getActors());
                article.setContent(bean.getContent());
		target
		.register(ArticleWriter.class)
		.request()
		.post(Entity.entity(article, MediaType.APPLICATION_JSON));
	}
}
