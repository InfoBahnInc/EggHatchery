package com.hackagong.hatchery.rest;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.module.SimpleModule;

import com.hackagong.hatchery.util.DateUtils;

@Provider
public class ObjectMapperContextResolver
	implements ContextResolver<ObjectMapper> {

	//
	// Private members
	//

	private ObjectMapper					mObjectMapper;

	//
	// Public methods
	//

	@Override
	public ObjectMapper getContext( Class<?> type ) {

		if ( mObjectMapper == null ) {

			mObjectMapper = new ObjectMapper()
					.configure( SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false )
					.setSerializationInclusion( JsonSerialize.Inclusion.NON_NULL );

			SimpleModule simpleModule = new SimpleModule( ObjectMapperContextResolver.class.getName(), new Version( 1, 0, 0, null ) );
			simpleModule.addSerializer( Date.class, new DateSerializer() );
			mObjectMapper.registerModule( simpleModule );
		}

		return mObjectMapper;
	}

	//
	// Inner class
	//

	/**
	 * Serialize Dates in AEST. By default JSON serializes as UTC. This can be a pain to convert
	 * back (because of daylight savings etc)
	 */

	/* package private */static class DateSerializer
		extends JsonSerializer<Date> {

		//
		// Public methods
		//

		@Override
		public void serialize( Date value, JsonGenerator jgen, SerializerProvider provider )
			throws JsonGenerationException, IOException {

			if ( value == null ) {
				return;
			}

			jgen.writeString( DateUtils.formatISODateTime( value ) );
		}
	}
}