package cz.komix.temprec.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class HelpViewTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final String lineSeparator = System.lineSeparator();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void show() throws Exception {
        String expected = "usage: temperaturerecorder [(-h) (-v) (-f) FILE]" + lineSeparator
                + "This application record and store temperature measurement"
                + lineSeparator + "in different regions." + lineSeparator
                + " -f,--file <file>   file or text file with list of measurement data" + lineSeparator
                + " -h,--help          print this message" + lineSeparator
                + " -v,--version       print version of the application" + lineSeparator
                + "Temperature recorder allow you to insert new measurement"
                + lineSeparator + "from console or text file." + lineSeparator + lineSeparator
                + "Commands in application:" + lineSeparator
                + " add      - add new measurement sample" + lineSeparator
                + " help     - print this message" + lineSeparator
                + " version  - print version of the application" + lineSeparator
                + " load     - load measurement samples from file" + lineSeparator
                + " quit     - exit the application" + lineSeparator + lineSeparator;

        View view = new HelpView();
        view.show();

        assertEquals(expected, outContent.toString());
        assertEquals("", errContent.toString());
    }

}
