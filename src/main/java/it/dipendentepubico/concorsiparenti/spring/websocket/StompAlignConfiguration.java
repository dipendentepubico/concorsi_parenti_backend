package it.dipendentepubico.concorsiparenti.spring.websocket;

import it.dipendentepubico.concorsiparenti.rest.align.model.AlignStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Configuration
public class StompAlignConfiguration {
    public static final String ALIGN_DESTINATION = "/topic/alignStatus";
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private List<Principal> subscriptions = new LinkedList<>();

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    /**
     * Notifica tutti indistintamente
     * @param alignRunning
     */
    public void notifyAlignToClients(boolean alignRunning){
        AlignStatusMessage m = new AlignStatusMessage(alignRunning);
        messagingTemplate.convertAndSend(ALIGN_DESTINATION, m);
    }



    /**
     * Notifica il singolo sottoscrittore
     * @param alignRunning
     * @param user
     */
    public void notifyAlignToClient(boolean alignRunning, Principal user){
        AlignStatusMessage m = new AlignStatusMessage(alignRunning);
        messagingTemplate.convertAndSendToUser(user.getName(), ALIGN_DESTINATION, m);
    }

    public void addSubscription(Principal user){
        subscriptions.add(user);
    }

    public void removeSubscription(Principal user) {
        subscriptions.remove(user);
    }
}
