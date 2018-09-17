package sportsbetting;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookieRepository extends JpaRepository<Bookie, Long> {

	@Query(value = "SELECT bk FROM Bookie bk WHERE (bk.bookieName =?1) AND (bk.bookiePassword =?2)")
	public Bookie findBookieByUserNameAndPassword(@Param("userName") String userName,
			@Param("password") String password) throws SportException;

	public Collection<Bookie> findAllBookiesByBookieBets(Bet bookieBet) throws SportException;

	public Bookie findByBookieId(Long bookieId) throws SportException;

	// public Collection<Bet> findAllByBookieBets(Bet bookieBets) throws
	// SportException;

	public Collection<Bookie> findAllByBookieBets(Bet bookieBets) throws SportException;

}
