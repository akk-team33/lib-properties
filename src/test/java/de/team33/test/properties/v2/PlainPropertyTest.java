package de.team33.test.properties.v2;

import de.team33.libs.properties.v2.PlainProperty;
import de.team33.test.properties.shared.Container;
import de.team33.test.properties.shared.Immutable;
import de.team33.test.properties.shared.Mutable;

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
}
