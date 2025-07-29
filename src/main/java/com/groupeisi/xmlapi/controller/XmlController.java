package com.groupeisi.xmlapi.controller;

import com.groupeisi.xmlapi.service.XmlService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/xml")
public class XmlController {

    private final XmlService xmlService;

    public XmlController(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    // 1. Upload fichier XML — bien indiquer que le endpoint consomme
    // multipart/form-data
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadXml(@RequestParam("file") MultipartFile file) {
        try {
            xmlService.saveXmlFile(file);
            return ResponseEntity.ok("Fichier XML uploadé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'upload : " + e.getMessage());
        }
    }

    // 2. Transformer le XML avec XSLT
    @GetMapping(value = "/transform", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> transformXml() {
        try {
            String transformed = xmlService.transformXmlWithXslt();
            return ResponseEntity.ok(transformed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur de transformation : " + e.getMessage());
        }
    }

    // 3. Lire le contenu XML actuel
    @GetMapping(value = "/content", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getContent() {
        try {
            return ResponseEntity.ok(xmlService.getXmlContent());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    // 4. Ajouter un élément
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> addElement(@RequestBody String newElementXml) {
        try {
            xmlService.addElement(newElementXml);
            return ResponseEntity.ok("Élément ajouté avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    // 5. Modifier un élément
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> updateElement(@RequestBody String updatedElementXml) {
        try {
            xmlService.updateElement(updatedElementXml);
            return ResponseEntity.ok("Élément modifié avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    // 6. Supprimer un élément par ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteElement(@PathVariable String id) {
        try {
            xmlService.deleteElementById(id);
            return ResponseEntity.ok("Élément supprimé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
}
