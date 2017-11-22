package se.sciion.GBT.nodes;

import se.sciion.GBT.BehaviorStatus;

public class InverterNode extends DecoratorNode{

	public InverterNode(BehaviorNode child) {
		super(child);
	}

	@Override
	protected BehaviorStatus onUpdate() {
		BehaviorStatus childStatus = child.tick();
		
		switch(childStatus) {
		case FAILURE:
			status = BehaviorStatus.SUCCESS;
			break;
		case SUCCESS:
			status = BehaviorStatus.FAILURE;
			break;
		case RUNNING:
		case UNDEFINED:
			status = childStatus;
		}
		
		return status;
	}

	@Override
	public BehaviorNode replicate() {
		return new InverterNode(child.replicate());
	}

}
