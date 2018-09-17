package sportsbetting;

import java.util.Collection;

public interface BookieService {

	public Bet addBet(Bet betToAdd) throws SportException;

	public Bet updateBet(Long betId, Bet betToUpdate) throws SportException;

	public void removeBet(Long betId) throws SportException;

	public Bet getBet(Long betId) throws SportException;

	public Collection<Bet> getAllBets() throws SportException;

	public Collection<Bet> getMyBookieBets() throws SportException;

	public Collection<Bet> getMyBookieBetsBySport(Sport sport) throws SportException;

	public Collection<Bet> getMyBookieBetsUpToWager(double wager) throws SportException;

	public BookieService bookieLogin(ClientType clientType, String userName, String password) throws SportException;
}
