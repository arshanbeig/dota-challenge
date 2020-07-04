package gg.bayes.challenge.rest.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data 
@Entity
public class HeroSpells {
	@JsonIgnore
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "spell_id")
	private Integer id;
	
	@Column(name = "hero_id")
	private Integer heroId;	
    private String spell;	
	private Integer casts;
	
	public HeroSpells() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public HeroSpells(String spell2, Integer integer) {
		setSpell(spell2);
		setCasts(integer);
	}

    
    public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public Integer getCasts() {
		return casts;
	}

	public void setCasts(Integer casts) {
		this.casts = casts;
	}

//	public HeroKills getHero() {
//		return hero;
//	}
//
//	public void setHero(HeroKills hero) {
//		this.hero = hero;
//	}

	
}
