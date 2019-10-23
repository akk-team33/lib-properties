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
     * @throws NullPointerException          If a context is required but {@code null} is given.
     * @throws IllegalContextException       If the given context does not support this property.
     */
    V getValue(C context) throws UnsupportedOperationException, NullPointerException, IllegalContextException;

    /**
     * Sets this property of a given context to a given value.
     *
     * @throws UnsupportedOperationException If the value of this property can not be set.
     * @throws NullPointerException          If a context is required but {@code null} is given.
     * @throws IllegalContextException       If the given context does not support this property.
     * @throws IllegalValueException         If the value can not be assigned to this property.
     */
    void setValue(C context, V value) throws UnsupportedOperationException, NullPointerException,
                                             IllegalContextException, IllegalValueException;
}
