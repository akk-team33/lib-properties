package de.team33.test.properties.v3;

import de.team33.libs.properties.v3.Properties;
import de.team33.test.properties.shared.Mutable;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class PropertiesTest {

    @Test
    public void getKeys() {
        final Properties<Mutable> properties = Properties.builder().prepare().from(Mutable.class);
        Assert.assertEquals(new HashSet<>(Arrays.asList("value")),
                            properties.getKeys().stream()
                                      .map(Properties.Key::toString)
                                      .collect(Collectors.toSet()));
    }
}