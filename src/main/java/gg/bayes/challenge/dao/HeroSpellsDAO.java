package gg.bayes.challenge.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import gg.bayes.challenge.rest.model.HeroSpells;

@Repository
public interface HeroSpellsDAO extends CrudRepository<HeroSpells, Integer>{

	List<HeroSpells> findByHeroId(Integer heroId);

}
