package se.sciion.GBT.nodes;

import java.util.ArrayList;
import java.util.List;

import se.sciion.GBT.BehaviourStatus;
import se.sciion.GBT.Prototypes;

public class SequenceNode extends CompositeNode{

	static {
		SequenceNode node = new SequenceNode();
		Prototypes.register(node.getClass().getSimpleName(), node);
	}
	
	public SequenceNode() {
		
	}
	
	public SequenceNode(List<BehaviourNode> nodes) {
		super(nodes);
	}

	public SequenceNode(BehaviourNode... nodes) {
		super(nodes);
	}
	
	@Override
	protected BehaviourStatus onUpdate() {
		// Nothing to do
		if(children.isEmpty()) {
			return status;
		}
		
		// Check if we reached end of children
		if(currentChild < children.size()) {
			status = children.get(currentChild).tick();
			
			// We proceed until all children tested
			if(status == BehaviourStatus.SUCCESS && currentChild < children.size()){
				currentChild++;
				status = onUpdate();
			}
		}
		return status;
	}

	@Override
	public BehaviourNode replicate() {
		List<BehaviourNode> nodes = new ArrayList<BehaviourNode>();
		for(BehaviourNode n: children){
			nodes.add(n.replicate());
		}
		return new SequenceNode(nodes);
	}
}
