package se.sciion.GBT.nodes;

import se.sciion.GBT.BehaviorStatus;

public class SucceederNode extends DecoratorNode{

	public SucceederNode(BehaviorNode child) {
		super(child);
	}

	@Override
	protected BehaviorStatus onUpdate() {
		
		BehaviorStatus childStatus = child.tick();
		
		// We only check for failure since the rest should be the same.
		if(childStatus == BehaviorStatus.FAILURE) {
			status = BehaviorStatus.SUCCESS;
		}
		else {
			status = childStatus;
		}
		
		return status;
	}

	@Override
	public BehaviorNode prototype() {
		return new SucceederNode(child.prototype());
	}

}
