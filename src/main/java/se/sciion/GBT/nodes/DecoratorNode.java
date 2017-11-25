package se.sciion.GBT.nodes;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.sciion.GBT.Prototypes;

// Extends if a node with one child is needed, useful for condition checking.
public abstract class DecoratorNode extends BehaviourNode {

	protected BehaviourNode child;

	public DecoratorNode() {

	}

	public DecoratorNode(BehaviourNode child) {
		this.child = child;
		this.child.setParent(this);
	}

	public void setChild(BehaviourNode node) {
		if (child != null)
			child.setParent(null);
		child = node;
		node.setParent(this);
	}

	@Override
	public void mutate() {
		child.mutate();
	}

	@Override
	public BehaviourNode randomize() {
		DEPTH_PRINT+=1;
		BehaviourNode node = Prototypes.randomPrototype();
		setChild(node.randomize());

		DEPTH_PRINT-=1;
		return this;
	}

	public BehaviourNode getChild() {
		return child;
	}

	@Override
	public void getNodes(List<BehaviourNode> nodes) {
		nodes.add(this);
		child.getNodes(nodes);
	}

	@Override
	public Element toXML(Document doc) {
		Element e = doc.createElement(getClass().getSimpleName());
		e.appendChild(child.toXML(doc));
		return e;
	}

	@Override
	public void fromXML(Element rootElement) {
		NodeList list = rootElement.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element n = (Element) node;
				BehaviourNode child = Prototypes.getPrototype(n.getTagName()).replicate();
				child.fromXML(n);
				setChild(child);
				break;
			}
		}
	}
}
