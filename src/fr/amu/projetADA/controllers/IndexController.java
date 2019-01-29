package fr.amu.projetADA.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.amu.projetADA.services.person.PersonManager;

@ManagedBean(name = "index")
@SessionScoped
public class IndexController implements Serializable{
	
	private static final long serialVersionUID = 453806333383650241L;
	
	@EJB
	private PersonManager personManager;
	
	public IndexController() {}
	
}
