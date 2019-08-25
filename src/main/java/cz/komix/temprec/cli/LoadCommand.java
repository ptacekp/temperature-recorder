/*
 * Copyright (c) 2019 Petr Ptáček
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package cz.komix.temprec.cli;

import cz.komix.temprec.domain.SampleLoader;
import cz.komix.temprec.controller.ApplicationController;
import cz.komix.temprec.domain.Sample;
import cz.komix.temprec.view.ErrorView;
import cz.komix.temprec.view.MessageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * {@code LoadCommand} load measurement samples from resource file.
 *
 * <p>There are only four possible ends of load command execution:
 * <ul>
 *     <li>resource file contains X lines with measurement
 *         samples - the application show user message
 *         with text 'Imported X measurement samples.'</li>
 *     <li>resource file is empty - the application show user
 *         message with text 'Nothing to import.'</li>
 *     <li>resource file missing - the application show error user
 *         message with text 'Unable to open file 'XYZ''</li>
 *     <li>resource file missing - the application show error user
 *         message with text 'Unable to open file 'XYZ''</li>
 * </ul>
 *
 * @author Petr Ptáček
 */
public class LoadCommand implements Command {

    /**
     * Resource file path and name.
     */
    private final String path;

    /**
     * Application controller binding.
     */
    private final ApplicationController app;

    /**
     * Create new instance of {@code LoadCommand} class.
     *
     * @param path
     *          resource file path and name.
     *
     * @param app
     *          application controller.
     */
    public LoadCommand(String path, ApplicationController app) {
        this.path = path;
        this.app = app;
    }

    /**
     * Load measurement samples from resource file.
     */
    public void execute() {
        try {
            Reader reader = createReader();
            List<Sample> samples = SampleLoader.load(reader);

            if (samples != null) {
                for (Sample sample : samples) {
                    app.addSample(sample);
                }
                new MessageView("Imported " + samples.size() + " measurement samples.").show();
            } else {
                new MessageView("Nothing to import.").show();
            }
        } catch (FileNotFoundException e) {
            new ErrorView(e, "Unable to open file '" + path + "'").show();
        } catch (IOException e) {
            new ErrorView(e, "Error reading file '" + path + "'").show();
        }
    }

    /**
     * Create file reader.
     *
     * @return instance of file reader.
     *          Never returns <tt>null</tt>.
     *
     * @throws IOException
     *          occurs when file not found.
     */
    protected Reader createReader() throws IOException {
        return SampleLoader.createFileReader(path);
    }
}