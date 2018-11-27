package sportsbetting;

import java.util.Collection;

public interface PlayerService {

	public Bet takeBet(Bet betToTake) throws SportException;

	public Collection<Bet> getAllAvailableBets() throws SportException;

	public Collection<Bet> getAllAvailableBetsBySport(Sport sport) throws SportException;

	public Collection<Bet> getAllAvailableBetsUpToWager(double wager) throws SportException;

	public Collection<Bet> getMyPlayerBets() throws SportException;

	public Collection<Bet> getMyPlayerBetsBySport(Sport sport) throws SportException;

	public Collection<Bet> getMyPlayerBetsUpToWager(double wager) throws SportException;

	public PlayerService playerLogin(ClientType clientType, String userName, String password) throws SportException;
}
