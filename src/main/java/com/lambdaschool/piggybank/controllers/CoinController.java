package com.lambdaschool.piggybank.controllers;

import com.lambdaschool.piggybank.models.Coin;
import com.lambdaschool.piggybank.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoinController
{
    @Autowired
    CoinRepository coinrepos;

    private List<Coin> findCoins(List<Coin> myList, CheckCoin tester)
    {
        List<Coin> tempList = new ArrayList<>();

        for (Coin c : myList)
        {
            if(tester.test(c))
            {
                tempList.add(c);
            }
        }

        return tempList;
    }

    // http://localhost:2019/total
    @GetMapping(value = "/total", produces = "application/json")
    public ResponseEntity<?> listPiggyBank()
    {
        List<Coin> myList = new ArrayList<>();
        coinrepos.findAll().iterator().forEachRemaining(myList::add);

        double total = 0.0;
        for (Coin c : myList)
        {
            total += (c.getValue() * c.getQuantity());
            System.out.println(c.getQuantity() + " " + (c.getQuantity() == 1 ? c.getName() : c.getNameplural()));
        }

        System.out.println("The Piggy Bank Holds " + total);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
