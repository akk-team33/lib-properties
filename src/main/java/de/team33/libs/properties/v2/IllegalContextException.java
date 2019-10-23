package de.team33.libs.properties.v2;

import static java.lang.String.format;

public class IllegalContextException extends IllegalArgumentException {

    private static final String DEFAULT_MESSAGE = "cannot get property \"%s\" from context of %s";

    public IllegalContextException(final Property property, final Object context, final Exception cause) {
        super(format(DEFAULT_MESSAGE, property.getKey(), (null == context) ? null : context.getClass()), cause);
    }
}
