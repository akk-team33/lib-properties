package de.team33.test.properties.v2;

import de.team33.libs.properties.v2.FieldProperty;
import de.team33.test.properties.shared.Container;
import de.team33.test.properties.shared.Immutable;

public class FieldPropertyTest extends PropertyTestBase {

    public FieldPropertyTest() throws NoSuchFieldException {
        super("value", new FieldProperty(Immutable.class.getDeclaredField("value")));
    }

    @Override
    protected Container newContext(final Long value) {
        return new Immutable(value);
    }
}
