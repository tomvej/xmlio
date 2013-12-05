package org.tomvej.xmlio.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.tomvej.xmlio.ObjectFactory;
import org.tomvej.xmlio.XMLFactory;
import org.tomvej.xmlio.exceptions.XMLException;
import org.tomvej.xmlio.exceptions.XMLExceptionType;
import org.tomvej.xmlio.utils.XMLUtils;
import org.w3c.dom.Document;


public class TransformationContextImpl<T> implements TransformationContext<T> {
	private final XMLFactory<T>		xmlFact;
	private final ObjectFactory<T>	objFact;
	private final String				namespace;
	private final URL					schema;

	public TransformationContextImpl(XMLFactory<T> xmlFactory, ObjectFactory<T> objectFactory, URL schemaURL, String namespace) {
		xmlFact = xmlFactory;
		objFact = objectFactory;
		schema = schemaURL;
		this.namespace = (namespace != null) ? namespace : "";
	}

	public TransformationContextImpl(XMLFactory<T> xmlFactory, ObjectFactory<T> objectFactory, URL schemaURL)
			throws XMLException {
		this(xmlFactory, objectFactory, schemaURL, getNamespaceFromSchema(schemaURL));
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
			InputStream is = new FileInputStream(new File(schemaURL.toURI()));
			Document schema = XMLUtils.parseDocument(is, XMLUtils.getDocumentBuilder());
			return schema.getDocumentElement().getAttribute("targetNamespace");
		} catch (FileNotFoundException fnfe) {
			throw new XMLException(XMLExceptionType.SRC_OPEN, fnfe);
		} catch (URISyntaxException urise) {
			throw new XMLException(XMLExceptionType.SRC_OPEN, urise);
		}
	}
}
