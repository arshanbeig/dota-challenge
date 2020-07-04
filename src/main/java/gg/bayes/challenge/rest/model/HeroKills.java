package gg.bayes.challenge.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class HeroKills implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@Column(name = "hero_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String hero;
	private Integer kills;

	@Column(name = "match_id")
	private Integer matchId;

	public HeroKills() {
		// Default Constructor
	}

	public HeroKills(String hero, Integer kills) {
		setHero(hero);
		setKills(kills);
	}

	@JsonIgnore
	@JoinColumn(name = "hero_id", referencedColumnName = "hero_id")
	@OneToMany(cascade = CascadeType.ALL)
	List<HeroItems> items = new ArrayList<HeroItems>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "hero_id", referencedColumnName = "hero_id")
	List<HeroSpells> spells = new ArrayList<HeroSpells>();

	@JsonIgnore
	@JoinColumn(name = "hero_id", referencedColumnName = "hero_id")
	@OneToMany(cascade = CascadeType.ALL)
	List<HeroDamage> heroDamage = new ArrayList<HeroDamage>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHero() {
		return hero;
	}

	public void setHero(String hero) {
		this.hero = hero;
	}

	public Integer getKills() {
		return kills;
	}

	public void setKills(Integer kills) {
		this.kills = kills;
	}

	public List<HeroItems> getItems() {
		return items;
	}

	public void setItems(List<HeroItems> items) {
		this.items = items;
	}

	public List<HeroSpells> getSpells() {
		return spells;
	}

	public void setSpells(List<HeroSpells> spells) {
		this.spells = spells;
	}

	public List<HeroDamage> getHeroDamage() {
		return heroDamage;
	}

	public void setHeroDamage(List<HeroDamage> heroDamage) {
		this.heroDamage = heroDamage;
	}

}
