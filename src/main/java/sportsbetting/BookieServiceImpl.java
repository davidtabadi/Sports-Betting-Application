package sportsbetting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
		Date today = new Date();
		if (betToAdd.getEventDate().before(today)) {
			throw new SportException("Event Date can't be earlier than today");
		}
		if (betToAdd.getBetOdds() < 1) {
			throw new SportException("Odds must be greater than 1");
		}
		if (betToAdd.getBetWager() <= 0) {
			throw new SportException("Wager must be greater than 0");
		}
		Collection<Bet> bookieBets = getMyBookieBets();
		betRepository.save(betToAdd);
		bookieBets.add(betToAdd);
		myBookie().setBookieBets(bookieBets);
		bookieRepository.save(myBookie());
		return betToAdd;
	}

	@Transactional
	@Override
	public Bet updateBet(Long betId, Bet betToUpdate) throws SportException {
		Bet originBet = getBet(betId);
		if (originBet == null) {
			throw new SportException("The Bet ID does not match. ");
		} else {
			betToUpdate.setBetId(betId);
			betRepository.save(betToUpdate);
		}
		return betToUpdate;
	}

	// Remove Bet Method === remove Bet, remove Bet from playerBets,
	// remove Bet from BookieBets
	@Transactional
	@Override
	public void removeBet(Long betId, Bet betToRemove) throws SportException {
		Bet originBet = getBet(betId);
		if (originBet == null) {
			throw new SportException("No Bet with this ID . ");
		} else {

			Bet betByBookieId = betRepository.getBookieBetByBookieId(myBookie().getBookieId(), betId);
			System.err.println(betByBookieId.toString());

			Player playerByBet = playerRepository.findByPlayerBets(originBet);
			System.err.println(playerByBet.toString());

			Bet betByPlayerId = betRepository.getPlayerBetByPlayerId(playerByBet.getPlayerId(), betId);
			System.err.println(betByPlayerId.toString());

			betRepository.delete(betByPlayerId);
			betRepository.delete(betByBookieId);

			System.err.println(originBet.toString());
			betRepository.delete(originBet);

			// betRepository.deleteById(betId);
			// ===== TO DO remove also playerBets related to BET
			// Remove Bet Method
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
		Collection<Bet> bookieBets = betRepository.findBookieBets(myBookie().getBookieId());
		System.err.println(bookieBets.toString());
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
			bookieRepository.save(bookie);
			System.out.println("Bookie ID: " + bookie.getBookieId());
			return this;
		}
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
