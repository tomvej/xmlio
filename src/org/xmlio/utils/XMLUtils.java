package org.xmlio.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlio.exceptions.XMLException;
import org.xmlio.exceptions.XMLExceptionType;


public class XMLUtils {
	private static final String	SCHEMA_XMLNS	= "http://www.w3.org/2001/XMLSchema";
	
	private XMLUtils() {}

	public static DocumentBuilder getDocumentBuilder() throws XMLException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			return dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			throw new XMLException(XMLExceptionType.UNDEFINED, pce);
		}
	}

	public static Validator getValidator(URL schemaURL) throws XMLException {
		SchemaFactory schFact = SchemaFactory.newInstance(SCHEMA_XMLNS);
		Schema schema;
		try {
			schema = schFact.newSchema(schemaURL);
		} catch (SAXException saxe) {
			throw new XMLException(XMLExceptionType.SCHEMA, saxe);
		}
		return schema.newValidator();
	}

	public static Document parseDocument(InputStream is, DocumentBuilder docBuild)
			throws XMLException {
		try {
			return docBuild.parse(is);
		} catch (SAXException saxe) {
			throw new XMLException(XMLExceptionType.PARSE, saxe);
		} catch (IOException ioe) {
			throw new XMLException(XMLExceptionType.PARSE_IO, ioe);
		}
	}

	public static Document validateDocument(Document src, Validator valid)
			throws XMLException {
		DOMResult result = new DOMResult();
		try {
			valid.validate(new DOMSource(src), result);
		} catch (SAXException saxe) {
			throw new XMLException(XMLExceptionType.VALIDATION, saxe);
		} catch (IOException ioe) {
			throw new XMLException(XMLExceptionType.VALIDATION_IO, ioe);
		}
		return (Document) result.getNode();
	}

	public static void removeEmptyNodes(Document target) throws XMLException {
		/*
		 * http://stackoverflow.com/questions/978810/how-to-strip-whitespace-only
		 * -text-nodes-from-a-dom-before-serialization
		 */
		XPathFactory fact = XPathFactory.newInstance();
		XPathExpression expr;
		try {
			expr = fact.newXPath().compile("//text()[normalize-space(.) = '']");
		} catch (XPathExpressionException xpee) {
			throw new XMLException(XMLExceptionType.UNDEFINED, xpee);
		}
		NodeList emptyNodes;
		try {
			emptyNodes = (NodeList) expr.evaluate(target, XPathConstants.NODESET);
		} catch (XPathExpressionException xpee) {
			throw new XMLException(XMLExceptionType.UNDEFINED, xpee);
		}
		for (int i = 0; i < emptyNodes.getLength(); i++) {
			Node empty = emptyNodes.item(i);
			empty.getParentNode().removeChild(empty);
		}
	}

	public static Transformer getTransformer() throws XMLException {
		TransformerFactory fact = TransformerFactory.newInstance();

		try {
			Transformer result = fact.newTransformer();
			result.setOutputProperty(OutputKeys.INDENT, "yes");
			return result;
		} catch (TransformerConfigurationException tce) {
			throw new XMLException(XMLExceptionType.UNDEFINED, tce);
		}
	}

	public static void printDocument(Transformer trans, Document doc, OutputStream os)
			throws XMLException {
		try {
			trans.transform(new DOMSource(doc), new StreamResult(os));
		} catch (TransformerException te) {
			throw new XMLException(XMLExceptionType.UNDEFINED, te);
		}
	}

	public static void closeStream(Closeable stream) throws XMLException {
		try {
			stream.close();
		} catch (IOException ioe) {
			throw new XMLException(XMLExceptionType.SRC_CLOSE, ioe);
		}
	}

}
