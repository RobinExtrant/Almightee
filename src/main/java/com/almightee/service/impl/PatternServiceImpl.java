package com.almightee.service.impl;

import com.almightee.domain.Pattern;
import com.almightee.repository.PatternRepository;
import com.almightee.repository.search.PatternSearchRepository;
import com.almightee.service.PatternService;
import com.almightee.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Service
@Transactional
public class PatternServiceImpl implements PatternService {

    private final Logger log = LoggerFactory.getLogger(PatternServiceImpl.class);

    @Autowired
    private PatternRepository patternRepository;
    @Autowired
    private PatternSearchRepository patternSearchRepository;
    @Autowired
    private StorageService storageService;


    @Override
    public Page<Pattern> retrieveAllPatterns(Pageable pageable) {
        return patternRepository.findAll(pageable);
    }

    @Override
    public Optional<Pattern> getPattern(Long id) {
        return patternRepository.findById(id);
    }

    @Override
    public Pattern createPattern(Pattern pattern, MultipartFile picture) {
        Pattern result = patternRepository.save(pattern);
        Long id = result.getId();

        patternSearchRepository.save(result);
        return result;
    }

    @Override
    public Pattern updatePattern(Pattern pattern) {
        Pattern result = patternRepository.save(pattern);
        patternSearchRepository.save(result);
        return result;
    }

    @Override
    public void deletePattern(Long id) {
        patternRepository.deleteById(id);
        patternSearchRepository.deleteById(id);
    }

    @Override
    public Page<Pattern> retrievePatternsByQuery(String query, Pageable pageable) {
        return patternSearchRepository.search(queryStringQuery(query), pageable);
    }
}
