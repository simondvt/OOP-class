package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method {@link #setOpen(boolean)
 * setOpen()}.
 */

public class Tap extends Element {

	private boolean isOpen;
	private double inFlow, outFlow;
	private Element connectedBy;

	public Tap(String name) {
		super(name);
		// TODO: complete
	}
	
	public String getNameComplete() {
		return "[" + getName() + "]Tap";
	}

	public void setOpen(boolean open) {
		isOpen = open;
	}

	public void gotConnectedBy(Element e) {
		connectedBy = e;
		
		computeFlow();
	}
	
	public boolean doesConnect() {
		return connectedBy != null;
	}

	public double getFlow() {
		return isOpen ? outFlow : 0;
	}

	public String getFlowInfo() {
		computeFlow();
		
		return "[TAP] " + getName() + "\n" + "inFlow: " + inFlow + "\t"
				+ "outFlow: " + outFlow + "\n";
	}
	
	private void computeFlow() {
		inFlow = connectedBy.getFlow();

		if (isOpen)
			outFlow = inFlow;
	}

}
