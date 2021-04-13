package com.epam.esm.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReaderHelper {

    public static String readFile(String fileName) throws URISyntaxException, IOException {

        ClassLoader classLoader = FileReaderHelper.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);

        byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
        return new String(bytes);

    }
}
