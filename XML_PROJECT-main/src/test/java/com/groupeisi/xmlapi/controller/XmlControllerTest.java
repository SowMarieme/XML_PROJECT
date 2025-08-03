package com.groupeisi.xmlapi.controller;

import com.groupeisi.xmlapi.service.XmlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class XmlControllerTest {

    private XmlService xmlService;
    private XmlController xmlController;

    @BeforeEach
    void setUp() {
        xmlService = mock(XmlService.class); // Mockito
        xmlController = new XmlController(xmlService);
    }

    // 1. Test de la méthode GET /content
    @Test
    void testGetContent_success() throws Exception {
        String fakeXml = "<root><data>ok</data></root>";
        when(xmlService.getXmlContent()).thenReturn(fakeXml);

        ResponseEntity<String> response = xmlController.getContent();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(fakeXml, response.getBody());
        verify(xmlService, times(1)).getXmlContent();
    }

    @Test
    void testGetContent_error() throws Exception {
        when(xmlService.getXmlContent()).thenThrow(new RuntimeException("Erreur interne"));

        ResponseEntity<String> response = xmlController.getContent();

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Erreur"));
        verify(xmlService, times(1)).getXmlContent();
    }

    // 2. Test de la méthode PUT /update
    @Test
    void testUpdateElement_success() throws Exception {
        String updatedXml = "<item id=\"123\">Nouveau contenu</item>";

        ResponseEntity<String> response = xmlController.updateElement(updatedXml);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Élément modifié avec succès.", response.getBody());
        verify(xmlService, times(1)).updateElement(updatedXml);
    }

    @Test
    void testUpdateElement_error() throws Exception {
        String updatedXml = "<item id=\"123\">...</item>";
        doThrow(new RuntimeException("Échec modification")).when(xmlService).updateElement(updatedXml);

        ResponseEntity<String> response = xmlController.updateElement(updatedXml);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Erreur"));
        verify(xmlService).updateElement(updatedXml);
    }

    // 3. Test de la méthode DELETE /delete/{id}
    @Test
    void testDeleteElement_success() throws Exception {
        String id = "456";

        ResponseEntity<String> response = xmlController.deleteElement(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Élément supprimé avec succès.", response.getBody());
        verify(xmlService).deleteElementById(id);
    }

    @Test
    void testDeleteElement_error() throws Exception {
        String id = "999";
        doThrow(new RuntimeException("Non trouvé")).when(xmlService).deleteElementById(id);

        ResponseEntity<String> response = xmlController.deleteElement(id);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Erreur"));
        verify(xmlService).deleteElementById(id);
    }
}
