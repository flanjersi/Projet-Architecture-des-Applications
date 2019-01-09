package fr.amu.projetADA.beans.cv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import fr.amu.projetADA.beans.person.Person;

/**
 * Class which represents a curriculum vitae of a person
 * @author Jeremy
 *
 */
@Entity
@Table(name = "CurriculumVitae")

@NamedQueries({
@NamedQuery(name = "findAllCurriculumVitae", query = "From CurriculumVitae")
})
public class CurriculumVitae implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(CurriculumVitae.class.getName());

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "curriculumVitae")
	private List<Activity> activities = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "curriculumVitae")
	private Person person;
	
	public CurriculumVitae() {}
	
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
	
	@Override
	public String toString() {
		return "CurriculumVitae [id=" + id + ", activities=" + getActivities() + "]";
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activities == null) ? 0 : activities.hashCode());
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
		
		return true;
	}
	
	
	
	
}
