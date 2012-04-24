package xmlio.resource;

import java.net.URL;

import xmlio.ObjectFactory;
import xmlio.XMLFactory;

/**
 * Defines all objects necessary to transform given class to and from XML:
 * <ul>
 * <li>Transformation from object to XML object ({@link XMLFactory}).</li>
 * <li>Transformation from XML object to object ({@link ObjectFactory}).</li>
 * <li>XML namespace.</li>
 * <li>Path to XML Schema.</li>
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 * @param <T>
 *            Type transformed to and from XML.
 */
public interface TransformationContext<T> {

	/**
	 * Defines transformation of objects into XML objects.
	 * 
	 * @return Factory transforming objects of designated type into XML objects.
	 */
	public XMLFactory<T> getXMLFactory();

	/**
	 * Defines transformation of XML objects into objects.
	 * 
	 * @return Factory transforming XML objects into objects of designated type.
	 */
	public ObjectFactory<T> getObjectFactory();

	/**
	 * Defines XML namespace of given type. Should be consistent with
	 * {@link StreamXMLResource#getXMLSchema()}.
	 * 
	 * @return Name of XML namespace.
	 */
	public String getXMLNamespace();

	/**
	 * Defines access to XML namespace of given type.
	 * 
	 * @return Path to XML schema associated with given type.
	 */
	public URL getXMLSchema();
}
