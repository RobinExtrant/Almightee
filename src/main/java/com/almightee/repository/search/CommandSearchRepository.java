package com.almightee.repository.search;

import com.almightee.domain.Command;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Command entity.
 */
public interface CommandSearchRepository extends ElasticsearchRepository<Command, Long> {
}
