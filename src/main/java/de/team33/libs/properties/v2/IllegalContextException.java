package de.team33.libs.properties.v2;

import static java.lang.String.format;

public class IllegalContextException extends IllegalArgumentException {

    private static final String GET_MESSAGE = "cannot get property \"%s\" from context of %s";
    private static final String SET_MESSAGE = "cannot set property \"%s\" from context of %s to value of %s";

    public IllegalContextException(final Property property, final Object context, final Exception cause) {
        super(format(GET_MESSAGE, property.getKey(), (null == context) ? null : context.getClass()), cause);
    }

    public IllegalContextException(final Property property, final Object context, final Object value,
                                   final Throwable cause) {
        super(format(SET_MESSAGE, property.getKey(),
                (null == context) ? null : context.getClass(), (null == value) ? null : value.getClass()), cause);
    }
}
