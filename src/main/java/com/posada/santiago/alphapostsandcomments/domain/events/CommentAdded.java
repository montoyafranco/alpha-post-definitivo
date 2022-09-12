package com.posada.santiago.alphapostsandcomments.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Setter;

@Setter
public class CommentAdded extends DomainEvent {

    private String id;
    private String author;
    private String content;


    public CommentAdded(String id, String author, String content) {
        super("posada.santiago.commentcreated");
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
