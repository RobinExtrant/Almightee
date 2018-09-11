package com.almightee.dao;

import com.almightee.model.Pattern;
import java.util.List;

public interface PatternDao {
    public List<Pattern> findAll();
    public Pattern findById(int id);
    public Pattern save(Pattern pattern);
}
