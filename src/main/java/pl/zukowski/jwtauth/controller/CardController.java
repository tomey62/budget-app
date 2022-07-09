package pl.zukowski.jwtauth.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zukowski.jwtauth.dto.CardDto;
import pl.zukowski.jwtauth.service.CardService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/card")
    public void saveCard(@RequestBody CardDto cardDto, HttpServletRequest request)
    {
        cardService.saveCard(cardDto,request);
    }

    @DeleteMapping("/card/{cardNumber}")
    public void deleteCard(@PathVariable Long cardNumber, HttpServletRequest request)
    {
        cardService.deleteCard(cardNumber, request);
    }

    @GetMapping("/card")
    public ResponseEntity<List<CardDto>> getCard(HttpServletRequest request)
    {
        return ResponseEntity.ok().body(cardService.getUserCards(request));
    }


}
