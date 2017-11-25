package se.sciion.GBT.nodes;

import se.sciion.GBT.BehaviourStatus;
import se.sciion.GBT.Prototypes;

// Inverts the status of child node. Only applies to Success and Failure.
public class InverterNode extends DecoratorNode{

	static {
		InverterNode node = new InverterNode();
		Prototypes.register(node.getClass().getSimpleName(), node);
	}
	
	public InverterNode() {
	}
	
	public InverterNode(BehaviourNode child) {
		super(child);
	}

	@Override
	protected BehaviourStatus onUpdate() {
		BehaviourStatus childStatus = child.tick();
		
		switch(childStatus) {
		case FAILURE:
			status = BehaviourStatus.SUCCESS;
			break;
		case SUCCESS:
			status = BehaviourStatus.FAILURE;
			break;
		case RUNNING:
		case UNDEFINED:
			status = childStatus;
		}
		
		return status;
	}

	@Override
	public BehaviourNode replicate() {
		if(child != null)
			return new InverterNode(child.replicate());
		else
			return new InverterNode();
	}

}
