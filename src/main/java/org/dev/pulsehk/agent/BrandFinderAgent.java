package org.dev.pulsehk.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.observability.AgentMonitor;
import dev.langchain4j.agentic.observability.MonitoredAgent;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface BrandFinderAgent extends MonitoredAgent {

    @SystemMessage ("""
        Analyze the following user request and find the brand name.
        """)
    @UserMessage("""
            userRequest: {{userRequest}}
            """)
    @Agent(description = "Finds the brand name from the userRequest")
    String findBrandName(@MemoryId String memoryId, @V("userRequest") String userRequest);

}

