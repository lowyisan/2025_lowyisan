package com.example.coin_api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://140.245.122.206")
public class CoinController {

    @PostMapping("/min-coins")
    public ResponseEntity<?> getMinCoins(@RequestBody CoinRequest request) {
        try {
            // Validate input
            if (request.getCoinDenominations() == null || request.getCoinDenominations().isEmpty()) {
                return ResponseEntity.badRequest().body("Coin denominations cannot be empty.");
            }

            if (request.getTargetAmount() <= 0 || request.getTargetAmount() > 10000.0) {
                return ResponseEntity.badRequest().body("Target amount must be between 0 and 10,000.");
            }

            // Validate denominations
            List<Double> allowedDenominations = List.of(0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0);
            for (double coin : request.getCoinDenominations()) {
                if (!allowedDenominations.contains(coin)) {
                    return ResponseEntity.badRequest().body("Invalid coin denomination: " + coin);
                }
            }

            // Calculate minimum coins
            List<Double> coins = new ArrayList<>(request.getCoinDenominations());
            coins.sort(Collections.reverseOrder());

            double targetAmount = request.getTargetAmount();
            double remainingAmount = targetAmount;
            List<Double> result = new ArrayList<>();

            for (double coin : coins) {
                while (remainingAmount >= coin) {
                    result.add(coin);
                    remainingAmount -= coin;
                    remainingAmount = Math.round(remainingAmount * 100.0) / 100.0; // Fix precision issues
                }
            }

            // Check if the target amount can be achieved
            if (Math.abs(remainingAmount) > 0.01) {
                return ResponseEntity.badRequest().body("Cannot achieve the target amount with the provided denominations.");
            }

            result.sort(Double::compareTo); // Sort the result in ascending order
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
