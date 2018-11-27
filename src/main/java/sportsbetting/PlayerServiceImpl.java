package sportsbetting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
		Date today = new Date();
		if (betToTake.getEventDate().before(today)) {
			throw new SportException("Event Date can't be earlier than today");
		}
		if (betToTake.getBetOdds() < 1) {
			throw new SportException("Odds must be greater than 1");
		}
		if (betToTake.getBetWager() <= 0) {
			throw new SportException("Wager must be greater than 0");
		}
		Collection<Bet> allAvailableBets = getAllAvailableBets();
		Bet betFound = betRepository.findByBetTitle(betToTake.getBetTitle());
		Collection<Bet> playerBets = getMyPlayerBets();
		for (Bet bet : allAvailableBets) {
			if (bet.equals(betFound)) {
				betRepository.save(betToTake);
				playerBets.add(betToTake);
				myPlayer().setPlayerBets(playerBets);
				playerRepository.save(myPlayer());
			}
		}
		return betToTake;
	}

	@Transactional
	@Override
	public Collection<Bet> getMyPlayerBets() throws SportException {
		Collection<Bet> myPlayerBets = betRepository.findPlayerBets(myPlayer().getPlayerId());
		System.err.println(myPlayerBets.toString());
		return myPlayerBets;
	}

	@Transactional
	@Override
	public PlayerServiceImpl playerLogin(ClientType clientType, String userName, String password)
			throws SportException {
		player = playerRepository.findPlayerByUserNameAndPassword(userName, password);
		if (player == null) {
			throw new SportException("Player Login Failed");
		} else {
			Collection<Bet> playerBets = betRepository.findAllByPlayers(player);
			player.setPlayerBets(playerBets);
			playerRepository.save(player);
			System.err.println("Player ID: " + player.getPlayerId());
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

	@Transactional
	@Override
	public Collection<Bet> getAllAvailableBets() throws SportException {
		Collection<Bet> allBets = betRepository.findAll();
		Collection<Bet> myPlayerBets = getMyPlayerBets();
		Collection<Bet> availableBets = new ArrayList<Bet>();
		int check;
		if (myPlayerBets == null) {
			return allBets;
		} else {
			for (Bet betInAllBets : allBets) {
				check = 0;
				for (Bet betInMyBets : myPlayerBets) {
					if (betInMyBets.getBetId() == betInAllBets.getBetId()) {
						check = 1;
					}
				}
				if (check == 0) {
					availableBets.add(betInAllBets);
				}
			}
		}
		return availableBets;
	}

	@Transactional
	@Override
	public Collection<Bet> getAllAvailableBetsBySport(Sport sport) throws SportException {
		Collection<Bet> allAvailableBets = getAllAvailableBets();
		Collection<Bet> allAvailableBetsBySport = new ArrayList<Bet>();
		for (Bet bet : allAvailableBets) {
			if (sport.equals(bet.getSport())) {
				allAvailableBetsBySport.add(bet);
			}
		}
		return allAvailableBetsBySport;
	}

	@Transactional
	@Override
	public Collection<Bet> getAllAvailableBetsUpToWager(double wager) throws SportException {
		Collection<Bet> allAvailableBets = getAllAvailableBets();
		Collection<Bet> allAvailableBetsUpToWagerr = new ArrayList<Bet>();
		for (Bet bet : allAvailableBets) {
			if ((wager > bet.getBetWager()) || (wager == bet.getBetWager())) {
				allAvailableBetsUpToWagerr.add(bet);
			}
		}
		return allAvailableBetsUpToWagerr;
	}
}
