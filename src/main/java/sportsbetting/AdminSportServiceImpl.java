package sportsbetting;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminSportServiceImpl implements AdminSportService {

	@Autowired
	private BookieRepository bookieRepository;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private BetRepository betRepository;

	@Transactional
	@Override
	public Bookie addBookie(Bookie bookieToAdd) throws SportException {
		if (bookieToAdd.getBookieId() != null || bookieToAdd == null) {
			throw new SportException("You cannot add null Bookie or Bookie with ID");
		}
		bookieRepository.save(bookieToAdd);
		bookieToAdd.setBookieBets(new ArrayList<Bet>());
		bookieRepository.save(bookieToAdd);
		return bookieToAdd;
	}

	@Transactional
	@Override
	public Bookie updateBookie(Long bookieId, Bookie bookieToUpdate) throws SportException {
		Bookie originBookie = getBookie(bookieId);
		if (originBookie == null) {
			throw new SportException("The Bookie ID does not match. ");
		} else {
			bookieToUpdate.setBookieId(bookieId);
			bookieRepository.save(bookieToUpdate);
		}
		return bookieToUpdate;
	}

	@Transactional
	@Override
	public void removeBookie(Long bookieId, Bookie bookieToRemove) throws SportException {
		Bookie originBookie = getBookie(bookieId);
		if (originBookie == null) {
			throw new SportException("No Bookie with this ID . ");
		} else {
			bookieRepository.deleteById(bookieId);
		}
	}

	@Transactional
	@Override
	public Bookie getBookie(Long bookieId) throws SportException {
		return bookieRepository.findByBookieId(bookieId);
	}

	@Transactional
	@Override
	public Collection<Bookie> getAllBookies() throws SportException {
		return bookieRepository.findAll();
	}

	@Transactional
	@Override
	public Player addPlayer(Player playerToAdd) throws SportException {
		if (playerToAdd.getPlayerId() != null || playerToAdd == null) {
			throw new SportException("You cannot add null Player or Player with ID");
		}
		playerRepository.save(playerToAdd);
		playerToAdd.setPlayerBets(new ArrayList<Bet>());
		playerRepository.save(playerToAdd);
		return playerToAdd;
	}

	@Transactional
	@Override
	public Player updatePlayer(Long playerId, Player playerToUpdate) throws SportException {
		Player originPlayer = getPlayer(playerId);
		if (originPlayer == null) {
			throw new SportException("The Player ID does not match. ");
		} else {
			playerToUpdate.setPlayerId(playerId);
			playerRepository.save(playerToUpdate);
		}
		return playerToUpdate;
	}

	@Transactional
	@Override
	public void removePlayer(Long playerId, Player playerToRemove) throws SportException {
		Player originPlayer = getPlayer(playerId);
		if (originPlayer == null) {
			throw new SportException("No Player with this ID . ");
		} else {
			playerRepository.deleteById(playerId);
		}
	}

	@Transactional
	@Override
	public Player getPlayer(Long playerId) throws SportException {
		return playerRepository.findByPlayerId(playerId);
	}

	@Transactional
	@Override
	public Collection<Player> getAllPlayers() throws SportException {
		return playerRepository.findAll();
	}

	@Transactional
	@Override
	public AdminSportServiceImpl adminLogin(ClientType clientType, String userName, String password)
			throws SportException {
		if (userName.equals("admin") && password.equals("1111")) {
			return this;
		} else {
			throw new SportException("Admin Login Failed");
		}
	}

	@Transactional
	@Override
	public Bookie getBookieByUserNameAndPassword(String userName, String password) throws SportException {
		return bookieRepository.findBookieByUserNameAndPassword(userName, password);
	}

	@Transactional
	@Override
	public Player getPlayerByUserNameAndPassword(String userName, String password) throws SportException {
		return playerRepository.findPlayerByUserNameAndPassword(userName, password);
	}

	@Transactional
	@Override
	public Collection<Bet> getAllBets() throws SportException {
		Collection<Bet> allBets = betRepository.findAll();
		System.err.println(allBets.toString());
		return allBets;
	}

	@Transactional
	@Override
	public Collection<Bet> getAllBetsBySport(Sport sport) throws SportException {
		Collection<Bet> allBets = getAllBets();
		Collection<Bet> allBetsBySport = new ArrayList<Bet>();
		for (Bet bet : allBets) {
			if (sport.equals(bet.getSport())) {
				allBetsBySport.add(bet);
			}
		}
		return allBetsBySport;
	}

	@Transactional
	@Override
	public Collection<Bet> getAllBetsUpToWager(double wager) throws SportException {
		Collection<Bet> allBets = getAllBets();
		Collection<Bet> allBetsUpToWager = new ArrayList<Bet>();
		for (Bet bet : allBets) {
			if ((wager > bet.getBetWager()) || (wager == bet.getBetWager())) {
				allBetsUpToWager.add(bet);
			}
		}
		return allBetsUpToWager;
	}

}
