package org.tomvej.xmlio.resource;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.validation.Validator;

import org.apache.commons.lang3.Validate;
import org.tomvej.xmlio.XMLResource;
import org.tomvej.xmlio.exceptions.XMLException;
import org.tomvej.xmlio.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

	private T root;
	private final TransformationContext<T> context;

	/**
	 * Specifies context of transformation between objects and XML.
	 * 
	 * @param context
	 *            Context of transformation.
	 * @see TransformationContext
	 */
	public StreamXMLResource(TransformationContext<T> context) {
		Validate.notNull(context);
		this.context = context;
	}

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
		if (context.getObjectFactory() == null) {
			throw new UnsupportedOperationException(
					"Load operation is not supported by transformation context.");
		}

		Validator valid = null;
		if (context.getXMLSchema() != null) {
			valid = XMLUtils.getValidator(context.getXMLSchema());
		}

		InputStream is = openIpnut();
		try {
			Document doc = XMLUtils.parseDocument(is,
					XMLUtils.getDocumentBuilder());
			Document input;
			if (valid != null) {
				input = XMLUtils.validateDocument(doc, valid);
			} else {
				input = doc;
			}
			input.normalize();
			XMLUtils.removeEmptyNodes(input);
			setRoot(context.getObjectFactory().get(input.getDocumentElement()));
		} finally {
			XMLUtils.closeStream(is);
		}
	}

	@Override
	public void store() throws XMLException {
		if (context.getXMLFactory() == null) {
			throw new UnsupportedOperationException(
					"Store operation is not supported by transformation context.");
		}
		if (root == null) {
			throw new IllegalStateException("No object to be stored.");
		}
		Document doc = XMLUtils.getDocumentBuilder().newDocument();
		Element target = context.getXMLFactory().toXML(root, doc);
		setNamespace(target);
		doc.appendChild(target);

		OutputStream os = openOutput();
		try {
			XMLUtils.printDocument(XMLUtils.getTransformer(), doc, os);
		} finally {
			XMLUtils.closeStream(os);
		}
	}

	private void setNamespace(Element target) {
		target.setAttribute("xmlns", context.getXMLNamespace());
	}

}
