package cz.komix.temprec.view;

import cz.komix.temprec.domain.Sample;
import cz.komix.temprec.domain.Samples;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AveragesViewTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final String lineSeparator = System.lineSeparator();
    private Samples samples;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        samples = new Samples();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);

        samples = null;
    }

    @Test
    public void show() throws Exception {
        Sample s1 = Sample.create("SK", new BigDecimal(30));
        Sample s2 = Sample.create("CZ", new BigDecimal(20));
        Sample s3 = Sample.create("CZ", new BigDecimal(10));

        samples.add(s1);
        samples.add(s2);
        samples.add(s3);

        View view = new AveragesView(samples);
        view.show();

        String expected = "-- Average temperature per region --" + lineSeparator
                            + "CZ 15.0" + lineSeparator
                            + "SK 30.0" + lineSeparator + lineSeparator;

        assertEquals(expected, outContent.toString());
    }

}
