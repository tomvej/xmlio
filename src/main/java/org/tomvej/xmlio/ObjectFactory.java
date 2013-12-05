package org.tomvej.xmlio;

import org.tomvej.xmlio.exceptions.XMLFormatException;
import org.tomvej.xmlio.exceptions.XMLInvalidException;
import org.w3c.dom.Node;


/**
 * Transforms XML objects to objects of designated type.
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 * @param <T>
 *            Type of created objects.
 */
public interface ObjectFactory<T> {

	/**
	 * Transforms XML object into a new object. Typically expects source XML
	 * object to have undergone validation against schema associated with
	 * designated type.
	 * 
	 * @param src
	 *            XML object to be transformed.
	 * @return New object of designated type.
	 * @throws XMLFormatException
	 *             when source XML object does not form a valid object.
	 * @throws XMLInvalidException
	 *             when source XML is invalid against schema associated with
	 *             designated type.
	 */
	public T get(Node src) throws XMLFormatException;
}
