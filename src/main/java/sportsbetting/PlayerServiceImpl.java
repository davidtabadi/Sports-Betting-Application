package sportsbetting;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private BetRepository betRepository;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private BookieRepository bookieRepository;

	private Player player;

	@Bean
	public Player myPlayer() {
		return this.player;
	}

	@Transactional
	@Override
	public Bet takeBet(Bet betToTake) throws SportException {

		// Handler dispatch failed; nested exception is java.lang.StackOverflowError
		// ========================================================

		betRepository.save(betToTake);
		Collection<Bet> playerBets = getMyPlayerBets();
		playerBets.add(betToTake);

		Collection<Player> allPlayers = playerRepository.findAllByPlayerBets(betToTake);
		allPlayers.add(myPlayer());
		Collection<Bookie> allBookies = bookieRepository.findAllByBookieBets(betToTake);
		betToTake.setPlayers(allPlayers);
		betToTake.setBookies(allBookies);
		betRepository.save(betToTake);

		return betToTake;
	}

	@Transactional
	@Override
	public Collection<Bet> getMyPlayerBets() throws SportException {
		Collection<Bet> playerBets = betRepository.findAllByPlayers(player);
		playerBets = myPlayer().getPlayerBets();
		System.out.println(playerBets.toString());
		return playerBets;
	}

	@Transactional
	@Override
	public PlayerServiceImpl playerLogin(ClientType clientType, String userName, String password)
			throws SportException {
		player = playerRepository.findPlayerByUserNameAndPassword(userName, password);
		// Long playerId = player.getPlayerId();
		if (player == null) {
			throw new SportException("Player Login Failed");
		} else {

			Collection<Bet> playerBets = betRepository.findAllByPlayers(player);
			player.setPlayerBets(playerBets);
			playerRepository.save(player);
			System.out.println("Player Bets: " + playerBets.toString());
			System.out.println("Player ID: " + player.getPlayerId());

			return this;
		}

	}

	@Transactional
	@Override
	public Collection<Bet> getMyPlayerBetsBySport(Sport sport) throws SportException {
		Collection<Bet> allPlayerBets = getMyPlayerBets();
		Collection<Bet> allPlayerBetsBySport = new ArrayList<Bet>();
		for (Bet bet : allPlayerBets) {
			if (sport.equals(bet.getSport())) {
				allPlayerBetsBySport.add(bet);
			}
		}
		return allPlayerBetsBySport;
	}

	@Transactional
	@Override
	public Collection<Bet> getMyPlayerBetsUpToWager(double wager) throws SportException {
		Collection<Bet> allPlayerBets = getMyPlayerBets();
		Collection<Bet> allPlayerBetsUpToWager = new ArrayList<Bet>();
		for (Bet bet : allPlayerBets) {
			if ((wager > bet.getBetWager()) || (wager == bet.getBetWager())) {
				allPlayerBetsUpToWager.add(bet);
			}
		}
		return allPlayerBetsUpToWager;
	}
}
