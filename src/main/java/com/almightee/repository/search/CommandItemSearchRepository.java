package com.almightee.repository.search;

import com.almightee.domain.CommandItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommandItem entity.
 */
public interface CommandItemSearchRepository extends ElasticsearchRepository<CommandItem, Long> {
}
