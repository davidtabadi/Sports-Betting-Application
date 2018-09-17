package sportsbetting;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookieServiceImpl implements BookieService {

	@Autowired
	private BookieRepository bookieRepository;

	@Autowired
	private BetRepository betRepository;

	@Autowired
	private PlayerRepository playerRepository;

	private Bookie bookie;

	@Bean
	public Bookie myBookie() {
		return this.bookie;
	}

	@Transactional
	@Override
	public Bet addBet(Bet betToAdd) throws SportException {

		betRepository.save(betToAdd);
		Collection<Bet> bookieBets = getMyBookieBets();
		bookieBets.add(betToAdd);

		Collection<Bookie> allBookies = bookieRepository.findAllByBookieBets(betToAdd);
		allBookies.add(myBookie());
		Collection<Player> allPlayers = playerRepository.findAllByPlayerBets(betToAdd);
		betToAdd.setBookies(allBookies);
		betToAdd.setPlayers(allPlayers);
		betRepository.save(betToAdd);

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
			originBet.setBookies(betToUpdate.getBookies());
			originBet.setPlayers(betToUpdate.getPlayers());
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
		return betRepository.findByBetId(betId);
	}

	@Transactional
	@Override
	public Collection<Bet> getMyBookieBets() throws SportException {
		Collection<Bet> bookieBets = betRepository.findAllByBookies(bookie);
		bookieBets = myBookie().getBookieBets();
		System.out.println(bookieBets.toString());
		return bookieBets;
	}

	@Transactional
	@Override
	public BookieServiceImpl bookieLogin(ClientType clientType, String userName, String password)
			throws SportException {
		bookie = bookieRepository.findBookieByUserNameAndPassword(userName, password);
		if (bookie == null) {
			throw new SportException("Bookie Login Failed");
		} else {

			Collection<Bet> bookieBets = betRepository.findAllByBookies(bookie);
			bookie.setBookieBets(bookieBets);
			bookieRepository.save(bookie);
			System.out.println("Bookie Bets: " + bookieBets.toString());
			System.out.println("Bookie ID: " + bookie.getBookieId());

			return this;
		}
	}

	@Override
	public Collection<Bet> getAllBets() throws SportException {
		return betRepository.findAll();
	}

	@Transactional
	@Override
	public Collection<Bet> getMyBookieBetsBySport(Sport sport) throws SportException {
		Collection<Bet> allBookieBets = getMyBookieBets();
		Collection<Bet> allBookieBetsBySport = new ArrayList<Bet>();
		for (Bet bet : allBookieBets) {
			if (sport.equals(bet.getSport())) {
				allBookieBetsBySport.add(bet);
			}
		}
		return allBookieBetsBySport;
	}

	@Transactional
	@Override
	public Collection<Bet> getMyBookieBetsUpToWager(double wager) throws SportException {
		Collection<Bet> allBookieBets = getMyBookieBets();
		Collection<Bet> allBookieBetsUpToWager = new ArrayList<Bet>();
		for (Bet bet : allBookieBets) {
			if ((wager > bet.getBetWager()) || (wager == bet.getBetWager())) {
				allBookieBetsUpToWager.add(bet);
			}
		}
		return allBookieBetsUpToWager;
	}

}
