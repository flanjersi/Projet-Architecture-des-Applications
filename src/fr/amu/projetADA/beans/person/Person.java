package fr.amu.projetADA.beans.person;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import fr.amu.projetADA.beans.cv.CurriculumVitae;

/**
 * Class which represents a Person
 * @author Jeremy
 *
 */
@Entity
@Table(name = "Person", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })

@NamedQueries({
@NamedQuery(name = "findAllPersons", query = "From Person"),
@NamedQuery(name = "findByFirstName", query = "SELECT p From Person p WHERE p.firstName = :firstName"),
@NamedQuery(name = "findByNames", query = "SELECT p From Person p WHERE p.name like Concat('%',:name,'%')"),
@NamedQuery(name = "findByEmail", query = "SELECT p From Person p WHERE p.email = :email"),
@NamedQuery(name = "findByEmailAndPwd", query = "SELECT p From Person p WHERE p.email = :email and p.password = :pwd"),
@NamedQuery(name = "countPersons", query = "SELECT count(p.id) From Person p"),
})
public class Person implements Serializable{

	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(Person.class.getName());

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Basic
	@Column(nullable = false)
	private String firstName;

	@Basic
	@Column(nullable = false)
	private String name;

	@Basic
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date birthDay;

	@Basic
	@Column(nullable = false)
	private String password;

	@Basic
	@Column(nullable = false)
	private String email;

	@Basic
	@Column(nullable = true)
	private String webSite;

	@OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	@JoinColumn(name="person_id", referencedColumnName="id")
	private CurriculumVitae curriculumVitae;

	public Person() {}

	public Person(String firstName, String name, Date birthday, String password, String email) {
		this.firstName = firstName;
		this.name = name;
		this.birthDay = birthday;
		this.password = password;
		this.email = email;
	}


	@PreUpdate
	public void beforeUpdate() {
		logger.info("PreUpdate of " + this);
	}

	@PostUpdate
	public void afterUpdate() {
		logger.info("PostUpdate of " + this);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public CurriculumVitae getCurriculumVitae() {
		return curriculumVitae;
	}

	public void setCurriculumVitae(CurriculumVitae curriculumVitae) {
		if (curriculumVitae == null) {
			if (this.curriculumVitae != null) {
				this.curriculumVitae.setPerson(null);
			}
		}
		else {
			curriculumVitae.setPerson(this);
		}
		this.curriculumVitae = curriculumVitae;	
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", name=" + name + ", birthDay=" + birthDay
		+ ", password=" + password + ", email=" + email + ", webSite=" + webSite + ", curriculum vitae=" + curriculumVitae + "]";
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Person other = (Person) obj;

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

		if (birthDay == null) {
			if (other.birthDay != null)
				return false;
		} 
		else if (!formatDate.format(birthDay).equals(formatDate.format(other.birthDay)))
			return false;

		if (email == null) {
			if (other.email != null)
				return false;
		} 
		else if (!email.equals(other.email))
			return false;

		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} 
		else if (!firstName.equals(other.firstName))
			return false;

		if (id != other.id)
			return false;

		if (name == null) {
			if (other.name != null)
				return false;
		} 
		else if (!name.equals(other.name))
			return false;

		if (password == null) {
			if (other.password != null)
				return false;
		} 
		else if (!password.equals(other.password))
			return false;

		if (webSite == null) {
			if (other.webSite != null)
				return false;
		} 
		else if (!webSite.equals(other.webSite))
			return false;

		if (curriculumVitae == null) {
			if (other.curriculumVitae != null)
				return false;
		} 
		else if (!curriculumVitae.equals(other.curriculumVitae))
			return false;

		return true;
	}	



}
