package jungle.spaceship.video;

import lombok.RequiredArgsConstructor;
import org.kurento.client.KurentoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import java.util.Objects;

@Configuration
@EnableWebSocket // 웹 소켓에 대해 자동 설정
@RequiredArgsConstructor
public class KurentoConfig implements WebSocketConfigurer {


    @Value("${kms.url}")
    private String kmsUrl;

    /* WebSocket Handler */
    @Bean
    public KurentoHandler kurentoHandler() {
        return new KurentoHandler();
    }

    /* Kurento 서버 통신을 위한 인스턴스 */
//    @Bean
//    public KurentoClient kurentoClient() {
//        return KurentoClient.create(url);
//    }
    @Bean
    public KurentoClient kurentoClient() {
        String envKmsUrl = System.getenv("KMS_URL");
        if(Objects.isNull(envKmsUrl) || envKmsUrl.isEmpty()){
            return KurentoClient.create(kmsUrl);
        }

        return KurentoClient.create(envKmsUrl);
    }

    /* WebSocket 연결에 대한 서버 컨테이너 설정을 구성 : 최대 텍스트 메시지 버퍼 크기를 설정 */
    @Bean
    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(32768);
        return container;
    }

    /* Endpoint 에 Handler 등록 : /call 에 접속하면 CallHandler가 처리 */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(kurentoHandler(), "/call");
    }


}
