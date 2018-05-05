package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {

	private Element first = null, second = null;
	private double inFlow, outFlows;
	private Element connectedBy;
	
	/**
	 * Constructor
	 * @param name
	 */
	public Split(String name) {
		super(name);
		//TODO: complete
	}
	
	public String getNameComplete() {
		return "[" + getName() + "]Split";
	}
	
	public Element getFirst() {
		return first;
	}
	
	public Element getSecond() {
		return second;
	}
	
	public boolean doesConnect() {
		return connectedBy != null;
	}
    
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
    	return new Element[] {first, second};
    }

	public void connect(Element elem, int noutput){
		if (noutput == 0)
			first = elem;
		else
			second = elem;
		
		elem.gotConnectedBy(this);
	}

	
	public void gotConnectedBy(Element e) {	
		connectedBy = e;
		
		computeFlow();
	}
	
	public double getFlow() {
		return outFlows;
	}
	
	public String getFlowInfo() {
		computeFlow();
		
		return "[SPLIT]: " + getName() + "\n" + "inFlow: " + inFlow + "\t" + "outFlow(s): " + outFlows + "\n";
	}
	
	private void computeFlow() {
		inFlow = connectedBy.getFlow();
		outFlows = inFlow / 2;
	}
}
