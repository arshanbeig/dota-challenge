package gg.bayes.challenge.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gg.bayes.challenge.rest.model.HeroDamage;

@Repository
public interface HeroDamageDAO extends CrudRepository<HeroDamage, Integer>{

	List<HeroDamage> findByHeroId(Integer heroId);

}
