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
package cz.komix.temprec.controller;

import cz.komix.temprec.common.Constants;
import cz.komix.temprec.domain.Samples;
import cz.komix.temprec.view.AveragesView;

/**
 * {@code AveragesController} is responsible for periodic updating
 * UI output with list of regions and average temperatures.
 *
 * <p>Period of updates is define by value
 * {@value Constants#AVERAGES_VIEW_UPDATE_PERIOD}. Default
 * update period is one per minute.
 *
 * @author Petr Ptáček
 */
public class AveragesController extends Thread {

    /**
     * Measurement samples model.
     */
    private final Samples samples;

    /**
     * Create new instance of {@code AveragesController} class.
     *
     * @param samples
     *          measurement samples model.
     */
    public AveragesController(Samples samples) {
        this.samples = samples;
    }

    /**
     * Run infinite loop of periodic updating UI output
     * with list of regions and average temperatures.
     */
    @Override
    public void run() {
        AveragesView view = new AveragesView(samples);

        while(true){
            try {
                Thread.sleep(Constants.AVERAGES_VIEW_UPDATE_PERIOD);

                view.show();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
