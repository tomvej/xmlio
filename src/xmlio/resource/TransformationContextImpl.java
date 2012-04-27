package xmlio.resource;

import java.net.URL;

import xmlio.ObjectFactory;
import xmlio.XMLFactory;

public class TransformationContextImpl<T> implements TransformationContext<T> {
	private XMLFactory<T>		xmlFact;
	private ObjectFactory<T>	objFact;
	private String				namespace;
	private URL					schema;

	public TransformationContextImpl(XMLFactory<T> xmlFactory, ObjectFactory<T> objectFactory, URL schemaURL, String namespace) {
		xmlFact = xmlFactory;
		objFact = objectFactory;
		schema = schemaURL;
		this.namespace = namespace;
	}

	@Override
	public XMLFactory<T> getXMLFactory() {
		return xmlFact;
	}

	@Override
	public ObjectFactory<T> getObjectFactory() {
		return objFact;
	}

	@Override
	public String getXMLNamespace() {
		return namespace;
	}

	@Override
	public URL getXMLSchema() {
		return schema;
	}

}
