package xmlio.representable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xmlio.XMLFactory;

/**
 * Defines transformation of objects into XML objects according to specification
 * of {@link XMLRepresentable}.
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 * @param <T>
 *            Type of objects to by transformed.
 * @see XMLRepresentable
 */
public class XMLRepresentableFactory<T extends XMLRepresentable> implements
		XMLFactory<T> {

	@Override
	public Element toXML(T target, Document doc) {
		return target.toXML(doc);
	};
}
