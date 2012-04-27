package xmlio.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import org.w3c.dom.Document;

import xmlio.ObjectFactory;
import xmlio.XMLFactory;
import xmlio.exceptions.XMLException;
import xmlio.exceptions.XMLExceptionType;
import xmlio.utils.XMLUtils;

public class TransformationContextImpl<T> implements TransformationContext<T> {
	private XMLFactory<T>		xmlFact;
	private ObjectFactory<T>	objFact;
	private String				namespace;
	private URL					schema;

	public TransformationContextImpl(XMLFactory<T> xmlFactory, ObjectFactory<T> objectFactory, URL schemaURL, String namespace) {
		xmlFact = xmlFactory;
		objFact = objectFactory;
		schema = schemaURL;
		this.namespace = namespace;
	}

	public TransformationContextImpl(XMLFactory<T> xmlFactory, ObjectFactory<T> objectFactory, URL schemaURL)
			throws XMLException {
		xmlFact = xmlFactory;
		objFact = objectFactory;
		schema = schemaURL;
		namespace = getNamespaceFromSchema(schemaURL);
	}

	@Override
	public XMLFactory<T> getXMLFactory() {
		return xmlFact;
	}

	@Override
	public ObjectFactory<T> getObjectFactory() {
		return objFact;
	}

	@Override
	public String getXMLNamespace() {
		return namespace;
	}

	@Override
	public URL getXMLSchema() {
		return schema;
	}

	public static String getNamespaceFromSchema(URL schemaURL)
			throws XMLException {
		try {
			InputStream is = new FileInputStream(new File(schemaURL.toString()));
			Document schema = XMLUtils.parseDocument(is, XMLUtils.getDocumentBuilder());
			return schema.getDocumentElement().getAttribute("targetNamespace");
		} catch (FileNotFoundException fnfe) {
			throw new XMLException(XMLExceptionType.SRC_OPEN, fnfe);
		}

	}
}
