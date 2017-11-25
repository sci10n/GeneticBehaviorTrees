package se.sciion.GBT.nodes;

import se.sciion.GBT.BehaviourStatus;
import se.sciion.GBT.Prototypes;
import se.sciion.GBT.nodes.BehaviourNode;
import se.sciion.GBT.nodes.LeafNode;

public final class SuccessNode extends LeafNode{

	static {
		SuccessNode node = new SuccessNode();
		Prototypes.register(node.getClass().getSimpleName(), node);
	}
	
	@Override
	public BehaviourNode replicate() {
		return new SuccessNode();
	}

	@Override
	protected BehaviourStatus onUpdate() {
		status = BehaviourStatus.SUCCESS;
		return status;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SuccessNode)
			return true;
		return false;
	}
}
