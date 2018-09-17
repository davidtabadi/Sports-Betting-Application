package sportsbetting;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends JpaRepository<Player, Long> {

	@Query("SELECT p FROM Player p WHERE (p.playerName=?1) AND (p.playerPassword=?2)")
	public Player findPlayerByUserNameAndPassword(@Param("userName") String userName,
			@Param("password") String password) throws SportException;

	public Collection<Player> findAllPlayersByPlayerBets(Bet playerBet) throws SportException;

	public Player findByPlayerId(Long playerId) throws SportException;

	// public Collection<Bet> findAllByPlayerBets(Bet playerBets) throws
	// SportException;

	public Collection<Player> findAllByPlayerBets(Bet playerBets) throws SportException;
}
