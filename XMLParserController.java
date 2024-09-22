package com.example.demo.controller;

import com.example.demo.service.XMLParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class XMLParserController {

    private final XMLParserService xmlParserService;

    @Autowired
    public XMLParserController(XMLParserService xmlParserService) {
        this.xmlParserService = xmlParserService;
    }

    @GetMapping("/parse-xml")
    public Map<String, String> parseXML(@RequestParam String fileName) {
        return xmlParserService.parseXMLFromResources(fileName);
    }
}
