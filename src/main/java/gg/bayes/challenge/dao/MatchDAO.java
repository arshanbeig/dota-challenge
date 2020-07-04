package gg.bayes.challenge.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gg.bayes.challenge.rest.model.Match;

@Repository
public interface MatchDAO extends CrudRepository<Match, Integer>{

}
