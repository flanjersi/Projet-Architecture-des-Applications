package fr.amu.projetADA.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;

@ManagedBean(name = "personsView")
@SessionScoped
public class PersonsViewController implements Serializable{

	private static final long serialVersionUID = -8813884141809137537L;

	private Person selectedPerson;

	private LazyDataModel<Person> persons;

	@EJB
	private PersonManager personManager;

	private String filter;

	private int nbData;

	@PostConstruct
	public void init() {

		persons = new LazyDataModel<Person>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Person> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				if(filter == null || filter.length() == 0){
					persons.setRowCount((int) personManager.countNbPerson());

					List<Person> personsData = personManager.findAllPerson(pageSize, first);

					setNbData(personsData.size());

					return personsData;

				}
				else {
					persons.setRowCount((int) personManager.countByNamesAndFirstNameByPattern(filter));

					List<Person> personsData = personManager.findByNamesAndFirstNameByPattern(filter, pageSize, first);

					setNbData(personsData.size());
					
					return personsData;
				}
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



	public String getFilter() {
		return filter;
	}



	public void setFilter(String filter) {
		this.filter = filter;
	}



	public int getNbData() {
		return nbData;
	}



	public void setNbData(int nbData) {
		this.nbData = nbData;
	}




}
