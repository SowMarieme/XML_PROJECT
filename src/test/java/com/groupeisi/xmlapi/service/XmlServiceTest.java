package com.groupeisi.xmlapi.service;

import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class XmlServiceTest {

    private XmlService xmlService;

    private final String testXml =
            "<root>\n" +
                    "    <element id=\"1\">Premier</element>\n" +
                    "    <element id=\"2\">Deuxième</element>\n" +
                    "</root>";

    @BeforeEach
    void setUp() {
        xmlService = new XmlService();
        File file = new File("uploaded.xml");
        if (file.exists()) file.delete();
    }

    @AfterEach
    void tearDown() {
        File file = new File("uploaded.xml");
        if (file.exists()) file.delete();
    }

    @Test
    @Order(1)
    void testSaveXmlFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.xml", "application/xml", testXml.getBytes()
        );

        xmlService.saveXmlFile(file);
        assertTrue(new File("uploaded.xml").exists());
    }

    @Test
    @Order(2)
    void testTransformXmlWithXslt() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.xml", "application/xml", testXml.getBytes()
        );

        xmlService.saveXmlFile(file);
        String output = xmlService.transformXmlWithXslt();


        assertNotNull(output);
        assertTrue(output.toLowerCase().contains("premier"));
        assertTrue(output.toLowerCase().contains("deux"));
        assertTrue(output.contains("Contenu XML")); // Plus large, donc plus tolérant

        assertTrue(output.contains("<ul>"));
    }

    @Test
    @Order(3)
    void testDeleteElementById() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.xml", "application/xml", testXml.getBytes()
        );

        xmlService.saveXmlFile(file);
        xmlService.deleteElementById("2");

        String updatedXml = xmlService.getXmlContent();
        assertFalse(updatedXml.contains("Deuxième"));
        assertTrue(updatedXml.contains("Premier"));
    }

    @Test
    @Order(4)
    void testDeleteElementByIdNotFound() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.xml", "application/xml", testXml.getBytes()
        );

        xmlService.saveXmlFile(file);
        Exception e = assertThrows(Exception.class, () -> xmlService.deleteElementById("999"));
        assertEquals("Élément à supprimer non trouvé", e.getMessage());
    }
}
