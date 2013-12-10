package org.tomvej.xmlio.utils;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.tomvej.xmlio.exceptions.XMLException;
import org.tomvej.xmlio.exceptions.XMLFormatException;

/**
 * Utility class providing procedures to simplify DOM parsing.
 * 
 * 
 * @author Tomáš Vejpustek
 * 
 */
public class DOMUtils {
	public static Element element(Node src) throws XMLException {
		return element(src, "");
	}

	public static Element element(Node src, String message)
			throws XMLFormatException {
		if (src instanceof Element) {
			return (Element) src;
		} else {
			throw new XMLFormatException(message);
		}
	}

	private DOMUtils() {
	}
}
