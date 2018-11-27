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
public class AdminSportController {

	@Autowired
	private AdminSportService adminSportService;

	private AdminSportService getAdminServiceFromSession(HttpSession session) throws SportException {
		adminSportService = (AdminSportService) session.getAttribute("admin");
		return adminSportService;
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Message handleException(Exception e) {
		String message = e.getMessage();
		if (message == null) {
			message = "Error while invoking request - please login as ADMIN";
		}
		return new Message(message);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/admin/login/{userName}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ClientTypeDTO adminLogin(@PathVariable("userName") String userName,
			@PathVariable("password") String password, HttpSession session) throws SportException {
		adminSportService.adminLogin(ClientType.ADMINSPORT, userName, password);
		if (adminSportService.adminLogin(ClientType.ADMINSPORT, userName, password) != null && userName != null
				&& !userName.trim().isEmpty() && password != null && !password.trim().isEmpty()) {
			session.setAttribute("admin", adminSportService);
			String sessionID = session.getId();
			System.err.println("AdminSport Session ID: " + sessionID);
			return new ClientTypeDTO(userName, password, ClientType.ADMINSPORT);
		} else {
			throw new SportException("AdminSport Login Failed");
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/admin/logout")
	// @ResponseStatus(HttpStatus.NO_CONTENT)
	public void adminLogout(HttpSession session) {
		session.invalidate();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/api/admin/bookies", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Bookie addBookie(@RequestBody Bookie bookieToAdd, HttpSession session) throws SportException {
		getAdminServiceFromSession(session).addBookie(bookieToAdd);
		return bookieToAdd;
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/api/admin/bookies/{bookieId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Bookie updateBookie(@PathVariable("bookieId") Long bookieId, @RequestBody Bookie bookieToUpdate,
			HttpSession session) throws SportException {
		getAdminServiceFromSession(session).updateBookie(bookieId, bookieToUpdate);
		return bookieToUpdate;
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/api/admin/bookies/{bookieId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removeBookie(@PathVariable("bookieId") Long bookieId, @RequestBody Bookie bookieToRemove,
			HttpSession session) throws SportException {
		getAdminServiceFromSession(session).removeBookie(bookieId, bookieToRemove);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/admin/bookies/{bookieId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bookie getBookie(@PathVariable("bookieId") Long bookieId, HttpSession session) throws SportException {
		return getAdminServiceFromSession(session).getBookie(bookieId);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/admin/bookies", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bookie[] getAllBookies(HttpSession session) throws SportException {
		Bookie[] allBookies = getAdminServiceFromSession(session).getAllBookies().toArray(new Bookie[0]);
		return allBookies;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/api/admin/players", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Player addPlayer(@RequestBody Player playerToAdd, HttpSession session) throws SportException {
		getAdminServiceFromSession(session).addPlayer(playerToAdd);
		return playerToAdd;
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/api/admin/players/{playerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Player updatePlayer(@PathVariable("playerId") Long playerId, @RequestBody Player playerToUpdate,
			HttpSession session) throws SportException {
		getAdminServiceFromSession(session).updatePlayer(playerId, playerToUpdate);
		return playerToUpdate;
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/api/admin/players/{playerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removePlayer(@PathVariable("playerId") Long playerId, @RequestBody Player playerToRemove,
			HttpSession session) throws SportException {
		getAdminServiceFromSession(session).removePlayer(playerId, playerToRemove);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/admin/players/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Player getPlayer(@PathVariable("playerId") Long playerId, HttpSession session) throws SportException {
		return getAdminServiceFromSession(session).getPlayer(playerId);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/admin/players", produces = MediaType.APPLICATION_JSON_VALUE)
	public Player[] getAllPlayers(HttpSession session) throws SportException {
		Player[] allPlayers = getAdminServiceFromSession(session).getAllPlayers().toArray(new Player[0]);
		return allPlayers;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/admin/bets", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getAllBets(HttpSession session) throws SportException {
		Bet[] allBets = getAdminServiceFromSession(session).getAllBets().toArray(new Bet[0]);
		return allBets;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/admin/betsbysport/{sport}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getAllBetsBySport(@PathVariable Sport sport, HttpSession session) throws SportException {
		Bet[] allBetsBySport = getAdminServiceFromSession(session).getAllBetsBySport(sport).toArray(new Bet[0]);
		return allBetsBySport;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/admin/betsuptowager/{wager}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getAllBetsUpToWager(@PathVariable double wager, HttpSession session) throws SportException {
		Bet[] allBetsUpToWager = getAdminServiceFromSession(session).getAllBetsUpToWager(wager).toArray(new Bet[0]);
		return allBetsUpToWager;
	}
}
