package sportsbetting;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping("/api")
public class BookieController {

	@Autowired
	private BookieService bookieService;

	private BookieService getBookieServiceFromSession(HttpSession session) throws SportException {
		bookieService = (BookieService) session.getAttribute("bookie");
		return bookieService;
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Message handleException(Exception e) {
		String message = e.getMessage();
		if (message == null) {
			message = "Error while invoking request - please login as BOOKIE";
		}
		return new Message(message);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/bookie/login/{userName}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Message bookieUserLogin(@PathVariable("userName") String userName, @PathVariable("password") String password,
			HttpSession session) throws SportException {
		bookieService.bookieLogin(ClientType.BOOKIE, userName, password);
		if ((bookieService.bookieLogin(ClientType.BOOKIE, userName, password)) != null && userName != null
				&& !userName.trim().isEmpty() && password != null && !password.trim().isEmpty()) {
			session.setAttribute("bookie", bookieService);
			return new Message("Bookie : Session ID: " + session.getId());
		} else {
			throw new SportException("Bookie Login Failed");
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/bookie/logout")
	// @ResponseStatus(HttpStatus.NO_CONTENT)
	public void bookieLogout(HttpSession session) {
		session.invalidate();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/api/bookie/createbet", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Bet addBet(@RequestBody Bet betToAdd, HttpSession session) throws SportException {
		getBookieServiceFromSession(session).addBet(betToAdd);
		return betToAdd;
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/api/bookie/updatebet/{betId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Bet updateBet(@PathVariable("betId") Long betId, @RequestBody Bet betToUpdate, HttpSession session)
			throws SportException {
		getBookieServiceFromSession(session).updateBet(betId, betToUpdate);
		return betToUpdate;
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/api/bookie/removebet/{betId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removeBet(@PathVariable("betId") Long betId, HttpSession session) throws SportException {
		getBookieServiceFromSession(session).removeBet(betId);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/bookie/getbet/{betId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet getBet(@PathVariable Long betId, HttpSession session) throws SportException {
		return getBookieServiceFromSession(session).getBet(betId);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/bookie/getbookiebets", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getBookieBets(HttpSession session) throws SportException {
		Bet[] bookieBets = getBookieServiceFromSession(session).getMyBookieBets().toArray(new Bet[0]);
		return bookieBets;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/bookie/getbookiebetsbysport/{sport}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getBookieBetsBySport(@PathVariable Sport sport, HttpSession session) throws SportException {
		Bet[] bookieBetsBySport = getBookieServiceFromSession(session).getMyBookieBetsBySport(sport)
				.toArray(new Bet[0]);
		return bookieBetsBySport;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/bookie/getbookiebetsuptowager/{wager}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getBookieBetsUpToWager(@PathVariable double wager, HttpSession session) throws SportException {
		Bet[] bookieBetsUpToWager = getBookieServiceFromSession(session).getMyBookieBetsUpToWager(wager)
				.toArray(new Bet[0]);
		return bookieBetsUpToWager;
	}

}
