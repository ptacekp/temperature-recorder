package cz.komix.temprec.cli;

import cz.komix.temprec.controller.ApplicationController;
import cz.komix.temprec.domain.Sample;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class AddCommandTest {
    @Test
    public void execute() throws Exception {
        ApplicationController app = mock(ApplicationController.class);
        AddCommand command = new AddCommand("CZ 1", app);

        command.execute();

        verify(app, times(1)).addSample(Sample.create("CZ", BigDecimal.ONE));
        verifyNoMoreInteractions(app);
    }

}
