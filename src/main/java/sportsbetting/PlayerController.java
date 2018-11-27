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
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	private PlayerService getPlayerServiceFromSession(HttpSession session) throws SportException {
		playerService = (PlayerService) session.getAttribute("player");
		return playerService;
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Message handleException(Exception e) {
		String message = e.getMessage();
		if (message == null) {
			message = "Error while invoking request - please login as PLAYER";
		}
		return new Message(message);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/player/login/{userName}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ClientTypeDTO playerUserLogin(@PathVariable("userName") String userName,
			@PathVariable("password") String password, HttpSession session) throws SportException {
		playerService.playerLogin(ClientType.PLAYER, userName, password);
		if ((playerService.playerLogin(ClientType.PLAYER, userName, password)) != null && userName != null
				&& !userName.trim().isEmpty() && password != null && !password.trim().isEmpty()) {
			session.setAttribute("player", playerService);
			String sessionID = session.getId();
			System.err.println("Player Session ID: " + sessionID);
			return new ClientTypeDTO(userName, password, ClientType.PLAYER);
		} else {
			throw new SportException("Player Login Failed");
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/player/logout")
	// @ResponseStatus(HttpStatus.NO_CONTENT)
	public void playerLogout(HttpSession session) throws SportException {
		session.invalidate();
	}

	@RequestMapping(method = RequestMethod.POST, path = "api/player/takebet")
	public Bet takeBet(@RequestBody Bet betToTake, HttpSession session) throws SportException {
		getPlayerServiceFromSession(session).takeBet(betToTake);
		return betToTake;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/player/getplayerbets", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getPlayerBets(HttpSession session) throws SportException {
		Bet[] playerBets = getPlayerServiceFromSession(session).getMyPlayerBets().toArray(new Bet[0]);
		return playerBets;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/player/getplayerbetsbysport/{sport}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getPlayerBetsBySport(@PathVariable Sport sport, HttpSession session) throws SportException {
		Bet[] playerBetsBySport = getPlayerServiceFromSession(session).getMyPlayerBetsBySport(sport)
				.toArray(new Bet[0]);
		return playerBetsBySport;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/player/getplayerbetsuptowager/{wager}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getPlayerBetsUpToWager(@PathVariable double wager, HttpSession session) throws SportException {
		Bet[] playerBetsUpToWager = getPlayerServiceFromSession(session).getMyPlayerBetsUpToWager(wager)
				.toArray(new Bet[0]);
		return playerBetsUpToWager;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/player/getallavailabledbets", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getAllAvailableBets(HttpSession session) throws SportException {
		Bet[] allAvailableBets = getPlayerServiceFromSession(session).getAllAvailableBets().toArray(new Bet[0]);
		return allAvailableBets;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/player/getallavailabledbetsbysport/{sport}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getAllAvailableBetsBySport(@PathVariable Sport sport, HttpSession session) throws SportException {
		Bet[] allAvailableBetsBySport = getPlayerServiceFromSession(session).getAllAvailableBetsBySport(sport)
				.toArray(new Bet[0]);
		return allAvailableBetsBySport;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/api/player/getallavailabledbetsuptowager/{wager}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bet[] getAllAvailableBetsUpToWager(@PathVariable double wager, HttpSession session) throws SportException {
		Bet[] allAvailableBetsUpToWager = getPlayerServiceFromSession(session).getAllAvailableBetsUpToWager(wager)
				.toArray(new Bet[0]);
		return allAvailableBetsUpToWager;
	}

}
