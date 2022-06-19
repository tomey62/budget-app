package pl.zukowski.jwtauth.serviceImpl;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.dto.TransactionDto;
import pl.zukowski.jwtauth.dto.TransactionGetDto;
import pl.zukowski.jwtauth.entity.Card;
import pl.zukowski.jwtauth.entity.Transaction;
import pl.zukowski.jwtauth.exception.ResourceConflictException;
import pl.zukowski.jwtauth.repository.TransactionRepository;
import pl.zukowski.jwtauth.service.CardService;
import pl.zukowski.jwtauth.service.TransactionService;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepo;
    private final CardService cardService;
    private final ModelMapper modelMapper;
    private final LocalDate myDate = LocalDate.now();


    @Override
    public void save(TransactionDto transactionDto, Long number,HttpServletRequest request) {
        Transaction transaction = new Transaction(null, transactionDto.getPrice(), transactionDto.getCategory()
                , transactionDto.getType(), myDate.toString(), cardService.getCard(number,request));

        if (transactionDto.getType().equals("incoming")) {
            cardService.updateBalance(number, transactionDto.getPrice(), request);
            transactionRepo.save(transaction);
        }
        else if(transactionDto.getType().equals("outgoing")) {
            cardService.updateBalance(number, (-transaction.getPrice()), request);
            transactionRepo.save(transaction);
        }
        else
            throw new ResourceConflictException("Something goes wrong");
    }

    @Override
    public List<TransactionGetDto> getTransaction(Long cardNumber, HttpServletRequest request) {
        Card card = cardService.getCard(cardNumber,request);
        return transactionRepo.findByCard(card).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionGetDto convertToDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionGetDto.class);
    }
}
