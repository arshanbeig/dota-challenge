package gg.bayes.challenge.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import gg.bayes.challenge.rest.model.HeroItems;

@Repository
public interface HeroItemsDAO extends CrudRepository<HeroItems, Integer>{

	List<HeroItems> findByHeroId(Integer heroId);

}
