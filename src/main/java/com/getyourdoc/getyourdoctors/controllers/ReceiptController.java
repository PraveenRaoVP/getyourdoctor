package com.getyourdoc.getyourdoctors.controllers;

import com.getyourdoc.getyourdoctors.models.Receipt;
import com.getyourdoc.getyourdoctors.services.ReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/create/{appointmentId}")
    public ResponseEntity<Receipt> createReceipt(@PathVariable Long appointmentId,@RequestBody Receipt receipt) {
        Receipt createdReceipt = receiptService.createReceipt(appointmentId,receipt);
        return new ResponseEntity<>(createdReceipt, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable("id") Long receiptId) {
        Receipt receipt = receiptService.getReceiptById(receiptId);
        if (receipt == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Receipt>> getAllReceipts() {
        List<Receipt> receipts = receiptService.getAllReceipts();
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receipt> updateReceipt(@PathVariable("id") Long receiptId, @RequestBody Receipt updatedReceipt) {
        Receipt receipt = receiptService.updateReceipt(receiptId, updatedReceipt);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceipt(@PathVariable("id") Long receiptId) {
        receiptService.deleteReceipt(receiptId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
