package de.team33.libs.properties.v1;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Abstracts a property
 *
 * @param <C> Container: The type to which the property belongs
 */
public interface Property<C> {

    /**
     * Creates a simple property based on the given arguments ...
     *
     * @param name the name of the property
     * @param getter a method to get the value of this property based on an instance of the underlying type.
     * @param setter a method to set the value of this property to an instance of the underlying type.
     */
    static <C, V> Property<C> simple(final String name, final Function<C, V> getter, final BiConsumer<C, V> setter) {
        return new Property<C>() {
            @Override
            public final String name() {
                return name;
            }

            @Override
            public final Object get(final C container) {
                return getter.apply(container);
            }

            @Override
            public final Property<C> set(final C container, final Object value) {
                //noinspection unchecked
                setter.accept(container, (V) value);
                return this;
            }
        };
    }

    static <C, V> BiConsumer<C, V> noSetter(final String name) {
        return (c, v) -> {
            throw new UnsupportedOperationException("setter is not supported for this property (\"" + name + "\")");
        };
    }

    static <C, V> Function<C, V> noGetter(final String name) {
        return c -> {
            throw new UnsupportedOperationException("getter is not supported for this property (\"" + name + "\")");
        };
    }

    /**
     * Creates a simple property that does not support a setter based on the given arguments ...
     *
     * @param name the name of the property
     * @param getter a method to get the value of this property based on an instance of the underlying type.
     */
    static <C, V> Property<C> simple(final String name, final Function<C, V> getter) {
        return simple(name, getter, noSetter(name));
    }

    /**
     * Creates a simple property that does not support a getter based on the given arguments ...
     *
     * @param name the name of the property
     * @param setter a method to set the value of this property to an instance of the underlying type.
     */
    static <C, V> Property<C> simple(final String name, final BiConsumer<C, V> setter) {
        return simple(name, noGetter(name), setter);
    }

    /**
     * Returns the name of this property.
     */
    String name();

    /**
     * Gets the value of this property from a specific container.
     *
     * @throws UnsupportedOperationException If the value of this property can not be retrieved.
     * @throws NullPointerException          If the given container is {@code null}.
     */
    Object get(C container) throws UnsupportedOperationException, NullPointerException;

    /**
     * Sets this property of a given container to a given value.
     *
     * @throws UnsupportedOperationException If the value of this property can not be set.
     * @throws NullPointerException          If the given container is {@code null} or if the value is
     *                                       {@code null} and therefore can not be assigned to this property.
     * @throws ClassCastException            If the value can not be assigned to this property because of its type.
     * @throws IllegalArgumentException      If the value can not be assigned to this property for any other reason
     *                                       that results from the given value.
     * @throws IllegalStateException         If the value can not be assigned to this property for any other reason
     *                                       that results from the given container.
     */
    Property<C> set(C container, Object value) throws UnsupportedOperationException, NullPointerException,
                                                      ClassCastException, IllegalArgumentException,
                                                      IllegalStateException;
}
