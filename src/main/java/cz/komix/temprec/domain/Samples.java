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
package cz.komix.temprec.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Collection of measurement samples. Holding a list
 * of {@link Sample} class instance in insert order.
 *
 * <p>The {@code Samples} class provide operation for
 * add and retrive data from collection. All operations
 * are thread-safe. It is possible to call add operation
 * from one thread and get operation from different thread
 * on same instance of {@code Samples}.
 *
 * @author Petr Ptáček
 */
public class Samples {

    /**
     * Collection holding all measurement samples data.
     */
    private final List<Sample> samples;

    /**
     * Create new instance of {@code Samples} and init
     * samples collection.
     */
    public Samples() {
        this.samples = new ArrayList<>();
    }

    /**
     * Add one measurement sample to this collection.
     * <p>
     * Add operation is thread-safe. It is possible to
     * call add operation from one thread and get operations
     * from different thread on same instance of {@code Samples}.
     *
     * @param sample
     *          measurement sample.
     */
    public void add(Sample sample) {
        synchronized (this.samples) {
            this.samples.add(sample);
        }
    }

    /**
     * Return all recorded measurement samples in the collection.
     * Returned list is unmodifiable.
     *
     * <p>Get operation is thread-safe. It is possible to
     * call get operation from one thread and add operations
     * from different thread on same instance of {@code Samples}.
     *
     * @return  an unmodifiable view of the samples list.
     */
    public List<Sample> getAll() {
        synchronized (this.samples) {
            return Collections.unmodifiableList(this.samples);
        }
    }

    /**
     * Return list of averages measurement per region.
     * Returned list is unmodifiable.
     *
     * <p>This method first count average temperature
     * for every recorded region. This averages is converted
     * to the list of measurement samples {@link Sample}.
     * At the end of process all of measurement
     * samples is sorted by region name.
     *
     * <p>Get operation is thread-safe. It is possible to
     * call get operation from one thread and add operations
     * from different thread on same instance of {@code Samples}.
     *
     * @return  an unmodifiable view of the samples list.
     */
    public List<Sample> getAverages() {
        Map<String, BigDecimal> averages = countAverages();
        List<Sample> result = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> e : averages.entrySet()) {
            String region = e.getKey();
            BigDecimal temperature = e.getValue();

            result.add(Sample.create(region, temperature));
        }

        result.sort((s1, s2) -> s1.region().compareTo(s2.region()));
        return Collections.unmodifiableList(result);
    }

    /**
     * Count averages for every recorder region.
     * This method si thread-safe.
     *
     * @return  map of regions and their average temperature.
     */
    private Map<String, BigDecimal> countAverages() {
        Map<String, BigDecimal> averages = new HashMap<>();

        synchronized (this.samples) {
            for (Sample sample : samples) {
                String region = sample.region();
                BigDecimal temperature = sample.temperature();

                BigDecimal prev = averages.get(region);

                if (prev == null) {
                    averages.put(region, temperature);
                } else {
                    temperature = temperature.add(prev)
                            .divide(new BigDecimal(2), 10, RoundingMode.HALF_UP);
                    averages.put(region, temperature);
                }
            }
        }

        return averages;
    }
}