package se.sciion.GBT;

import se.sciion.GBT.nodes.BehaviourNode;

/**
 * Interface used for creating a runtime-accessible data structure of prototype nodes. Good to have when mutating control nodes.
 * @author sciion
 *
 */
public interface Prototype{

	// Renamed from clone because eclipse wouldn't allow clone.
	public BehaviourNode replicate();
}
