package com.ey.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloWorldResponse
{
    @JsonProperty("BP hello")
    private final String name;

    public HelloWorldResponse( final String name ) {
        this.name = name;
    }
}
