package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * Lo status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {

	private double flow;
	
	public Source(String name) {
		super(name);
		//TODO: complete
	}
	
	public String getNameComplete() {
		return "[" + getName() + "]Source";
	}

	public void setFlow(double flow){
		this.flow = flow;
	}
	
	public String getFlowInfo() {
		return "[SRC]: " + getName() + "\n" + "inFlow: " + flow + "\t" + "outFlow: " + flow + "\n";
	}
	
	public double getFlow() {
		return flow;
	}
}
