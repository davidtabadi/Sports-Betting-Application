package sportsbetting;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BOOKIES")
public class Bookie {

	private Long bookieId;
	private String bookieName;
	private String bookiePassword;
	private String bookieEmail;

	private Collection<Bet> bookieBets;

	public Bookie() {
		super();
	}

	public Bookie(String bookieName, String bookiePassword, String bookieEmail) {
		super();
		this.bookieName = bookieName;
		this.bookiePassword = bookiePassword;
		this.bookieEmail = bookieEmail;
	}

	@Id
	@GeneratedValue
	@Column(name = "BOOKIE_ID")
	public Long getBookieId() {
		return bookieId;
	}

	public void setBookieId(Long bookieId) {
		this.bookieId = bookieId;
	}

	@Column(name = "BOOKIE_NAME", nullable = false, unique = true)
	public String getBookieName() {
		return bookieName;
	}

	public void setBookieName(String bookieName) {
		this.bookieName = bookieName;
	}

	@Column(name = "BOOKIE_PASSWORD", nullable = false)
	public String getBookiePassword() {
		return bookiePassword;
	}

	public void setBookiePassword(String bookiePassword) {
		this.bookiePassword = bookiePassword;
	}

	@Column(name = "BOOKIE_EMAIL", nullable = false, unique = true)
	public String getBookieEmail() {
		return bookieEmail;
	}

	public void setBookieEmail(String bookieEmail) {
		this.bookieEmail = bookieEmail;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "BOOKIE_BETS", joinColumns = @JoinColumn(name = "BOOKIE_ID"), inverseJoinColumns = @JoinColumn(name = "BET_ID"))
	public Collection<Bet> getBookieBets() {
		return bookieBets;
	}

	public void setBookieBets(Collection<Bet> bookieBets) {
		this.bookieBets = bookieBets;
	}

	@Override
	public String toString() {
		return "Bookie [bookieId=" + bookieId + ", bookieName=" + bookieName + ", bookiePassword=" + bookiePassword
				+ ", bookieEmail=" + bookieEmail + "]";
	}

}