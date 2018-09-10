package com.almightee.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of LineItemSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LineItemSearchRepositoryMockConfiguration {

    @MockBean
    private LineItemSearchRepository mockLineItemSearchRepository;

}
