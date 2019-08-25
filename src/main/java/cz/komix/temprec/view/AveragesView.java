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
package cz.komix.temprec.view;

import cz.komix.temprec.domain.Sample;
import cz.komix.temprec.domain.Samples;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @code AveragesView} print list of regions
 * and average temperatures to the standard output.
 * List is sorted by region.
 *
 * @author Petr Ptáček
 */
public class AveragesView implements View {

    /**
     * Measurement samples collection model.
     */
    private final Samples samples;

    /**
     * Create an instance of class {@link AveragesView} according
     * to the specified parameters.
     *
     * @param samples
     *          measurement samples collection model
     */
    public AveragesView(Samples samples) {
        this.samples = samples;
    }

    /**
     * Print list of regions and average temperatures to the standard output.
     * List is sorted by region.
     *
     * <p>This method prints header, list of regions and average temperatures
     * and blank line. The header format is:
     * <pre>-- Average temperature per region --</pre>
     * Region format is two capital letters of region name, space
     * and decimal number rounded up to one decimal point.
     */
    @Override
    public final void show() {
        List<Sample> averages = this.samples.getAverages();
        if (averages == null || averages.isEmpty()) {
            return;
        }

        synchronized (System.out) {
            System.out.println("-- Average temperature per region --");

            for (Sample sample : averages) {
                printSample(sample);
            }

            System.out.println("");
        }
    }

    /**
     * Print one measurement sample to standard output.
     *
     * @param sample
     *          sample to print.
     */
    private void printSample(Sample sample) {
        String region = sample.region();
        BigDecimal temperature = sample.temperature()
                .setScale(1, RoundingMode.HALF_UP);

        if (new BigDecimal(0).compareTo(temperature) >= 0) {
            return;
        }

        System.out.println(region + " " + temperature);
    }
}
