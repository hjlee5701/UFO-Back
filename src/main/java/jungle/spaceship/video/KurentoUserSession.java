package jungle.spaceship.video;

import java.io.IOException;

import lombok.Getter;
import org.kurento.client.IceCandidate;
import org.kurento.client.WebRtcEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.JsonObject;

@Getter
public class KurentoUserSession {

    private static final Logger log = LoggerFactory.getLogger(KurentoUserSession.class);

    private final WebSocketSession session;
    private WebRtcEndpoint webRtcEndpoint;  // WebRTC 연결을 처리하기 위한 EndPoint

    public KurentoUserSession(WebSocketSession session) {
        this.session = session;
    }

    /* 사용자에게 메시지 전송 */
    public void sendMessage(JsonObject message) throws IOException {
        log.debug("Sending message from user with session Id '{}': {}", session.getId(), message);
        session.sendMessage(new TextMessage(message.toString()));
    }

    /* 사용자가 KMS 와 WebRTC 연결을 수행하는데 사용하는 EndPoint */
    public void setWebRtcEndpoint(WebRtcEndpoint webRtcEndpoint) {
        this.webRtcEndpoint = webRtcEndpoint;
    }

    /* ICE 후보를 WebRTC Endpoint에 추가 : 피어 간의 연결 가능한 주소를 나타냄 (각 피어는 자신의 후보 목록을 생성 후 상대에게 전송) */
    public void addCandidate(IceCandidate candidate) {
        webRtcEndpoint.addIceCandidate(candidate);
    }
}
/**
 *
 ICE는 다음과 같은 주요 구성 요소로 구성됩니다:

 Candidate (후보): 피어 간의 연결 가능한 주소(호스트, 서버 레퍼런스, 리플렉티드)를 나타냅니다. 각 피어는 자신의 후보 목록을 생성하고 상대방에게 전송합니다.

 Offer/Answer 모델: ICE는 SDP(Session Description Protocol)를 사용하여 각 피어가 자신의 후보를 상대방에게 알리는데, 이를 Offer/Answer 모델이라고 합니다.

 STUN (Session Traversal Utilities for NAT): STUN 서버는 컴퓨터의 공인 IP 주소를 찾고 NAT이 어떻게 동작하는지를 확인하는 데 사용됩니다.

 TURN (Traversal Using Relays around NAT): TURN 서버는 P2P 연결을 설정하는 데 필요한 중계 역할을 합니다. 만약 두 피어가 직접 통신할 수 없는 경우, TURN 서버를 통해 데이터를 전송할 수 있습니다.

 ICE는 두 피어 간의 최적의 연결 경로를 찾아내고 P2P 통신을 가능하게 하여 WebRTC에서 안정적이고 효과적인 미디어 스트리밍을 지원합니다.

 * ICE는 각 피어가 자신의 네트워크 주소 후보(Candidate)를 생성하고, 이를 Offer/Answer 형식으로 교환합니다.


 */