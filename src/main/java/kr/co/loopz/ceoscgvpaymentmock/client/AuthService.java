package kr.co.loopz.ceoscgvpaymentmock.client;

import kr.co.loopz.ceoscgvpaymentmock.client.domain.Client;
import kr.co.loopz.ceoscgvpaymentmock.client.exception.ClientErrorCode;
import kr.co.loopz.ceoscgvpaymentmock.client.exception.ClientException;
import kr.co.loopz.ceoscgvpaymentmock.client.repository.ClientRepository;
import kr.co.loopz.ceoscgvpaymentmock.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

    private final ClientRepository clientRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public String getAuthToken(String nickname) {
        Client client = clientRepository.findClientByNickname(nickname)
                .orElseThrow(() -> new ClientException(ClientErrorCode.CLIENT_NOT_FOUND));
        Authentication authentication = jwtProvider.getAuthenticationFromUserId(client.getClientId());

        String accessToken = jwtProvider.generateAccessToken(authentication, client.getClientId());
        client.updateApiKey(accessToken);

        return accessToken;
    }


}
