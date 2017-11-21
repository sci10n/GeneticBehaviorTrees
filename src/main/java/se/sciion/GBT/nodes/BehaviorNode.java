package se.sciion.GBT.nodes;

import se.sciion.GBT.BehaviorStatus;
import se.sciion.GBT.Mutatable;
import se.sciion.GBT.Prototype;

public abstract class BehaviorNode implements Mutatable, Prototype{

	protected BehaviorStatus status;
	
	public BehaviorNode() {
		status = BehaviorStatus.UNDEFINED;
	}

	protected void onStart() {
		status = BehaviorStatus.RUNNING;
	}
	
	protected abstract BehaviorStatus onUpdate();
	
	protected void onExit() {}
	
	public BehaviorStatus tick() {
		
		// Run setup if not running
		if(status != BehaviorStatus.RUNNING)
			onStart();
		// Run update
		status = onUpdate();
		
		// Run exit if we succeeded or failed
		if(status != BehaviorStatus.RUNNING)
			onExit();
		
		return status;
	}
	
	
}
