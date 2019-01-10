package fr.amu.projetADA.beans.cv;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class which represents an activity of a Curriculum Vitae
 * @author Jeremy
 *
 */
@Entity
@Table(name = "Activity")
@NamedQueries({
	@NamedQuery(name = "findAllActivity", query = "From Activity"),
	@NamedQuery(name = "findByTitle", query = "From Activity WHERE title LIKE CONCAT('%',:title,'%')"),
})
public class Activity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(Activity.class.getName());
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic
	@Column(nullable = false)
	private int year;
	
	@Basic
	@Column(nullable = false)
	private String type;
	
	@Basic
	@Column(nullable = false)
	private String title;
	
	@Basic
	@Column(nullable = true)
	private String description;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date begin;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date end;
	
	@Basic
	@Column(nullable = true)
	private String webSite;
	
	@ManyToOne()
	private CurriculumVitae curriculumVitae;
	
	public Activity() {	}

	public Activity(int year, String type, String title, Date begin, Date end) {
		this.year  = year;
		this.type  = type;
		this.title = title;
		this.begin = begin;
		this.end = end;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		this.curriculumVitae = curriculumVitae;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	

	@Override
	public String toString() {
		return "Activity [id=" + id + ", year=" + year + ", type=" + type + ", title=" + title + ", description="
				+ description + ", begin=" + begin + ", end=" + end + ", webSite=" + webSite + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((webSite == null) ? 0 : webSite.hashCode());
		result = prime * result + year;
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
		
		Activity other = (Activity) obj;
		
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		
		if (id != other.id)
			return false;
		
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		
		if (webSite == null) {
			if (other.webSite != null)
				return false;
		} else if (!webSite.equals(other.webSite))
			return false;
		
		if (year != other.year)
			return false;
		
		return true;
	}




	
	
	
	
	
	
	
	
}