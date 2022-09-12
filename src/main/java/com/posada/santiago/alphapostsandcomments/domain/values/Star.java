package com.posada.santiago.alphapostsandcomments.domain.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Star implements ValueObject<String> {
    private final String value;

    public Star(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }


}
