package com.posada.santiago.alphapostsandcomments.domain.commands;

import co.com.sofka.domain.generic.Command;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@AllArgsConstructor
public class AddFavorites extends Command {
    private String postId;
    private String id;
    private String author;
    private String star;

    public AddFavorites() {

    }

    public String getPostId() {
        return postId;
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
