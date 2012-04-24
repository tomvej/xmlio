package xmlio.exceptions;

public class XMLException extends Exception {
	private XMLExceptionType	type;

	public XMLException(XMLExceptionType type) {
		this.type = type;
	}

	public XMLException(XMLExceptionType type, Throwable cause) {
		super(cause);
		this.type = type;
	}

	public XMLExceptionType getType() {
		return type;
	}
}
