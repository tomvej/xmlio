package xmlio.exceptions;

/**
 * Exception thrown when given XML object cannot be transformed (i.e. is
 * invalid).
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 */
public class XMLFormatException extends XMLException {
	public XMLFormatException() {
		super(XMLExceptionType.FORMAT);
	}

	public XMLFormatException(Throwable cause) {
		super(XMLExceptionType.FORMAT, cause);
	}

	// TODO message?
}
