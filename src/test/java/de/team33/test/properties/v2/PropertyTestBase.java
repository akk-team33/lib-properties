package de.team33.test.properties.v2;

import de.team33.libs.properties.v2.Property;
import de.team33.test.properties.shared.Container;
import org.junit.Test;

import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public abstract class PropertyTestBase {

    private final Random random = new Random();
    private final String expectedPropertyName;
    private final Property property;

    protected PropertyTestBase(final String expectedPropertyName, final Property property) {
        this.expectedPropertyName = expectedPropertyName;
        this.property = property;
    }

    private void forSeveralValues(final Consumer<Long> consumer) {
        Stream.concat(Stream.of(0L, 1L, 5L, -7L, null), Stream.generate(random::nextLong))
              .distinct().limit(100)
              .forEach(consumer);
    }

    protected abstract Container newContainer(final ContainerType type, final Long value);

    @Test
    public final void consistence() {
        forSeveralValues(value -> Stream.of(ContainerType.values())
                                        .forEach(type -> assertEquals(value, newContainer(type, value).getValue())));
    }

    @Test
    public final void getKey() {
        assertEquals(expectedPropertyName, property.getKey());
    }

    @Test
    public final void getValue() {
        forSeveralValues(value -> {
            final Container sample = newContainer(ContainerType.Familiar, value);
            assertEquals(sample.getValue(), property.getValue(sample));
        });
    }

    @Test(expected = NullPointerException.class)
    public void getValueByNull() {
        property.getValue(null);
    }

    @Test(expected = RuntimeException.class) // TODO: IllegalArgumentException.class
    public void getValueByForeign() {
        final Container sample = newContainer(ContainerType.Foreign, random.nextLong());
        assertEquals(sample.getValue(), property.getValue(sample));
    }

    @Test
    public void setValue() {
        forSeveralValues(value -> {
            final Container sample = newContainer(ContainerType.Familiar, null);
            property.setValue(sample, value);
            assertEquals(value, sample.getValue());
        });
    }

    protected enum ContainerType {
        Familiar,
        Foreign
    }
}
