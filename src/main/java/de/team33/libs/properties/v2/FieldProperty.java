package de.team33.libs.properties.v2;

import java.lang.reflect.Field;

public class FieldProperty<C, V> implements Property<C, V> {

    private final String name;
    private final Field field;

    public FieldProperty(final Field field) {
        this(field.getName(), field);
    }

    public FieldProperty(final String name, final Field field) {
        field.setAccessible(true);
        this.name = name;
        this.field = field;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public V getValue(final C context) throws UnsupportedOperationException, NullPointerException {
        try {
            //noinspection unchecked
            return (V) field.get(context);
        } catch (final IllegalAccessException | IllegalArgumentException e) {
            throw new IllegalContextException(this, context, e);
        }
    }

    @Override
    public void setValue(final C context, final V value) throws UnsupportedOperationException, NullPointerException, ClassCastException, IllegalArgumentException, IllegalStateException {
        try {
            field.set(context, value);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
