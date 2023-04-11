package co.develhope.websocket2.controller;

import ch.qos.logback.core.net.server.Client;
import co.develhope.websocket2.entities.ClientMessageDTO;
import co.develhope.websocket2.entities.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController{

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //invio messaggio
    @PostMapping("/broadcast")
    public ResponseEntity broadcastMessage(@RequestBody MessageDTO messageDTO){
        simpMessagingTemplate.convertAndSend("/topic/broadcast",messageDTO);
        return ResponseEntity.accepted().body("Messaggio " + messageDTO.getMessage() + " inviato al /topic/broadcast");
    }
    /*
    //ricevo il messagio e inoltro verso un altro canale
    @MessageMapping("/broadcast")
    @SendTo("/topic/broadcast")
    public MessageDTO sendNameToChat(MessageDTO messageDTO){
        return new MessageDTO("Frontend","Messagio da " + messageDTO + ": " + messageDTO.getMessage());
    }*/

    @MessageMapping("/client-message")
    @SendTo("/topic/broadcast")
    public MessageDTO sendMessageNewUser(ClientMessageDTO clientMessageDTO){
        return new MessageDTO(clientMessageDTO.getClientName(), clientMessageDTO.getClientAlert(), clientMessageDTO.getClientMsg());
    }









}