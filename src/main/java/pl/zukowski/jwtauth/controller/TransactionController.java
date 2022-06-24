package pl.zukowski.jwtauth.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zukowski.jwtauth.dto.SaveTransactionDto;
import pl.zukowski.jwtauth.dto.SumDto;
import pl.zukowski.jwtauth.dto.TransactionDto;
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
    public ResponseEntity<TransactionDto> saveTransaction(@RequestBody SaveTransactionDto dto, @PathVariable Long cardNumber, HttpServletRequest request) {
       return ResponseEntity.ok().body(transactionService.save(dto, cardNumber, request));
    }

    @GetMapping("/transaction/get/{cardNumber}")
    public ResponseEntity<List<TransactionDto>> getTransaction(@PathVariable Long cardNumber,
                                                               HttpServletRequest request, @RequestParam int page) {
        return ResponseEntity.ok().body(transactionService.getTransaction(cardNumber, request,page));
    }

    @DeleteMapping("/transaction/delete/{transactionId}")
    public void deleteTransaction(@PathVariable Long transactionId, HttpServletRequest request) {
        transactionService.deleteTransaction(transactionId, request);
    }

    @GetMapping("/transaction/getBetween")
    public ResponseEntity<List<TransactionDto>> getTransactionBetween(@RequestParam String start, @RequestParam String end
            , @RequestParam Long cardNumber, HttpServletRequest request, int page) {
        return ResponseEntity.ok()
                .body(transactionService.getTransactionBetween(start, end, cardNumber, request, page));
    }

    @GetMapping("/transaction/getSum/{cardNumber}")
    public ResponseEntity<List<SumDto>> getTransactionSumByCategory(@RequestParam String date, @PathVariable Long cardNumber, HttpServletRequest request)
    {
        return ResponseEntity.ok()
                .body(transactionService.getSumByMonthAndCategory(date,cardNumber,request));
    }
}
