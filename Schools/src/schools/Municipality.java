package schools;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

/**
 * Represents a municipality.
 * A municipality may belong to a {@link Community}
 *
 */

public class Municipality {
	
	private String name;
	private String province;
	private Community community;
	
	private HashMap<Integer, Branch> branches = new HashMap<>();
	

	Municipality(String name, String province) {
		this.name = name;
		this.province = province;
		this.community = null;
	}
	
	Municipality(String name, String province, Community community) {
		this(name, province);
		this.community = community;
		this.community.addMunicipality(this);
	}
	
	/**
	 * Getter method for the municipality's name
	 * 
	 * @return name of the municipality
	 */
	public String getName() {
		return name;	
	}
	
	/**
	 * Getter method for province where the municipality lies
	 * 
	 * @return province of the municipality
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Retrieves the community the municipality belongs to as an {@link Optional}
	 * If the municipality the optional will be empty.
	 * 
	 * @return optional community
	 */
	public Optional<Community> getCommunity() {
		return Optional.ofNullable(community);
	}	
	
	/**
	 * Retrieves all the school branches located in the municipality 
	 * 
	 * @return collection of branches
	 */
	public Collection<Branch> getBranches() {
		return branches.values();
	}

	public void addBranch(Branch b) {
		branches.put(b.getCode(), b);
	}
}
