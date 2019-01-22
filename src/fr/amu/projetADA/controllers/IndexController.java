package fr.amu.projetADA.controllers;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;

@ManagedBean(name = "index")
@SessionScoped
public class IndexController {
	
	@EJB
	private PersonManager personManager;
	
	public IndexController() {}
	
}
