package org.tomvej.xmlio.utils;

import java.util.AbstractList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Wrapper for {@link NodeList}. Unmodifiable.
 * 
 * @author Tomáš Vejpustek
 * 
 */
public class NodeListList extends AbstractList<Node> implements List<Node> {
	private final NodeList list;

	public NodeListList(NodeList nodeList) {
		if (nodeList == null) {
			throw new NullPointerException("Node list is null.");
		}
		list = nodeList;
	}

	@Override
	public Node get(int arg0) {
		return list.item(arg0);
	}

	@Override
	public int size() {
		return list.getLength();
	}

}
