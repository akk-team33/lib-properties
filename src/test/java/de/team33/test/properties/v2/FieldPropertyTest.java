package de.team33.test.properties.v2;

import de.team33.libs.properties.v2.FieldProperty;
import de.team33.test.properties.shared.Container;
import de.team33.test.properties.shared.Immutable;
import de.team33.test.properties.shared.Mutable;

public class FieldPropertyTest extends PropertyTestBase {

    @Override
    protected Context getContext() {
        try {
            return new Context("value", new FieldProperty(Immutable.class.getDeclaredField("value"))) {
                @Override
                protected Container newContainer(final ContainerType type, final Long value) {
                    switch (type) {
                        case Familiar:
                            return new Immutable(value);
                        default:
                            return new Mutable().setValue(value);
                    }
                }
            };
        } catch (final NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
