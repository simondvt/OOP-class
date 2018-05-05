package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	private double flow;
	private Element connectedBy;
	
	/**
	 * Constructor
	 * @param name
	 */
	public Sink(String name) {
		super(name);
		//TODO: complete
	}
	
	public String getNameComplete() {
		return "[" + getName() + "]Sink";
	}
	
	public boolean doesConnect() {
		return connectedBy != null;
	}
	
	@Override
	public Element getOutput(){
		System.out.println("Trying to call \"getOutput\" on Sink.");
		
		return null;
	}
	
	public void gotConnectedBy(Element e) {
		connectedBy = e;
		computeFlow();
	}
	
	public String getFlowInfo() {
		computeFlow();
		
		return "[SINK] " + getName() + "\n" + "inFlow: " + flow + "\n";
	}
	
	private void computeFlow() {
		flow = connectedBy.getFlow();
	}
}
