package xmlio;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Transforms objects of designated type into XML objects.
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 * @param <T>
 *            type of transformed objects.
 */
public interface XMLFactory<T> {

	/**
	 * Transforms given object into a new XML object.
	 * 
	 * @param target
	 *            Object to be transformed.
	 * @param doc
	 *            Parent document (each XML object must have a parent document).
	 * @return New XML object created from given object with a given parent
	 *         document.
	 */
	public Element toXML(T target, Document doc);
}
