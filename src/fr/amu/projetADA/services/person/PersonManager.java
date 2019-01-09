package fr.amu.projetADA.services.person;

import java.util.List;

import javax.ejb.Local;

import fr.amu.projetADA.beans.person.Person;

@Local
public interface PersonManager {

	
	public Person findPerson(long id);
	
	public void addPerson(Person person);
	
	public void removePerson(Person person);
	
	public Person updatePerson(Person person);
	
	public List<Person> findAllPerson();
	
	public List<Person> findByFirstName(String firstName);
	
	public List<Person> findByName(String Name);
	
	public Person findByemail(String email);
	
	public Person findByEmailAndPassword(String email,String pwd);
	
}