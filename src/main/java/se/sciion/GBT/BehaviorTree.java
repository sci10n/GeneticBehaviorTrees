package se.sciion.GBT;

import java.util.ArrayList;

import se.sciion.GBT.nodes.BehaviorNode;
import se.sciion.GBT.nodes.CompositeNode;
import se.sciion.GBT.nodes.DecoratorNode;

public class BehaviorTree {

	private BehaviorNode root;
	
	public BehaviorTree(BehaviorNode root) {
		this.root = root;
	}
	
	public BehaviorStatus tick() {
		return root.tick();
	}

	public BehaviorTree prototype() {
		return new BehaviorTree(root.replicate());
	}
	
	public void mutate(){
		root.mutate();
	}
	
	// Also known as: Swap random sub-trees
	public void crossover(BehaviorTree tree){
		ArrayList<BehaviorNode> n1 = new ArrayList<BehaviorNode>();
		ArrayList<BehaviorNode> n2 = new ArrayList<BehaviorNode>();
		
		root.getNodes(n1);
		tree.root.getNodes(n2);
		
		// Pick random node from each tree
		BehaviorNode r1 = n1.get((int)(Math.random()*n1.size()));
		BehaviorNode r2 = n2.get((int)(Math.random()*n2.size()));
		
		// Get parents, hard to replace without at least one tree reference
		BehaviorNode pr1 = r1.getParent();
		BehaviorNode pr2 = r2.getParent();
		
		
		if(pr1 instanceof CompositeNode) {
			((CompositeNode) pr1).replaceChild(r1, r2);
		} 
		else if( pr1 instanceof DecoratorNode) {
			((DecoratorNode) pr1).setChild(r2);
		}
		// Root node is selected for crossover
		else {
			root = r1;
			root.setParent(null);
		}
		
		if(pr2 instanceof CompositeNode) {
			((CompositeNode) pr2).replaceChild(r2, r1);
		} 
		else if(pr2 instanceof DecoratorNode) {
			((DecoratorNode) pr2).setChild(r1);
		}
		// Root node is selected for crossover
		else {
			tree.root = r1;
			tree.root.setParent(null);
		}

	}
	
	@Override
	public String toString() {
		return root.toString();
	}

}
