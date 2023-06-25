package pl.zukowski.jwtauth.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.entity.Location;
import pl.zukowski.jwtauth.entity.Score;
import pl.zukowski.jwtauth.repository.LocationRepository;
import pl.zukowski.jwtauth.repository.ScoreRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ScoreServiceImpl {
    private final ScoreRepository scoreRepository;
    private final LocationRepository locationRepository;

    public Score addScore(float score,Long id)
    {
        Location location = locationRepository.getById(id);
        Score score1 = new Score();
        score1.setLocation(location);
        score1.setRating(score);
        return scoreRepository.save(score1);
    }
    public float getAverageRatingForLocation(Long locationId) {
        float averageRating = 0.0f;
        List<Score> scores = scoreRepository.findByLocationId(locationId);

        if (!scores.isEmpty()) {
            float sum = 0.0f;
            for (Score score : scores) {
                sum += score.getRating();
            }
            averageRating = sum / scores.size();
        }

        return averageRating;
    }
}
