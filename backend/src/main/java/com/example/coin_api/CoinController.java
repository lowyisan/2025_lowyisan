package com.example.coin_api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CoinController {

    @PostMapping("/min-coins")
    public List<Double> getMinCoins(@RequestBody CoinRequest request) {
        List<Double> result = new ArrayList<>();
        List<Double> coins = new ArrayList<>(request.getCoinDenominations());
        coins.sort(Collections.reverseOrder()); // Sort coins in descending order

        double targetAmount = request.getTargetAmount();
        double originalAmount = targetAmount;

        for (double coin : coins) {
            while (targetAmount >= coin) {
                result.add(coin);
                targetAmount -= coin;
                targetAmount = Math.round(targetAmount * 100.0) / 100.0; // Fix precision
            }
        }

        // Validate if targetAmount is fully reduced to zero
        if (targetAmount > 0) {
            throw new IllegalArgumentException(
                "Target amount " + originalAmount + " cannot be achieved with the provided denominations: " 
                + request.getCoinDenominations()
            );
        }

        // Sort the final result in ascending order
        result.sort(Comparator.naturalOrder());
        return result;
    }
}
