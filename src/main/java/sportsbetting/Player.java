package sportsbetting;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PLAYERS")
public class Player {

	private Long playerId;
	private String playerName;
	private String playerPassword;
	private String playerEmail;

	private Collection<Bet> playerBets;

	public Player() {
		super();
	}

	public Player(String playerName, String playerPassword, String playerEmail, Collection<Bet> playerBets) {
		super();
		this.playerName = playerName;
		this.playerPassword = playerPassword;
		this.playerEmail = playerEmail;
		this.playerBets = playerBets;
	}

	@Id
	@GeneratedValue
	@Column(name = "PLAYER_ID")
	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	@Column(name = "PLAYER_NAME", nullable = false, unique = true)
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Column(name = "PLAYER_PASSWORD", nullable = false)
	public String getPlayerPassword() {
		return playerPassword;
	}

	public void setPlayerPassword(String playerPassword) {
		this.playerPassword = playerPassword;
	}

	@Column(name = "PLAYER_EMAIL", nullable = false, unique = true)
	public String getPlayerEmail() {
		return playerEmail;
	}

	public void setPlayerEmail(String playerEmail) {
		this.playerEmail = playerEmail;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "PLAYER_BETS", joinColumns = @JoinColumn(name = "PLAYER_ID"), inverseJoinColumns = @JoinColumn(name = "BET_ID"))
	public Collection<Bet> getPlayerBets() {
		return playerBets;
	}

	public void setPlayerBets(Collection<Bet> playerBets) {
		this.playerBets = playerBets;
	}

	@Override
	public String toString() {
		return "Player [playerId=" + playerId + ", playerName=" + playerName + ", playerPassword=" + playerPassword
				+ ", playerEmail=" + playerEmail + ", playerBets=" + playerBets + "]";
	}

}