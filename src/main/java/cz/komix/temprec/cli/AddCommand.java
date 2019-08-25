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

/**
 * Add new measurement sample based on user request.
 *
 * @author Petr Ptáček
 */
public class AddCommand implements Command {

    /**
     * User add request.
     */
    private final String request;

    /**
     * Application controller binding.
     */
    private final ApplicationController app;

    /**
     * Create new instance of {@code AddCommand} class.
     *
     * @param request
     *          user add request
     *
     * @param app
     *          application controller binding
     */
    public AddCommand(String request, ApplicationController app) {
        this.request = request;
        this.app = app;
    }

    /**
     * Parser user request and add sample to model.
     *
     * <p>User request is parsed by {@link SampleLoader#parse(String)}
     * method. Parsed instance of {@link Sample} class
     * is added to application model.
     */
    public void execute() {
        Sample sample = SampleLoader.parse(request);

        if (sample != null) {
            this.app.addSample(sample);
        }
    }
}