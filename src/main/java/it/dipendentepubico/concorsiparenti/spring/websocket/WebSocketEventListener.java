package it.dipendentepubico.concorsiparenti.spring.websocket;

import it.dipendentepubico.concorsiparenti.usecase.SettingsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Component
public class WebSocketEventListener {

    @Autowired
    private StompAlignConfiguration stompAlignConfiguration;

    @Autowired
    private SettingsUseCase settingsUseCase;

    /**
     * Il client che in questo momento sottoscrive il topic di align viene immediatamente notificato
     * se l'align è in corso
     * @param event         Evento di sottoscrizione al topic
     */
    @EventListener
    private void handleSubscribe(SessionSubscribeEvent event){
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
       if(headers.getDestination()!=null && headers.getDestination().equals("/user"+StompAlignConfiguration.ALIGN_DESTINATION)
               && settingsUseCase.isAlignRunning()){
           if(event.getUser()!=null){
               stompAlignConfiguration.notifyAlignToClient(true, event.getUser() );
               stompAlignConfiguration.addSubscription(event.getUser());
           }
       }
    }

    @EventListener
    private void handleUnsubscribe(SessionUnsubscribeEvent event){
        stompAlignConfiguration.removeSubscription(event.getUser());
    }

}
