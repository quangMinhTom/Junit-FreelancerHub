package com.example.demo.socket.module;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.demo.socket.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SocketModule {

    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;

        // Lắng nghe kết nối và các sự kiện
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());
        server.addEventListener("send_offer", Map.class, onOfferReceived());
        server.addEventListener("send_answer", Map.class, onAnswerReceived());
        server.addEventListener("send_ice_candidate", Map.class, onIceCandidateReceived());
    }

    private DataListener<Message> onChatReceived() {
        return (client, message, ackSender) -> {
            if (message.getRoom() == null || message.getMessage() == null) {
                log.error("Invalid message data: room or message is null");
                return;
            }
            log.info("Received chat message from client [{}] in room [{}]: {}", client.getSessionId(), message.getRoom(), message.getMessage());
            socketService.sendMessage(message.getRoom(), "get_message", client, message.getMessage());
        };
    }

    private DataListener<Map> onOfferReceived() {
        return (client, offer, ackSender) -> {
            if (offer.get("room") == null || offer.get("offer") == null) {
                log.error("Invalid offer: room or offer is null from client [{}]", client.getSessionId());
                return;
            }
            String room = (String) offer.get("room");
            String offerData = (String) offer.get("offer");
            log.info("Received offer from client [{}] for room [{}]: {}", client.getSessionId(), room, offerData);

            try {
                // Gửi offer tới tất cả các client khác trong phòng
                socketService.sendSignal(room, "receive_offer", client, offerData);
            } catch (Exception e) {
                log.error("Error sending offer for room [{}]: {}", room, e.getMessage());
            }
        };
    }

    private DataListener<Map> onAnswerReceived() {
        return (client, answer, ackSender) -> {
            if (answer.get("room") == null || answer.get("answer") == null) {
                log.error("Invalid answer: room or answer is null from client [{}]", client.getSessionId());
                return;
            }
            String room = (String) answer.get("room");
            String answerData = (String) answer.get("answer");
            log.info("Received answer from client [{}] for room [{}]: {}", client.getSessionId(), room, answerData);

            try {
                // Gửi answer tới tất cả các client khác trong phòng
                socketService.sendSignal(room, "receive_answer", client, answerData);
            } catch (Exception e) {
                log.error("Error sending answer for room [{}]: {}", room, e.getMessage());
            }
        };
    }

    private DataListener<Map> onIceCandidateReceived() {
        return (client, candidate, ackSender) -> {
            if (candidate.get("candidate") == null || candidate.get("room") == null) {
                log.error("Invalid ICE candidate: missing candidate or room from client [{}]", client.getSessionId());
                return;
            }
            String room = (String) candidate.get("room");
            String iceCandidate = (String) candidate.get("candidate");
            log.info("Received ICE candidate from client [{}] for room [{}]: {}", client.getSessionId(), room, iceCandidate);

            try {
                // Gửi ICE candidate tới tất cả các client trong phòng
                socketService.sendSignal(room, "receive_ice_candidate", client, candidate);
            } catch (Exception e) {
                log.error("Error sending ICE candidate for room [{}]: {}", room, e.getMessage());
            }
        };
    }

    private ConnectListener onConnected() {
        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            if (room != null) {
                client.joinRoom(room);
                log.info("Client [{}] connected to room [{}]", client.getSessionId(), room);
            } else {
                log.warn("Client [{}] attempted to join a room without a room parameter", client.getSessionId());
            }
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            client.getAllRooms().stream()
                    .filter(room -> !room.equals(client.getSessionId().toString()))  // Đảm bảo client không rời khỏi phòng chính của mình
                    .forEach(client::leaveRoom);
            log.info("Client [{}] disconnected", client.getSessionId());
        };
    }
}
