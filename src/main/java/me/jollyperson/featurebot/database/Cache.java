package me.jollyperson.featurebot.database;

import java.util.HashMap;
import java.util.Map;

public enum Cache {
    INSTANCE();

    public Cache getInstance(){
        return INSTANCE;
    }
    private Cache(){};

    private Map<Long, String> PREFIXES = new HashMap<>();
    public Map<Long,String> getPrefixes(){
        return PREFIXES;
    }
}

