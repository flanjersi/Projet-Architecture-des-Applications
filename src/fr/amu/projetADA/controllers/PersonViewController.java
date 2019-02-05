package fr.amu.projetADA.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import fr.amu.projetADA.beans.cv.Activity;
import fr.amu.projetADA.beans.cv.CurriculumVitae;
import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.activity.ActivityManager;
import fr.amu.projetADA.services.connectedUser.ConnectedUser;
import fr.amu.projetADA.services.person.PersonManager;

@ManagedBean(name = "personView")
@SessionScoped
public class PersonViewController implements Serializable{

	private static final long serialVersionUID = 7112822155888079869L;

	@EJB
	private ConnectedUser connectedUser;

	@EJB
	private PersonManager personManager;

	@EJB
	private ActivityManager activityManager;


	private String currentPasswordEdit;
	private String newPasswordEdit;
	private String confirmNewPasswordEdit;
	
	private String cvTitle;

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


	public void setConnectedPerson(Person p) {	
		if(p == null)
			return;

		p.setLastConnexion(new Date(System.currentTimeMillis()));
		p = personManager.updatePerson(p);
		connectedUser.setPersonLogged(p);
	}

	public String logout() {
		connectedUser.setPersonLogged(null);

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "profil?faces-redirect=true";
	}

	public String delete() {
		personManager.removePerson(connectedUser.getPersonLogged());
		connectedUser.setPersonLogged(null);

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "profil?faces-redirect=true";
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
	
	public void editCVTitle() {
		personManager.updatePerson(getPerson());
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful update", "Successful update");
		FacesContext.getCurrentInstance().addMessage(null, msg);		
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
		if(getPerson().getCurriculumVitae() == null) {
			return new ArrayList<>();
		}
					
		return getPerson().getCurriculumVitae().getActivities()
				.stream()
				.filter(act -> act.getType().equals(type))
				.collect(Collectors.toList());
	}
	
	public void addCV() {
		if(getPerson().getCurriculumVitae() != null) {
			return;
		}
		
		CurriculumVitae curriculumVitae = new CurriculumVitae(cvTitle);
		
		getPerson().setCurriculumVitae(curriculumVitae);
		personManager.updatePerson(getPerson());
		
		PrimeFaces.current().executeScript("PF('addCVDialog').hide();PF('addActivityDialog').show();");
	}


	public String getCvTitle() {
		return cvTitle;
	}


	public void setCvTitle(String cvTitle) {
		this.cvTitle = cvTitle;
	}
	
	
}
