package de.team33.test.properties.v2;

import de.team33.libs.properties.v2.PlainProperty;
import de.team33.libs.properties.v2.Property;
import de.team33.test.properties.shared.Container;
import de.team33.test.properties.shared.Immutable;
import de.team33.test.properties.shared.Mutable;
import org.junit.Test;

import static org.junit.Assert.fail;

public class PlainPropertyTest extends PropertyTestBase {

    public PlainPropertyTest() {
        super("value", new PlainProperty<>("value", Mutable::getValue, Mutable::setValue));
    }

    @Override
    protected Container newContainer(final ContainerType type, final Long value) {
        switch (type) {
            case Familiar:
                return new Mutable().setValue(value);
            default:
                return new Immutable(value);
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getValueUnsupported() {
        final Property<Mutable, Long> property = new PlainProperty<>("value", Mutable::setValue);
        final Mutable sample = new Mutable().setValue(random.nextLong());
        fail("expected to fail but was " + property.getValue(sample));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setValueUnsupported() {
        final Property<Mutable, Long> property = new PlainProperty<>("value", Mutable::getValue);
        final Mutable sample = new Mutable().setValue(null);
        property.setValue(sample, random.nextLong());
        fail("expected to fail but was " + sample.getValue());
    }
}
