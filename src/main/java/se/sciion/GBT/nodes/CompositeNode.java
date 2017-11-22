package se.sciion.GBT.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CompositeNode extends BehaviorNode {

	protected List<BehaviorNode> children;
	
	// Used since this implementation is based as an iterative process.
	protected int currentChild;
	
	public CompositeNode(List<BehaviorNode> nodes) {
		children = nodes;
		currentChild = 0;
		
		for(BehaviorNode node: children){
			node.setParent(this);
		}
	}
	
	public CompositeNode(BehaviorNode ... nodes) {
		children = new ArrayList<BehaviorNode>();
		children.addAll(Arrays.asList(nodes));
		currentChild = 0;
		
		for(BehaviorNode node: children){
			node.setParent(this);
		}
	}
	
	public void addChild(BehaviorNode node){
		children.add(node);
		node.setParent(this);
	}
	
	public void removeChild(BehaviorNode node) {
		children.remove(node);
		node.setParent(null);
	}
	
	public void replaceChild(BehaviorNode n1, BehaviorNode n2) {
		int indx = children.indexOf(n1);
		children.set(indx, n2);
		n1.setParent(null);
		n2.setParent(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		currentChild = 0;
	}
	
	public List<BehaviorNode> getChildren() {
		return children;
	}
	
	@Override
	public void mutate() {
		for(BehaviorNode node: children){
			node.mutate();
		}
	}
	
	@Override
	public void getNodes(List<BehaviorNode> nodes) {
		nodes.add(this);
		for(BehaviorNode c: children) {
			c.getNodes(nodes);
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < DEPTH_PRINT; i++) {
			s += "-";
		}
		
		s += getClass().getSimpleName() + "\n";
		
		DEPTH_PRINT += 1;
		for(BehaviorNode c: children) {
			s += c.toString();
		}
		DEPTH_PRINT -= 1;
		return s;
	}
}
