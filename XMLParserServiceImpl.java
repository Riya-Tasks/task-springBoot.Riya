package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class XMLParserServiceImpl implements XMLParserService {

    @Override
    public Map<String, String> parseXMLFromResources(String fileName) {
        Map<String, String> dataMap = new HashMap<>();

        try {
            InputStream xmlFile = getClass().getClassLoader().getResourceAsStream(fileName);
            if (xmlFile == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList mappingConfigList = doc.getElementsByTagName("MappingConfiguration");

            for (int i = 0; i < mappingConfigList.getLength(); i++) {
                Node mappingConfigNode = mappingConfigList.item(i);

                if (mappingConfigNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element mappingConfigElement = (Element) mappingConfigNode;
                    String name = mappingConfigElement.getAttribute("name");

                    if ("MSS EOD Next".equals(name) || "MSS_EOD_Europe".equals(mappingConfigElement.getAttribute("base"))) {
                        NodeList mapList = mappingConfigElement.getElementsByTagName("Map");

                        for (int j = 0; j < mapList.getLength(); j++) {
                            Node mapNode = mapList.item(j);
                            if (mapNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element mapElement = (Element) mapNode;

                                NodeList entryList = mapElement.getElementsByTagName("entry");
                                for (int k = 0; k < entryList.getLength(); k++) {
                                    Node entryNode = entryList.item(k);

                                    if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element entryElement = (Element) entryNode;
                                        String key = entryElement.getAttribute("key");
                                        String value = entryElement.getAttribute("value");

                                        dataMap.put(key, value);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataMap;
    }
}
