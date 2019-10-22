package de.team33.libs.properties.v2;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class PlainProperty implements Property {

    private static final BiConsumer UNSUPPORTED_SETTER = (c, v) -> {
        throw new UnsupportedOperationException("setter is not supported for this property");
    };
    private static final Function UNSUPPORTED_GETTER = c -> {
        throw new UnsupportedOperationException("getter is not supported for this property");
    };

    private final String name;
    private final Function getter;
    private final BiConsumer setter;

    public <C, V> PlainProperty(final String name, final Function<C, V> getter, final BiConsumer<C, V> setter) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
    }

    public <C, V> PlainProperty(final String name, final Function<C, V> getter) {
        //noinspection unchecked
        this(name, getter, UNSUPPORTED_SETTER);
    }

    public <C, V> PlainProperty(final String name, final BiConsumer<C, V> setter) {
        //noinspection unchecked
        this(name, UNSUPPORTED_GETTER, setter);
    }

    @Override
    public final String getKey() {
        return name;
    }

    @Override
    public final Object getValue(final Object container) throws UnsupportedOperationException, NullPointerException {
        //noinspection unchecked
        return getter.apply(container);
    }

    @Override
    public final void setValue(final Object container, final Object value) throws UnsupportedOperationException, NullPointerException, ClassCastException, IllegalArgumentException, IllegalStateException {
        //noinspection unchecked
        setter.accept(container, value);
    }
}
