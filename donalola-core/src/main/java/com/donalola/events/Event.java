package com.donalola.events;

import java.security.Principal;

public interface Event {

    Principal getPrincipal();

    void setPrincipal(Principal principal);
}
