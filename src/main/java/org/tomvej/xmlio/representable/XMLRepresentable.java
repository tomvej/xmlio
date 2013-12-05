package org.tomvej.xmlio.representable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Specifies an approach to transforming objects into XML, where each class
 * defines its transformation. Thus, it may include encapsulated fields.
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 */
public interface XMLRepresentable {

	/**
	 * Transforms object into its XML representation.
	 * 
	 * @param doc
	 *            Parent document (each XML object must have parent document.
	 * @return New XML object representing this object with given parent
	 *         document.
	 */
	public Element toXML(Document doc);
}
