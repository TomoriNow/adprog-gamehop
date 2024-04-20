package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.Transaction;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.repository.TransactionRepository;
import id.ac.ui.cs.advprog.adproggameshop.repository.UserRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameBuyer;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.InsufficientFundsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataExtractor<Game> dataExtractor; // Inject DataExtractor
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Game save(Game game){
        gameRepository.save(game);
        return game;
    }

    public Game saveWithOwner(Game game, Long owner_id) {
        User owner = userRepository.findUserByUserId(owner_id).orElse(null);
        game.setOwner(owner);
        return gameRepository.save(game);
    }

    public Game saveWithOwner(Game game, User owner) {
        game.setOwner(owner);
        return gameRepository.save(game);
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public List<GameDTO> findAllBy() {
        return gameRepository.findAllBy();
    }

    @Override
    public List<GameDTO> findAllByCategory(String category) {
        return gameRepository.findAllByCategory(category);
    }

    @Override
    public List<GameDTO> findAllByOwnerId(Long ownerid) {
        User owner = userRepository.findUserByUserId(ownerid).orElse(null);
        return gameRepository.findAllByOwner(owner);
    }

    @Override
    public List<GameDTO> findAllByOwner(User owner) {
        return gameRepository.findAllByOwner(owner);
    }

    @Override
    public List<Game> extractGameData() {
        return dataExtractor.extractData();
    }

    @Override @Transactional
    public Game buyGame(Long gameId, User buyer, int amount, GameBuyer gameBuyer){
        Game game = gameRepository.findByProductId(gameId);
        gameBuyer.buyGame(game, buyer, amount);

        Game result = gameRepository.save(gameBuyer.getGame());
        userRepository.save(gameBuyer.getSeller());
        userRepository.save(gameBuyer.getBuyer());
        transactionRepository.save(gameBuyer.createTransactionRecord());
        return result;
    }
}
