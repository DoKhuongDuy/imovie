package com.ptit.service.model.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum MovieType {
    ROMANCE("Lãng mạn"), MARTIAL_ART("Võ Thuật"), ACTION("Hành động"), HUMOR("Hài hước"), FAMILY("Gia đình"),
    SCHOOL("Học đường"), CRIMINAL("Hình sự"), ENTERTAINMENT("Giải trí"), FICTION("Viễn tưởng"), DRAMA("Drama");

    private static final Map<String, MovieType> cached;

    static {
        Map<String, MovieType> map = new ConcurrentHashMap<>();
        for (MovieType movieType : MovieType.values()) {
            map.put(movieType.getType(), movieType);
        }
        cached = map;
    }

    private final String type;

    MovieType(String type) {
        this.type = type;
    }

    public static MovieType getMovieType(String type) {
        return cached.get(type);
    }

    public String getType() {
        return type;
    }

}
