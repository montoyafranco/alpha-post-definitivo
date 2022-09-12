package com.posada.santiago.alphapostsandcomments.domain.values;

import co.com.sofka.domain.generic.Identity;

public class FavoritesId extends Identity {
    public FavoritesId(){}

    private FavoritesId(String uuid){
        super(uuid);

    }

    public static FavoritesId of(String uuid){
        return new FavoritesId(uuid);
    }
}
