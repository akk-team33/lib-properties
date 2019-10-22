package de.team33.libs.properties.v2;

/**
 * Abstracts a property as a key-value pair.
 */
public interface Property<C, V> {

    /**
     * Returns the key of this property.
     */
    String getKey();

    /**
     * Gets the value of this property from a specific context.
     *
     * @throws UnsupportedOperationException If the value of this property can not be retrieved.
     * @throws NullPointerException          If the given context is {@code null}.
     */
    V getValue(C context) throws UnsupportedOperationException, NullPointerException;

    /**
     * Sets this property of a given context to a given value.
     *
     * @throws UnsupportedOperationException If the value of this property can not be set.
     * @throws NullPointerException          If the given context is {@code null} or if the value is
     *                                       {@code null} and therefore can not be assigned to this property.
     * @throws ClassCastException            If the value can not be assigned to this property because of its type.
     * @throws IllegalArgumentException      If the value can not be assigned to this property for any other reason
     *                                       that results from the given value.
     * @throws IllegalStateException         If the value can not be assigned to this property for any other reason
     *                                       that results from the given context.
     */
    void setValue(C context, V value) throws UnsupportedOperationException, NullPointerException,
                                             ClassCastException, IllegalArgumentException,
                                             IllegalStateException;
}
