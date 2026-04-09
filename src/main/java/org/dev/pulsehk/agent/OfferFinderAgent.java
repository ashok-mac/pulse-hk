package org.dev.pulsehk.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.observability.AgentMonitor;
import dev.langchain4j.agentic.observability.MonitoredAgent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.V;
import org.dev.pulsehk.model.Offer;
import org.dev.pulsehk.service.OfferService;

import java.util.List;
import java.util.Map;

public class OfferFinderAgent implements MonitoredAgent {

    private final OfferService  offerService;
    public OfferFinderAgent(final OfferService offerService) {
        this.offerService = offerService;
    }

    @SystemMessage("""
            You are a helpful offer finder assistant, " +
                                        "help the user with offers available for the given brand name."
                                        +"\\n 1. Extract the brand Name from user message." +
                                        "2. Call getOfferDetails tool to get the offer details for the brand name" +
                                        "Example" +
                                        "Hello,What is the offer available for Graden products." +
                                        "Following Offers are available:" +
                                        "SuperMarket: PARKNSHOP" +
                                        "Products: " +
                                        "  Name: Marbo Cake - Cherry & Raisin 75g" +
                                        "  Offer: Buy 2 to save $4.00" +
                                        "  Name: Chiffon Cake - Chocolate Flavour 60g" +
                                        "  Offer: Buy 2 to save $5.00 " +
            
                                        "3. DO NOT Make up any answers, always response from the tool call and give Response in example format")
            
            """)
    @Agent(value = "Offer Finder for a given brand names",
            outputKey = "offerDetails")
    public Map<String, List<String>> findOfferForBrands(@V("brandName") String brandNames) {
        return offerService.getOfferDetails(brandNames);
    }


    @Override
    public AgentMonitor agentMonitor() {
        return new  AgentMonitor();
    }
}

