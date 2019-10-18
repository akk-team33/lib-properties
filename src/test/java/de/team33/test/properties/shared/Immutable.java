package de.team33.test.properties.shared;

public class Immutable implements Container {

    private final Long value;

    public Immutable(final Long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }
}
