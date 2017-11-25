package se.sciion.GBT.nodes;

import se.sciion.GBT.BehaviourStatus;
import se.sciion.GBT.Prototypes;

public class SucceederNode extends DecoratorNode{

	static {
		SucceederNode node = new SucceederNode();
		Prototypes.register(node.getClass().getSimpleName(), node);
	}
	
	public SucceederNode() {
		
	}
	
	public SucceederNode(BehaviourNode child) {
		super(child);
	}

	@Override
	protected BehaviourStatus onUpdate() {
		
		BehaviourStatus childStatus = child.tick();
		
		// We only check for failure since the rest should be the same.
		if(childStatus == BehaviourStatus.FAILURE) {
			status = BehaviourStatus.SUCCESS;
		}
		else {
			status = childStatus;
		}
		
		return status;
	}

	@Override
	public BehaviourNode replicate() {
		if(child != null){
			return new SucceederNode(child.replicate());
		}else{
			return new SucceederNode();

		}
	}
}
