package se.sciion.GBT.nodes;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sciion.GBT.BehaviourStatus;
import se.sciion.GBT.Prototypes;

public class ParallelNode extends CompositeNode {

	public enum Policy {
		EXIT_ON_FAIL, EXIT_ON_SUCCESS
	}

	static {
		ParallelNode node = new ParallelNode();
		Prototypes.register(node.getClass().getSimpleName(), node);
	}
	
	private Policy policy;

	private ArrayList<Integer> completedNodes;
	
	public ParallelNode() {
		policy = Policy.EXIT_ON_FAIL;
	}
	
	public ParallelNode(Policy policy, List<BehaviourNode> nodes) {
		super(nodes);
		this.policy = policy;
		completedNodes = new ArrayList<Integer>();
	}

	public ParallelNode(Policy policy, BehaviourNode... nodes) {
		super(nodes);
		this.policy = policy;
		completedNodes = new ArrayList<Integer>();
	}

	@Override
	public BehaviourNode replicate() {
		ArrayList<BehaviourNode> nodes = new ArrayList<BehaviourNode>();
		for (BehaviourNode child : children) {
			nodes.add(child.replicate());
		}
		return new ParallelNode(policy, nodes);
	}

	@Override
	protected BehaviourStatus onUpdate() {

		int numSuccess = 0;
		int numFailure = 0;
		for (int i = 0; i < children.size(); i++) {
			if(completedNodes.contains(i)){
				continue;
			}
			
			BehaviourNode n = children.get(i);
			BehaviourStatus s = n.tick();
			
			if (s == BehaviourStatus.FAILURE) {
				numFailure++;
				if(policy == Policy.EXIT_ON_FAIL){
					status = BehaviourStatus.FAILURE;
					break;
				}
				completedNodes.add(i);
			} else if (s == BehaviourStatus.SUCCESS) {
				numSuccess++;
				if(policy == Policy.EXIT_ON_SUCCESS){
					status = BehaviourStatus.SUCCESS;
					break;
				}
				completedNodes.add(i);
			}
		}
		
		if(numSuccess == children.size()){
			status = BehaviourStatus.SUCCESS;
		}
		if(numFailure == children.size()){
			status = BehaviourStatus.FAILURE;
		}
		
		return status;
	}

	@Override
	protected void onExit() {
		completedNodes.clear();
		super.onExit();
	}
	
	@Override
	public Element toXML(Document doc) {
		Element e = doc.createElement("ParallelNode");
		for(BehaviourNode n: children){
			e.appendChild(n.toXML(doc));
		}
		e.setAttribute("Policy", policy.toString());
		return e;
	}
}
