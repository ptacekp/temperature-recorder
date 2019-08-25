package cz.komix.temprec.domain;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SampleLoaderTest {
    @Test
    public void load() throws Exception {
        List<Sample> expected = Arrays.asList(
                Sample.create("CZ", new BigDecimal(1)),
                Sample.create("EN", new BigDecimal(2)),
                Sample.create("SK", new BigDecimal(3))
        );

        Reader reader = new StringReader("CZ 1\nEN 2\nSK 3");
        List<Sample> loaded = SampleLoader.load(reader);

        assertEquals(expected, loaded);
    }

    @Test
    public void parseValid() throws Exception {
        String line = "CZ -123.456";
        Sample expected = Sample.create("CZ", new BigDecimal("-123.456"));

        Sample parsed = SampleLoader.parse(line);

        assertEquals(expected, parsed);
    }

    @Test
    public void parseInvalid() throws Exception {
        String line = "bla bla bla";
        Sample parsed = SampleLoader.parse(line);

        assertNull(parsed);
    }
}
