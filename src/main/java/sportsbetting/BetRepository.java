package sportsbetting;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet, Long> {

	// query to find All Bookie Bets
	public Collection<Bet> findAllByBookies(Bookie bookies) throws SportException;

	// query to find All Player Bets
	public Collection<Bet> findAllByPlayers(Player players) throws SportException;

}
