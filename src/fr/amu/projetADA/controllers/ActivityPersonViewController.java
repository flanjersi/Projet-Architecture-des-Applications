package fr.amu.projetADA.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import fr.amu.projetADA.beans.cv.Activity;
import fr.amu.projetADA.services.person.PersonManager;

@ManagedBean(name = "activityPersonView")
@SessionScoped
public class ActivityPersonViewController {
	
	@ManagedProperty("#{personView}")
	private PersonViewController personViewController;

	@EJB
	private PersonManager personManager;

	private Activity activity;
	
	public ActivityPersonViewController() {}
	
	@PostConstruct
	public void init() {
		System.out.println("Test");
		activity = new Activity();		
	}
	
	public Activity getActivity() {
		if(activity == null) {
			activity = new Activity();
		}
		
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void add() {
		personViewController.getPerson().getCurriculumVitae().addActivity(activity);
		personManager.updatePerson(personViewController.getPerson());
		
		activity = new Activity();
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
	
	
	
}
