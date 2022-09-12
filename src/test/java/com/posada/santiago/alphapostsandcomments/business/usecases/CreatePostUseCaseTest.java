package com.posada.santiago.alphapostsandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.alphapostsandcomments.business.gateways.DomainEventRepository;
import com.posada.santiago.alphapostsandcomments.business.gateways.EventBus;
import com.posada.santiago.alphapostsandcomments.domain.commands.CreatePostCommand;
import com.posada.santiago.alphapostsandcomments.domain.events.PostCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseTest {
    @Mock
    DomainEventRepository domainEventRepository;
    @Mock
    EventBus eventBus;
    CreatePostUseCase createPostUseCase;

    @BeforeEach
    void init() {
        createPostUseCase = new CreatePostUseCase(domainEventRepository, eventBus);
    }

    @Test
    void testPost() {
        var postACrear = new CreatePostCommand(
                "1", "agus", "rey"
        );
        var postCreado = new PostCreated(
                postACrear.getAuthor(),
                postACrear.getTitle()
        );


        Mockito.when(domainEventRepository.saveEvent(Mockito.any(PostCreated.class)))
                .thenReturn(Mono.just(postCreado));
        var useCaseAplicado = createPostUseCase.apply(Mono.just(postACrear)).collectList();


        StepVerifier.create(useCaseAplicado)
                .expectSubscription()
                .expectNextMatches(events -> events.size() == 1 && events.get(0) instanceof PostCreated)
                .verifyComplete();
        BDDMockito.verify(this.eventBus, BDDMockito.times(1))
                .publish(ArgumentMatchers.any(DomainEvent.class));
    }

}