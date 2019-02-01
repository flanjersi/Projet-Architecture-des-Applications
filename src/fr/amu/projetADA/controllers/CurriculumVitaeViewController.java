package fr.amu.projetADA.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import fr.amu.projetADA.beans.cv.Activity;
import fr.amu.projetADA.beans.cv.CurriculumVitae;
import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.curriculumVitae.CurriculumVitaeManager;

@ManagedBean(name="curriculumVitaeView")
@SessionScoped
public class CurriculumVitaeViewController implements Serializable{

	private static final long serialVersionUID = 7255657121709014514L;

	@EJB
	private CurriculumVitaeManager curriculumVitaeManager;
	
	private LazyDataModel<CurriculumVitae> curriculumsVitae;
	
	private CurriculumVitae selectedCV;



	private String filter;

	
	@PostConstruct
	public void init() {
		curriculumsVitae = new LazyDataModel<CurriculumVitae>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<CurriculumVitae> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				
				curriculumsVitae.setRowCount((int) curriculumVitaeManager.countNbCurriculumVitae());
				
				return curriculumVitaeManager.findAllCurriculumVitae(pageSize, first);
			}
		};	
	}
	
	
	public CurriculumVitae getSelectedCV() {
		return selectedCV;
	}


	public void setSelectedCV(CurriculumVitae selectedCV) {
		this.selectedCV = selectedCV;
	}
	
	public String seeProfil(Person person) {
		return "persons";
	}
 
	public LazyDataModel<CurriculumVitae> getCurriculumsVitae() {
		return curriculumsVitae;
	}

	public void setPersonManager(CurriculumVitaeManager curriculumVitaeManager) {
		this.curriculumVitaeManager = curriculumVitaeManager;
	}


	public void setCurriculumsVitae(LazyDataModel<CurriculumVitae> curriculumsViate) {
		this.curriculumsVitae = curriculumsViate;
	}


	public String getFilter() {
		return filter;
	}


	public void setFilter(String filter) {
		this.filter = filter;
	}
	

	public List<Activity> getExperiences(){		
		return filterActivitiesByType("Experience");
	}

	public List<Activity> getFormations(){
		return filterActivitiesByType("Formation");
	}

	public List<Activity> getCertifications(){
		return filterActivitiesByType("Certification");
	}

	public List<Activity> getProjets(){
		return filterActivitiesByType("Projet");
	}
	
	private List<Activity> filterActivitiesByType(String type){
		if(getSelectedCV() == null) {
			return new ArrayList<>();
		}
					
		return getSelectedCV().getActivities()
				.stream()
				.filter(act -> act.getType().equals(type))
				.collect(Collectors.toList());
	}

	
}
