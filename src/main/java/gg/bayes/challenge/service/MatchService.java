package gg.bayes.challenge.service;

import java.util.List;

import gg.bayes.challenge.rest.model.*;

public interface MatchService {
    Long ingestMatch(String payload);

	List<HeroKills> findByMatchId(Integer matchId);

	List<HeroItems> findItems(Integer matchId, String heroName);

	List<HeroSpells> findSpells(Integer matchId, String heroName);
	
	List<HeroDamage> findDamage(Integer matchId, String heroName);

    // TODO add more methods as needed
}
