package de.team33.libs.properties.v2;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class PlainProperty<C, V> implements Property<C, V> {

    private static final BiConsumer UNSUPPORTED_SETTER = (c, v) -> {
        throw new UnsupportedOperationException("setter is not supported for this property");
    };
    private static final Function UNSUPPORTED_GETTER = c -> {
        throw new UnsupportedOperationException("getter is not supported for this property");
    };

    private final String name;
    private final Function<C, V> getter;
    private final BiConsumer<C, V> setter;

    public PlainProperty(final String name, final Function<C, V> getter, final BiConsumer<C, V> setter) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
    }

    public PlainProperty(final String name, final Function<C, V> getter) {
        //noinspection unchecked
        this(name, getter, UNSUPPORTED_SETTER);
    }

    public PlainProperty(final String name, final BiConsumer<C, V> setter) {
        //noinspection unchecked
        this(name, UNSUPPORTED_GETTER, setter);
    }

    @Override
    public final String getKey() {
        return name;
    }

    @Override
    public final V getValue(final C context) throws UnsupportedOperationException, NullPointerException, IllegalContextException {
        try {
            return getter.apply(context);
        } catch (final ClassCastException e) {
            throw new IllegalContextException(this, context, e);
        }
    }

    @Override
    public final void setValue(final C context, final V value) throws UnsupportedOperationException, NullPointerException, ClassCastException, IllegalArgumentException, IllegalStateException {
        setter.accept(context, value);
    }
}
