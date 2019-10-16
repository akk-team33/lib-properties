package de.team33.libs.properties.v1;

import java.lang.reflect.Field;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class FieldProperty<C> implements Property<C> {

    private static final String GET_FAILED = "cannot access <%s> within container of <%s>";

    private final String name;
    private final Field field;

    public FieldProperty(final String name, final Field field) {
        this.name = name;
        this.field = field;
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final Object get(final C container) throws UnsupportedOperationException, NullPointerException {
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
            throw new UnsupportedOperationException("not yet implemented", e);
        }
        throw new UnsupportedOperationException("not yet implemented");
    }

    public static class Source {

        private final Function<Class<?>, Stream<Field>> fields;
        private final BiFunction<Class<?>, Field, String> naming;

        public Source(final Function<Class<?>, Stream<Field>> fields,
                      final BiFunction<Class<?>, Field, String> naming) {
            this.fields = fields;
            this.naming = naming;
        }

        public final <C> Stream<FieldProperty<C>> stream(final Class<C> containerClass) {
            return fields.apply(containerClass)
                         .map(field -> new FieldProperty<>(naming.apply(containerClass, field), field));
        }
    }
}
