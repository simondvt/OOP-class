package schools;

import java.util.Collection;
import java.util.HashMap;

/**
 * Represents the schools.
 * Schools are aggregations of branches, 
 * each school has one or more {@link Branch branches} (at least one)
 *
 */
public class School {
	
	private String name;
	private String code;
	private int grade;
	private String description;
	
	private HashMap<Integer, Branch> branches = new HashMap<>();
	
	School(String name, String code, int grade, String description) {
		this.name = name;
		this.code = code;
		this.grade = grade;
		this.description = description;
	}

	/**
	 * Getter method for the school name
	 * @return name of the school
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter method for the school code
	 * @return code of the school
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Getter method for the grade
	 * @return grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Getter method for the description
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Retrieves the branches of the school.
	 * 
	 * @return collection of the branches
	 */
	public Collection<Branch> getBranches() {
		return branches.values();
	}

	public void addBranch(Branch b) {
		branches.put(b.getCode(), b);
	}

}
