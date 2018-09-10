package com.almightee.repository.search;

import com.almightee.domain.LineItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LineItem entity.
 */
public interface LineItemSearchRepository extends ElasticsearchRepository<LineItem, Long> {
}
