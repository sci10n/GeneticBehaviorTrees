import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import se.sciion.GBT.BehaviorStatus;
import se.sciion.GBT.BehaviorTree;
import se.sciion.GBT.Prototypes;
import se.sciion.GBT.nodes.BehaviorNode;
import se.sciion.GBT.nodes.SelectorNode;
import nodes.FailNode;
import nodes.SuccessNode;

public class LibraryTest {
    @Test public void testBasicTreeConstruction() {

    	// Has to be constructed from bottom-up
    	SuccessNode success = new SuccessNode();
    	FailNode fail = new FailNode();
    	
    	SelectorNode selector = new SelectorNode(fail,success);
    	BehaviorTree successTree = new BehaviorTree(selector);
    			
    	System.out.println(successTree.toString());
    	
    	assertTrue("Basic tree return success", successTree.tick() == BehaviorStatus.SUCCESS);
    	
    	BehaviorTree failTree = new BehaviorTree(new SelectorNode(fail,fail));
    	
    	assertTrue("Basic tree return fail", failTree.tick() == BehaviorStatus.FAILURE);
    	
    }
    
    @Test public void testTreePrototypeGeneration(){
    	
    	SuccessNode success = new SuccessNode();
    	FailNode fail = new FailNode();
    	SelectorNode selector = new SelectorNode(success,fail);
    	
    	BehaviorTree tree = new BehaviorTree(selector);
    	
    	BehaviorTree treeClone = tree.prototype();
    	
    	assertTrue("Assert tree produces Success", tree.tick() == BehaviorStatus.SUCCESS);
    	assertTrue("Assert tree and cloned tree results in same behaviour", tree.tick() == treeClone.tick());
    }
}
