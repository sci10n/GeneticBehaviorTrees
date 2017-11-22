package se.sciion.GBT.nodes;

import java.util.List;

public abstract class DecoratorNode extends BehaviorNode{

	protected BehaviorNode child;
	
	public DecoratorNode(BehaviorNode child){
		this.child = child;
		this.child.setParent(this);
	}
	
	public void setChild(BehaviorNode node) {
		child.setParent(null);
		child = node;
		node.setParent(this);
	}
	
	@Override
	public void mutate() {
		child.mutate();
	}
	
	public BehaviorNode getChild(){
		return child;
	}
	
	@Override
	public void getNodes(List<BehaviorNode> nodes) {
		nodes.add(this);
		child.getNodes(nodes);
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < DEPTH_PRINT; i++) {
			s += "-";
		}
		
		s += getClass().getSimpleName() + "\n";
		
		DEPTH_PRINT++;
			s += child.toString();
		DEPTH_PRINT--;
		return s;
	}
}
