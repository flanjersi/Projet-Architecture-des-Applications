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

@ManagedBean(name = "personsView")
@SessionScoped
public class PersonsViewController {

	private Person selectedPerson;

	private LazyDataModel<Person> persons;

	@EJB
	private PersonManager personManager;

	
	@PostConstruct
	public void init() {
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
