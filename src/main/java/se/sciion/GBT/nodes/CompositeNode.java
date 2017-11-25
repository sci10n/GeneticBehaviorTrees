package se.sciion.GBT.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.sciion.GBT.Prototypes;

// Extends if a node with more than one child is required
public abstract class CompositeNode extends BehaviourNode {

	protected List<BehaviourNode> children;
	
	// Used since this implementation is based as an iterative process.
	protected int currentChild;
	
	public CompositeNode() {
		currentChild = 0;
		children = new ArrayList<BehaviourNode>();

	}
	
	public CompositeNode(List<BehaviourNode> nodes) {
		children = nodes;
		currentChild = 0;
		
		for(BehaviourNode node: children){
			node.setParent(this);
		}
	}
	
	public CompositeNode(BehaviourNode ... nodes) {
		children = new ArrayList<BehaviourNode>();
		children.addAll(Arrays.asList(nodes));
		currentChild = 0;
		
		for(BehaviourNode node: children){
			node.setParent(this);
		}
	}
	
	public void addChild(BehaviourNode node){
		children.add(node);
		node.setParent(this);
	}
	
	public void removeChild(BehaviourNode node) {
		children.remove(node);
		node.setParent(null);
	}
	
	public void removeChild(int index){
		children.get(index).setParent(null);
		children.remove(index);
	}
	public void replaceChild(BehaviourNode n1, BehaviourNode n2) {
		int indx = children.indexOf(n1);
		children.set(indx, n2);
		n1.setParent(null);
		n2.setParent(this);
	}
	
	public void replaceChild(int index , BehaviourNode n) {
		children.get(index).setParent(null);
		children.set(index, n);
		n.setParent(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		currentChild = 0;
	}
	
	public List<BehaviourNode> getChildren() {
		return children;
	}
	
	@Override
	public void mutate() {
		int randomChild = (int) (Math.random() * children.size());
		int randomAction = (int) (Math.random() * 4);
		
		// Remove child
		if(randomAction == 0){
			removeChild(randomChild);
		}
		// Replace child
		else if(randomAction == 1){
			replaceChild(randomChild, Prototypes.randomPrototype().randomize());
		}
		// Add Child
		else if(randomAction == 2){
			addChild(Prototypes.randomPrototype().randomize());
		}
		// Shuffle children
		else if(randomAction == 3){
			int randomChild2 = (int) (Math.random() * children.size());
			replaceChild(children.get(randomChild), children.get(randomChild2));
		}
	}
	
	@Override
	public BehaviourNode randomize() {
		DEPTH_PRINT+=1;
		int numChildren = (int) (Math.random() * 4);
		for(int i = 0; i <= numChildren; i++){
			BehaviourNode child = Prototypes.randomPrototype();
			while(DEPTH_PRINT >= MAX_DEPTH && !(child instanceof LeafNode)){
				child = Prototypes.randomPrototype();
			}
			addChild(child.randomize());
		}
		DEPTH_PRINT-= 1;
		return this;
	}
	
	@Override
	public void getNodes(List<BehaviourNode> nodes) {
		nodes.add(this);
		for(BehaviourNode c: children) {
			c.getNodes(nodes);
		}
	}
	
	@Override
	public Element toXML(Document doc) {
		Element e = doc.createElement(getClass().getSimpleName());
		for(BehaviourNode n: children){
			e.appendChild(n.toXML(doc));
		}
		return e;
	}
	
	@Override
	public void fromXML(Element rootElement) {
		NodeList list = rootElement.getChildNodes();
		for(int i = 0 ; i < list.getLength(); i++){
			if(list.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element n = (Element) list.item(i);
				BehaviourNode child = Prototypes.getPrototype(n.getTagName()).replicate();
				child.fromXML(n);
				addChild(child);
			}
		}
	}
}
