package com.posada.santiago.alphapostsandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.alphapostsandcomments.business.gateways.DomainEventRepository;
import com.posada.santiago.alphapostsandcomments.business.gateways.EventBus;
import com.posada.santiago.alphapostsandcomments.domain.commands.AddCommentCommand;
import com.posada.santiago.alphapostsandcomments.domain.events.CommentAdded;
import com.posada.santiago.alphapostsandcomments.domain.events.PostCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class AddCommentUseCaseTest {

    @Mock
    DomainEventRepository domainEventRepository;
    @Mock
    EventBus eventBus;
    AddCommentUseCase addCommentUseCase;

    @BeforeEach
    void init() {
        addCommentUseCase = new AddCommentUseCase(domainEventRepository, eventBus);

    }

    @Test
    void testComment() {
        AddCommentCommand command = new AddCommentCommand(
                "1",
                "2",
                "agus",
                "montoya"
        );
        CommentAdded event = new CommentAdded(
                command.getCommentId(),
                command.getAuthor(),
                command.getContent()
        );
        event.setAggregateRootId(command.getCommentId());
//        event.setId(command.getCommentId());
        DomainEvent post = new PostCreated("agus", "montoya");
        post.setAggregateRootId("1");

        BDDMockito.when(this.domainEventRepository.findById(Mockito.any(String.class)))
                .thenReturn(Flux.just(post));
        BDDMockito.when(this.domainEventRepository.saveEvent(Mockito.any(DomainEvent.class)))
                .thenReturn(Mono.just(event));

        var eventoAccion = addCommentUseCase.apply(Mono.just(command));

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