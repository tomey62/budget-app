package pl.zukowski.jwtauth.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.repository.ScoreRepository;

@AllArgsConstructor
@Service
public class ScoreServiceImpl {
    private final ScoreRepository scoreRepository;
}
