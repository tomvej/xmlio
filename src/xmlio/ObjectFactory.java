package xmlio;

import org.w3c.dom.Node;

public interface ObjectFactory<T> {

	public T get(Node src);
}
