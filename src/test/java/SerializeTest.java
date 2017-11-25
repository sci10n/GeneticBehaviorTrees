import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.transform.stream.StreamResult;

import org.junit.Test;

import nodes.FailNode;
import nodes.SuccessNode;
import se.sciion.GBT.BehaviourTree;
import se.sciion.GBT.nodes.InverterNode;
import se.sciion.GBT.nodes.ParallelNode;
import se.sciion.GBT.nodes.ParallelNode.Policy;
import se.sciion.GBT.nodes.SelectorNode;

public class SerializeTest {

	
	@Test
	public void behaviorTreeSerializationTest() {
		// Has to be constructed from bottom-up
    	SuccessNode success = new SuccessNode();
    	FailNode fail = new FailNode();
    	
    	SelectorNode selector = new SelectorNode(fail,success);
    	ParallelNode parallel = new ParallelNode(Policy.EXIT_ON_FAIL, selector, new InverterNode(new FailNode()));
    	BehaviourTree successTree = new BehaviourTree(parallel);
    	
    	//System.out.println(successTree.toString());

    	ByteArrayOutputStream os = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(os);
    	successTree.toXML(result);
    	
    	BehaviourTree parsedTree = new BehaviourTree(null);
    	
    	InputStream is = new ByteArrayInputStream(os.toByteArray());
    	parsedTree.fromXML(is);
    	
    	//System.out.println(parsedTree.toString());
   	}
	
	@Test
	public void testTreeRandomization(){
		
		BehaviourTree tree = new BehaviourTree();

		try{
			tree.randomize();
			tree.toXML(new StreamResult(System.out));
		}catch(StackOverflowError error){
			error.printStackTrace();
		}
	}
}
