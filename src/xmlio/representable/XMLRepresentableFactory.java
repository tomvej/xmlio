package xmlio.representable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xmlio.XMLFactory;

public class XMLRepresentableFactory<T extends XMLRepresentable> implements
		XMLFactory<T> {

	public Element toXML(T target, Document doc) {
		return target.toXML(doc);
	};
}
