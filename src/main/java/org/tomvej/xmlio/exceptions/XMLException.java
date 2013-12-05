package org.tomvej.xmlio.exceptions;

import org.tomvej.xmlio.XMLResource;

/**
 * Exception thrown during storing and loading of objects to and from XML.
 * 
 * There is only a fixed number of errors possible during such transformation
 * (whose messages really have very limited information value). Thus, their type
 * is specified by {@link XMLExceptionType}.
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * @see XMLResource
 */
public class XMLException extends Exception {
	private XMLExceptionType	type;

	/**
	 * Create exception of given type.
	 * 
	 * @param type
	 *            Type of occurred error.
	 */
	public XMLException(XMLExceptionType type) {
		this.type = type;
	}

	/**
	 * Create exception of given type and cause.
	 * 
	 * @param type
	 *            Type of occurred error.
	 * @param cause
	 *            Exception causing occurred error.
	 */
	public XMLException(XMLExceptionType type, Throwable cause) {
		super(cause);
		this.type = type;
	}

	/**
	 * Return type of exception.
	 * 
	 * @return Type of error causing this exception.
	 */
	public XMLExceptionType getType() {
		return type;
	}
}
