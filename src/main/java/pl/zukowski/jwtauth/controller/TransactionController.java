package pl.zukowski.jwtauth.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zukowski.jwtauth.dto.TransactionDto;
import pl.zukowski.jwtauth.dto.TransactionGetDto;
import pl.zukowski.jwtauth.entity.Transaction;
import pl.zukowski.jwtauth.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transaction/save/{cardNumber}")
    public ResponseEntity<TransactionGetDto> saveTransaction(@RequestBody TransactionDto dto, @PathVariable Long cardNumber, HttpServletRequest request) {
       return ResponseEntity.ok().body(transactionService.save(dto, cardNumber, request));
    }

    @GetMapping("/transaction/get/{cardNumber}")
    public ResponseEntity<List<TransactionGetDto>> getTransaction(@PathVariable Long cardNumber, HttpServletRequest request) {
        return ResponseEntity.ok().body(transactionService.getTransaction(cardNumber, request));
    }

    @DeleteMapping("/transaction/delete/{transactionId}")
    public void deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
    }

    @GetMapping("/transaction/getBetween")
    public ResponseEntity<List<TransactionGetDto>> getTransactionBetween(@RequestParam String start, @RequestParam String end
            , @RequestParam Long cardNumber, HttpServletRequest request) {
        return ResponseEntity.ok()
                .body(transactionService.getTransactionBetween(start, end, cardNumber, request));
    }
}
