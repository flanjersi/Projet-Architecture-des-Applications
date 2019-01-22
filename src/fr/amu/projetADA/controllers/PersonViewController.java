package fr.amu.projetADA.controllers;

import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.connectedUser.ConnectedUser;
import fr.amu.projetADA.services.person.PersonManager;

@ManagedBean(name = "personView")
@SessionScoped
public class PersonViewController {

	@EJB
	private ConnectedUser connectedUser;
	
	@EJB
	private PersonManager personManager;
	
	private String currentPasswordEdit;
	private String newPasswordEdit;
	private String confirmNewPasswordEdit;
	
	
	@PostConstruct
	public void init() {
		if(personManager.findByemail("admin@admin.fr") == null) {
			Person person = new Person("admin", "admin", new Date(System.currentTimeMillis()), "admin", "admin@admin.fr");
			person.setLastConnexion(new Date(System.currentTimeMillis()));
			personManager.addPerson(person);	
		}
	
		
		if(personManager.findByemail("jeremy.gros@youcv.fr") == null) {
			Person personJeremy = new Person("Jérémy", "Gros", new Date(System.currentTimeMillis()), "jeremy.gros", "jeremy.gros@youcv.fr");
			personManager.addPerson(personJeremy);
		}
		
		if(personManager.findByemail("timothee.goibeault@youcv.fr") == null) {
			Person personTimothee = new Person("Timothee-Swann", "Goibeault", new Date(System.currentTimeMillis()), "timothee.goibeault", "timothee.goibeault@youcv.fr");
			personManager.addPerson(personTimothee);
		}
		
		if(personManager.findByemail("youcef.guellil@youcv.fr") == null) {
			Person personRamzi = new Person("Youcef", "Guellil", new Date(System.currentTimeMillis()), "youcef.guellil", "youcef.guellil@youcv.fr");			
			personManager.addPerson(personRamzi);
		}
	}
	
	
	public boolean login(String login, String pwd) {	
		Person p = personManager.findByEmailAndPassword(login, pwd);
		
		if(p == null)
			return false;
		
		p.setLastConnexion(new Date(System.currentTimeMillis()));
		p = personManager.updatePerson(p);
		connectedUser.setPersonLogged(p);
		
		return true;
	}
	
	public String logout() throws IOException {
		connectedUser.setPersonLogged(null);

		return "profil";
	}
	
	public String delete() throws IOException {
		personManager.removePerson(connectedUser.getPersonLogged());
		connectedUser.setPersonLogged(null);
		
		return "profil";
	}
	
	public boolean isLogged()  {
		return connectedUser.getPersonLogged() != null;
	}
	
	public Person getPerson() {
		return connectedUser.getPersonLogged();
	}
	
	public void setPerson(Person person) {
		connectedUser.setPersonLogged(person);
	}	
	
	public String getCurrentPasswordEdit() {
		return currentPasswordEdit;
	}


	public void setCurrentPasswordEdit(String currentPasswordEdit) {
		this.currentPasswordEdit = currentPasswordEdit;
	}


	public String getNewPasswordEdit() {
		return newPasswordEdit;
	}


	public void setNewPasswordEdit(String newPasswordEdit) {
		this.newPasswordEdit = newPasswordEdit;
	}


	public String getConfirmNewPasswordEdit() {
		return confirmNewPasswordEdit;
	}


	public void setConfirmNewPasswordEdit(String confirmNewPasswordEdit) {
		this.confirmNewPasswordEdit = confirmNewPasswordEdit;
	}


	public void edit() {
		setPerson(personManager.updatePerson(getPerson()));
	}
	
	public void editPassword() {
		if(!org.apache.commons.codec.digest.DigestUtils.sha256Hex(currentPasswordEdit).equals(getPerson().getPassword())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect current password", "Incorrect current password");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		
		if(!newPasswordEdit.equals(confirmNewPasswordEdit)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password not equals", "Password not equals");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			return;
		}
		
		getPerson().setPassword(confirmNewPasswordEdit);
		personManager.updatePerson(getPerson());
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful update", "Successful update");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	
	
		currentPasswordEdit = "";
		confirmNewPasswordEdit = "";
		newPasswordEdit = "";
	}
	
}
