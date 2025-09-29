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

    private String name;
    private String password;
    // 간단하게 하기 위해 직접 지정
    private String apiKey;
    private String storeId;

}
