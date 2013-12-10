package org.tomvej.xmlio.utils;

import org.tomvej.xmlio.exceptions.XMLFormatException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility class providing procedures to simplify DOM parsing.
 * 
 * 
 * @author Tomáš Vejpustek
 * 
 */
public class DOMUtils {
	public static Element element(Node src) throws XMLFormatException {
		return element(src, "Node `" + src.getNodeName()
				+ "' is not an element.");
	}

	public static Element element(Node src, String message)
			throws XMLFormatException {
		if (src instanceof Element) {
			return (Element) src;
		} else {
			throw new XMLFormatException(message);
		}
	}

	public static Node childWithName(Node src, String name)
			throws XMLFormatException {
		return childWithName(src, name, "Node `" + src.getNodeName()
				+ "' has no child `" + name + "'.");
	}

	public static Node childWithName(Node src, String name, String message)
			throws XMLFormatException {
		NodeList children = element(src).getElementsByTagName(name);
		if (children == null || children.getLength() == 0) {
			throw new XMLFormatException(message);
		}
		return children.item(0);
	}
	
	private DOMUtils() {
	}
}
