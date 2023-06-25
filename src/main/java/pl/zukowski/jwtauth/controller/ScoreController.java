package pl.zukowski.jwtauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zukowski.jwtauth.entity.Score;
import pl.zukowski.jwtauth.serviceImpl.ScoreServiceImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ScoreController {
    private final ScoreServiceImpl scoreService;

    @PostMapping("/score")
    public ResponseEntity<?> addScore(@RequestBody Score score, @PathVariable Long id) {
        return ResponseEntity.ok().body(addScore(score,id));

    }
}
