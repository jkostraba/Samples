package apitest;

import java.util.HashMap;
import java.util.Map;

public class ContactStorage {

	private Map<String, Contact> personMap = new HashMap<>();
	
	// This class is a singleton - as such, it is referred to using
	// "getInstance()" and it is not new'ed as other classes.
	//
	private static ContactStorage instance = new ContactStorage();
	public static ContactStorage getInstance() { return instance; }

	// when this singleton is created, put a few records in it - for demo purposes
	private ContactStorage(){
		personMap.put("John", new Contact("John", "12 Anon Dr", "602-377-anon"));
		personMap.put("Braden", new Contact("Braden", "9914 Anon St", "415-200-anon"));
		personMap.put("Mike", new Contact("Mike", "415 Anon Ct", "719-411-anon"));
	}

	public Contact get(String key) {
		return personMap.get(key);
	}

	public void put(Contact person) {
		personMap.put(person.getKey(), person);
	}
}
