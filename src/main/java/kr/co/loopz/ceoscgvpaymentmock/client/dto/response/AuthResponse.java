package kr.co.loopz.ceoscgvpaymentmock.client.dto.response;

public record AuthResponse(
        String apiSecret,
        String storeId
) {

    public static AuthResponse of(String apiSecret, String storeId) {
        return new AuthResponse(apiSecret, storeId);
    }
}
