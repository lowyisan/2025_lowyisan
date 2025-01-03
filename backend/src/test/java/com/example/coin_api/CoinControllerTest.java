package com.example.coin_api;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class CoinControllerTest {

    private final CoinController controller = new CoinController();

    @Test
    void testGetMinCoins_ValidInput() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(7.03);
        request.setCoinDenominations(Arrays.asList(0.01, 0.05, 0.1, 0.5, 1.0, 5.0, 10.0));

        System.out.println("Test: testGetMinCoins_ValidInput");
        System.out.println("Input Target Amount: " + request.getTargetAmount());
        System.out.println("Input Coin Denominations: " + request.getCoinDenominations());

        List<Double> expected = Arrays.asList(0.01, 0.01, 0.01, 1.0, 1.0, 5.0);
        List<Double> result = controller.getMinCoins(request);

        System.out.println("Expected Output: " + expected);
        System.out.println("Actual Output: " + result);

        assertEquals(expected, result);
    }

    @Test
    void testGetMinCoins_InvalidCoinDenomination() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(7.03);
        request.setCoinDenominations(Arrays.asList(0.03, 1.0));

        System.out.println("Test: testGetMinCoins_InvalidCoinDenomination");
        System.out.println("Input Target Amount: " + request.getTargetAmount());
        System.out.println("Input Coin Denominations: " + request.getCoinDenominations());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.getMinCoins(request);
        });

        System.out.println("Exception Message: " + exception.getMessage());

        assertEquals("Invalid coin denomination: 0.03", exception.getMessage());
    }

    @Test
    void testGetMinCoins_AmountOutOfRange() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(15000);
        request.setCoinDenominations(Arrays.asList(0.01, 0.5, 1.0, 5.0, 10.0));

        System.out.println("Test: testGetMinCoins_AmountOutOfRange");
        System.out.println("Input Target Amount: " + request.getTargetAmount());
        System.out.println("Input Coin Denominations: " + request.getCoinDenominations());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.getMinCoins(request);
        });

        System.out.println("Exception Message: " + exception.getMessage());

        assertEquals("Target amount must be between 0.0 and 10000.0", exception.getMessage());
    }
}
