package com.posada.santiago.alphapostsandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.alphapostsandcomments.business.gateways.DomainEventRepository;
import com.posada.santiago.alphapostsandcomments.business.gateways.EventBus;
import com.posada.santiago.alphapostsandcomments.domain.commands.AddFavorites;
import com.posada.santiago.alphapostsandcomments.domain.events.FavoritesAdded;
import com.posada.santiago.alphapostsandcomments.domain.events.PostCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class AddFavoritesUseCaseTest {
    @Mock
    DomainEventRepository domainEventRepository;
    @Mock
    EventBus eventBus;
    AddFavoritesUseCase addFavoritesUseCase;

    @BeforeEach
    void init() {
        addFavoritesUseCase = new AddFavoritesUseCase(domainEventRepository, eventBus);

    }

    @Test
    void testFavorites() {
        AddFavorites command = new AddFavorites(
                "1",
                "2",
                "agus",
                "1"
        );
        FavoritesAdded event = new FavoritesAdded(
                command.getId(),
                command.getAuthor(),
                command.getStar()
        );
       event.setAggregateRootId(command.getId());
//        event.setId(command.getCommentId());
        DomainEvent post = new PostCreated("agus", "montoya");
        post.setAggregateRootId("1");

        BDDMockito.when(this.domainEventRepository.findById(Mockito.any(String.class)))
                .thenReturn(Flux.just(post));
        BDDMockito.when(this.domainEventRepository.saveEvent(Mockito.any(DomainEvent.class)))
                .thenReturn(Mono.just(event));

        var eventoAccion = addFavoritesUseCase.apply(Mono.just(command));

        StepVerifier.create(eventoAccion)
                .expectNext(event)
                .verifyComplete();



        BDDMockito.verify(this.eventBus, BDDMockito.times(1))
                .publish(ArgumentMatchers.any(DomainEvent.class));
        BDDMockito.verify(this.domainEventRepository, BDDMockito.times(1))
                .saveEvent(ArgumentMatchers.any(DomainEvent.class));
        BDDMockito.verify(this.domainEventRepository, BDDMockito.times(1))
                .findById(ArgumentMatchers.any(String.class));

    }

}