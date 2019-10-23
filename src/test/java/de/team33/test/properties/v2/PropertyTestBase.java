package de.team33.test.properties.v2;

import de.team33.libs.properties.v2.IllegalContextException;
import de.team33.libs.properties.v2.Property;
import de.team33.test.properties.shared.Container;
import org.junit.Test;

import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@SuppressWarnings("unchecked")
public abstract class PropertyTestBase {

    protected final Random random = new Random();

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
    public final void getValueMissingContext() {
        fail("expected to fail but was " + property.getValue(null));
    }

    @Test(expected = IllegalContextException.class)
    public final void getValueByForeignContext() {
        final Foreign sample = new Foreign(random.nextLong());
        fail("expected to fail but was " + property.getValue(sample));
    }

    @Test
    public final void setValue() {
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

    private static class Foreign {

        private final Long value;

        public Foreign(final Long value) {
            this.value = value;
        }

        public Long getValue() {
            return value;
        }
    }
}
