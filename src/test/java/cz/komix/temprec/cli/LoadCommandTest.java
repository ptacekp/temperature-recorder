package cz.komix.temprec.cli;

import cz.komix.temprec.controller.ApplicationController;
import cz.komix.temprec.domain.Sample;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class LoadCommandTest {

    private ApplicationController app;

    @Before
    public void setUp() {
        app = mock(ApplicationController.class);
    }

    @After
    public void restore() {
        app = null;
    }

    @Test
    public void execute() throws Exception {
        Command command = new LoadCommand("", app) {
            @Override
            protected Reader createReader() throws IOException {
                return new StringReader("CZ 1\nEN 2\nSK 3");
            }
        };

        command.execute();

        verify(app, times(1)).addSample(Sample.create("CZ", new BigDecimal(1)));
        verify(app, times(1)).addSample(Sample.create("EN", new BigDecimal(2)));
        verify(app, times(1)).addSample(Sample.create("SK", new BigDecimal(3)));

        verifyNoMoreInteractions(app);
    }


}
