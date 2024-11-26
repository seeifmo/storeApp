package com.cgc.store.controller;

import com.cgc.store.dto.AppResponse;
import com.cgc.store.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/history/userId/{userId}")
    public ResponseEntity<AppResponse> getPurchaseHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(new AppResponse(200,  purchaseService.getPurchaseHistory(userId), "Success"));
        }

    @PostMapping("/complete/{userId}")
    public ResponseEntity<AppResponse> completePurchase(@PathVariable Long userId) {
        return ResponseEntity.ok( new AppResponse(200, purchaseService.completePurchase(userId), "Success"));
    }

}
