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
@NamedQuery(name = "findByNames", query = "SELECT p From Person p WHERE LOWER(p.name) like Concat('%',LOWER(:name),'%')"),
@NamedQuery(name = "findByEmail", query = "SELECT p From Person p WHERE p.email = :email"),
@NamedQuery(name = "findByEmailAndPwd", query = "SELECT p From Person p WHERE p.email = :email and p.password = :pwd"),
@NamedQuery(name = "findByNamesAndFirstName", query = "SELECT p From Person p WHERE LOWER(p.name) like Concat(LOWER(:name),'%') OR LOWER(p.firstName) like Concat(LOWER(:firstName),'%') ORDER BY p.firstName, p.name"),
@NamedQuery(name = "findByNamesAndFirstNameByPattern", query = "SELECT p From Person p WHERE LOWER(p.name) like Concat(LOWER(:str),'%') OR LOWER(p.firstName) like Concat(LOWER(:str),'%') OR LOWER(Concat(p.firstName, ' ', p.name)) like Concat(LOWER(:str),'%') ORDER BY p.firstName, p.name"),

@NamedQuery(name = "countPersons", query = "SELECT count(p.id) From Person p"),
@NamedQuery(name = "countPersonsByNamesAndFirstName", query = "SELECT count(p.id) From Person p WHERE LOWER(p.name) like Concat(LOWER(:name),'%') OR LOWER(p.firstName) like Concat(LOWER(:firstName),'%')"),
@NamedQuery(name = "countPersonsByNamesAndFirstNameByPattern", query = "SELECT count(p.id) From Person p WHERE LOWER(p.name) like Concat(LOWER(:str),'%') OR LOWER(p.firstName) like Concat(LOWER(:str),'%') OR LOWER(Concat(p.firstName, ' ', p.name)) like Concat(LOWER(:str),'%')"),
}
)
public class Person implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 9080475780123353521L;

	private final static Logger logger = Logger.getLogger(Person.class.getName());

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Basic(optional = false)
	@Column(nullable = false, length = 100)
	private String firstName;

	@Basic(optional = false)
	@Column(nullable = false, length = 100)
	private String name;

	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date birthDay;

	@Basic(optional = false)
	@Column(nullable = false)
	private String password;

	@Basic(optional = false)
	@Column(nullable = false, length = 100)
	private String email;

	@Basic(optional = true)
	@Column(nullable = true, length = 200)
	private String webSite;

	@Basic(optional = true)
	@Column(nullable = true, length = 3000)
	private String description;

	@Basic(optional = true)
	@Column(nullable = true, length = 100)
	private String status;
	
	@Basic(optional = true)
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date lastConnexion;
		
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private CurriculumVitae curriculumVitae;

	public Person() {}

	public Person(String firstName, String name, Date birthday, String password, String email) {
		this.firstName = firstName;
		this.name = name;
		this.birthDay = birthday;
		this.password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
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
		this.password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
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

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public Date getLastConnexion() {
		return lastConnexion;
	}

	public void setLastConnexion(Date lastConnexion) {
		this.lastConnexion = lastConnexion;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", name=" + name + ", birthDay=" + birthDay
				+ ", password=" + password + ", email=" + email + ", webSite=" + webSite + ", description="
				+ description + ", status=" + status + "]";
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

		if (description == null) {
			if (other.description != null)
				return false;
		} 
		else if (!description.equals(other.description))
			return false;

		if (status == null) {
			if (other.status != null)
				return false;
		} 
		else if (!status.equals(other.status))
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
