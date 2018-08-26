package sportsbetting;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SportServiceImpl implements SportService {

	@Autowired
	private BookieRepository bookieRepository;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private BetRepository betRepository;

	private Bookie bookie;
	private Player player;

	@Transactional
	@Override
	public Bookie addBookie(Bookie bookieToAdd) throws SportException {
		if (bookieToAdd.getBookieId() != null || bookieToAdd == null) {
			throw new SportException("You cannot add null Bookie or Bookie with ID");
		}
		bookieRepository.save(bookieToAdd);
		return bookieToAdd;
	}

	@Transactional
	@Override
	public Bookie updateBookie(Long bookieId, Bookie bookieToUpdate) throws SportException {
		Bookie originBookie = getBookie(bookieId);
		if (originBookie.getBookieId() != bookieId) {
			throw new SportException("The Bookie ID does not match. ");
		} else {
			originBookie.setBookiePassword(bookieToUpdate.getBookiePassword());
			originBookie.setBookieEmail(bookieToUpdate.getBookieEmail());
			bookieRepository.save(bookieToUpdate);
			return originBookie;
		}
	}

	@Transactional
	@Override
	public void removeBookie(Long bookieId) throws SportException {
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
		return bookieRepository.getOne(bookieId);
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
		return playerToAdd;
	}

	@Transactional
	@Override
	public Player updatePlayer(Long playerId, Player playerToUpdate) throws SportException {
		Player originPlayer = getPlayer(playerId);
		if (originPlayer.getPlayerId() != playerId) {
			throw new SportException("The Player ID does not match. ");
		} else {
			originPlayer.setPlayerPassword(playerToUpdate.getPlayerPassword());
			originPlayer.setPlayerEmail(playerToUpdate.getPlayerEmail());
			playerRepository.save(playerToUpdate);
			return originPlayer;
		}
	}

	@Transactional
	@Override
	public void removePlayer(Long playerId) throws SportException {
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
		return playerRepository.getOne(playerId);
	}

	@Transactional
	@Override
	public Collection<Player> getAllPlayers() throws SportException {
		return playerRepository.findAll();
	}

	@Transactional
	@Override
	public Bet addBet(Bet betToAdd) throws SportException {
		Collection<Bet> bookieBets = getBookieBets();
		betRepository.save(betToAdd);
		bookieBets.add(betToAdd);
		return betToAdd;
	}

	@Transactional
	@Override
	public Bet updateBet(Long betId, Bet betToUpdate) throws SportException {
		Bet originBet = getBet(betId);
		if (originBet.getBetId() != betId) {
			throw new SportException("The Bet ID does not match. ");
		} else {
			originBet.setBetWager(betToUpdate.getBetWager());
			originBet.setBetOdds(betToUpdate.getBetOdds());
			originBet.setBetImage(betToUpdate.getBetImage());
			originBet.setBetReceipt(betToUpdate.getBetReceipt());
			betRepository.save(betToUpdate);
			return originBet;
		}
	}

	@Transactional
	@Override
	public void removeBet(Long betId) throws SportException {
		Bet originBet = getBet(betId);
		if (originBet == null) {
			throw new SportException("No Player with this ID . ");
		} else {
			betRepository.deleteById(betId);
		}
	}

	@Transactional
	@Override
	public Bet getBet(Long betId) throws SportException {
		return betRepository.getOne(betId);
	}

	@Transactional
	@Override
	public Collection<Bet> getBookieBets() throws SportException {
		return betRepository.findAllByBookies(this.bookie);
	}

	@Transactional
	@Override
	public Bet takeBet(Bet betToTake) throws SportException {
		Collection<Bet> playerBets = getPlayerBets();
		playerBets.add(betToTake);
		return betToTake;
	}

	@Transactional
	@Override
	public Collection<Bet> getPlayerBets() throws SportException {
		return betRepository.findAllByPlayers(this.player);
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
	public SportServiceImpl adminLogin(ClientType clientType, String userName, String password) throws SportException {
		if (userName.equals("admin") && password.equals("1111")) {
			return this;
		} else {
			throw new SportException("Admin Login Failed");
		}
	}

	@Transactional
	@Override
	public SportServiceImpl bookieLogin(ClientType clientType, String userName, String password) throws SportException {
		Bookie bookie = getBookieByUserNameAndPassword(userName, password);
		if (bookie == null) {
			throw new SportException("Bookie Login Failed");
		} else {
			return this;
		}
	}

	@Transactional
	@Override
	public SportServiceImpl playerLogin(ClientType clientType, String userName, String password) throws SportException {
		Player player = getPlayerByUserNameAndPassword(userName, password);
		if (player == null) {
			throw new SportException("Player Login Failed");
		} else {
			return this;
		}
	}

	@Transactional
	@Override
	public Collection<Bet> getAllBets() throws SportException {
		return betRepository.findAll();
	}

}
