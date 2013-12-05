package org.tomvej.xmlio.exceptions;

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
	/** Document was malformed. */
	PARSE,
	/** IO error during parsing. */
	PARSE_IO,
	/** XML document was invalid. */
	VALIDATION,
	/** IO error during validation */
	VALIDATION_IO,
	/** Input XML was in wrong format (not included in validation). */
	FORMAT,
	/** Source could not be opened. */
	SRC_OPEN,
	/** Source could not be closed. */
	SRC_CLOSE,
	/** Schema could not be parsed. */
	SCHEMA;

}
