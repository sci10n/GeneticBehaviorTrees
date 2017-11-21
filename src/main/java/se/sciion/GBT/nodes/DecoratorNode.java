package se.sciion.GBT.nodes;

public abstract class DecoratorNode extends BehaviorNode{

	protected BehaviorNode child;
	
	public DecoratorNode(BehaviorNode child){
		this.child = child;
		this.child.parent = this;
	}
	
	@Override
	public void mutate() {
		child.mutate();
	}
	
	public BehaviorNode getChild(){
		return child;
	}
}
