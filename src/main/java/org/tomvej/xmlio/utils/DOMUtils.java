package org.tomvej.xmlio.utils;

import java.util.List;

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
	/**
	 * Converts {@link Node} into {@link Element} or throws
	 * {@link XMLFormatException}.
	 */
	public static Element element(Node src) throws XMLFormatException {
		return element(src, "Node `" + src.getNodeName()
				+ "' is not an element.");
	}

	/**
	 * Converts {@link Node} into {@link Element} or throws
	 * {@link XMLFormatException}.
	 * 
	 * @param message
	 *            Error message when target {@link Node} is not an
	 *            {@link Element}.
	 */
	public static Element element(Node src, String message)
			throws XMLFormatException {
		if (src instanceof Element) {
			return (Element) src;
		} else {
			throw new XMLFormatException(message);
		}
	}
	
	/**
	 * Return unmodifiable {@link List} wrapper for {@link NodeList}.
	 */
	public static List<Node> nodeList(NodeList list) {
		return new NodeListList(list);
	}
	
	/**
	 * Return first child of element with given name or throws
	 * {@link XMLFormatException} when there is no such child.
	 */
	public static Node childWithName(Node src, String name)
			throws XMLFormatException {
		return childWithName(src, name, "Node `" + src.getNodeName()
				+ "' has no child `" + name + "'.");
	}

	/**
	 * Return first child of element with given name or throws
	 * {@link XMLFormatException} when there is no such child.
	 * @param message Error message when there is no child of such name.
	 */
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
