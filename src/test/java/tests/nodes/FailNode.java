package tests.nodes;

import javax.xml.bind.annotation.XmlRootElement;

import se.sciion.GBT.BehaviorStatus;
import se.sciion.GBT.nodes.BehaviorNode;

@XmlRootElement
public class FailNode extends BehaviorNode{

	@Override
	public void mutate() {
		
	}

	@Override
	public BehaviorNode prototype() {
		return new FailNode();
	}

	@Override
	protected BehaviorStatus onUpdate() {
		status = BehaviorStatus.FAILURE;
		return status;
	}

}
