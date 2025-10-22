package kr.co.loopz.ceoscgvpaymentmock.common.redis.repository;

import kr.co.loopz.ceoscgvpaymentmock.common.redis.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
