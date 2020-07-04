package gg.bayes.challenge.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@Entity
public class HeroDamage {
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "damage_id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "hero_id")
	private Integer heroId;

	private String target;
	@JsonProperty("damage_instances")
	private Integer damageInstances;

	@JsonProperty("total_damage")
	private Integer totalDamage;

	public HeroDamage() {
		// Default Constructor
	}

	public HeroDamage(String target, int damageInstances, int damage) {
		setTarget(target);
		setDamageInstances(damageInstances);
		setTotalDamage(damage);
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getDamageInstances() {
		return damageInstances;
	}

	public void setDamageInstances(Integer damageInstances) {
		this.damageInstances = damageInstances;
	}

	public Integer getTotalDamage() {
		return totalDamage;
	}

	public void setTotalDamage(Integer totalDamage) {
		this.totalDamage = totalDamage;
	}

}
