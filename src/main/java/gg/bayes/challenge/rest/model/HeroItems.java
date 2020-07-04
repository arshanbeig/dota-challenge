package gg.bayes.challenge.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data 
@Entity
public class HeroItems {
	@JsonIgnore
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "items_id")
	private Integer id;
	
	@Column(name = "hero_id")
	private Integer heroId;	
	private String item;	
    private Long timestamp;
    
    public HeroItems() {}
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

    
    
}
