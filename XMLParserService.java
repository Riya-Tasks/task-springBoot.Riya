package com.example.demo.service;

import java.util.Map;

public interface XMLParserService {

    /**
     * Parse XML file from the resources and return a map of key-value pairs.
     *
     * @param fileName The name of the XML file in the resources folder.
     * @return A map containing the parsed key-value pairs from the XML file.
     */
    Map<String, String> parseXMLFromResources(String fileName);
}
