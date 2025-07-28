package com.groupeisi.xmlapi;

import com.groupeisi.controller.XmlController;
import com.groupeisi.service.XmlService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class XmlControllerTest {

    @Test
    void testUploadXmlSuccess() throws Exception {
        // Arrange
        XmlService mockService = mock(XmlService.class);
        XmlController controller = new XmlController(mockService);

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.xml", "text/xml", "<root></root>".getBytes());

        // Act
        ResponseEntity<String> response = controller.uploadXml(mockFile);

        // Assert
        verify(mockService).saveXmlFile(mockFile);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Fichier XML uploadé avec succès.", response.getBody());
    }

    @Test
    void testUploadXmlFails() throws Exception {
        XmlService mockService = mock(XmlService.class);
        XmlController controller = new XmlController(mockService);

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.xml", "text/xml", "<root></root>".getBytes());

        doThrow(new RuntimeException("Erreur simulée")).when(mockService).saveXmlFile(any());

        ResponseEntity<String> response = controller.uploadXml(mockFile);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Erreur lors de l'upload"));
    }

    @Test
    void testTransformXmlSuccess() throws Exception {
        XmlService mockService = mock(XmlService.class);
        XmlController controller = new XmlController(mockService);

        when(mockService.transformXmlWithXslt()).thenReturn("<html>OK</html>");

        ResponseEntity<String> response = controller.transformXml();

        verify(mockService).transformXmlWithXslt();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("<html>OK</html>", response.getBody());
    }

    @Test
    void testTransformXmlFails() throws Exception {
        XmlService mockService = mock(XmlService.class);
        XmlController controller = new XmlController(mockService);

        when(mockService.transformXmlWithXslt()).thenThrow(new Exception("Erreur"));

        ResponseEntity<String> response = controller.transformXml();

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Erreur de transformation"));
    }

    @Test
    void testAddElementSuccess() throws Exception {
        XmlService mockService = mock(XmlService.class);
        XmlController controller = new XmlController(mockService);

        String xmlFragment = "<person id='1'><name>John</name></person>";

        ResponseEntity<String> response = controller.addElement(xmlFragment);

        verify(mockService).addElement(xmlFragment);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Élément ajouté avec succès.", response.getBody());
    }

    @Test
    void testAddElementFails() throws Exception {
        XmlService mockService = mock(XmlService.class);
        XmlController controller = new XmlController(mockService);

        String xmlFragment = "<person id='1'><name>John</name></person>";

        doThrow(new Exception("Erreur ajout")).when(mockService).addElement(xmlFragment);

        ResponseEntity<String> response = controller.addElement(xmlFragment);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Erreur"));
    }
}
