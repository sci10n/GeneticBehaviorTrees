package se.sciion.GBT.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CompositeNode extends BehaviorNode{

	protected List<BehaviorNode> children;
	
	// Used since this implementation is based as an iterative process.
	protected int currentChild;
	
	public CompositeNode(List<BehaviorNode> nodes) {
		children = nodes;
		currentChild = 0;
		
		for(BehaviorNode node: children){
			node.parent = this;
		}
	}
	
	public CompositeNode(BehaviorNode ... nodes) {
		children = new ArrayList<BehaviorNode>();
		children.addAll(Arrays.asList(nodes));
		currentChild = 0;
		
		for(BehaviorNode node: children){
			node.parent = this;
		}
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
}
