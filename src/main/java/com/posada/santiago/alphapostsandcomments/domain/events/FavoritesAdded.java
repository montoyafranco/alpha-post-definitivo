package com.posada.santiago.alphapostsandcomments.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Setter;

@Setter

public class FavoritesAdded extends DomainEvent {
    private String id;
    private String author;
    private String star;

    public FavoritesAdded(String id,String author,String star) {
        super("posada.santiago.FavoritesAdded");
        this.id = id;
        this.author = author;
        this.star = star;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getStar() {
        return star;
    }
}
