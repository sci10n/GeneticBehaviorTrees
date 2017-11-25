package se.sciion.GBT;

import java.util.HashMap;
import java.util.Map;

import se.sciion.GBT.nodes.BehaviourNode;

public class Prototypes {

	private static Map<String,BehaviourNode> protypes;
	
	static {
		protypes = new HashMap<String,BehaviourNode>();	
	}
	
	private static int generated = 0;
	public static void register(String name, BehaviourNode node){
		// Equals checking
		if(!protypes.containsKey(name.toLowerCase())){
			protypes.put(name.toLowerCase(), node);
		}
	}
	
	public static Map<String,BehaviourNode> getPrototypes() {
		return protypes;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends BehaviourNode> T getPrototype(String name){
		return (T) protypes.get(name.toLowerCase());
	}
	
	public static BehaviourNode randomPrototype(){
		generated++;
		int index = (int) (Math.random() * protypes.size());
		return ((BehaviourNode) protypes.values().toArray()[index]).replicate();
	}
}
