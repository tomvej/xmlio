package xmlio;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface XMLFactory<T> {

	public Element toXML(T target, Document doc);
}
