package com.posada.santiago.alphapostsandcomments.domain;

import co.com.sofka.domain.generic.Entity;
import com.posada.santiago.alphapostsandcomments.domain.values.Author;
import com.posada.santiago.alphapostsandcomments.domain.values.FavoritesId;
import com.posada.santiago.alphapostsandcomments.domain.values.Star;

public class Favorites extends Entity<FavoritesId> {

    private Author author;
    private Star star;

    public Favorites(FavoritesId entityId, Author author, Star star) {
        super(entityId);
        this.author = author;
        this.star = star;
    }

    public Author author() {
        return author;
    }

    public Star star() {
        return star;
    }

}
