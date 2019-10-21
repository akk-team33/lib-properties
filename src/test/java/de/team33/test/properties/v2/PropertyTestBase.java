package de.team33.test.properties.v2;

import de.team33.libs.properties.v2.Property;
import de.team33.test.properties.shared.Container;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public abstract class PropertyTestBase {

    private final Random random = new Random();

    private Stream<Long> values() {
        return Stream.concat(Stream.of(0L, 1L, 5L, -7L, null), Stream.generate(random::nextLong))
                     .distinct().limit(100);
    }

    protected abstract Context getContext();

    @Test
    public final void name() {
        final Context context = getContext();
        Assert.assertEquals(context.expectedPropertyName, context.property.name());
    }

    @Test
    public final void direct() {
        final Context context = getContext();
        values().forEach(value -> {
            final Container sample = context.newContainer(ContainerType.Familiar, value);
            Assert.assertEquals(value, sample.getValue());
        });
    }

    @Test
    public void get() {
        final Context context = getContext();
        values().forEach(value -> {
            final Container sample = context.newContainer(ContainerType.Familiar, value);
            Assert.assertEquals(sample.getValue(), context.property.get(sample));
        });
    }

    @Test(expected = NullPointerException.class)
    public void getNull() {
        Optional.ofNullable(getContext())
                .map(ctx -> ctx.property)
                .orElseThrow(() -> new IllegalStateException("getContext().property must be present"))
                .get(null);
    }

    @Test(expected = ClassCastException.class)
    public void getForeign() {
        final Context context = getContext();
        final Container sample = context.newContainer(ContainerType.Foreign, random.nextLong());
        Assert.assertEquals(sample.getValue(), context.property.get(sample));
    }

    @Test
    public void set() {
        final Context context = getContext();
        values().forEach(value -> {
            final Container sample = context.newContainer(ContainerType.Familiar, null);
            context.property.set(sample, value);
            Assert.assertEquals(value, sample.getValue());
        });
    }

    protected enum ContainerType {
        Familiar,
        Foreign
    }

    protected static abstract class Context {

        private final String expectedPropertyName;
        private final Property property;

        protected Context(final String expectedPropertyName, final Property property) {
            this.expectedPropertyName = expectedPropertyName;
            this.property = property;
        }

        protected abstract Container newContainer(final ContainerType type, final Long value);
    }
}
