package it.dipendentepubico.concorsiparenti.spring.websocket;



import it.dipendentepubico.concorsiparenti.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private WebSocketProperties webSocketProperties;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor != null &&
                        (StompCommand.CONNECT.equals(accessor.getCommand())
                              ||  StompCommand.SEND.equals(accessor.getCommand())
                         )
                ) {
                    String authHeader = accessor.getFirstNativeHeader("Authorization");
                    if (authHeader != null) {
                        accessor.setUser(jwtUtils.authenticateWithJWT(authHeader.substring(7, authHeader.length())));
                    }
                }
                return message;
            }
        });
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker( webSocketProperties.getTopicPrefixes());
        registry.setApplicationDestinationPrefixes(webSocketProperties.getApplicationPrefix());
        registry.setUserDestinationPrefix(webSocketProperties.getUserPrefix());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(webSocketProperties.getEndpoint())
                .setAllowedOrigins(webSocketProperties.getAllowedOrigins())
				// nel caso si voglia usare sockjs
//                .setHandshakeHandler(new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy()))
//                .withSockJS()
        ;
    }


}