package xmlio.representable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface XMLRepresentable {
	public Element toXML(Document doc);
}
