package apitest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactStorage {

	private Map<String, Contact> personMap = new HashMap<>();
	private Map<String, ContactList> listMap = new HashMap<>();
	
	// This class is a singleton - as such, it is referred to using
	// "getInstance()" and it is not new'ed as other classes.
	//
	private static ContactStorage instance = new ContactStorage();
	public static ContactStorage getInstance() { return instance; }

	// when this singleton is created, put a few records in it - for demo purposes
	private ContactStorage(){
		personMap.put("jk@somewhere.com", new Contact( "jk@somewhere.com", "John", "12 Anon Dr", "602-377-anon"));
		personMap.put("bc@somewhere.com", new Contact( "bc@somewhere.com", "Braden", "9914 Anon St", "415-200-anon"));
		personMap.put("mike@somewhere.com", new Contact( "mike@somewhere.com", "Mike", "415 Anon Ct", "719-411-anon"));
	}

	public Contact get(String key) {
		return personMap.get(key);
	}

	public void put(Contact person) {
		personMap.put(person.getKey(), person);
	}
	
	public Contact delete(String key) {
		return personMap.remove(key);
	}

	public ArrayList<Contact> getContacts() {
		ArrayList<Contact> retVal = new ArrayList<>();
		retVal.addAll( this.personMap.values() );
		return retVal;
	}
	
	public ContactList getList(String key) {
	    return listMap.get(key);
	}
	
	public void putList(ContactList list) {
	    listMap.put(list.getName(), list);
	}
	
	public ArrayList<ContactList> getContactLists() {
		ArrayList<ContactList> retVal = new ArrayList<>();
		retVal.addAll( this.listMap.values() ) ;
		return retVal;
	}
}
