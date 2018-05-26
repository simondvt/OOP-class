package schools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

// Hint: to write compact stream expressions
// you can include the following
//import static java.util.stream.Collectors.*;
//import static java.util.Comparator.*;

/**
 * Represents the region and serves as a facade class for the package.
 * 
 * It provides factory methods for creating instances of {@link Community},
 * {@link Municipality}, {@link School}, and {@link Branch}
 *
 */
public class Region {

	private String name;

	private HashMap<String, Community> communities = new HashMap<>();
	private HashMap<String, Municipality> municipalities = new HashMap<>();

	private HashMap<String, School> schools = new HashMap<>();

	/**
	 * Creates a new region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = name;
	}

	/**
	 * Getter method
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves all schools in the region
	 * 
	 * @return collection of schools
	 */
	public Collection<School> getSchools() {
		return schools.values();
	}

	/**
	 * Retrieves all the communities
	 * 
	 * @return the collection of all communities
	 */
	public Collection<Community> getCommunities() {
		return communities.values();
	}

	/**
	 * Retrieves all municipalities in the region
	 * 
	 * @return the collection of municipalities
	 */
	public Collection<Municipality> getMunicipalies() {
		return municipalities.values();
	}

	// factory methods

	/**
	 * Factory method that build a new community of the given type. The type is
	 * {@link Community.Type}
	 * 
	 * @param name
	 *            name of the community
	 * @param type
	 *            type of the community
	 * @return the new created community
	 */
	public Community newCommunity(String name, Community.Type type) {
		Community c = new Community(name, type);
		communities.put(c.getName(), c);

		return c;
	}

	/**
	 * Factory method that build a new municipality.
	 * 
	 * @param nome
	 *            name of the municipality
	 * @param province
	 *            province of the municipality
	 * @return the new created municipality
	 */
	public Municipality newMunicipality(String nome, String province) {
		Municipality m = new Municipality(nome, province);
		municipalities.put(m.getName(), m);

		return m;
	}

	/**
	 * Factory methods, that build a new municipality that is part of a community.
	 * 
	 * @param nome
	 *            name of the municipality
	 * @param province
	 *            province of the municipality
	 * @param comunita
	 *            community the municipality belongs to
	 * @return the new created municipality
	 */
	public Municipality newMunicipality(String nome, String province, Community comunita) {
		Municipality m = new Municipality(nome, province, comunita);
		municipalities.put(m.getName(), m);

		return m;
	}

	/**
	 * Factory method that creates a new school
	 * 
	 * @param name
	 *            name of the school
	 * @param code
	 *            code of the school
	 * @param grade
	 *            grade of the school (1 to 4)
	 * @param description
	 *            description of the school
	 * 
	 * @return a new school object
	 */
	public School newSchool(String name, String code, int grade, String description) {
		School s = new School(name, code, grade, description);
		schools.put(s.getCode(), s);

		return s;
	}

	/**
	 * Factory method that creates a new school branch
	 * 
	 * @param regionalCode
	 *            regional code of the branch
	 * @param municipality
	 *            municipality where the branch is located
	 * @param address
	 *            address of the branch
	 * @param zipCode
	 *            zip code of the branch
	 * @param school
	 *            school the branch is part of
	 * @return the new created branch
	 */
	public Branch newBranch(int regionalCode, Municipality municipality, String address, int zipCode, School school) {
		Branch b = new Branch(regionalCode, municipality, address, zipCode, school);
		municipality.addBranch(b);
		
		return b;
	}

	/**
	 * Load data from a file.
	 * 
	 * The file must be a CSV file and it is supposed to contain the following
	 * fields:
	 * <ul>
	 * <li>{@code "Provincia"}, (province)
	 * <li>{@code "Comune"}, (municipality)
	 * <li>{@code "Grado Scolastico"}, (school grade)
	 * <li>{@code "Descrizione Scuola"}, (school description)
	 * <li>{@code "Cod Sede"}, (branch code)
	 * <li>{@code "Cod Scuola"}, (school code)
	 * <li>{@code "Denominazione Scuola"}, (name of the school)
	 * <li>{@code "Indirizzo e n. civico"}, (address of the branch)
	 * <li>{@code "C.A.P."}, (zip code of the branch)
	 * <li>{@code "Comunita Collinare"}, (Hill community)
	 * <li>{@code "Comunita Montana"}, (Mountain community)
	 * </ul>
	 * 
	 * @param file
	 *            the path of the file
	 */
	public void readData(String file) {
		List<String> lines = null;
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			lines = in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		// each item of the list contains a line of
		// the CSV file that can be split using "," as separator
		
		lines.stream().skip(1).forEach(line -> {
			String[] parts = line.split(",");
			
			String provincia = parts[0];
			String comune = parts[1];
			Integer grado = Integer.parseInt(parts[2]);
			String descrizione = parts[3];
			Integer branchCode = Integer.parseInt(parts[4]);
			String schoolCode = parts[5];
			String schoolName = parts[6];
			String address = parts[7];
			Integer zipCode = Integer.parseInt(parts[8]);
			String comCollineare = 9 < parts.length ? parts[9] : "";
			String comMontana = 10 < parts.length ? parts[10] : "";
			
			// detect type
			Community.Type communityType = null;
			String communityName = "";
			
			if (!comCollineare.equals("")) {
				communityType = Community.Type.COLLINARE;
				communityName = comCollineare;
			}
			else if (!comMontana.equals("")) {
				communityType = Community.Type.MONTANA;
				communityName = comMontana;
			}		
			
			// check for municipality
			Municipality mun = null;
			List<Municipality> ml = new ArrayList<Municipality>(getMunicipalies());
			for (Municipality m : ml)
				if (m.getName().equals(comune) && m.getProvince().equals(provincia))
					mun = m;
			
			if (mun == null) // no municipality found
				if (!communityName.equals("")) {
					mun = newMunicipality(comune, provincia, newCommunity(communityName, communityType));
				}
				else
					mun = newMunicipality(comune, provincia);
			
			// check for school
			School sch = null;
			List<School> sl = new ArrayList<School>(getSchools());
			for (School s : sl)
				if (s.getCode().equals(schoolCode))
					sch = s;
			
			if (sch == null) // no school found
				sch = newSchool(schoolName, schoolCode, grado, descrizione);
			
			// add branch
			newBranch(branchCode, mun, address, zipCode, sch);
		});
	}

	/**
	 * Counts how many schools there exist for each description
	 * 
	 * @return a map of school count vs. description
	 */
	public Map<String, Long> countSchoolsPerDescription() {
		return getSchools().stream()
				.collect(groupingBy(School::getDescription, counting()));
	}

	/**
	 * Count how many school branches there exist for each municipality
	 * 
	 * @return a map of branch count vs. municipality
	 */
	public Map<String, Long> countBranchesPerMunicipality() {
		return getMunicipalies().stream()
				.collect(toMap(m -> m.getName(), m -> Long.valueOf(m.getBranches().size())));
	}

	/**
	 * Counts the number of school branches per municipality and groups them by
	 * province.
	 * 
	 * @return a map of maps the inner reports count of branches vs. municipality
	 *         the outer reports provinces as keys
	 */
	public Map<String, Map<String, Long>> countBranchesPerMunicipalityPerProvince() {
		return getMunicipalies().stream()
				.collect(groupingBy(m -> m.getProvince(),
				toMap(m -> m.getName(), m -> Long.valueOf(m.getBranches().size()))));
	}

	/**
	 * returns a list of strings with format {@code "### - XXXXXX"}, where
	 * {@code ###} represents the number of schools (not branches) and
	 * {@code XXXXXX} represents the name of the municipality. If a school has more
	 * than one branch in a municipality it must be counted only once.
	 * 
	 * @return a collection of strings with the counts
	 */
	public Collection<String> countSchoolsPerMunicipality() {
		return getSchools().stream()
				.flatMap(s -> s.getBranches().stream())
				.collect(groupingBy(s -> s.getMunicipality().getName(), 
							mapping(Branch::getSchool, collectingAndThen(toSet(), Set::size))))
				.entrySet().stream()
				.map(e -> e.getValue() + " - " + e.getKey())
				.collect(toList());
	}

	/**
	 * returns a list of strings with format {@code "### - XXXXXX"}, where
	 * {@code ###} represents the number of schools (not branches) and
	 * {@code XXXXXX} represents the name of the community. They are sorted by
	 * descending number of schools. The list must contain only schools having at
	 * least a branch in a municipality part of a community.
	 * 
	 * @return a collection of strings with the counts
	 */
	public List<String> countSchoolsPerCommunity() {
		return getSchools().stream()
				.map(School::getBranches)
				.flatMap(Collection::stream)
				.filter( s->s.getMunicipality().getCommunity().isPresent() )
				.collect(groupingBy( s->s.getMunicipality().getCommunity().get().getName(),
									 mapping(Branch::getSchool,collectingAndThen(toSet(), Set::size))
									)
						)
				.entrySet().stream()
				.sorted(comparing(Map.Entry::getValue,reverseOrder()))
				.map( e -> e.getValue()+ " - "+e.getKey())
				.collect(toList())
				;
	}

}
