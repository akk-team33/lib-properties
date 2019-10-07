package de.team33.libs.properties.v1;

/**
 * Abstracts a property
 *
 * @param <C> Container: The type to which the property belongs
 * @param <V> Value: The type of a value of the property
 */
public interface Property<C, V> {

    /**
     * Returns the name of this property.
     */
    String name();

    /**
     * Gets the value of this property from a specific container.
     *
     * @throws UnsupportedOperationException If the value of this property can not be retrieved.
     */
    V get(C container) throws UnsupportedOperationException, NullPointerException;

    /**
     * Sets this property of a given container to a given value.
     *
     * @throws UnsupportedOperationException If the value of this property can not be set.
     * @throws NullPointerException          If the given container is {@code null} or if the value is
     *                                       {@code null} and therefore can not be assigned to this property.
     * @throws ClassCastException            If the value can not be assigned to this property because of its type.
     * @throws IllegalArgumentException      If the value of this property can not be assigned for any other reason
     *                                       that results from the given value.
     * @throws IllegalStateException         If the value of this property can not be assigned for any other reason
     *                                       that results from the given container.
     */
    void set(C container, V value) throws UnsupportedOperationException, NullPointerException, ClassCastException,
                                          IllegalArgumentException, IllegalStateException;
}
