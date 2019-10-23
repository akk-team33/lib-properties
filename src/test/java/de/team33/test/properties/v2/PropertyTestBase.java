package de.team33.test.properties.v2;

import de.team33.libs.properties.v2.IllegalContextException;
import de.team33.libs.properties.v2.IllegalValueException;
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

    protected abstract Container newContext(final Long value);

    @Test
    public final void consistence() {
        forSeveralValues(value -> assertEquals(value, newContext(value).getValue()));
    }

    @Test
    public final void getKey() {
        assertEquals(expectedPropertyName, property.getKey());
    }

    @Test
    public final void getValue() {
        forSeveralValues(value -> {
            final Container sample = newContext(value);
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
            final Container sample = newContext(null);
            property.setValue(sample, value);
            assertEquals(value, sample.getValue());
        });
    }

    @Test(expected = NullPointerException.class)
    public final void setValueMissingContext() {
        final Container sample = null;
        property.setValue(sample, random.nextLong());
        fail("expected to fail but was " + sample.getValue());
    }

    @Test(expected = IllegalContextException.class)
    public final void setValueByForeignContext() {
        final Foreign sample = new Foreign(null);
        property.setValue(sample, random.nextLong());
        fail("expected to fail but was " + sample.getValue());
    }

    @Test(expected = IllegalValueException.class)
    public final void setValueByForeignValue() {
        final Container sample = newContext(null);
        property.setValue(sample, random.nextDouble());
        fail("expected to fail but was " + sample.getValue());
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
