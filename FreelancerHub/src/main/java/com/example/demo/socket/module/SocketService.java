//package com.example.demo.socket.module;
//
//import com.corundumstudio.socketio.SocketIOClient;
//import com.example.demo.socket.message.Message;
//import com.example.demo.socket.message.MessageType;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import java.util.Map;
//
//@Service
//@Slf4j
//public class SocketService {
//
////    public void sendMessage(String room, String eventName, SocketIOClient senderClient, String message) {
////        for (
////                SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
////            if (!client.getSessionId().equals(senderClient.getSessionId())) {
////                client.sendEvent(eventName,
////                        new Message(MessageType.SERVER, message));
////            }
////        }
////    }
//
//    public void sendMessage(String room, String eventName, SocketIOClient senderClient, String message) {
//        log.info("Client [{}] is in rooms: {}", senderClient.getSessionId(), senderClient.getAllRooms());
//        log.info("Sending message to room [{}]: {}", room, message);
//
//        senderClient.getNamespace().getRoomOperations(room).getClients().forEach(client -> {
//            if (!client.getSessionId().equals(senderClient.getSessionId())) {
//                client.sendEvent(eventName, new Message(MessageType.SERVER, message));
//                log.info("Message sent to client [{}] in room [{}]", client.getSessionId(), room);
//            }
//        });
//    }
////    public void sendCallSignal(String room, String eventName, SocketIOClient senderClient, Map<String, Object> signalData) {
////        senderClient.getNamespace().getRoomOperations(room).getClients().forEach(client -> {
////            if (!client.getSessionId().equals(senderClient.getSessionId())) {
////                client.sendEvent(eventName, signalData);
////            }
////        });
////    }
//
//
//    public void sendCallSignal(String room, String eventName, SocketIOClient senderClient, Map<String, Object> signalData) {
//        senderClient.getNamespace().getRoomOperations(room).getClients().forEach(client -> {
//            if (!client.getSessionId().equals(senderClient.getSessionId())) {
//                client.sendEvent(eventName, signalData);
//            }
//        });
//    }
//    public void sendOffer(String room, SocketIOClient senderClient, Map<String, Object> offerData) {
//        sendCallSignal(room, "call_started", senderClient, offerData);
//    }
//
//    // Gửi answer khi client nhận cuộc gọi
//    public void sendAnswer(String room, SocketIOClient senderClient, Map<String, Object> answerData) {
//        sendCallSignal(room, "call_answered", senderClient, answerData);
//    }
//}
//
package com.example.demo.socket.module;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.demo.socket.message.Message;
import com.example.demo.socket.message.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SocketService {

    public void sendMessage(String room, String eventName, SocketIOClient senderClient, String message) {
        log.info("Client [{}] is in rooms: {}", senderClient.getSessionId(), senderClient.getAllRooms());
        log.info("Sending message to room [{}]: {}", room, message);

        senderClient.getNamespace().getRoomOperations(room).getClients().forEach(client -> {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName, new Message(MessageType.SERVER, message));
                log.info("Message sent to client [{}] in room [{}]", client.getSessionId(), room);
            }
        });


    }
    public void sendSignal(String room, String eventName, SocketIOClient senderClient, Object data) {
        log.info("Sending signal [{}] to room [{}]: {}", eventName, room, data);

        senderClient.getNamespace().getRoomOperations(room).getClients().forEach(client -> {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName, data);
                log.info("Signal sent to client [{}] in room [{}]", client.getSessionId(), room);
            }
        });
    }



}
