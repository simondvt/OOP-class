import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import schools.Community;
import schools.Municipality;
import schools.Region;
import schools.School;

public class prova {

	public static void main(String[] args) {
		
		String line = "123,ciao,poli,,strano";
		String[] parts = line.split(",");
		for (int i = 0; i < parts.length; i++)
			System.out.println(i + ": " + parts[i]);
		
		System.out.println(parts[3].equals(""));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		System.exit(0);
		Region r = new Region("Piemonte");
		r.readData("D:\\simon\\Google Drive\\Politecnico\\2 Anno\\Programmazione a oggetti\\SVN\\Lab\\OOP_LAB_Schools\\schools.csv");
	
		Collection<Community> communities = r.getCommunities();
		Map<Community.Type,Long> counts = communities.stream().collect(Collectors.groupingBy(Community::getType,Collectors.counting()));
	
		System.out.println("Size: " + communities.size());
		System.out.println("Size collineare: " + counts.get(Community.Type.COLLINARE).longValue());
		System.out.println("Size montana: " + counts.get(Community.Type.MONTANA).longValue());
	}

}
