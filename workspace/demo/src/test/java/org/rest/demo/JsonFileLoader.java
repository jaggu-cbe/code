package org.rest.demo;

import java.io.IOException;
import java.io.InputStream;

import org.rest.demo.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFileLoader {

    public User[] loadTestJson(String fileName) throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream= classLoader.getResourceAsStream(fileName);
        return new ObjectMapper().readValue(inputStream, User[].class);
    }
}
