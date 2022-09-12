package com.posada.santiago.alphapostsandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.alphapostsandcomments.business.gateways.DomainEventRepository;
import com.posada.santiago.alphapostsandcomments.business.gateways.EventBus;
import com.posada.santiago.alphapostsandcomments.business.generic.UseCaseForCommand;
import com.posada.santiago.alphapostsandcomments.domain.Post;
import com.posada.santiago.alphapostsandcomments.domain.commands.AddFavorites;
import com.posada.santiago.alphapostsandcomments.domain.values.Author;
import com.posada.santiago.alphapostsandcomments.domain.values.FavoritesId;
import com.posada.santiago.alphapostsandcomments.domain.values.PostId;
import com.posada.santiago.alphapostsandcomments.domain.values.Star;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AddFavoritesUseCase extends UseCaseForCommand<AddFavorites> {

    private final DomainEventRepository repository;
    private final EventBus bus;

    public AddFavoritesUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }
    @Override
    public Flux<DomainEvent> apply(Mono<AddFavorites> addFavorites) {
        return addFavorites.flatMapMany(
                command -> repository.findById(command.getPostId())
                        .collectList()
                        .flatMapIterable(domainEvents -> {
                            Post post = Post.from(     PostId.of(command.getPostId()),   domainEvents );
                            post.addFavorites(
                                    FavoritesId.of(command.getId()),
                                    new Author(command.getAuthor()),
                                    new Star(command.getStar())
                            );
                            return post.getUncommittedChanges();
                        }).map(event -> {
                            bus.publish(event);
                            return event;
                        }).flatMap(
                                event -> repository.saveEvent(event)
                        )//aca me estan retornando
        );

    }
}
