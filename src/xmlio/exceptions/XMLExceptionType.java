package xmlio.exceptions;

/**
 * Enumerates possible types of {@link XMLException}.
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 * @see XMLException
 * 
 */
public enum XMLExceptionType {
	/** Type of exception not defined. */
	UNDEFINED,
	/** XML document was invalid. */
	VALIDATION,
	/** Input XML was in wrong format (not included in validation). */
	FORMAT;

}
