
/*
    Copyright (C) 2017 e-ito Technology Services GmbH
    e-mail: info@e-ito.de
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


/**
 * 
 */
package hr.eito.model;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Tests the DateSerializer.
 *
 * @author Hrvoje
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/app-config.xml" })
@ActiveProfiles("test")
public class DateSerializerTest {

	/**
	 * Runs before the tests start.
	 */
	@BeforeClass
	public static void testStart() { }
	
	/**
	 * Runs after the tests end.
	 */
	@AfterClass
	public static void testEnd() { }

	/**
	 * Test default construction.
	 */
	@Test
	public void testDefaultCtor() {
		DateSerializer ds = new DateSerializer();
		Assert.assertNotNull(ds);
	}

	/**
	 * String construction.
	 */
	@Test
	public void testStringCtor() {
		DateSerializer ds = new DateSerializer(String.class);
		Assert.assertNotNull(ds);
	}

	/**
	 * Test value serialization.
	 */
	@Test
	public void testValueSerialization() throws IOException, JsonProcessingException {
		// Prepare all objects - date time in UTC
		final String value = "2016-12-21T23:20:30.000Z";
		// Prepare surrounding and serialize
		Writer jsonWriter = new StringWriter();
	    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
	    jsonGenerator.setCodec(new ObjectMapper());
	    SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();
	    new DateSerializer().serialize(value, jsonGenerator, serializerProvider);
	    jsonGenerator.flush();
	    
	    Assert.assertEquals("\"2016-12-22 00:20\"", jsonWriter.toString());
	}
}
