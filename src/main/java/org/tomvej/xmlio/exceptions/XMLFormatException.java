package org.tomvej.xmlio.exceptions;

/**
 * Exception thrown when given XML object cannot be transformed (i.e. is
 * invalid).
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 */
public class XMLFormatException extends XMLException {
	private final String message;
	
	public XMLFormatException(String message, Throwable cause) {
		super(XMLExceptionType.FORMAT, cause);
		this.message = message;
	}
	
	public XMLFormatException() {
		super(XMLExceptionType.FORMAT);
		message = "";
	}

	public XMLFormatException(Throwable cause) {
		super(XMLExceptionType.FORMAT, cause);
		message = "";
	}

	public XMLFormatException(String message) {
		super(XMLExceptionType.FORMAT);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
