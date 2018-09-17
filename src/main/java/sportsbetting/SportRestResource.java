// package sportsbetting;
//
// import javax.servlet.http.HttpSession;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.RestController;
//
// @RestController
//// @RequestMapping("/api")
// public class SportRestResource {
//
// @Autowired
// private SportService sportService;
//
// private SportService getAdminServiceFromSession(HttpSession session) throws
// SportException {
// SportService adminService = (SportService) session.getAttribute("admin");
// return adminService;
// }
//
// private SportService getBookieServiceFromSession(HttpSession session) throws
// SportException {
// SportService bookieService = (SportService) session.getAttribute("bookie");
// return bookieService;
// }
//
// private SportService getPlayerServiceFromSession(HttpSession session) throws
// SportException {
// SportService playerService = (SportService) session.getAttribute("player");
// return playerService;
// }
//
// @ExceptionHandler
// @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
// public Message handleException(Exception e) {
// String message = e.getMessage();
// if (message == null) {
// message = "Error while invoking request - please login ";
// }
// return new Message(message);
// }
//
// @RequestMapping(method = RequestMethod.POST, path = "/api/admin/bookies",
// produces = MediaType.APPLICATION_JSON_VALUE, consumes =
// MediaType.APPLICATION_JSON_VALUE)
// public Bookie addBookie(@RequestBody Bookie bookieToAdd, HttpSession session)
// throws SportException {
// getAdminServiceFromSession(session).addBookie(bookieToAdd);
// return bookieToAdd;
// }
//
// @RequestMapping(method = RequestMethod.PUT, path =
// "/api/admin/bookies/{bookieId}", consumes = MediaType.APPLICATION_JSON_VALUE)
// public Bookie updateBookie(@PathVariable("bookieId") Long bookieId,
// @RequestBody Bookie bookieToUpdate,
// HttpSession session) throws SportException {
// getAdminServiceFromSession(session).updateBookie(bookieId, bookieToUpdate);
// return bookieToUpdate;
// }
//
// @RequestMapping(method = RequestMethod.DELETE, path =
// "/api/admin/bookies/{bookieId}", consumes = MediaType.APPLICATION_JSON_VALUE)
// public void removeBookie(@PathVariable("bookieId") Long bookieId, HttpSession
// session) throws SportException {
// getAdminServiceFromSession(session).removeBookie(bookieId);
// }
//
// @RequestMapping(method = RequestMethod.GET, path =
// "/api/admin/bookies/{bookieId}", produces = MediaType.APPLICATION_JSON_VALUE)
// public Bookie getBookie(@PathVariable("bookieId") Long bookieId, HttpSession
// session) throws SportException {
// return getAdminServiceFromSession(session).getBookie(bookieId);
// }
//
// @RequestMapping(method = RequestMethod.GET, path = "/api/admin/bookies",
// produces = MediaType.APPLICATION_JSON_VALUE)
// public Bookie[] getAllBookies(HttpSession session) throws SportException {
// Bookie[] allBookies =
// getAdminServiceFromSession(session).getAllBookies().toArray(new Bookie[0]);
// return allBookies;
// }
//
// @RequestMapping(method = RequestMethod.POST, path = "/api/admin/players",
// produces = MediaType.APPLICATION_JSON_VALUE, consumes =
// MediaType.APPLICATION_JSON_VALUE)
// public Player addPlayer(@RequestBody Player playerToAdd, HttpSession session)
// throws SportException {
// getAdminServiceFromSession(session).addPlayer(playerToAdd);
// return playerToAdd;
// }
//
// @RequestMapping(method = RequestMethod.PUT, path =
// "/api/admin/players/{playerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
// public Player updatePlayer(@PathVariable("playerId") Long playerId,
// @RequestBody Player playerToUpdate,
// HttpSession session) throws SportException {
// getAdminServiceFromSession(session).updatePlayer(playerId, playerToUpdate);
// return playerToUpdate;
// }
//
// @RequestMapping(method = RequestMethod.DELETE, path =
// "/api/admin/players/{playerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
// public void removePlayer(@PathVariable("playerId") Long playerId, HttpSession
// session) throws SportException {
// getAdminServiceFromSession(session).removePlayer(playerId);
// }
//
// @RequestMapping(method = RequestMethod.GET, path =
// "/api/admin/players/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
// public Player getPlayer(@PathVariable("playerId") Long playerId, HttpSession
// session) throws SportException {
// return getAdminServiceFromSession(session).getPlayer(playerId);
// }
//
// @RequestMapping(method = RequestMethod.GET, path = "/api/admin/players",
// produces = MediaType.APPLICATION_JSON_VALUE)
// public Player[] getAllPlayers(HttpSession session) throws SportException {
// Player[] allPlayers =
// getAdminServiceFromSession(session).getAllPlayers().toArray(new Player[0]);
// return allPlayers;
// }
//
// @RequestMapping(method = RequestMethod.POST, path = "/api/bookie/createbet",
// produces = MediaType.APPLICATION_JSON_VALUE, consumes =
// MediaType.APPLICATION_JSON_VALUE)
// public Bet addBet(@RequestBody Bet betToAdd, HttpSession session) throws
// SportException {
// getBookieServiceFromSession(session).addBet(betToAdd);
// return betToAdd;
// }
//
// @RequestMapping(method = RequestMethod.PUT, path =
// "/api/bookie/updatebet/{betId}", consumes = MediaType.APPLICATION_JSON_VALUE)
// public Bet updateBet(@PathVariable("betId") Long betId, @RequestBody Bet
// betToUpdate, HttpSession session)
// throws SportException {
// getBookieServiceFromSession(session).updateBet(betId, betToUpdate);
// return betToUpdate;
// }
//
// @RequestMapping(method = RequestMethod.DELETE, path =
// "/api/bookie/removebet/{betId}", consumes = MediaType.APPLICATION_JSON_VALUE)
// public void removeBet(@PathVariable("betId") Long betId, HttpSession session)
// throws SportException {
// getBookieServiceFromSession(session).removeBet(betId);
// }
//
// @RequestMapping(method = RequestMethod.GET, path =
// "/api/bookie/getbet/{betId}", produces = MediaType.APPLICATION_JSON_VALUE)
// public Bet getBet(@PathVariable Long betId, HttpSession session) throws
// SportException {
// return getBookieServiceFromSession(session).getBet(betId);
// }
//
// @RequestMapping(method = RequestMethod.GET, path =
// "/api/bookie/getbookiebets", produces = MediaType.APPLICATION_JSON_VALUE)
// public Bet[] getBookieBets(HttpSession session) throws SportException {
// Bet[] bookieBets =
// getBookieServiceFromSession(session).getBookieBets().toArray(new Bet[0]);
// return bookieBets;
// }
//
// @RequestMapping(method = RequestMethod.POST, path = "api/player/takebet")
// public Bet takeBet(@RequestBody Bet betToTake, HttpSession session) throws
// SportException {
// getPlayerServiceFromSession(session).takeBet(betToTake);
// return betToTake;
// }
//
// @RequestMapping(method = RequestMethod.GET, path =
// "/api/player/getplayerbets", produces = MediaType.APPLICATION_JSON_VALUE)
// public Bet[] getPlayerBets(HttpSession session) throws SportException {
// Bet[] playerBets =
// getPlayerServiceFromSession(session).getPlayerBets().toArray(new Bet[0]);
// return playerBets;
// }
//
// @RequestMapping(method = RequestMethod.GET, path =
// "/api/admin/login/{userName}/{password}", produces =
// MediaType.APPLICATION_JSON_VALUE)
// public Message adminLogin(@PathVariable("userName") String userName,
// @PathVariable("password") String password,
// HttpSession session) throws SportException {
// sportService.adminLogin(ClientType.ADMINSPORT, userName, password);
// if (sportService.adminLogin(ClientType.ADMINSPORT, userName, password) !=
// null && userName != null
// && !userName.trim().isEmpty() && password != null &&
// !password.trim().isEmpty()) {
// session.setAttribute("admin", sportService);
// return new Message("Admin Sport : Session ID: " + session.getId());
// } else {
// throw new SportException("Admin Login Failed");
// }
// }
//
// @RequestMapping(method = RequestMethod.GET, path = "/api/admin/logout")
// @ResponseStatus(HttpStatus.NO_CONTENT)
// public void adminLogout(HttpSession session) {
// session.invalidate();
// }
//
// @RequestMapping(method = RequestMethod.GET, path =
// "/api/bookie/login/{userName}/{password}", produces =
// MediaType.APPLICATION_JSON_VALUE)
// public Message bookieUserLogin(@PathVariable("userName") String userName,
// @PathVariable("password") String password,
// HttpSession session) throws SportException {
// sportService.bookieLogin(ClientType.BOOKIE, userName, password);
// if ((sportService.bookieLogin(ClientType.BOOKIE, userName, password)) != null
// && userName != null
// && !userName.trim().isEmpty() && password != null &&
// !password.trim().isEmpty()) {
// session.setAttribute("bookie", sportService);
// return new Message("Bookie : Session ID: " + session.getId());
// } else {
// throw new SportException("Bookie Login Failed");
// }
// }
//
// @RequestMapping(method = RequestMethod.GET, path = "/api/bookie/logout")
// @ResponseStatus(HttpStatus.NO_CONTENT)
// public void bookieLogout(HttpSession session) {
// session.invalidate();
// }
//
// @RequestMapping(method = RequestMethod.GET, path =
// "/api/player/login/{userName}/{password}", produces =
// MediaType.APPLICATION_JSON_VALUE)
// public Message playerUserLogin(@PathVariable("userName") String userName,
// @PathVariable("password") String password,
// HttpSession session) throws SportException {
// sportService.playerLogin(ClientType.PLAYER, userName, password);
// if ((sportService.playerLogin(ClientType.PLAYER, userName, password)) != null
// && userName != null
// && !userName.trim().isEmpty() && password != null &&
// !password.trim().isEmpty()) {
// session.setAttribute("player", sportService);
// return new Message("Player: Session ID: " + session.getId());
// } else {
// throw new SportException("Player Login Failed");
// }
// }
//
// @RequestMapping(method = RequestMethod.GET, path = "/api/player/logout")
// @ResponseStatus(HttpStatus.NO_CONTENT)
// public void playerLogout(HttpSession session) throws SportException {
// session.invalidate();
// }
//
// @RequestMapping(method = RequestMethod.GET, path = "/api/admin/bets",
// produces = MediaType.APPLICATION_JSON_VALUE)
// public Bet[] getAllBets(HttpSession session) throws SportException {
// Bet[] allBets = getAdminServiceFromSession(session).getAllBets().toArray(new
// Bet[0]);
// return allBets;
// }
// }
