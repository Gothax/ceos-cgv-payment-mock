package kr.co.loopz.ceoscgvpaymentmock.client.repository;

import kr.co.loopz.ceoscgvpaymentmock.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientByNickname(String nickname);

    Optional<Client> findClientByClientId(String clientId);
}
