package de.team33.test.properties.v1;

import de.team33.libs.properties.v1.Property;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.stream.Stream;

public class PropertyTest {

    private final Random random = new Random();

    private Stream<Long> values() {
        return Stream.concat(Stream.of(0L, 1L, 5L, -7L, null), Stream.generate(random::nextLong))
                     .distinct().limit(100);
    }

    @Test
    public void name() {
        final Property<Sample> property = Property.simple("value", Sample::getValue, Sample::setValue);
        Assert.assertEquals("value", property.name());
    }

    @Test
    public void direct() {
        values().forEach(value -> {
            final Sample sample = new Sample().setValue(value);
            Assert.assertEquals(value, sample.getValue());
        });
    }

    @Test
    public void get() {
        final Property<Sample> property = Property.simple("value", Sample::getValue);
        values().forEach(value -> {
            final Sample sample = new Sample().setValue(value);
            Assert.assertEquals(sample.getValue(), property.get(sample));
        });
    }

    @Test
    public void set() {
        final Property<Sample> property = Property.simple("value", Sample::setValue);
        values().forEach(value -> {
            final Sample sample = property.set(new Sample(), value);
            Assert.assertEquals(value, sample.getValue());
        });
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unsupportedGet() {
        final Property<Sample> property = Property.simple("value", Sample::setValue);
        final Sample sample = new Sample().setValue(random.nextLong());
        Assert.fail("Expected to fail but was <" + property.get(sample) + ">");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unsupportedSet() {
        final Property<Sample> property = Property.simple("value", Sample::getValue);
        final Sample sample = property.set(new Sample(), random.nextLong());
        Assert.fail("Expected to fail but was <" + sample.getValue() + ">");
    }

    public static class Sample {

        private Long value;

        public Long getValue() {
            return value;
        }

        public Sample setValue(final Long value) {
            this.value = value;
            return this;
        }
    }
}
