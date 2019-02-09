package fr.amu.projetADA.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import fr.amu.projetADA.beans.cv.Activity;
import fr.amu.projetADA.beans.cv.CurriculumVitae;
import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.curriculumVitae.CurriculumVitaeManager;

@ManagedBean(name="curriculumVitaeView")
@ViewScoped
public class CurriculumVitaeViewController implements Serializable{

	private static final long serialVersionUID = 7255657121709014514L;

	@EJB
	private CurriculumVitaeManager curriculumVitaeManager;

	private LazyDataModel<CurriculumVitae> curriculumsVitae;

	private CurriculumVitae selectedCV;

	private int nbData;

	private String filter;


	@PostConstruct
	public void init() {
		curriculumsVitae = new LazyDataModel<CurriculumVitae>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<CurriculumVitae> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				if(filter == null || filter.length() == 0){
					curriculumsVitae.setRowCount((int) curriculumVitaeManager.countNbCurriculumVitae());
					List<CurriculumVitae> cvsData = curriculumVitaeManager.findAllCurriculumVitae(pageSize, first);
					setNbData(cvsData.size());
					
					return cvsData;
				}
				else {
					
					curriculumsVitae.setRowCount((int) curriculumVitaeManager.sizeOfCvs(filter));
					
					List<CurriculumVitae> cvsByActivity = curriculumVitaeManager.findCvByActivity(filter,pageSize,first);
					List<CurriculumVitae> cvsByTitle = curriculumVitaeManager.findCvByTitle(filter,pageSize,first);
					List<CurriculumVitae> concatCvs = new ArrayList<>();
					concatCvs.addAll(cvsByActivity);					
					
					for(CurriculumVitae cv : cvsByTitle )
						if(!concatCvs.contains(cv))
							concatCvs.add(cv);
					
					//concatCvs.addAll(cvsByTitle); 
					//curriculumsVitae.setRowCount((int) curriculumVitaeManager.findCvByActivity(filter,pageSize,first));
					
					setNbData(concatCvs.size());
					return concatCvs;
				}
			}
		};	
	}

	public int getNbData() {
		return nbData;
	}


	public void setNbData(int nbData) {
		this.nbData = nbData;
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


	public List<Activity> getExperiences(CurriculumVitae curriculumVitae){	
		if(curriculumVitae == null) return new ArrayList<>();
		
		return filterActivitiesByType("Experience", curriculumVitae);
	}

	public List<Activity> getFormations(CurriculumVitae curriculumVitae){
		if(curriculumVitae == null) return new ArrayList<>();
		
		return filterActivitiesByType("Formation", curriculumVitae );
	}

	public List<Activity> getCertifications(CurriculumVitae curriculumVitae){
		if(curriculumVitae == null) return new ArrayList<>();
		
		return filterActivitiesByType("Certification", curriculumVitae);
	}

	public List<Activity> getProjets(CurriculumVitae curriculumVitae){
		if(curriculumVitae == null) return new ArrayList<>();
		
		return filterActivitiesByType("Projet", curriculumVitae);
	}

	private List<Activity> filterActivitiesByType(String type, CurriculumVitae curriculumVitae){
		if(curriculumVitae == null) {
			return new ArrayList<>();
		}

		return curriculumVitae.getActivities()
				.stream()
				.filter(act -> act.getType().equals(type))
				.collect(Collectors.toList());
	}


}
