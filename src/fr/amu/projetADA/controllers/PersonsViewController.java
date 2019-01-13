package fr.amu.projetADA.controllers;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;
import io.codearte.jfairy.Fairy;

@ManagedBean(name = "personsView")
@SessionScoped
public class PersonsViewController {

	private Person selectedPerson;

	private LazyDataModel<Person> persons;

	@EJB
	private PersonManager personManager;

	
	@PostConstruct
	public void init() {
		Fairy fairy = Fairy.create();
		
		if(personManager.countNbPerson() == 0) {
			for(int i = 0 ; i < 100_000 ; i++) {
				io.codearte.jfairy.producer.person.Person person = fairy.person();
				Person personEntity = new Person(person.getFirstName(), person.getLastName(), person.getDateOfBirth().toDate(), person.getPassword(), "example" + i + "@example.fr");
				personManager.addPerson(personEntity);
				System.out.println(i);
			}	
		}
		
		
		
		persons = new LazyDataModel<Person>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Person> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				
				persons.setRowCount((int) personManager.countNbPerson());
				
				return personManager.findAllPerson(pageSize, first);
			}
			
		};	
	}
	
	
	public String seeProfil(Person person) {
		return "persons";
	}
 
	public Person getSelectedPerson() {
		return selectedPerson;
	}

	public void setSelectedPerson(Person selectedPerson) {
		this.selectedPerson = selectedPerson;
	}

	public LazyDataModel<Person> getPersons() {
		return persons;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}


	public void setPersons(LazyDataModel<Person> persons) {
		this.persons = persons;
	}
	
	
}
