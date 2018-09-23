package com.almightee.repository.search;

import com.almightee.domain.Pattern;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pattern entity.
 */
public interface PatternSearchRepository extends ElasticsearchRepository<Pattern, Long> {
}
