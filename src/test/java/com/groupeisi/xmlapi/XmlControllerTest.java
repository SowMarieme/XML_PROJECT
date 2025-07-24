package com.groupeisi.xmlapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class XmlControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testUploadXml_shouldReturnSuccess() throws Exception {
		MockMultipartFile xmlFile = new MockMultipartFile(
				"file", "test.xml", "text/xml", "<root></root>".getBytes());

		mockMvc.perform(multipart("/api/xml/upload").file(xmlFile))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("uploadé avec succès")));
	}

	@Test
	void testTransform_shouldReturnXml() throws Exception {
		mockMvc.perform(get("/api/xml/transform"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/xml"));
	}
}
