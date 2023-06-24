package pl.zukowski.jwtauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zukowski.jwtauth.serviceImpl.ScoreServiceImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ScoreController {
    private final ScoreServiceImpl scoreService;
}
