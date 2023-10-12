package vn.momo.dailysteps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vn.momo.dailysteps.service.NotificationService;

@Controller
public class MessageController {
    @Autowired
    private NotificationService notificationService;

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public String getMessage(final String message) throws InterruptedException {
        Thread.sleep(1000);
        notificationService.sendGlobalNotification("Global message");
        return message;
    }
}
