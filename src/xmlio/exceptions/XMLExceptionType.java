package xmlio.exceptions;

public enum XMLExceptionType {
	/** Type of exception not defined. */
	UNDEFINED,
	/** XML document was invalid. */
	VALIDATION,
	/** Input XML was in wrong format (not included in validation). */
	FORMAT;

}
