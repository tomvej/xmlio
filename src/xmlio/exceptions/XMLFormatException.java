package xmlio.exceptions;

public class XMLFormatException extends XMLException {
	public XMLFormatException() {
		super(XMLExceptionType.FORMAT);
	}

	public XMLFormatException(Throwable cause) {
		super(XMLExceptionType.FORMAT, cause);
	}

	// TODO message?
}
