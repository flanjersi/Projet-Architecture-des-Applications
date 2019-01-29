package fr.amu.projetADA.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import fr.amu.projetADA.beans.cv.CurriculumVitae;
import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.curriculumVitae.CurriculumVitaeManager;

@ManagedBean(name="curriculumVitaeView")
@SessionScoped
public class CurriculumVitaeViewController implements Serializable{

	private static final long serialVersionUID = 7255657121709014514L;

	@EJB
	private CurriculumVitaeManager curriculumVitaeManager;
	
	private LazyDataModel<CurriculumVitae> curriculumsViate;
	
	private String filter;

	
	@PostConstruct
	public void init() {
		curriculumsViate = new LazyDataModel<CurriculumVitae>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<CurriculumVitae> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				
				curriculumsViate.setRowCount((int) curriculumVitaeManager.countNbCurriculumVitae());
				
				return curriculumVitaeManager.findAllCurriculumVitae(pageSize, first);
			}
		};	
	}
	
	
	public String seeProfil(Person person) {
		return "persons";
	}
 
	public LazyDataModel<CurriculumVitae> getCurriculumsVitae() {
		return curriculumsViate;
	}

	public void setPersonManager(CurriculumVitaeManager curriculumVitaeManager) {
		this.curriculumVitaeManager = curriculumVitaeManager;
	}


	public void setCurriculumsVitae(LazyDataModel<CurriculumVitae> curriculumsViate) {
		this.curriculumsViate = curriculumsViate;
	}


	public String getFilter() {
		return filter;
	}


	public void setFilter(String filter) {
		this.filter = filter;
	}
	
}
