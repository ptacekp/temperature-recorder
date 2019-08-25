package cz.komix.temprec.cli;

import cz.komix.temprec.view.ErrorView;
import cz.komix.temprec.view.HelpView;
import cz.komix.temprec.view.VersionView;
import org.apache.commons.cli.*;

/**
 * {@code ArgumentHandler} process startup application arguments.
 *
 * @author Petr Ptáček
 */
public class ArgumentHandler {

    /**
     * Parsed command line interface.
     */
    private final CommandLine line;

    /**
     * Create new instance of {@code ArgumentHandler} class.
     *
     * @param args
     *          application startup arguments.
     */
    public ArgumentHandler(String[] args) {
        this.line = parse(args);
    }

    /**
     * Process all command from application startup arguments.
     */
    public void process() {
        if (line == null) {
            return;
        }

        if (line.hasOption("h")) {
            new HelpView().show();
            return;
        }

        if (line.hasOption("v")) {
            new VersionView().show();
        }
    }

    /**
     * Return status of parsing startup arguments.
     * It says if application can continue or should quit.
     *
     * @return  <tt>true</tt> if arguments is empty,
     *          or doesn't contains version or help argument.
     *          Otherwise return <tt>false</tt>.
     */
    public boolean canContinue() {
        return line == null || (!line.hasOption("h") && !line.hasOption("v"));
    }

    /**
     * Returns file path and name from startup arguments.
     *
     * @return  instance of {@link String} class
     *          or <tt>null</tt> if file startup argument
     *          is not set.
     */
    public String getFileName() {
        if (line == null || !line.hasOption("f")) {
            return null;
        }

        return line.getOptionValue("f");
    }

    /**
     * Returns startup options of the application.
     *
     * @return  instance of {@link Options} class.
     *          Never returns <tt>null</tt>.
     */
    public static Options getOptions() {
        Options options = new Options();

        //init options
        options.addOption("h", "help", false, "print this message");

        //init options
        options.addOption("v", "version", false, "print version of the application");

        Option of = new Option("f", "file", true,
                "file or text file with list of measurement data");
        of.setArgName("file");
        options.addOption(of);

        return options;
    }

    /**
     * Parse startup application arguments.
     *
     * @param args
     *          array of startup arguments
     *
     * @return  parsed command line or null,
     *          if there is no startup arguments.
     */
    private static CommandLine parse(String[] args) {
        if (args == null || args.length == 0) {
            return null;
        }

        Options options = getOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse( options, args);
        } catch (ParseException e) {
            new HelpView().show();
            new ErrorView(null, "Parsing failed.  Reason: " + e.getMessage()).show();
        }

        return null;
    }
}