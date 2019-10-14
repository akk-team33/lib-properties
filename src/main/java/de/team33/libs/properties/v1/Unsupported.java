package de.team33.libs.properties.v1;

import java.util.function.BiConsumer;
import java.util.function.Function;

final class Unsupported {

    static <C, V> BiConsumer<C, V> setter(final String name) {
        return (c, v) -> {
            throw new UnsupportedOperationException("setter is not supported for this property (\"" + name + "\")");
        };
    }

    static <C, V> Function<C, V> getter(final String name) {
        return c -> {
            throw new UnsupportedOperationException("getter is not supported for this property (\"" + name + "\")");
        };
    }
}
