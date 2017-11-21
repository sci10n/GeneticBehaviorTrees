package se.sciion.GBT;

import se.sciion.GBT.nodes.BehaviorNode;

public class BehaviorTree {

	private BehaviorNode root;
	
	public BehaviorTree(BehaviorNode root) {
		this.root = root;
	}
	
	public BehaviorStatus tick() {
		return root.tick();
	}

	public BehaviorTree prototype() {
		return new BehaviorTree(root.prototype());
	}
	
	public void mutate(){
		root.mutate();
	}
	
	public void crossover(BehaviorTree tree){
		
	}
	

}
