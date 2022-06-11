package it.dipendentepubico.concorsiparenti.rest.align.controller;

import it.dipendentepubico.concorsiparenti.rest.align.model.AlignInMessage;
import it.dipendentepubico.concorsiparenti.rest.align.model.AlignOutMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Controller di test
 */
@Controller
public class AlignController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public AlignOutMessage send(AlignInMessage message) {
        return new AlignOutMessage(message.getCode(), message.getMessage(), new Date());
    }
}
