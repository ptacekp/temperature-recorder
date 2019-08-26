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
package cz.komix.temprec;

import cz.komix.temprec.cli.ArgumentHandler;
import cz.komix.temprec.cli.Command;
import cz.komix.temprec.cli.LoadCommand;
import cz.komix.temprec.controller.ApplicationController;
import cz.komix.temprec.controller.AveragesController;
import cz.komix.temprec.domain.Samples;

/**
 * This class is an entry point of Temperature Recorder application.
 *
 * @author Petr Ptáček
 */
public class TemperatureRecorder {

    /**
     * Main method and entry point of the application.
     *
     * @param args
     *          startup arguments.
     */
    public static void main(String[] args) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        argHandler.process();
        if (!argHandler.canContinue()) {
            return;
        }

        Samples samples = new Samples();

        Thread averages = new AveragesController(samples);
        averages.setDaemon(true);
        averages.start();

        ApplicationController app = new ApplicationController(samples, argHandler);
        app.start();
    }
}
