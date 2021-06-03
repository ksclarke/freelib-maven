
package info.freelibrary.maven.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;

/**
 * Tests for the automatically generated MediaType enumeration.
 */
public class MediaTypeTest {

    /**
     * Tests the {@link MediaType#toString()} method.
     */
    @Test
    public final void testToString() {
        assertEquals("image/jp2", MediaType.IMAGE_JP2.toString());
    }

    /**
     * Tests the {@link MediaType#getExt()} method.
     */
    @Test
    public final void testGetExt() {
        assertEquals("jp2", MediaType.IMAGE_JP2.getExt());
    }

    /**
     * Tests the {@link MediaType#getExts()} method.
     */
    @Test
    public final void testGetExts() {
        assertTrue(MediaType.IMAGE_JP2.getExts().length >= 2); // >= because the CI OS may have different mime.types
    }

    /**
     * Tests the {@link MediaType#fromString(String)} method.
     */
    @Test
    public final void testFromString() {
        assertEquals(MediaType.IMAGE_JPEG, MediaType.fromString("image/jpeg").get());
    }

    /**
     * Tests the {@link MediaType#fromExt(String)} method.
     */
    @Test
    public final void testFromExt() {
        assertEquals(MediaType.IMAGE_JPEG, MediaType.fromExt("jpg").get());
        assertEquals(MediaType.IMAGE_JPEG, MediaType.fromExt("jpeg").get());
    }

    /**
     * Tests the {@link MediaType#getTypes(String)} method.
     */
    @Test
    public final void testGetTypes() {
        assertTrue(MediaType.getTypes("image").size() >= 6);
    }

    /**
     * Tests the {@link MediaType#parse(String)} method.
     */
    @Test
    public final void testParseString() {
        assertEquals(MediaType.IMAGE_SVG_PLUS_XML, MediaType.parse("http:/thing.com/image.svg").get());
    }

    /**
     * Tests the {@link MediaType#parse(String)} method.
     */
    @Test
    public final void testParseStringWithoutExtension() {
        assertTrue(MediaType.parse("http:/thing.com/image").isEmpty());
    }

    /**
     * Tests the {@link MediaType#parse(URI)} method.
     */
    @Test
    public final void testParseURI() {
        assertEquals(MediaType.IMAGE_SVG_PLUS_XML, MediaType.parse(URI.create("http:/thing.com/image2.svg")).get());
    }
}
