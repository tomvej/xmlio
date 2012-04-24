package xmlio;

import xmlio.exceptions.XMLException;

public interface XMLResource<T> {

	public T getRoot();

	public void setRoot(T root);
	
	public void load() throws XMLException;
	
	public void store() throws XMLException;
}
