package org.xmlio.exceptions;

import org.xmlio.ObjectFactory;

/**
 * Thrown when argument expected by {@link ObjectFactory#get(org.w3c.dom.Node)}
 * is not created from XML document valid w.r.t schema associated schema.
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 */
public class XMLInvalidException extends RuntimeException {
	public XMLInvalidException(String message) {
		super(message);
	}

	public XMLInvalidException(String message, Throwable cause) {
		super(message, cause);
	}
}
