package gg.bayes.challenge.rest.model;

import java.io.Serializable;
import java.util.List;

//import org.hibernate.annotations.Cascade;
//import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
public class Match implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "match_id")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "match_id", referencedColumnName = "match_id")
	List<HeroKills> heros;

	public Match() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<HeroKills> getHeros() {
		return heros;
	}

	public void setHeros(List<HeroKills> heros) {
		this.heros = heros;
	}

}
