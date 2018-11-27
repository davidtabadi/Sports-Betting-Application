package sportsbetting;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BetRepository extends JpaRepository<Bet, Long> {

	public Bet findByBetId(Long betId) throws SportException;

	public Bet findByBetTitle(String betTitle) throws SportException;

	public Collection<Bet> findAllByBetTitle(String betTitle) throws SportException;

	// query to find All Player Bets
	public Collection<Bet> findAllByPlayers(Player player) throws SportException;

	// Join Query for getting Player Bets
	// Relationship between Player and playerBets is Many to Many == simple query
	@Query(value = "SELECT b FROM Bet b WHERE b.betId IN (SELECT b.betId FROM b.players p WHERE p.playerId=?1)")
	public Collection<Bet> findPlayerBets(@Param("playerId") Long playerId) throws SportException;

	// Join Query for getting Bookie Bets
	// Relationship between Bookie and bookieBets is One to Many == different query
	@Query(value = "SELECT b FROM Bet b WHERE b.betId IN (SELECT b.betId FROM Bookie bk WHERE bk.bookieId=?1)")
	public Collection<Bet> findBookieBets(@Param("bookieId") Long bookieId) throws SportException;

	// Query to getting Bet from PlayerBet by playerId and betId
	@Query("SELECT b FROM Bet b WHERE b.betId IN (SELECT b.betId FROM b.players p WHERE p.playerId=?1 AND b.betId = ?2)")
	public Bet getPlayerBetByPlayerId(@Param("playerId") Long playerId, @Param("betId") Long betId);

	// Query to getting Bet from BookieBet by bookieId and betId
	@Query("SELECT b FROM Bet b WHERE b.betId IN (SELECT b.betId FROM b.bookie bk WHERE bk.bookieId=?1 AND b.betId = ?2)")
	public Bet getBookieBetByBookieId(@Param("bookieId") Long bookieId, @Param("betId") Long betId);
}
