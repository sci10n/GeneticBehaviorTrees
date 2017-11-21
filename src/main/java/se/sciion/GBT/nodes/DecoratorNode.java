package se.sciion.GBT.nodes;

public abstract class DecoratorNode extends BehaviorNode{

	protected BehaviorNode child;
	
	public DecoratorNode(BehaviorNode child){
		this.child = child;
	}
	
	@Override
	public void mutate() {
		child.mutate();
	}
}
