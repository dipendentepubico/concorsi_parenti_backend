package it.dipendentepubico.concorsiparenti.spring;

import it.dipendentepubico.concorsiparenti.usecase.SettingsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("!TEST_PROFILE")
@Component
public class EventListenerConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(EventListenerConfiguration.class);

    @Autowired
    private SettingsUseCase settingsUseCase;

    /**
     * All'avvio dell'applicativo imposto l'allineamento massivo a false
     * @param event
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Context Refreshed: setting align run to false");
        settingsUseCase.setAlignStopped();
    }
}
