package sportsbetting;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet, Long> {

	public Bet findByBetId(Long betId) throws SportException;

	// query to find All Bookie Bets
	public Collection<Bet> findAllByBookies(Bookie bookie) throws SportException;

	public Collection<Bet> findAllBetsByBookies(Bookie bookie) throws SportException;

	// query to find All Player Bets
	public Collection<Bet> findAllByPlayers(Player player) throws SportException;

}
