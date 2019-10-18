package de.team33.test.properties.v2;

import de.team33.libs.properties.v2.Property;
import de.team33.test.properties.shared.Container;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.stream.Stream;

public abstract class PropertyTestBase {

    private final Random random = new Random();

    private Stream<Long> values() {
        return Stream.concat(Stream.of(0L, 1L, 5L, -7L, null), Stream.generate(random::nextLong))
                     .distinct().limit(100);
    }

    protected abstract Property getProperty();

    protected abstract Container newContainer(Long value);

    @Test
    public final void name() {
        Assert.assertEquals("value", getProperty().name());
    }

    @Test
    public final void direct() {
        values().forEach(value -> {
            final Container sample = newContainer(value);
            Assert.assertEquals(value, sample.getValue());
        });
    }

    @Test
    public void get() {
        final Property property = getProperty();
        values().forEach(value -> {
            final Container sample = newContainer(value);
            Assert.assertEquals(sample.getValue(), property.get(sample));
        });
    }

    @Test
    public void set() {
        final Property property = getProperty();
        values().forEach(value -> {
            final Container sample = newContainer(null);
            property.set(sample, value);
            Assert.assertEquals(value, sample.getValue());
        });
    }
}
