package com.groupeisi.xmlapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import java.io.*;

@Service
public class XmlService {

    private final File xmlFile = new File("uploaded.xml");  // stocke le fichier uploadé

    // 1. Sauvegarder fichier XML uploadé
    public void saveXmlFile(MultipartFile file) throws IOException {
        try (OutputStream os = new FileOutputStream(xmlFile)) {
            os.write(file.getBytes());
        }
    }

    // Charger XML en Document DOM
    private Document loadXmlDocument() throws Exception {
        if (!xmlFile.exists()) {
            throw new FileNotFoundException("Fichier XML non trouvé.");
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        try (InputStream is = new FileInputStream(xmlFile)) {
            return builder.parse(is);
        }
    }

    // Sauvegarder Document DOM dans fichier XML
    private void saveXmlDocument(Document doc) throws TransformerException, IOException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        try (FileOutputStream fos = new FileOutputStream(xmlFile)) {
            transformer.transform(new DOMSource(doc), new StreamResult(fos));
        }
    }

    // 2. Transformation XSLT sur le fichier XML
    public String transformXmlWithXslt() throws Exception {
        // 1. Charger le document XML
        Document doc = loadXmlDocument();

        // 2. Générer dynamiquement un contenu XSLT (exemple générique basé sur les balises enfants)
        String xsltContent = """
        <?xml version="1.0" encoding="UTF-8"?>
        <xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
          <xsl:output method="html" indent="yes"/>
          <xsl:template match="/">
            <html>
              <body>
                <h2>Contenu XML généré</h2>
                <ul>
                  <xsl:for-each select="*/*">
                    <li>
                      <xsl:for-each select="*">
                        <b><xsl:value-of select="name()"/>:</b>
                        <xsl:value-of select="."/>
                        <br/>
                      </xsl:for-each>
                    </li>
                  </xsl:for-each>
                </ul>
              </body>
            </html>
          </xsl:template>
        </xsl:stylesheet>
        """;

        // 3. Transformer via contenu XSLT dynamique
        TransformerFactory tf = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new StringReader(xsltContent));
        Transformer transformer = tf.newTransformer(xslt);

        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));

        return writer.toString();
    }


    // 3. Obtenir contenu XML en String (formaté)
    public String getXmlContent() throws Exception {
        Document doc = loadXmlDocument();
        StringWriter writer = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    // 4. Ajouter élément XML (fragment)
    public void addElement(String newElementXml) throws Exception {
        Document doc = loadXmlDocument();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document fragmentDoc = builder.parse(new InputSource(new StringReader(newElementXml)));
        Node importedNode = doc.importNode(fragmentDoc.getDocumentElement(), true);

        doc.getDocumentElement().appendChild(importedNode);

        saveXmlDocument(doc);
    }

    // 5. Modifier élément XML (recherche par tag racine)
    public void updateElement(String updatedElementXml) throws Exception {
        Document doc = loadXmlDocument();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document updatedDoc = builder.parse(new InputSource(new StringReader(updatedElementXml)));
        Element updatedElement = updatedDoc.getDocumentElement();

        String tagName = updatedElement.getTagName();
        String idToUpdate = updatedElement.getAttribute("id");

        if (idToUpdate == null || idToUpdate.isEmpty()) {
            throw new Exception("L'élément à modifier doit avoir un attribut id");
        }

        NodeList nodes = doc.getElementsByTagName(tagName);

        Element nodeToUpdate = null;

        for (int i = 0; i < nodes.getLength(); i++) {
            Element current = (Element) nodes.item(i);
            if (idToUpdate.equals(current.getAttribute("id"))) {
                nodeToUpdate = current;
                break;
            }
        }

        if (nodeToUpdate == null) {
            throw new Exception("Élément avec id=" + idToUpdate + " non trouvé");
        }

        Node parent = nodeToUpdate.getParentNode();
        Node importedNode = doc.importNode(updatedElement, true);

        parent.replaceChild(importedNode, nodeToUpdate);

        saveXmlDocument(doc);
    }


    // 6. Supprimer élément par id (XPath)
    public void deleteElementById(String id) throws Exception {
        Document doc = loadXmlDocument();

        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = String.format("//*[@id='%s']", id);
        Node nodeToDelete = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);

        if (nodeToDelete == null) {
            throw new Exception("Élément à supprimer non trouvé");
        }

        nodeToDelete.getParentNode().removeChild(nodeToDelete);
        saveXmlDocument(doc);
    }


}
