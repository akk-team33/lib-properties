package de.team33.test.properties.v1;

import de.team33.libs.properties.v1.FieldProperty;
import de.team33.test.properties.shared.Immutable;
import de.team33.test.properties.shared.Mutable;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class FieldPropertyTest {

    private final Random random = new Random();
    private final FieldProperty property;

    public FieldPropertyTest() throws NoSuchFieldException {
        property = new FieldProperty(Immutable.class.getDeclaredField("value"));
    }

    private Stream<Long> values() {
        return Stream.concat(Stream.of(0L, 1L, 5L, -7L, null), Stream.generate(random::nextLong))
                     .distinct().limit(100);
    }

    @Test
    public void name() {
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
    public void getLegal() {
        values().forEach(value -> {
            final Immutable sample = new Immutable(value);
            Assert.assertEquals(sample.getValue(), property.get(sample));
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void getIllegal() {
        final Mutable sample = new Mutable().setValue(random.nextLong());
        Assert.fail("expected to fail but was " + property.get(sample));
    }

    @Test
    public void set() {
        values().forEach(value -> {
            final Immutable sample = (Immutable) property.set(new Immutable(null), value);
            Assert.assertEquals(value, sample.getValue());
        });
    }
}
