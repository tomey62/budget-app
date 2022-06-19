package pl.zukowski.jwtauth.service;

import pl.zukowski.jwtauth.dto.TransactionDto;
import pl.zukowski.jwtauth.dto.TransactionGetDto;
import pl.zukowski.jwtauth.entity.Card;
import pl.zukowski.jwtauth.entity.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface TransactionService {
    void save (TransactionDto transactionDto, Long number, HttpServletRequest request);
    List<TransactionGetDto> getTransaction(Long cardNumber,HttpServletRequest request);
    TransactionGetDto convertToDto(Transaction transaction);
    void deleteTransaction(Long transactionId);
    List<TransactionGetDto> getTransactionBetween(String start, String end, Long cardNumber, HttpServletRequest request);
}
