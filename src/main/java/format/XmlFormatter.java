package format;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Pretty-print xml, supplied as a string.
 * <p/>
 * eg.
 * <code>
 * String formattedXml = XmlFormatter.toPrettyString("<tag><nested>hello</nested></tag>");
 * </code>
 */
public class XmlFormatter {

    private static final String UTF_8 = "UTF-8";
    private static final int DEFAULT_INDENT = 4;

    /**
     * @param xml XML string
     * @return pretty formatted XML string with default indent
     */
    public static String toPrettyString(String xml) {
        return toPrettyString(xml, DEFAULT_INDENT);
    }

    /**
     * @param src    XML string
     * @param indent indent
     * @return pretty formatted XML string with given indent
     */
    public static String toPrettyString(String src, int indent) {
        StringWriter writer = new StringWriter();

        try {
            // Turn xml string into a document
            Document document = parseXml(src);

            // Remove whitespaces outside tags
            normalizeSpace(document);

            // Setup pretty print options
            Transformer transformer = createTransformer(indent);

            transformer.transform(new DOMSource(document), new StreamResult(writer));

        } catch (IOException
                | SAXException
                | ParserConfigurationException
                | XPathExpressionException
                | TransformerException e) {
            throw new RuntimeException(e);
        }

        return writer.toString();
    }

    private static void normalizeSpace(Document document) throws XPathExpressionException {
        document.normalize();

        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodeList = (NodeList) xPath.evaluate(
                "//text()[normalize-space()='']",
                document,
                XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node node = nodeList.item(i);
            node.getParentNode().removeChild(node);
        }
    }

    private static Document parseXml(String src) throws SAXException, IOException, ParserConfigurationException {
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new ByteArrayInputStream(src.getBytes(UTF_8))));
    }

    private static Transformer createTransformer(int indent) throws TransformerConfigurationException {
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setAttribute("indent-number", indent);

        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, UTF_8);
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        return transformer;
    }
}