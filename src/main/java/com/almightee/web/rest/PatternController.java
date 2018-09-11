package com.almightee.web.rest;
import com.almightee.dao.PatternDao;
import com.almightee.model.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PatternController {

    @Autowired
    private PatternDao patternDao;

    @RequestMapping(value="/patterns", method= RequestMethod.GET)
    public List<Pattern> listePatterns() {
        return patternDao.findAll();
    }

    @GetMapping(value="/patterns/{id}")
    public Pattern afficherUnPattern(@PathVariable int id) {
        return patternDao.findById(id);
    }

    @PostMapping(value = "/patterns")
    public ResponseEntity<Void> ajouterPattern(@RequestBody Pattern pattern) {

        Pattern patternAdded = patternDao.save(pattern);

        if (patternAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(patternAdded.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }
}
