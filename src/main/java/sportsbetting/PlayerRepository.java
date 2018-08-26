package sportsbetting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends JpaRepository<Player, Long> {

	@Query("SELECT p FROM Player p WHERE (p.playerName=?1) AND (p.playerPassword=?2)")
	public Player findPlayerByUserNameAndPassword(@Param("userName") String userName,
			@Param("password") String password) throws SportException;

}
