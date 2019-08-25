package cz.komix.temprec.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SamplesTest {

    private Samples samples;

    @Before
    public void setUpStreams() {
        samples = new Samples();
    }

    @After
    public void restoreStreams() {
        samples = null;
    }

    @Test
    public void add() throws Exception {
        Sample sample = Sample.create("CZ", new BigDecimal(123));

        samples.add(sample);

        List<Sample> all = samples.getAll();

        assertEquals(1, all.size());
        assertEquals(sample, all.get(0));
    }

    @Test
    public void getAll() throws Exception {
        Sample s1 = Sample.create("SK", new BigDecimal(30));
        Sample s2 = Sample.create("CZ", new BigDecimal(20));
        Sample s3 = Sample.create("CZ", new BigDecimal(10));

        samples.add(s1);
        samples.add(s2);
        samples.add(s3);

        List<Sample> expected = new ArrayList<>(3);
        expected.add(Sample.create("SK", new BigDecimal(30)));
        expected.add(Sample.create("CZ", new BigDecimal(20)));
        expected.add(Sample.create("CZ", new BigDecimal(10)));
        expected = Collections.unmodifiableList(expected);

        assertEquals(expected, samples.getAll());
    }

    @Test
    public void getAverages() throws Exception {
        Sample s1 = Sample.create("SK", new BigDecimal(30));
        Sample s2 = Sample.create("CZ", new BigDecimal(20));
        Sample s3 = Sample.create("CZ", new BigDecimal(10));

        samples.add(s1);
        samples.add(s2);
        samples.add(s3);

        List<Sample> expected = new ArrayList<>(2);
        expected.add(Sample.create("CZ", new BigDecimal(15).setScale(10, RoundingMode.HALF_UP)));
        expected.add(Sample.create("SK", new BigDecimal(30)));
        expected = Collections.unmodifiableList(expected);

        assertEquals(expected, samples.getAverages());
    }

}
