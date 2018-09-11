package com.almightee.dao;

import com.almightee.model.Pattern;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatternDaoImpl implements PatternDao {

    public static List<Pattern> patterns = new ArrayList<>();
    static {
        patterns.add(new Pattern(1, new String("image1"), new String("author1"), new String("url1"),2));
        patterns.add(new Pattern(2, new String("image2"), new String("author2"), new String("url2"),2));
        patterns.add(new Pattern(3, new String("image3"), new String("author3"), new String("url2"),3));
    }

        @Override
    public List<Pattern> findAll() {
        return patterns;
    }

    @Override
    public Pattern findById(int id) {
        for (Pattern pattern : patterns) {
            if(pattern.getId() ==id){
                return pattern;
            }
        }
        return null;    }

    @Override
    public Pattern save(Pattern pattern) {
        patterns.add(pattern);
        return pattern;
    }
}
