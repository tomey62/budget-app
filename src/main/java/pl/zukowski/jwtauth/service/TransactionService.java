package pl.zukowski.jwtauth.service;

import org.springframework.data.domain.Sort;
import pl.zukowski.jwtauth.dto.SaveTransactionDto;
import pl.zukowski.jwtauth.dto.TransactionDto;
import pl.zukowski.jwtauth.entity.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TransactionService {
    TransactionDto save (SaveTransactionDto transactionDto, Long number, HttpServletRequest request);
    List<TransactionDto> getTransaction(Long cardNumber, HttpServletRequest request, int page);
    TransactionDto convertToGetDto(Transaction transaction);
    void deleteTransaction(Long transactionId, HttpServletRequest request);
    List<TransactionDto> getTransactionBetween(String start, String end, Long cardNumber, HttpServletRequest request, int page);
}
