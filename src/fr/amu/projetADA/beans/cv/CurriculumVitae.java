package fr.amu.projetADA.beans.cv;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.amu.projetADA.beans.person.Person;

/**
 * Class which represents a curriculum vitae of a person
 * @author Jeremy
 *
 */
@Entity
@Table(name = "CurriculumVitae")

@NamedQueries({
@NamedQuery(name = "findAllCurriculumVitae", query = "From CurriculumVitae"),
@NamedQuery(name = "countCurriculumsVitae", query = "SELECT count(cv.id) From CurriculumVitae cv"),
@NamedQuery(name = "findCurriculumVitaeByTitle", query = "From CurriculumVitae WHERE LOWER(title) LIKE CONCAT('%',LOWER(:title),'%')"),
@NamedQuery(name = "findCurriculumVitaeByActivity", query = "From CurriculumVitae WHERE LOWER(title) LIKE CONCAT('%',LOWER(:title),'%')")
})

public class CurriculumVitae implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(CurriculumVitae.class.getName());

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false)
	@Column(nullable = false)
	private String title;
	

	@Basic(optional = true)
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date modifiedIn;
	

	@Basic(optional = true)
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date createdIn;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculumVitae")
	private List<Activity> activities = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	private Person person;
	
	public CurriculumVitae() {
		this.createdIn = new Date(System.currentTimeMillis());
		this.modifiedIn = new Date(System.currentTimeMillis());
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public void addActivity(Activity activity) {
		if(activity == null) return;
		
		activities.add(activity);
		activity.setCurriculumVitae(this);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getModifiedIn() {
		return modifiedIn;
	}

	public void setModifiedIn(Date modifiedIn) {
		this.modifiedIn = modifiedIn;
	}

	public Date getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}
	
	

	@Override
	public String toString() {
		return "CurriculumVitae [id=" + id + ", title=" + title + ", modifiedIn=" + modifiedIn + ", createdIn="
				+ createdIn + ", activities=" + getActivities() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activities == null) ? 0 : activities.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		
		CurriculumVitae other = (CurriculumVitae) obj;
		
		
		if (activities == null) {
			if (other.activities != null)
				return false;
		
		} else if (!new HashSet<>(activities).equals(new HashSet<>(other.activities)))
			return false;
		
		if (id != other.id)
			return false;
		
		if(title == null || other.title == null)
			return false;
		else if (!title.equals(other.title))
			return false;
		
		
		return true;
	}

	
}
