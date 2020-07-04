package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.dao.*;
import gg.bayes.challenge.rest.model.*;
import gg.bayes.challenge.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchServiceImpl implements MatchService {

	@Autowired
	public MatchServiceImpl() {

	}

	@Autowired
	MatchDAO matchDAOService;

	@Autowired
	HeroKillsDAO heroKillsDAOService;
	@Autowired
	HeroItemsDAO heroItemDAOService;

	@Autowired
	HeroSpellsDAO heroSpellsDAOService;
	@Autowired
	HeroDamageDAO heroDamageDAOService;

	@Override
	public Long ingestMatch(String payload){

		Match match = new Match();
		List<HeroKills> heroKills = new ArrayList<HeroKills>();
		Map<String, HeroKills> heroMap = new HashMap<>();	
			new BufferedReader(new StringReader(payload)).lines().forEach(line -> {
				String[] arr = line.split(" ");
			
					if (arr.length > 2 && arr[1].contains("npc_dota_hero_") && arr[2].equals("buys")
							|| arr[2].equals("casts") || arr[2].equals("hits")) {
						HeroKills hero = getHero(parseHeroName(arr[1]), match, heroMap);
						if (arr[2].equals("buys")) {
							HeroItems item = new HeroItems();
							item.setItem(parseItemName(arr[4]));
							item.setTimestamp(parseTimeStamp(arr[0]));
							hero.getItems().add(item);
						} else if (arr[2].equals("casts")) {
							List<HeroSpells> hSpells = getHeroSpellsList(hero, arr[4]);
							hero.setSpells(hSpells);
						} else if (arr[2].equals("hits")) {
							List<HeroDamage> heroDamageList = getHeroDamageList(hero, parseHeroName(arr[3]),
									Integer.parseInt(arr[7]));
							hero.setHeroDamage(heroDamageList);
						}
					} else if (arr.length > 5 && arr[3].equals("killed") && arr[5].contains("npc_dota_hero_")) {
						HeroKills hero = getHero(parseHeroName(arr[5]), match, heroMap);
						hero.setKills(hero.getKills() + 1);
					}
			});		
		
		heroKills = heroMap.values().stream().collect(Collectors.toList());
		match.setHeros(heroKills);
		matchDAOService.save(match);

		return (long) match.getId();
	}
	
	@Override
	public List<HeroKills> findByMatchId(Integer matchId) {
		Iterable<HeroKills> iterableheros = heroKillsDAOService.findByMatchId(matchId);
		List<HeroKills> heros = StreamSupport.stream(iterableheros.spliterator(), false).collect(Collectors.toList());
		return heros;
	}

	@Override
	public List<HeroItems> findItems(Integer matchId, String heroName) {
		Iterable<HeroKills> iterableheros = heroKillsDAOService.findByMatchId(matchId);
		Optional<HeroKills> hero = StreamSupport.stream(iterableheros.spliterator(), false)
				.filter(heroKills -> heroKills.getHero().equals(heroName)).findAny();
		Iterable<HeroItems> iterableItems = heroItemDAOService.findByHeroId(hero.get().getId());
		List<HeroItems> items = StreamSupport.stream(iterableItems.spliterator(), false).collect(Collectors.toList());
		return items;
	}

	@Override
	public List<HeroSpells> findSpells(Integer matchId, String heroName) {
		Iterable<HeroKills> iterableheros = heroKillsDAOService.findByMatchId(matchId);
		Optional<HeroKills> hero = StreamSupport.stream(iterableheros.spliterator(), false)
				.filter(heroKills -> heroKills.getHero().equals(heroName)).findAny();
		Iterable<HeroSpells> iterableSpells = heroSpellsDAOService.findByHeroId(hero.get().getId());
		List<HeroSpells> items = StreamSupport.stream(iterableSpells.spliterator(), false).collect(Collectors.toList());
		return items;
	}

	@Override
	public List<HeroDamage> findDamage(Integer matchId, String heroName) {
		Iterable<HeroKills> iterableheros = heroKillsDAOService.findByMatchId(matchId);
		Optional<HeroKills> hero = StreamSupport.stream(iterableheros.spliterator(), false)
				.filter(heroKills -> heroKills.getHero().equals(heroName)).findAny();
		Iterable<HeroDamage> iterableSpells = heroDamageDAOService.findByHeroId(hero.get().getId());
		List<HeroDamage> items = StreamSupport.stream(iterableSpells.spliterator(), false).collect(Collectors.toList());
		return items;
	}
	
	

	private HeroKills getHero(String hero, Match match, Map<String, HeroKills> heroMap) {
		HeroKills newHero = new HeroKills(hero, 0);
//		newHero.setMatch(match);
		heroMap.computeIfAbsent(hero, k -> newHero);
		return heroMap.get(hero);
	}

	private static List<HeroDamage> getHeroDamageList(HeroKills hero, String target, int damage) {
		List<HeroDamage> hDamageList = hero.getHeroDamage();
		AtomicInteger ifExist = new AtomicInteger(0);
		hDamageList.forEach(heroDamage -> {
			if (heroDamage.getTarget().equals(target)) {
				heroDamage.setTotalDamage(heroDamage.getTotalDamage() + damage);
				heroDamage.setDamageInstances(heroDamage.getDamageInstances() + 1);
				ifExist.set(1);
			}
		});
		if (ifExist.get() == 0) {
			HeroDamage hDamageNew = new HeroDamage(target, 1, damage);
			hDamageList.add(hDamageNew);
		}

		return hDamageList;

	}

	private static List<HeroSpells> getHeroSpellsList(HeroKills hero, String spell) {
		List<HeroSpells> hSpells = hero.getSpells();
		AtomicInteger ifExist = new AtomicInteger(0);
		hSpells.forEach(heroSpells -> {
			if (heroSpells.getSpell().equals(spell)) {
				heroSpells.setCasts(heroSpells.getCasts() + 1);
				ifExist.set(1);
			}
		});
		if (ifExist.get() == 0) {
			HeroSpells hSpellsNew = new HeroSpells(spell, 1);
			hSpells.add(hSpellsNew);
		}

		return hSpells;

	}

	private String parseItemName(String string) {
		return string.substring(5);
	}

	private String parseHeroName(String string) {
		return string.substring(14);
	}

	private Long parseTimeStamp(String string) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date reference;
		long seconds = 0;
		try {
			reference = dateFormat.parse("00:00:00.000");
			Date date = dateFormat.parse(string.substring(1, 12));
			seconds = (date.getTime() - reference.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return seconds;
	}


}
