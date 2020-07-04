package gg.bayes.challenge.rest.controller;

import gg.bayes.challenge.dao.MatchDAO;
import gg.bayes.challenge.rest.model.*;
import gg.bayes.challenge.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;
    
	@Autowired
	MatchDAO matchDAOService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping(consumes = "text/plain")
    public ResponseEntity<Long> ingestMatch(@RequestBody @NotNull @NotBlank String payload) {
        final Long matchId = matchService.ingestMatch(payload);
        return ResponseEntity.ok(matchId);
    }

    @GetMapping("{matchId}")
    public ResponseEntity<List<HeroKills>> getMatch(@PathVariable("matchId") Integer matchId) {

    	return ResponseEntity.ok().body(matchService.findByMatchId(matchId));
    }

    @GetMapping("{matchId}/{heroName}/items")
    public ResponseEntity<List<HeroItems>> getItems(@PathVariable("matchId") Integer matchId,
                                                    @PathVariable("heroName") String heroName) {
    	return ResponseEntity.ok().body(matchService.findItems(matchId,heroName));
 
    }

    @GetMapping("{matchId}/{heroName}/spells")
    public ResponseEntity<List<HeroSpells>> getSpells(@PathVariable("matchId") Integer matchId,
                                                      @PathVariable("heroName") String heroName) {
    	return ResponseEntity.ok().body(matchService.findSpells(matchId,heroName));
 
    }

    @GetMapping("{matchId}/{heroName}/damage")
    public ResponseEntity<List<HeroDamage>> getDamage(@PathVariable("matchId") Integer matchId,
                                                      @PathVariable("heroName") String heroName) {
    	return ResponseEntity.ok().body(matchService.findDamage(matchId,heroName));
 
    }
    
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
