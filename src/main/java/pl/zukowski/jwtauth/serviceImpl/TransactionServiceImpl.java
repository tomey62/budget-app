package pl.zukowski.jwtauth.serviceImpl;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.dto.SaveTransactionDto;
import pl.zukowski.jwtauth.dto.TransactionDto;
import pl.zukowski.jwtauth.entity.Card;
import pl.zukowski.jwtauth.entity.Transaction;
import pl.zukowski.jwtauth.exception.ResourceConflictException;
import pl.zukowski.jwtauth.exception.ResourceNotFoundException;
import pl.zukowski.jwtauth.repository.TransactionRepository;
import pl.zukowski.jwtauth.service.CardService;
import pl.zukowski.jwtauth.service.TransactionService;
import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final static int pageSize= 10;
    private final TransactionRepository transactionRepo;
    private final CardService cardService;
    private final ModelMapper modelMapper;
    private LocalDate myDate = LocalDate.now();


    @Override
    public TransactionDto save(SaveTransactionDto transactionDto, Long number, HttpServletRequest request) {
        Transaction transaction = new Transaction(null, transactionDto.getPrice(), transactionDto.getCategory()
                , transactionDto.getType(), myDate.toString(), cardService.getCard(number, request));
        TransactionDto getDto = new TransactionDto(transaction.getTransactionId(), transactionDto.getPrice(), transactionDto.getCategory()
                , transactionDto.getType(), myDate.toString());
        if (transactionDto.getType().equals("incoming")) {
            cardService.updateBalance(number, transactionDto.getPrice(), request);
            transactionRepo.save(transaction);
            return getDto;
        } else if (transactionDto.getType().equals("outgoing")) {
            cardService.updateBalance(number, (-transaction.getPrice()), request);
            transactionRepo.save(transaction);
            return getDto;
        } else
            throw new ResourceConflictException("Something goes wrong");

    }

    @Override
    public List<TransactionDto> getTransaction(Long cardNumber, HttpServletRequest request, int page) {
        Card card = cardService.getCard(cardNumber, request);
        return transactionRepo.findByCard(PageRequest.of(page,pageSize),card).stream()
                .map(this::convertToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTransaction(Long transactionId, HttpServletRequest request) {
        try {
            Transaction transaction = transactionRepo.getById(transactionId);
            if (transaction.getType().equals("incoming"))
                cardService.updateBalance(transaction.getCard().getCardNumber(), -(transaction.getPrice()), request);
            else
                cardService.updateBalance(transaction.getCard().getCardNumber(), transaction.getPrice(), request);
            transactionRepo.deleteById(transactionId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Transakcja nie istnieje");
        }
    }

    @Override
    public List<TransactionDto> getTransactionBetween(String start, String end, Long cardNumber,
                                                      HttpServletRequest request, int page) {
        Card card = cardService.getCard(cardNumber, request);
        return transactionRepo.findByDateCreatedBetweenAndCard(PageRequest.of(page,pageSize),start, end, card).stream()
                .map(this::convertToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto convertToGetDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }

}
