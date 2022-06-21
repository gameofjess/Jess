package com.gameofjess.javachess.helper.configuration;

import java.util.Objects;

public class OtherConfig implements Config {

    final String someStringProperty = "Something!";
    final boolean someBooleanProperty = true;
    final int someIntProperty = 42;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OtherConfig that = (OtherConfig) o;
        return someBooleanProperty == that.someBooleanProperty && someIntProperty == that.someIntProperty && Objects.equals(someStringProperty, that.someStringProperty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(someStringProperty, someBooleanProperty, someIntProperty);
    }
}
