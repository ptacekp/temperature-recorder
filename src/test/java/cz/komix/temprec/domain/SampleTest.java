package cz.komix.temprec.domain;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class SampleTest {

    @Test
    public void create() throws Exception {
        String region = "CZ";
        BigDecimal temperature = new BigDecimal("-1.543");

        Sample sample = Sample.create(region, temperature);

        Assert.assertEquals(region, sample.region());
        Assert.assertEquals(temperature, sample.temperature());
    }

    @Test
    public void equals() throws Exception {
        Sample s1 = Sample.create("CZ", new BigDecimal("-1.543"));
        Sample s2 = Sample.create("CZ", new BigDecimal("-1.543"));

        Assert.assertEquals(s1, s2);
    }

    @Test
    public void notEquals() throws Exception {
        Sample s1 = Sample.create("CZ", new BigDecimal("-1.543"));
        Sample s2 = Sample.create("CZ", new BigDecimal(24));

        Assert.assertNotEquals(s1, s2);

        s1 = Sample.create("CZ", new BigDecimal("-1.543"));
        s2 = Sample.create("SK", new BigDecimal("-1.543"));

        Assert.assertNotEquals(s1, s2);
    }
}
