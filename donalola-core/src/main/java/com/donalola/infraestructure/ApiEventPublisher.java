package com.donalola.infraestructure;

import com.donalola.events.Event;
import com.donalola.events.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApiEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ApiEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishEvent(Event event) {
        if (!Optional.ofNullable(event.getPrincipal()).isPresent()) {
            event.setPrincipal(getPrincipalAuthentication());
        }
        this.applicationEventPublisher.publishEvent(event);
    }

    private Authentication getPrincipalAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
