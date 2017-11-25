package se.sciion.GBT;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import se.sciion.GBT.BehaviourStatus;
import se.sciion.GBT.BehaviourTree;
import se.sciion.GBT.Prototypes;
import se.sciion.GBT.nodes.FailNode;
import se.sciion.GBT.nodes.ParallelNode;
import se.sciion.GBT.nodes.ParallelNode.Policy;
import se.sciion.GBT.nodes.SelectorNode;
import se.sciion.GBT.nodes.SuccessNode;

public class LibraryTest {
    @Test public void testBasicTreeConstruction() {

    	// Has to be constructed from bottom-up
    	SuccessNode success = new SuccessNode();
    	FailNode fail = new FailNode();
    	
    	SelectorNode selector = new SelectorNode(fail,success);
    	BehaviourTree successTree = new BehaviourTree(selector);
    			
    	//System.out.println(successTree.toString());
    	
    	assertTrue(successTree.tick() == BehaviourStatus.SUCCESS);
    	
    	BehaviourTree failTree = new BehaviourTree(new SelectorNode(fail,fail));
    	
    	assertTrue(failTree.tick() == BehaviourStatus.FAILURE);
    	
    }
    
    @Test public void testTreeeGeneration(){
    	
    	SuccessNode success = new SuccessNode();
    	FailNode fail = new FailNode();
    	ParallelNode selector = new ParallelNode(Policy.EXIT_ON_FAIL,success,fail);
    	
    	BehaviourTree tree = new BehaviourTree(selector);
    	assertTrue(tree.tick() == BehaviourStatus.FAILURE);
    }
    
    @Test public void testPrototypeGeneration(){
    	SuccessNode success = new SuccessNode();
    	SuccessNode node = Prototypes.getPrototype(success.getClass().getSimpleName());
    	assertTrue(node.equals(success));
    }
    
}
