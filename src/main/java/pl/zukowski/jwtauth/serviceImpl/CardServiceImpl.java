package pl.zukowski.jwtauth.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.dto.CardDto;
import pl.zukowski.jwtauth.entity.Card;
import pl.zukowski.jwtauth.entity.User;
import pl.zukowski.jwtauth.exception.ResourceConflictException;
import pl.zukowski.jwtauth.exception.ResourceNotFoundException;
import pl.zukowski.jwtauth.repository.CardRepository;
import pl.zukowski.jwtauth.service.CardService;
import pl.zukowski.jwtauth.service.UserService;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public void saveCard(CardDto cardDto, HttpServletRequest request) {
        User user = userService.getUserFromJwt(request);
        if (cardRepo.findByUserAndCardNumber(user, cardDto.getCardNumber()) == null) {
            Card card = new Card(null,cardDto.getCardNumber(), cardDto.getName(), cardDto.getType(), cardDto.getBalance(), user, new ArrayList<>());
            cardRepo.save(card);
        } else
            throw new ResourceConflictException("Taka karta już istnieje");
    }

    @Override
    public void deleteCard(Long cardNumber, HttpServletRequest request) {
        Card card = cardRepo.findByUserAndCardNumber(userService.getUserFromJwt(request),cardNumber);
        if (card != null)
            cardRepo.deleteById(card.getCardId());
        else
            throw new ResourceNotFoundException("Nie znaleziono takiej karty");
    }

    @Override
    public List<CardDto> getUserCards(HttpServletRequest request) {
        User user = userService.getUserFromJwt(request);
        List<CardDto> cardDto = cardRepo.findByUser(user).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        if (cardDto.size() == 0)
            throw new ResourceNotFoundException("Nie znaleziono kart");
        else
            return cardDto;
    }

    @Override
    public Card getCard(Long cardNumber, HttpServletRequest request) {
        User user = userService.getUserFromJwt(request);
        Card card = cardRepo.findByUserAndCardNumber(user,cardNumber);
        if(card == null)
            throw new ResourceNotFoundException("Nie znaleziono karty");
        else
        return card;
    }

    @Override
    public void updateBalance(Long cardNumber,Long balance, HttpServletRequest request) {
        User user = userService.getUserFromJwt(request);
        Card card = cardRepo.findByUserAndCardNumber(user,cardNumber);
        balance = balance + card.getBalance();
        card.setBalance(balance);
        cardRepo.save(card);
    }

    public CardDto convertEntityToDto(Card card) {
        return modelMapper.map(card, CardDto.class);
    }
}

