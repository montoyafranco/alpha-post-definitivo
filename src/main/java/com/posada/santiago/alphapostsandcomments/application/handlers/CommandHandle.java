package com.posada.santiago.alphapostsandcomments.application.handlers;


import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.alphapostsandcomments.business.usecases.AddCommentUseCase;
import com.posada.santiago.alphapostsandcomments.business.usecases.AddFavoritesUseCase;
import com.posada.santiago.alphapostsandcomments.business.usecases.CreatePostUseCase;
import com.posada.santiago.alphapostsandcomments.domain.commands.AddCommentCommand;
import com.posada.santiago.alphapostsandcomments.domain.commands.AddFavorites;
import com.posada.santiago.alphapostsandcomments.domain.commands.CreatePostCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration

public class CommandHandle {

    @Bean
    public RouterFunction<ServerResponse> createPost(CreatePostUseCase useCase) {

        return route(
                POST("/create/post")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> useCase.apply(request.bodyToMono(CreatePostCommand.class))
                        .collectList()
                        .flatMap(domainEvents -> {{

                            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(domainEvents);
                                }}

                        )
                        .onErrorResume(error -> {

                            return ServerResponse.badRequest().build();
                        })

        );
    }


    @Bean
    public RouterFunction<ServerResponse> addComment(AddCommentUseCase useCase) {

        return route(
                POST("/add/comment")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> useCase.apply(request.bodyToMono(AddCommentCommand.class))
                        .collectList()
                        .flatMap(domainEvents -> {
                                    {

                                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(domainEvents);
                                    }
                                }
                        )

                        .onErrorResume(error -> {

                            return ServerResponse.badRequest().build();
                        })
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addFavorites(AddFavoritesUseCase useCase) {
        return route(
                POST("/add/favorites").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.bodyToMono(AddFavorites.class)), DomainEvent.class))
        );
    }
}
