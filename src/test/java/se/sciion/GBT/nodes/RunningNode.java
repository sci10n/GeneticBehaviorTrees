package se.sciion.GBT.nodes;

import se.sciion.GBT.BehaviourStatus;
import se.sciion.GBT.Prototypes;
import se.sciion.GBT.nodes.BehaviourNode;
import se.sciion.GBT.nodes.LeafNode;

public class RunningNode extends LeafNode{
	
	static {
		RunningNode node = new RunningNode();
		Prototypes.register(node.getClass().getSimpleName(), node);
	}
	
	@Override
	public BehaviourNode replicate() {
		return new RunningNode();
	}

	@Override
	protected BehaviourStatus onUpdate() {
		status =  BehaviourStatus.RUNNING;
		return status;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RunningNode)
			return true;
		return false;
	}
}
