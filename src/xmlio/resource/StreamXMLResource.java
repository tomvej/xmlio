package xmlio.resource;

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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xmlio.ObjectFactory;
import xmlio.XMLFactory;
import xmlio.XMLResource;
import xmlio.exceptions.XMLException;
import xmlio.exceptions.XMLExceptionType;

/**
 * Stores and loads object of designated type to and from a stream. Implements
 * loading/storing machinery. When loading, validates the object against a given
 * schema.
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 * @param <T>
 *            Type of objects for storing and loading.
 */
public abstract class StreamXMLResource<T> implements XMLResource<T> {
	private static final String	SCHEMA_XMLNS	= "http://www.w3.org/2001/XMLSchema";

	/**
	 * Defines a way of access to input.
	 * 
	 * @return Newly opened input stream.
	 * @throws XMLException
	 *             when input could not be accessed.
	 */
	protected abstract InputStream openIpnut() throws XMLException;

	/**
	 * Defines a way of access to output.
	 * 
	 * @return Newly opened output stream.
	 * @throws XMLException
	 *             when output could not be accessed.
	 */
	protected abstract OutputStream openOutput() throws XMLException;

	/**
	 * Defines transformation of objects into XML objects.
	 * 
	 * @return Factory transforming objects of designated type into XML objects.
	 */
	protected abstract XMLFactory<T> getXMLFactory();

	/**
	 * Defines transformation of XML objects into objects.
	 * 
	 * @return Factory transforming XML objects into objects of designated type.
	 */
	protected abstract ObjectFactory<T> getObjectFactory();

	/**
	 * Defines XML namespace of given type. Should be consistent with
	 * {@link StreamXMLResource#getXMLSchema()}.
	 * 
	 * @return Name of XML namespace.
	 */
	protected abstract String getXMLNamespace();

	/**
	 * Defines access to XML namespace of given type.
	 * 
	 * @return Path to XML schema associated with given type.
	 */
	protected abstract URL getXMLSchema();

	private T	root;

	@Override
	public T getRoot() {
		return root;
	}

	@Override
	public void setRoot(T root) {
		this.root = root;
	}

	@Override
	public void load() throws XMLException {
		Validator valid = getValidator();
		InputStream is = openIpnut();
		try {
			Document doc = parseDocument(is, getDocumentBuilder());
			Document input = validateDocument(doc, valid);
			input.normalize();
			removeEmptyNodes(input);
			setRoot(getObjectFactory().get(input.getDocumentElement()));
		} finally {
			closeStream(is);
		}
	}

	private Validator getValidator() throws XMLException {
		SchemaFactory schFact = SchemaFactory.newInstance(SCHEMA_XMLNS);
		Schema schema;
		try {
			schema = schFact.newSchema(getXMLSchema());
		} catch (SAXException saxe) {
			throw new XMLException(XMLExceptionType.UNDEFINED, saxe);
		}
		return schema.newValidator();
	}

	private DocumentBuilder getDocumentBuilder() throws XMLException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			return dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			throw new XMLException(XMLExceptionType.UNDEFINED, pce);
		}
	}

	private Document parseDocument(InputStream is, DocumentBuilder docBuild)
			throws XMLException {
		try {
			return docBuild.parse(is);
		} catch (SAXException saxe) {
			throw new XMLException(XMLExceptionType.UNDEFINED, saxe);
		} catch (IOException ioe) {
			throw new XMLException(XMLExceptionType.UNDEFINED, ioe);
		}
	}

	private Document validateDocument(Document src, Validator valid)
			throws XMLException {
		DOMResult result = new DOMResult();
		try {
			valid.validate(new DOMSource(src), result);
		} catch (SAXException saxe) {
			throw new XMLException(XMLExceptionType.VALIDATION, saxe);
		} catch (IOException ioe) {
			throw new XMLException(XMLExceptionType.UNDEFINED, ioe);
		}
		return (Document) result.getNode();
	}

	private void removeEmptyNodes(Document target) throws XMLException {
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

	@Override
	public void store() throws XMLException {
		if (root == null) {
			throw new IllegalStateException("No object to be stored.");
		}
		Document doc = getDocumentBuilder().newDocument();
		Element target = getXMLFactory().toXML(root, doc);
		setNamespace(target);
		doc.appendChild(target);

		OutputStream os = openOutput();
		try {
			printDocument(getTransformer(), doc, os);
		} finally {
			closeStream(os);
		}
	}

	private void setNamespace(Element target) {
		target.setAttribute("xmlns", getXMLNamespace());
	}

	private Transformer getTransformer() throws XMLException {
		TransformerFactory fact = TransformerFactory.newInstance();

		try {
			Transformer result = fact.newTransformer();
			result.setOutputProperty(OutputKeys.INDENT, "yes");
			return result;
		} catch (TransformerConfigurationException tce) {
			throw new XMLException(XMLExceptionType.UNDEFINED, tce);
		}
	}

	private void printDocument(Transformer trans, Document doc, OutputStream os)
			throws XMLException {
		try {
			trans.transform(new DOMSource(doc), new StreamResult(os));
		} catch (TransformerException te) {
			throw new XMLException(XMLExceptionType.UNDEFINED, te);
		}
	}

	private void closeStream(Closeable stream) throws XMLException {
		try {
			stream.close();
		} catch (IOException ioe) {
			throw new XMLException(XMLExceptionType.SRC_CLOSE, ioe);
		}
	}
}
