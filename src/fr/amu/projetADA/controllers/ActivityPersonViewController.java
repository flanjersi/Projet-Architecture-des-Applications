package fr.amu.projetADA.controllers;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import fr.amu.projetADA.beans.cv.Activity;
import fr.amu.projetADA.services.person.PersonManager;

@ManagedBean(name = "activityPersonView")
@ViewScoped
public class ActivityPersonViewController implements Serializable{
	
	private static final long serialVersionUID = 3349151030787878471L;

	@EJB
	private PersonManager personManager;
	
	@ManagedProperty("#{personView}")
	private PersonViewController personViewController;

	private Activity activity;
	
	public ActivityPersonViewController() {
		activity = new Activity();		
	}
		
	public Activity getActivity() {
		return activity;
	}
	
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void add() {
		if(personViewController.getPerson().getCurriculumVitae() == null) {
			PrimeFaces.current().executeScript("PF('addActivityDialog').hide();PF('addCVDialog').show();");
			return;
		}
		
		personViewController.getPerson().getCurriculumVitae().addActivity(activity);
		personManager.updatePerson(personViewController.getPerson());
		
		activity = new Activity();
		
		PrimeFaces.current().executeScript("PF('addActivityDialog').hide();");
	}
	
	public void experience() {
		activity.setType("Experience");
	}
	
	public void formation() {
		activity.setType("Formation");
	}
	
	public void projet() {
		activity.setType("Projet");
	}
	
	public void certification() {
		activity.setType("Certification");
	}

	public PersonViewController getPersonViewController() {
		return personViewController;
	}

	public void setPersonViewController(PersonViewController personViewController) {
		this.personViewController = personViewController;
	}
	
	
}
