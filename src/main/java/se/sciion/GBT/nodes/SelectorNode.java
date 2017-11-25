package se.sciion.GBT.nodes;

import java.util.ArrayList;
import java.util.List;

import se.sciion.GBT.BehaviourStatus;
import se.sciion.GBT.Prototypes;

public class SelectorNode extends CompositeNode{

	static {
		SelectorNode node = new SelectorNode();
		Prototypes.register(node.getClass().getSimpleName(), node);
	}
	
	public SelectorNode() {
	}
	
	public SelectorNode(List<BehaviourNode> nodes) {
		super(nodes);
	}

	public SelectorNode(BehaviourNode... nodes) {
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
			if(status == BehaviourStatus.FAILURE && currentChild < children.size()){
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
		return new SelectorNode(nodes);
	}
}
