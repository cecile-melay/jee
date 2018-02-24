package org.glassfish.movieplex7.json;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.glassfish.movieplex7.entities.*;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MovieWriter implements MessageBodyWriter<Movie>{

	@Override
	public long getSize(Movie arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2, MediaType arg3) {
		// TODO Auto-generated method stub
		return Movie.class.isAssignableFrom(arg0);
	}

	@Override
	public void writeTo(Movie movie, Class<?> type, Type type1,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> multivaluedMap,
			OutputStream output)
					throws IOException, WebApplicationException {
		JsonGenerator generator = Json.createGenerator(output);
		generator.writeStartObject()
		.write("id", movie.getId())
		.write("name", movie.getName())
		.write("actors", movie.getActors())
		.writeEnd();
		generator.flush();
	}

}
