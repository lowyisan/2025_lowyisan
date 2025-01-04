package com.example.coin_api;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CoinControllerTest {

    private final CoinController controller = new CoinController();

    @Test
    void testGetMinCoins_SimpleExactMatch() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(7.0);
        request.setCoinDenominations(Arrays.asList(1.0, 2.0, 5.0));

        ResponseEntity<?> response = controller.getMinCoins(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Double> result = (List<Double>) response.getBody();
        List<Double> expected = Arrays.asList(2.0, 5.0);
        assertEquals(expected, result);
    }

    @Test
    void testGetMinCoins_MultipleCoins() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(7.03);
        request.setCoinDenominations(Arrays.asList(0.01, 0.05, 0.1, 0.5, 1.0, 2.0, 5.0));

        ResponseEntity<?> response = controller.getMinCoins(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Double> result = (List<Double>) response.getBody();
        List<Double> expected = Arrays.asList(0.01, 0.01, 0.01, 2.0, 5.0);
        assertEquals(expected, result);
    }

    @Test
    void testGetMinCoins_ImpossibleAmount() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(5.0);
        request.setCoinDenominations(Arrays.asList(2.0));

        ResponseEntity<?> response = controller.getMinCoins(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cannot achieve the target amount with the provided denominations.", response.getBody());
    }

    @Test
    void testGetMinCoins_EmptyDenominations() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(7.03);
        request.setCoinDenominations(Arrays.asList());

        ResponseEntity<?> response = controller.getMinCoins(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Coin denominations cannot be empty.", response.getBody());
    }

    @Test
    void testGetMinCoins_InvalidDenomination() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(7.03);
        request.setCoinDenominations(Arrays.asList(0.03, 1.0));

        ResponseEntity<?> response = controller.getMinCoins(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid coin denomination: 0.03", response.getBody());
    }

    @Test
    void testGetMinCoins_LargeTargetAmount() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(103.0);
        request.setCoinDenominations(Arrays.asList(1.0, 2.0, 50.0));

        ResponseEntity<?> response = controller.getMinCoins(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Double> result = (List<Double>) response.getBody();
        List<Double> expected = Arrays.asList(1.0, 2.0, 50.0, 50.0);
        assertEquals(expected, result);
    }
}