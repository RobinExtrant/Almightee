package com.almightee.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CommandItemSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CommandItemSearchRepositoryMockConfiguration {

    @MockBean
    private CommandItemSearchRepository mockCommandItemSearchRepository;

}
