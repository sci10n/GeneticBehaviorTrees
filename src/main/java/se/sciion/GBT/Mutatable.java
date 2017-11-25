package se.sciion.GBT;

import se.sciion.GBT.nodes.BehaviourNode;

public interface Mutatable {
	public void mutate();
	public BehaviourNode randomize();
}
