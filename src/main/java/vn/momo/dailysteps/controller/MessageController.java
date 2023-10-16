package vn.momo.dailysteps.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.momo.dailysteps.service.NotificationService;

@Controller
@RequiredArgsConstructor
public class MessageController {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final NotificationService notificationService;
    private final RabbitTemplate rabbitTemplate;

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public String getMessage(final String message) throws InterruptedException {
        Thread.sleep(1000);
        notificationService.sendGlobalNotification("Global message");
        return message;
    }

    @GetMapping("/send-message-amqp")
    public ResponseEntity<?> sendMessageToQueue(@RequestParam(name = "message") String message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);

        return ResponseEntity.ok("OK");
    }
}
