package de.team33.test.properties.v2;

import de.team33.libs.properties.v2.Property;
import de.team33.test.properties.shared.Container;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.stream.Stream;

public abstract class PropertyTestBase {

    private final Random random = new Random();
    private final String expectedPropertyName;
    private final Property property;

    protected PropertyTestBase(final String expectedPropertyName, final Property property) {
        this.expectedPropertyName = expectedPropertyName;
        this.property = property;
    }

    private Stream<Long> values() {
        return Stream.concat(Stream.of(0L, 1L, 5L, -7L, null), Stream.generate(random::nextLong))
                     .distinct().limit(100);
    }

    protected abstract Container newContainer(final ContainerType type, final Long value);

    @Test
    public final void consistence() {
        values().forEach(value -> {
            final Container sample1 = newContainer(ContainerType.Familiar, value);
            Assert.assertEquals(value, sample1.getValue());
            final Container sample2 = newContainer(ContainerType.Foreign, value);
            Assert.assertEquals(value, sample2.getValue());
        });
    }

    @Test
    public final void getKey() {
        Assert.assertEquals(expectedPropertyName, property.getKey());
    }

    @Test
    public final void getValue() {
        values().forEach(value -> {
            final Container sample = newContainer(ContainerType.Familiar, value);
            Assert.assertEquals(sample.getValue(), property.getValue(sample));
        });
    }

    @Test(expected = NullPointerException.class)
    public void getValueByNull() {
        property.getValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getValueByForeign() {
        final Container sample = newContainer(ContainerType.Foreign, random.nextLong());
        Assert.assertEquals(sample.getValue(), property.getValue(sample));
    }

    @Test
    public void setValue() {
        values().forEach(value -> {
            final Container sample = newContainer(ContainerType.Familiar, null);
            property.setValue(sample, value);
            Assert.assertEquals(value, sample.getValue());
        });
    }

    protected enum ContainerType {
        Familiar,
        Foreign
    }
}
