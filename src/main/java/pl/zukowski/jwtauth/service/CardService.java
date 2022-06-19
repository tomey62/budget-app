package pl.zukowski.jwtauth.service;

import pl.zukowski.jwtauth.dto.CardDto;
import pl.zukowski.jwtauth.entity.Card;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface CardService {
   void saveCard(CardDto cardDto, HttpServletRequest request);
   void deleteCard(Long cardNumber,HttpServletRequest request);
   List<CardDto>getUserCards(HttpServletRequest request);
   Card getCard(Long cardNumber,HttpServletRequest request);
   void updateBalance(Long cardNumber,Long balance, HttpServletRequest request);
}
