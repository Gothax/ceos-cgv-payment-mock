package kr.co.loopz.ceoscgvpaymentmock.client.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Client {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String clientId;

    private String nickname;
    // 간단하게 하기 위해 직접 지정
    private String apiKey;
    private String storeId;


    public void updateApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Client(String clientId, String nickname, String apiKey, String storeId) {
        this.clientId = clientId;
        this.nickname = nickname;
        this.apiKey = apiKey;
        this.storeId = storeId;
    }
}
