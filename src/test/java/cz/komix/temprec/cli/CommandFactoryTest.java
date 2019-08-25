package cz.komix.temprec.cli;

import cz.komix.temprec.controller.ApplicationController;
import cz.komix.temprec.view.HelpView;
import cz.komix.temprec.view.InvalidCommandView;
import cz.komix.temprec.view.VersionView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class CommandFactoryTest {

    private ApplicationController app;
    private CommandFactory factory;

    @Before
    public void setUp() {
        this.app = mock(ApplicationController.class);
        this.factory = new CommandFactory(app);
    }

    @After
    public void restore() {
        this.app = null;
        this.factory = null;
    }

    @Test
    public void parseQuit() throws Exception {
        Command command = factory.parse("quit");

        assertTrue(command instanceof ExitCommand);
    }

    @Test
    public void parseHelp() throws Exception {
        Command command = factory.parse("help");

        assertTrue(command instanceof DisplayCommand);
        assertTrue(((DisplayCommand) command).getView() instanceof HelpView);
    }

    @Test
    public void parseLoad() throws Exception {
        Command command = factory.parse("load samples.txt");

        assertTrue(command instanceof LoadCommand);
    }

    @Test
    public void parseVersion() throws Exception {
        Command command = factory.parse("version");

        assertTrue(command instanceof DisplayCommand);
        assertTrue(((DisplayCommand) command).getView() instanceof VersionView);
    }

    @Test
    public void parseAdd() throws Exception {
        Command command = factory.parse("add CZ 1");

        assertTrue(command instanceof AddCommand);
    }

    @Test
    public void parseInput() throws Exception {
        Command command = factory.parse("CZ 123");

        assertTrue(command instanceof AddCommand);
    }

    @Test
    public void parseInvalid() throws Exception {
        Command command = factory.parse("bla bla bla");

        assertTrue(command instanceof DisplayCommand);
        assertTrue(((DisplayCommand) command).getView() instanceof InvalidCommandView);
    }
}
