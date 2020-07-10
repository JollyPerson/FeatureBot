package me.jollyperson.featurebot.database;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private static final Cache INSTANCE = new Cache();

    public static Cache getInstance(){
        return INSTANCE;
    }
    public Cache(){
    };

    private Map<Long, String> PREFIXES = new HashMap<>();
    public Map<Long,String> getPrefixes(){
        return PREFIXES;
    }
}

