package de.team33.test.properties.shared;

public class Mutable implements Container {

    private Long value;

    @Override
    public Long getValue() {
        return value;
    }

    public Mutable setValue(final Long value) {
        this.value = value;
        return this;
    }
}
