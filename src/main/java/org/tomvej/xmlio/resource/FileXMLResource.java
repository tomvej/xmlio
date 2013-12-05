package org.tomvej.xmlio.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.tomvej.xmlio.exceptions.XMLException;
import org.tomvej.xmlio.exceptions.XMLExceptionType;


/**
 * Stores and loads objects of designated type to and from one file.
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 * @param <T>
 *            Type of objects for storing and loading.
 */
public abstract class FileXMLResource<T> extends StreamXMLResource<T> {

	/**
	 * Specifies context of transformation between objects and XML.
	 * 
	 * @param context
	 *            Context of transformation.
	 */
	public FileXMLResource(TransformationContext<T> context) {
		super(context);
	}

	/**
	 * Defines access to file used for storing and loading of objects.
	 * 
	 * @return Path to storage file.
	 */
	protected abstract File getFile();

	@Override
	protected InputStream openIpnut() throws XMLException {
		try {
			return new FileInputStream(getFile());
		} catch (FileNotFoundException fnfe) {
			throw new XMLException(XMLExceptionType.SRC_OPEN, fnfe);
		}
	}

	@Override
	protected OutputStream openOutput() throws XMLException {
		try {
			return new FileOutputStream(getFile());
		} catch (FileNotFoundException fnfe) {
			throw new XMLException(XMLExceptionType.SRC_OPEN, fnfe);
		}
	}
}
