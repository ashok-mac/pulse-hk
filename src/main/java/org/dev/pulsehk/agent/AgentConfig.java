package org.dev.pulsehk.agent;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.supervisor.SupervisorAgent;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import io.a2a.server.agentexecution.AgentExecutor;
import io.a2a.spec.AgentCapabilities;
import io.a2a.spec.AgentCard;
import org.dev.pulsehk.service.OfferService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class AgentConfig {

    @Bean
    public AgentCard agentCard(@Value("${server.port:8080}") int port,
                               @Value("${server.servlet.context-path:/}") String contextPath) {

        return new AgentCard.Builder()
                .name("HK Pulse LifeStyle Agent")
                .description("Provides Offer Details information for BrandNames")
                .url("http://127.0.0.1:" + port + contextPath + "/")
                .version("1.0.0")
                .capabilities(new AgentCapabilities.Builder().streaming(false).build())
                .defaultInputModes(List.of("text"))
                .defaultOutputModes(List.of("text"))
                .skills(List.of())
                .protocolVersion("0.3.0")
                .build();
    }

    @Bean
    public BrandFinderAgent brandFinderAgent(ChatModel chatModel) {
       return AgenticServices.agentBuilder(BrandFinderAgent.class)
                .chatModel(chatModel)
                .chatMemoryProvider(memoryId-> MessageWindowChatMemory.withMaxMessages(3))
                .build();
    }

    @Bean
    public SupervisorAgent supervisorAgent(ChatModel  chatModel,
                                           BrandFinderAgent brandFinderAgent,
                                           OfferService offerService) {
        return AgenticServices
                .supervisorBuilder()
                .chatModel(chatModel)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(3))
                .subAgents(brandFinderAgent,  new OfferFinderAgent(offerService))
                .responseStrategy(SupervisorResponseStrategy.LAST)
                .build();
    }

    @Bean
    public AgentExecutor agentExecutor(SupervisorAgent supervisorAgent) {
               return new CustomAgentExecutor(supervisorAgent ,(requestContext)->{
                    String userMessage = CustomAgentExecutor.extractTextFromMessage(requestContext.getMessage());
                    return supervisorAgent.invoke(userMessage);
                });
            }
}