package sportsbetting;

import java.util.Collection;

public interface AdminSportService {

	public Bookie addBookie(Bookie bookieToAdd) throws SportException;

	public Bookie updateBookie(Long bookieId, Bookie bookieToUpdate) throws SportException;

	public void removeBookie(Long bookieId) throws SportException;

	public Bookie getBookie(Long bookieId) throws SportException;

	public Collection<Bookie> getAllBookies() throws SportException;

	public Player addPlayer(Player playerToAdd) throws SportException;

	public Player updatePlayer(Long playerId, Player playerToUpdate) throws SportException;

	public void removePlayer(Long playerId) throws SportException;

	public Player getPlayer(Long playerId) throws SportException;

	public Collection<Player> getAllPlayers() throws SportException;

	public AdminSportServiceImpl adminLogin(ClientType clientType, String userName, String password)
			throws SportException;

	public Bookie getBookieByUserNameAndPassword(String userName, String password) throws SportException;

	public Player getPlayerByUserNameAndPassword(String userName, String password) throws SportException;

	public Collection<Bet> getAllBets() throws SportException;

	public Collection<Bet> getAllBetsBySport(Sport sport) throws SportException;

	public Collection<Bet> getAllBetsUpToWager(double wager) throws SportException;
}
