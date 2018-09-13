package com.almightee.web.rest;
import com.almightee.repository.PatternRepository;
import com.almightee.domain.Pattern;
import com.almightee.web.rest.errors.BadRequestAlertException;
import com.almightee.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class PatternRessource {

    @Autowired
    private PatternRepository patternRepository;

    @GetMapping("/patterns")
    @Timed
    public List<Pattern> getAllPatterns() {
        return patternRepository.findAll();
    }

    @GetMapping(value="/patterns/{id}")
    public Optional<Pattern> getPattern(@PathVariable Long id) {
       return patternRepository.findById(id);
    }

    @PostMapping("/patterns")
    @Timed
    public void createPattern(@RequestBody Pattern pattern) {
        if (pattern.getId() != null) {
            throw new BadRequestAlertException("A new pattern cannot already have an ID", "pattern", "idexists");
        }
        patternRepository.save(pattern);
    }
}
