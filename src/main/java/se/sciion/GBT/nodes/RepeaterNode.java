package se.sciion.GBT.nodes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sciion.GBT.BehaviourStatus;

public class RepeaterNode extends DecoratorNode{

	private int numRepeats;
	private int repeats;
	
	public RepeaterNode() {
		numRepeats = 0;
		repeats = 0;
	}
	
	public RepeaterNode(int times, BehaviourNode child){
		this.numRepeats = times;
		repeats = 0;
		this.child = child;
	}
	
	@Override
	public BehaviourNode replicate() {
		return new RepeaterNode(numRepeats,child.replicate());
	}

	@Override
	protected BehaviourStatus onUpdate() {
		if(repeats < numRepeats){
			status = child.tick();
			repeats++;
		} else {
			status = BehaviourStatus.SUCCESS;
		}
		
		return status;
	}

	@Override
	public Element toXML(Document doc) {
		Element e = doc.createElement(getClass().getSimpleName());
		e.setAttribute("Num-Repeats", ""+numRepeats);
		e.appendChild(child.toXML(doc));
		return e;
	}
	
	@Override
	public void fromXML(Element rootElement) {
		numRepeats = Integer.parseInt(rootElement.getAttribute("Num-Repeats"));
		super.fromXML(rootElement);
	}
	
	public void setNumRepeats(int numRepeats) {
		this.numRepeats = numRepeats;
	}
	
	public int getNumRepeats() {
		return numRepeats;
	}
}
