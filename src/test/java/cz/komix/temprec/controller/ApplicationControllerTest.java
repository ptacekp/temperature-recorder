package cz.komix.temprec.controller;

import cz.komix.temprec.cli.ArgumentHandler;
import cz.komix.temprec.domain.Sample;
import cz.komix.temprec.domain.Samples;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class ApplicationControllerTest {

    @Test
    public void addSample() throws Exception {
        Samples samples = mock(Samples.class);
        ArgumentHandler args = mock(ArgumentHandler.class);

        Sample sample = Sample.create("CZ", BigDecimal.ONE);

        ApplicationController app = new ApplicationController(samples, args);
        app.addSample(sample);

        verify(samples, times(1)).add(sample);
        verifyNoMoreInteractions(samples);
    }
}
