package nodes;

import javax.xml.bind.annotation.XmlRootElement;

import se.sciion.GBT.BehaviorStatus;
import se.sciion.GBT.nodes.BehaviorNode;

@XmlRootElement
public final class SuccessNode extends BehaviorNode{

	// No mutate available
	@Override
	public void mutate() {
		
	}

	@Override
	public BehaviorNode replicate() {
		return new SuccessNode();
	}

	@Override
	protected BehaviorStatus onUpdate() {
		status = BehaviorStatus.SUCCESS;
		return status;
	}
}
