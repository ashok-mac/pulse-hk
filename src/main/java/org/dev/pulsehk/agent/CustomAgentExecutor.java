package org.dev.pulsehk.agent;

import dev.langchain4j.agentic.supervisor.SupervisorAgent;
import io.a2a.server.agentexecution.AgentExecutor;
import io.a2a.server.agentexecution.RequestContext;
import io.a2a.server.events.EventQueue;
import io.a2a.server.tasks.TaskUpdater;
import io.a2a.spec.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAgentExecutor implements AgentExecutor {

    private static Logger logger = LoggerFactory.getLogger(CustomAgentExecutor.class);
    private final SupervisorAgent supervisorAgent;
    private final CustomClientExecutorHandler customClientExecutorHandler;

    public CustomAgentExecutor(SupervisorAgent supervisorAgent, CustomClientExecutorHandler customClientExecutorHandler) {
        this.supervisorAgent = supervisorAgent;
        this.customClientExecutorHandler = customClientExecutorHandler;
    }

    public static String extractTextFromMessage(Message message) {
       logger.info("Message Extracted: {}",message);
        return message != null && message.getParts() != null ? ((String)message.getParts().stream().filter((part) -> part instanceof TextPart).map((part) -> ((TextPart)part).getText()).collect(Collectors.joining())).trim() : "";
    }
    public void execute(RequestContext context, EventQueue eventQueue) throws JSONRPCError {
        TaskUpdater updater = new TaskUpdater(context, eventQueue);

        try {
            if (context.getTask() == null) {
                updater.submit();
            }

            updater.startWork();
            String response = this.customClientExecutorHandler.execute(context);
            logger.info("AI Response: {}", response);
            updater.addArtifact(List.of(new TextPart(response)), (String)null, (String)null, (Map)null);
            updater.complete();
        } catch (Exception e) {
            logger.error("Error executing agent task", e);
            throw new JSONRPCError(-32603, "Agent execution failed: " + e.getMessage(), (Object)null);
        }
    }

    public void cancel(RequestContext context, EventQueue eventQueue) throws JSONRPCError {
        logger.info("Cancelling task: {}", context.getTaskId());
        Task task = context.getTask();
        if (task.getStatus().state() == TaskState.CANCELED) {
            throw new TaskNotCancelableError();
        } else if (task.getStatus().state() == TaskState.COMPLETED) {
            throw new TaskNotCancelableError();
        } else {
            TaskUpdater updater = new TaskUpdater(context, eventQueue);
            updater.cancel();
        }
    }
}
