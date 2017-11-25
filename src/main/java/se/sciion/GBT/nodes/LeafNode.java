package se.sciion.GBT.nodes;

import java.util.List;

public abstract class LeafNode extends BehaviourNode{

	/**
	 * Flattens the tree such that crossover can be done easier.
	 * @param nodes array where the nodes will be stored
	 */
	public void getNodes(List<BehaviourNode> nodes) {
		nodes.add(this);
	}
	
	@Override
	public void mutate() {
		
	}
	
	@Override
	public BehaviourNode randomize() {
		return this;
	}
	
}
