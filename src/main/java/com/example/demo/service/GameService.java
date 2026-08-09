package com.example.demo.service;

import com.example.demo.model.Game;
import com.example.demo.repository.GameRepository;
import com.example.demo.strategy.DiscountContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final DiscountContext discountContext;

    public GameService(GameRepository gameRepository, DiscountContext discountContext) {
        this.gameRepository = gameRepository;
        this.discountContext = discountContext;
    }

    public List<Game> getAllGames() {
        List<Game> games = gameRepository.findAll();
        games.forEach(this::applyDiscountStrategy);
        return games;
    }

    public Optional<Game> getGameById(Long id) {
        Optional<Game> gameOpt = gameRepository.findById(id);
        gameOpt.ifPresent(this::applyDiscountStrategy);
        return gameOpt;
    }

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public Game updateGame(Long id, Game game) {
        return gameRepository.findById(id).map(currentGame -> {
            currentGame.setTitle(game.getTitle());
            currentGame.setGenre(game.getGenre());
            currentGame.setPlatform(game.getPlatform());
            currentGame.setRating(game.getRating());
            currentGame.setReleaseDate(game.getReleaseDate());
            currentGame.setPrice(game.getPrice());
            currentGame.setDiscountType(game.getDiscountType());
            return gameRepository.save(currentGame);
        }).orElse(null);
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    private void applyDiscountStrategy(Game game) {
        if (game.getPrice() != null) {
            double finalPrice = discountContext.calculatePrice(game.getDiscountType(), game.getPrice());
            String discountName = discountContext.getDiscountName(game.getDiscountType());
            game.setFinalPrice(finalPrice);
            game.setDiscountName(discountName);
        }
    }
}