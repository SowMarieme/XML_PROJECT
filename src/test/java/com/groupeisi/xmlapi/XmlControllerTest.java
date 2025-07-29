package com.groupeisi.xmlapi;

import com.groupeisi.xmlapi.service.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/xml")
public class XmlControllerTest {

	@Autowired
	private XmlService xmlService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadXml(@RequestParam("file") MultipartFile file) {
		try {
			xmlService.saveXmlFile(file);
			return ResponseEntity.ok("Fichier uploadé avec succès");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erreur lors de l'upload : " + e.getMessage());
		}
	}

	@GetMapping("/transform")
	public ResponseEntity<String> transformXml() {
		try {
			String result = xmlService.transformXmlWithXslt();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erreur lors de la transformation : " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteElement(@PathVariable String id) {
		try {
			xmlService.deleteElementById(id);
			return ResponseEntity.ok("Élément supprimé avec succès");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erreur lors de la suppression : " + e.getMessage());
		}
	}
}
