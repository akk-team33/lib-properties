package de.team33.libs.properties.v1;

import java.lang.reflect.Field;

public class FieldProperty<C> implements Property<C> {

    private static final String GET_FAILED = "cannot access <%s> within container of <%s>";
    private static final String SET_FAILED = "cannot set <%s> within container of <%s> to value of <%s>";

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
    public final String name() {
        return name;
    }

    @Override
    public final Object get(final C container) throws NullPointerException {
        try {
            return field.get(container);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException(String.format(GET_FAILED, field, container.getClass()), e);
        }
    }

    @Override
    public final C set(final C container, final Object value) throws UnsupportedOperationException, NullPointerException, ClassCastException, IllegalArgumentException, IllegalStateException {
        try {
            field.set(container, value);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException(String.format(SET_FAILED, field, container.getClass(), value.getClass()), e);
        }
        return container;
    }
}
