package org.xmlio;

import org.xmlio.exceptions.XMLException;

/**
 * External resource of objects in XML format. Contains exactly one object of
 * designated type, which may be stored and loaded.
 * 
 * Generally, at one time, instance of this class is associated with one
 * resource.
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 * @param <T>
 *            Type of contained object.
 */
public interface XMLResource<T> {

	/**
	 * Returns contained object.
	 * 
	 * @return contained object.
	 */
	public T getRoot();

	/**
	 * Sets contained object.
	 * 
	 * @param root
	 *            New contained object.
	 */
	public void setRoot(T root);

	/**
	 * Loads an object from XML to contained object.
	 * 
	 * @throws XMLException
	 *             when error during loading occurs.
	 */
	public void load() throws XMLException;

	/**
	 * Stores contained object to XML.
	 * 
	 * @throws XMLException
	 *             when error during storing occurs.
	 * @throws IllegalArgumentException
	 *             when there is no contained object.
	 */
	public void store() throws XMLException;
}
