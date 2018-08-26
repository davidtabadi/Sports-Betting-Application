package sportsbetting;

import java.util.Collection;

public interface SportService {

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

	public Bet addBet(Bet betToAdd) throws SportException;

	public Bet updateBet(Long betId, Bet betToUpdate) throws SportException;

	public void removeBet(Long betId) throws SportException;

	public Bet getBet(Long betId) throws SportException;

	public Collection<Bet> getAllBets() throws SportException;

	public Collection<Bet> getBookieBets() throws SportException;

	public Bet takeBet(Bet betToTake) throws SportException;

	public Collection<Bet> getPlayerBets() throws SportException;

	public Bookie getBookieByUserNameAndPassword(String userName, String password) throws SportException;

	public Player getPlayerByUserNameAndPassword(String userName, String password) throws SportException;

	public SportServiceImpl adminLogin(ClientType clientType, String userName, String password) throws SportException;

	public SportServiceImpl bookieLogin(ClientType clientType, String userName, String password) throws SportException;

	public SportServiceImpl playerLogin(ClientType clientType, String userName, String password) throws SportException;

}
