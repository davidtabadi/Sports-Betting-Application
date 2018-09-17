package sportsbetting;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "BETS")
public class Bet {

	private Long betId;
	private String betTitle;
	private Sport sport;
	private Date eventDate;
	private double betWager;
	private double betOdds;
	private String betImage;
	private String betReceipt;

	private Collection<Bookie> bookies;

	private Collection<Player> players;

	public Bet() {
		super();
	}

	public Bet(String betTitle, Sport sport, Date eventDate, double betWager, double betOdds, String betImage,
			String betReceipt, Collection<Bookie> bookies, Collection<Player> players) {
		super();
		this.betTitle = betTitle;
		this.sport = sport;
		this.eventDate = eventDate;
		this.betWager = betWager;
		this.betOdds = betOdds;
		this.betImage = betImage;
		this.betReceipt = betReceipt;
		this.bookies = bookies;
		this.players = players;
	}

	@Id
	@GeneratedValue
	@Column(name = "BET_ID")
	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	@Column(name = "BET_TITLE", nullable = false)
	public String getBetTitle() {
		return betTitle;
	}

	public void setBetTitle(String betTitle) {
		this.betTitle = betTitle;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "BET_SPORT", nullable = false)
	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EVENT_DATE", nullable = false)
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	@Column(name = "BET_AMOUNT", nullable = false)
	public double getBetWager() {
		return betWager;
	}

	public void setBetWager(double betWager) {
		this.betWager = betWager;
	}

	@Column(name = "BET_ODDS", nullable = false)
	public double getBetOdds() {
		return betOdds;
	}

	public void setBetOdds(double betOdds) {
		this.betOdds = betOdds;
	}

	@Column(name = "BET_IMAGE", nullable = false)
	public String getBetImage() {
		return betImage;
	}

	public void setBetImage(String betImage) {
		this.betImage = betImage;
	}

	@Column(name = "BET_RECEIPT", nullable = false)
	public String getBetReceipt() {
		return betReceipt;
	}

	public void setBetReceipt(String betReceipt) {
		this.betReceipt = betReceipt;
	}

	@ManyToMany(mappedBy = "bookieBets")
	public Collection<Bookie> getBookies() {
		return bookies;
	}

	public void setBookies(Collection<Bookie> bookies) {
		this.bookies = bookies;
	}

	@ManyToMany(mappedBy = "playerBets")
	public Collection<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Collection<Player> players) {
		this.players = players;
	}

	@Override
	public String toString() {
		return "Bet [betId=" + betId + ", betTitle=" + betTitle + ", sport=" + sport + ", eventDate=" + eventDate
				+ ", betWager=" + betWager + ", betOdds=" + betOdds + ", betImage=" + betImage + ", betReceipt="
				+ betReceipt + ", bookies=" + bookies + ", players=" + players + "]";
	}

}
