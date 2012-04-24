package xmlio;

import org.w3c.dom.Node;

import xmlio.exceptions.XMLFormatException;

public interface ObjectFactory<T> {

	public T get(Node src) throws XMLFormatException;
}
