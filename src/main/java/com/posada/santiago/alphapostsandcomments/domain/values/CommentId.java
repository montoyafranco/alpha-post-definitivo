package com.posada.santiago.alphapostsandcomments.domain.values;

import co.com.sofka.domain.generic.Identity;

public class CommentId extends Identity {
    private CommentId(String uuid) {
        super(uuid);
    }

    public CommentId() {
    }

    public static CommentId of(String uuid){
        return new CommentId(uuid);
    }
}
