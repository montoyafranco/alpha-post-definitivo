package com.posada.santiago.alphapostsandcomments.domain.commands;

import co.com.sofka.domain.generic.Command;

public class CreatePostCommand extends Command {
    private String postId;
    private String author;
    private String title;

    public CreatePostCommand() {
    }

    public CreatePostCommand(String postId, String author, String title) {
        this.postId = postId;
        this.author = author;
        this.title = title;
    }

    public String getPostId() {
        return postId;
    }


    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

}
