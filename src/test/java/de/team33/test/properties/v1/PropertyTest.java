package de.team33.test.properties.v1;

import de.team33.libs.properties.v1.Property;
import de.team33.test.properties.shared.Immutable;
import de.team33.test.properties.shared.Mutable;
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
        final Property<Mutable> property = Property.simple("value", Mutable::getValue, Mutable::setValue);
        Assert.assertEquals("value", property.name());
    }

    @Test
    public void direct() {
        values().forEach(value -> {
            final Mutable sample = new Mutable().setValue(value);
            Assert.assertEquals(value, sample.getValue());
        });
    }

    @Test
    public void get() {
        final Property<Mutable> property = Property.simple("value", Mutable::getValue);
        values().forEach(value -> {
            final Mutable sample = new Mutable().setValue(value);
            Assert.assertEquals(sample.getValue(), property.get(sample));
        });
    }

    @Test
    public void set() {
        final Property<Mutable> property = Property.simple("value", Mutable::setValue);
        values().forEach(value -> {
            final Mutable sample = property.set(new Mutable(), value);
            Assert.assertEquals(value, sample.getValue());
        });
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unsupportedGet() {
        final Property<Mutable> property = Property.simple("value", Mutable::setValue);
        final Mutable sample = new Mutable().setValue(random.nextLong());
        Assert.fail("Expected to fail but was <" + property.get(sample) + ">");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unsupportedSet() {
        final Property<Immutable> property = Property.simple("value", Immutable::getValue);
        final Immutable sample = property.set(new Immutable(null), random.nextLong());
        Assert.fail("Expected to fail but was <" + sample.getValue() + ">");
    }
}
