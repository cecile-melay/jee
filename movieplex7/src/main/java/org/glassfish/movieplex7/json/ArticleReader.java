package org.glassfish.movieplex7.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.glassfish.movieplex7.entities.*;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleReader implements MessageBodyReader<Article> {

	@Override
	public boolean isReadable(Class<?> arg0, Type arg1, Annotation[] arg2, MediaType arg3) {
		// TODO Auto-generated method stub
		return Article.class.isAssignableFrom(arg0);
	}

	@Override
	public Article readFrom(Class<Article> type, Type type1,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> multivaluedMap,
			InputStream inputSteam)
					throws IOException, WebApplicationException {
		Article article = new Article();
		JsonParser parser = Json.createParser(inputSteam);
		while (parser.hasNext()) {
			switch (parser.next()) {
			case KEY_NAME:
				String key = parser.getString();
				parser.next();
				switch (key) {
				case "id":
					article.setId(parser.getInt());
					break;
				case "name":
					article.setName(parser.getString());
					break;
				case "actors":
					article.setActors(parser.getString());
					break;
                                case "content":
                                    article.setContent(parser.getString());
                                    break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		return article;
	}

}
