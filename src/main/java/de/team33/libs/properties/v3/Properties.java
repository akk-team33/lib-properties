package de.team33.libs.properties.v3;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Represents the properties of a specific type
 *
 * @param <T> The type whose properties are to be represented
 */
public class Properties<T> {

    private final Map<Key, Properties<T>> backing;

    private Properties(final Class<T> tClass) {
        backing = new HashMap<>(0);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Set<Key> getKeys() {
        return backing.keySet();
    }

    public static class Key {

        private final String name;

        Key(final String name) {
            this.name = Objects.requireNonNull(name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            return (this == obj) || ((obj instanceof Key) && name.equals(((Key) obj).name));
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class Factory {

        private Factory(final Builder builder) {
            // TODO
        }

        public final <T> Properties<T> from(final Class<T> tClass) {
            return new Properties<>(tClass);
        }
    }

    public static class Builder {

        public final Factory prepare() {
            return new Factory(this);
        }
    }
}
