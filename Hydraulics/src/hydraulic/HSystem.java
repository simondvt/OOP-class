package hydraulic;

import java.util.ArrayList;

/**
 * Main class that act as a container of the elements for the simulation of an
 * hydraulics system
 * 
 */
public class HSystem {

	ArrayList<Element> list = new ArrayList<Element>();

	/**
	 * Adds a new element to the system
	 * 
	 * @param elem
	 */
	public void addElement(Element elem) {
		list.add(elem);
	}

	/**
	 * returns the element added so far to the system
	 * 
	 * @return an array of elements whose length is equal to the number of added
	 *         elements
	 */
	public Element[] getElements() {
		return (Element[]) list.toArray(new Element[list.size()]);
	}

	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout() {
		StringBuffer res = new StringBuffer("");

		for (Element s : list)
			if (s instanceof Source) {
				res.append("Layout for Source[" + s.getName() + "]:\n");
				printFromSource(s, res, 0);
			}

		return res.toString();
	}

	/**
	 * starts the simulation of the system
	 */
	public void simulate() {
		for (Element e : list) {
			if (!e.doesConnect()) {
				System.out.println("WARNING: not all elements were connected.\n");
				return;
			}
			System.out.println(e.getFlowInfo());
		}

	}

	private void printFromSource(Element s, StringBuffer res, int spaces) {
		String currentRes = "";

		// exit case of recursion: no more elements need to be printed
		if (s instanceof Sink) {
			res.append(s.getNameComplete() + "| \n");
			return;
		}

		// prints Element's name and type
		currentRes += s.getNameComplete();

		// split is a special case: launch two recursive calls
		if (s instanceof Split) {
			currentRes += " +-> ";
			res.append(currentRes);
			printFromSource(((Split) s).getFirst(), res, currentRes.length() + spaces);
			// -4 and -5 for indentation of " +-> "
			addSpaces(res, currentRes.length() + spaces - 4);
			res.append("|\n");
			addSpaces(res, currentRes.length() + spaces - 5);
			res.append(" +-> ");
			printFromSource(((Split) s).getSecond(), res, currentRes.length() + spaces);

		} else {
			currentRes += " -> ";

			// updates global string
			res.append(currentRes);

			// move on to the next element
			printFromSource(s.getConnectedElement(), res, currentRes.length() + spaces);
		}

	}

	// prints spaces for indentation
	private void addSpaces(StringBuffer res, int spaces) {
		for (int i = 0; i < spaces; i++)
			res.append(" ");
	}

}
