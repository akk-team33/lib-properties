package de.team33.libs.properties.v2;

import java.lang.reflect.Field;

public class FieldProperty implements Property {

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
    public Object getValue(final Object container) throws UnsupportedOperationException, NullPointerException {
        try {
            return field.get(container);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    public void setValue(final Object container, final Object value) throws UnsupportedOperationException, NullPointerException, ClassCastException, IllegalArgumentException, IllegalStateException {
        try {
            field.set(container, value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
