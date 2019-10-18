package de.team33.test.properties.v2;

import de.team33.libs.properties.v2.PlainProperty;
import de.team33.libs.properties.v2.Property;
import de.team33.test.properties.shared.Container;
import de.team33.test.properties.shared.Mutable;

public class PlainPropertyTest extends PropertyTestBase {

    private final Property property = new PlainProperty("value", Mutable::getValue, Mutable::setValue);

    @Override
    protected Property getProperty() {
        return property;
    }

    @Override
    protected Container newContainer(final Long value) {
        return new Mutable().setValue(value);
    }
}
