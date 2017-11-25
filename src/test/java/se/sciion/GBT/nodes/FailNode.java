package se.sciion.GBT.nodes;

import se.sciion.GBT.BehaviourStatus;
import se.sciion.GBT.Prototypes;
import se.sciion.GBT.nodes.BehaviourNode;
import se.sciion.GBT.nodes.LeafNode;

public class FailNode extends LeafNode{

	static {
		FailNode node = new FailNode();
		System.out.println(node);
		Prototypes.register(node.getClass().getSimpleName(), node);
	}
	
	@Override
	public BehaviourNode replicate() {
		return new FailNode();
	}

	@Override
	protected BehaviourStatus onUpdate() {
		status = BehaviourStatus.FAILURE;
		return status;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FailNode)
			return true;
		return false;
	}
}
