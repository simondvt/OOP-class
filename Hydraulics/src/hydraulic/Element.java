package hydraulic;

/**
 * Represents the generic abstract element of an hydraulics system. It is the
 * base class for all elements.
 *
 * Any element can be connect to a downstream element using the method
 * {@link #connect(Element) connect()}.
 */
public class Element {

	private String name = null;
	private Element connectedElement = null;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            the name of the element
	 */
	public Element(String name) {
		this.name = name;
	}

	/**
	 * getter method
	 * 
	 * @return the name of the element
	 */
	public String getName() {
		return name;
	}

	/**
	 * Connects this element to a given element. The given element will be connected
	 * downstream of this element
	 * 
	 * @param elem
	 *            the element that will be placed downstream
	 */
	public void connect(Element elem) {
		connectedElement = elem;
		elem.gotConnectedBy(this);
	}
	
	public Element getConnectedElement() {
		return connectedElement;
	}

	/**
	 * Retrieves the element connected downstream of this
	 * 
	 * @return downstream element
	 */
	public Element getOutput() {
		return connectedElement;
	}
	
	public boolean isConnected() {
		return connectedElement != null;
	}

	/* "abstract" */
	public void gotConnectedBy(Element e) { }
	public double getFlow() { return 0; }
	public String getFlowInfo() { return null; }
	public String getNameComplete() { return null; }
	public boolean doesConnect() { return true; }
}
