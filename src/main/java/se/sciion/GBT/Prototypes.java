package se.sciion.GBT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.sciion.GBT.nodes.BehaviorNode;

public class Prototypes {

	private static Map<String,BehaviorNode> protypes;
	
	static {
		protypes = new HashMap<String,BehaviorNode>();	
	}
	
	public static void register(String name, BehaviorNode node){
		// Equals checking
		if(!protypes.containsKey(name.toLowerCase())){
			protypes.put(name.toLowerCase(), node);
		}
	}
	
	public static Map<String,BehaviorNode> getPrototypes() {
		return protypes;
	}
	
	public static BehaviorNode getPrototype(String name){
		return protypes.get(name.toLowerCase());
	}
}
