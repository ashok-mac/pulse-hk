package org.dev.pulsehk.agent;

import io.a2a.server.agentexecution.RequestContext;
@FunctionalInterface
public interface CustomClientExecutorHandler {
    String execute(RequestContext requestContext);
}


