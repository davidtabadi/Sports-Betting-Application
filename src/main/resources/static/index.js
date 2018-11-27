angular.module('sportsBettingApp', ['ngResource'])
	.controller('SportsController', function($resource) {
	
//		var myApp = angular.module('sportsBettingApp', []);
		
		var c = this;
		
//		3 Login Resources
		
		c.adminLoginResource = $resource("api/admin/login/:username/:password", {
			"username":"@username", "password":"@password"});
	
		c.bookieLoginResource = $resource("api/bookie/login/:username/:password", {
			"username":"@username", "password":"@password"});
		
		c.playerLoginResource = $resource("api/player/login/:username/:password", {
			"username":"@username", "password":"@password"});
		
		c.loginFields = {
				"username" : "",
				"password" : "",
				"clientType" : ""		
		};
		
		c.bookieFields = {
				"bookieId" : null,
				"bookieName" : "",
				"bookiePassword" : "",
				"bookieEmail" : ""
		};
		
		c.playerFields = {
				"playerId" : null,
				"playerName" : "",
				"playerPassword" : "",
				"playerEmail" : ""	
		};
		
		c.betFields = {
				"betId" : null,
				"betTitle"	:	"",
				"sport" : "",	
				"eventDate" : "",
				"betWager" : "",
				"betOdds" : "",
				"betImage" : "",
				"betReceipt" : ""
		};
		
		
		c.isLoggedIn = false;
		c.adminDiv = false;
		c.bookieDiv = false;
		c.playerDiv = false;
		
		// Admin DIV

		c.createBookieDiv = false;			
		c.removeBookieyDiv = false;			
		c.updateBookieDiv = false;			
		c.getBookieDiv = false;			
		c.getAllBookiesDiv = false;		
		c.createPlayerDiv = false;		
		c.removePlayerDiv = false;		
		c.updatePlayerDiv = false;		
		c.getPlayerDiv = false;			
		c.getAllPlayersDiv = false;		

		// Bookie DIV

		c.createBetDiv = false;			
		c.removeBetDiv = false;			
		c.updateBetDiv = false;			
		c.getThisBookieDiv = false;		
		c.getBetByIdDiv = false;			
		c.getAllBetsDiv = false;			

		// Player DIV

		c.placeBetDiv = false;				
		c.getAllPlayerBetsDiv = false;		

		// Table DIV

		c.bookieTableDiv = false;				
		c.updateBookieTableDiv = false;		
		c.getBookieTableDiv = false;			
		c.getAllBookiesTableDiv = false;		
		c.playerTableDiv = false;				
		c.updatePlayerTableDiv = false;		
		c.getPlayerTableDiv = false;			
		c.getAllPlayersTableDiv = false;		
		
		c.createBetTableDiv = false;			
		c.updateBetTableDiv = false;			
		c.getMyBookieTableDiv = false;						
		c.getBetByIdTableDiv = false;		
		c.getAllBetsTableDiv = false;		
		
		c.getAllSystemBetsTableDiv = false;		
		c.placeBetSuccessDiv = false;			
		c.getPlayerBetsTableDiv = false;	
		
		
		// Exceptions DIV
		
		c.loginExceptionDiv = false;
		
		//Admin
		c.bookieExceptionDiv = false;				
		c.removeBookieSuccessDiv = false;			
		c.removeBookieExceptionDiv = false;		
		c.showAllBookiesExceptionDiv = false;		
		c.getBookieExceptionDiv = false;			
		c.getAllBookiesExceptionDiv = false;		
		c.playerExceptionDiv = false;				
		c.removePlayerSuccessDiv = false;			
		c.removePlayerExceptionDiv = false;		
		c.showAllPlayersExceptionDiv = false;		
		c.updatePlayerExceptionDiv = false;		
		c.getPlayerExceptionDiv = false;			
		c.getAllPlayersExceptionDiv = false;		
		
		//Bookie
		c.createBetExceptionDiv = false;			
		c.removeBetSuccessDiv = false;			
		c.getAllMyBookieBetsExceptionDiv = false	
		c.removeBetExceptionDiv = false;			
		c.updateBetExceptionDiv = false;			
		c.getMyBookieExceptionDiv = false;			
		c.getBetByIdExceptionDiv = false;		
		c.getAllBetsExceptionDiv = false;		
		
		//Player
		c.getAllSystemBetsExceptionDiv = false;		
		c.getPlayerBetsExceptionDiv = false;	
		
		c.infoMessage = "";
		c.errorMessage = "";

		c.info = function(message) {
			c.infoMessage = message;
			c.errorMessage = "";
		}

		c.error = function(message) {
			c.infoMessage = "";
			c.errorMessage = message;
		}

		c.handleError = function(response, defaultMessage) {
			if (response.data != null && response.data.message != null) {
				c.error("Error: " + response.data.message);
			} else {
				c.error(defaultMessage);
			}
		}

		c.submitLoginForm = function() {
			if (c.loginFields.clientType == "ADMINSPORT") {
				c.adminLoginResource.get(c.loginFields,
						function(response) {
							c.adminDiv = true;				
							c.loginDiv = false;
							c.bookieDiv = false;			
							c.playerDiv = false;			
							
							c.createBookieDiv = false;		
							c.removeBookieDiv = false;		
							c.updateBookieDiv = false;		
							c.getBookieDiv = false;		
							c.getAllBookiesDiv = false;	
							c.createPlayerDiv = false;	
							c.removePlayerDiv = false;	
							c.updatePlayerDiv = false;	
							c.getPlayerDiv = false;		
							c.getAllPlayersDiv = false;	
						}, function(response, defaultMessage) {
							c.handleError(response, "Login Failed");
							c.loginExceptionDiv = true;
						});
			} else if (c.loginFields.clientType == "BOOKIE") {
				c.bookieLoginResource.get(
						c.loginFields, function(response) {
							c.loginFields.username;
							c.bookieDiv = true;			
							c.loginDiv = false;
							c.adminDiv = false;				
							c.playerDiv = false;			
							
							c.createBetDiv = false;		
							c.removeBetDiv = false;		
							c.updateBetDiv = false;		
							c.getThisBookieDiv = false;	
							c.getBetByIdDiv = false;		
							c.getAllBetsDiv = false;		
						}, function(response, defaultMessage) {
							c.handleError(response, "Login Failed");
							c.loginExceptionDiv = true;
						});
			} else if (c.loginFields.clientType == "PLAYER") {
				c.playerLoginResource.get(
						c.loginFields, function(response) {
							c.loginFields.username;
							c.playerDiv = true;				
							c.loginDiv = false;
							c.adminDiv = false;					
							c.bookieDiv = false;				
							
							c.placeBetDiv = false;				
							c.getAllSystemBetsTableDiv = false;		
							c.placeBetSuccessDiv = false;			
							c.getPlayerBetsDiv = false;		
						}, function(response, defaultMessage) {
							c.handleError(response, "Login Failed");
							c.loginExceptionDiv = true;
						});
			}
		}

		c.adminLogoutResource = $resource("api/admin/logout");

		c.adminLogoutButton = function() {
			c.adminLogoutResource.get(function(response) {
				c.loginFields = {
					"username" : "",
					"password" : "",
					"clientType" : ""
				};
				c.loginDiv = true;
				c.loginExceptionDiv = false;
				c.playerDiv = false;				
				c.adminDiv = false;					
				c.bookieDiv = false;				
			});
		}

		c.bookieLogoutResource = $resource("api/bookie/logout");

		c.bookieLogoutButton = function() {
			c.bookieLogoutResource.get(function(response) {
				c.loginFields = {
					"username" : "",
					"password" : "",
					"clientType" : ""
				};
				c.loginDiv = true;
				c.loginExceptionDiv = false;
				c.playerDiv = false;				
				c.adminDiv = false;					
				c.bookieDiv = false;				
			});
		}

		c.playerLogoutResource = $resource("api/player/logout");

		c.playerLogoutButton = function() {
			c.playerLogoutResource.get(function(response) {
				c.loginFields = {
					"username" : "",
					"password" : "",
					"clientType" : ""
				};
				c.loginDiv = true;
				c.loginExceptionDiv = false;
				c.playerDiv = false;				
				c.adminDiv = false;					
				c.bookieDiv = false;				
			});
		}

//=============================================================================================================================
		
		//Admin resource for bookies management
		c.adminBookiesResource = $resource("api/admin/bookies/:id",{'id':'@id'},{
		"updateBookie": {method:"PUT"}
		});
	
		c.adminAllBookiesResource = $resource("api/admin/bookies");
		
		//Admin recourse for players management
		c.adminPlayersResource = $resource("api/admin/players/:id",{'id':'@id'},{
		"updatePlayer": {method:"PUT"}
		});
	
		c.adminAllPlayersResource = $resource("api/admin/players");
		
		c.createBookieForm = function() {
			c.adminBookiesResource.save(c.bookieFields, function(bookie) {
				c.bookieFields.id = bookie.bookieId;
				c.bookieFields.bookieName = bookie.bookieName;
				c.bookieFields.bookiePassword = bookie.bookiePassword;
				c.bookieFields.bookieemail = bookie.bookieEmail;
				c.bookieTableDiv = true;			
				c.bookieExceptionDiv = false;		
			}, function(response, defaultMessage) {
				c.handleError(response, "Bookie Creation Failed");
				c.bookieExceptionDiv = true;		
				c.bookieTableDiv = false;			
			});
		}
		
		c.showAllBookies = [];
		
		c.showAllBookiesForm = function() {
			c.showAllBookies = c.adminAllBookiesResource.query(c.bookieFields, function() {
				if (c.showAllBookies.length > 0) {
					c.showAllBookies.forEach(function(bookie){
						c.showAllBookiesTableDiv = true;								
						c.showAllBookiesExceptionDiv = false;			
						c.removeBookieSuccessDiv = false;				
						c.removeBookieExceptionDiv = false;			
						})
				} else {
					c.error("No bookies were found");
					c.showAllBookiesExceptionDiv = true;				
					c.showAllBookiesTableDiv = false;					
					c.removeBookieSuccessDiv = false;					
					c.removeBookieExceptionDiv = false;				
				}
				}, function() {
					c.error("Request could not be completed");
					c.showAllBookiesExceptionDiv = true;				
					c.showAllBookiesTableDiv = false;					
					c.removeBookieSuccessDiv = false;					
					c.removeBookieExceptionDiv = false;				
				});
		}
		
		c.removeBookieForm = function(bookie) {
			c.adminBookiesResource.delete(bookie, function() {
				c.info("Operation completed successfully");
				c.removeBookieSuccessDiv = true;						
				c.removeBookieExceptionDiv = false;					
				c.showAllBookiesTableDiv = false;						
				c.showAllBookiesExceptionDiv = false;					
			}, function(response, defaultMessage) {
				c.handleError(response, "Request could not be completed");
				c.removeBookieExceptionDiv = true;						
				c.removeBookieSuccessDiv = false;						
				c.showAllBookiesTableDiv = false;						
				c.showAllBookiesExceptionDiv = false;					
			});
		}
			
		c.updateBookieForm = function() {
			c.adminBookiesResource.get(c.bookieFields, function(bookie) {
				if (c.bookieFields.bookiePassword != "") {
					bookie.bookiePassword = c.bookieFields.bookiePassword;
				}
				if (c.bookieFields.bookieEmail != "") {
					bookie.bookieEmail = c.bookieFields.bookieEmail;
				}
				bookie.$updateBookie(bookie, function() {
					c.bookieFields.bookieId = bookie.bookieId;
					c.bookieFields.bookieName = bookie.bookieName;
					c.bookieFields.bookiePassword = bookie.bookiePassword;
					c.bookieFields.bookieEmail = bookie.bookieEmail;
					c.updateBookieTableDiv = true;					
					c.updateBookieExceptionDiv = false;			
				})
			},function(response, defaultMessage) {
				c.handleError(response, "Request could not be completed");
				c.updateBookieExceptionDiv = true;					
				c.updateBookieTableDiv = false;					
			});
		}
			
		c.getBookieForm = function() {
			c.adminBookiesResource.get(c.bookieFields, function(bookie) {
				c.bookieFields = bookie; 
				c.getBookieTableDiv = true;				
				c.getBookieExceptionDiv = false;			
			}, function(response, defaultMessage) {
				c.handleError(response, "Request could not be completed");
				c.getBookieExceptionDiv = true;			
				c.getBookieTableDiv = false;				
			});
		}

		c.allBookies = [];
		
		c.getAllBookiesForm = function() {
			c.allBookies = c.adminAllBookiesResource.query(c.bookieFields, function() {
				if (c.allBookies.length > 0) {
					c.allBookies.forEach(function(bookie){
						c.getAllBookiesTableDiv = true;		
						c.getAllBookiesExceptionDiv = false;	
						})
				} else {
					c.error("No bookies were found");
					c.getAllBookiesExceptionDiv = true;	
					c.getAllBookiesTableDiv = false;		
				}
				}, function(response, defaultMessage) {
					c.handleError(response, "Request could not be completed");
					c.getAllBookiesExceptionDiv = true;	
					c.getAllBookiesTableDiv = false;		
				});
		}
		

		c.createPlayerForm = function() {
			c.adminPlayersResource.save(c.playerFields, function(player) {
				c.playerFields.playerId = player.playerId;
				c.playerFields.playerName = player.playerName;
				c.playerFields.playerPasword = player.playerPassword;
				c.playerFields.playerEmail = player.playerEmail;
				c.playerTableDiv = true;		
				c.playerExceptionDiv = false;	
			}, function(response, defaultMessage) {
				c.handleError(response, "Player Creation Failed");
				c.playerExceptionDiv = true;	
				c.playerTableDiv = false;		
			});
		}
		
		c.showAllPlayers = [];
		
		c.showAllPlayersForm = function() {
			c.showAllPlayers = c.adminAllPlayersResource.query(c.playerFields, function() {
				if (c.showAllPlayers.length > 0) {
					c.showAllPlayers.forEach(function(player){
						c.showAllPlayersTableDiv = true;		
						c.showAllPlayersExceptionDiv = false;	
						c.removePlayerSuccessDiv = false;		
						c.removePlayerExceptionDiv = false;	
						})
				} else {
					c.error("No players were found");
					c.showAllPlayersExceptionDiv = true;	
					c.showAllPlayersTableDiv = false;		
					c.removePlayerSuccessDiv = false;		
					c.removePlayerExceptionDiv = false;	
				}
				}, function(response, defaultMessage) {
					c.handleError(response, "Request could not be completed");
					c.showAllPlayersExceptionDiv = true;	
					c.showAllPlayersTableDiv = false;		
					c.removePlayerSuccessDiv = false;		
					c.removePlayerExceptionDiv = false;	
				});
		}
		
		c.removePlayerForm = function(player) {
			c.adminPlayersResource.delete(player, function() {
				c.info("Operation completed successfully");
				c.removePlayerSuccessDiv = true;		
				c.removePlayerExceptionDiv = false;	
				c.showAllPlayersTableDiv = false;		
				c.showAllPlayersExceptionDiv = false;	
			}, function(response, defaultMessage) {
				c.handleError(response, "Request could not be completed");
				c.removePlayerExceptionDiv = true;	
				c.removePlayerSuccessDiv = false;		
				c.showAllPlayersTableDiv = false;		
				c.showAllPlayersExceptionDiv = false;	
			});
		}
		
		c.updatePlayerrForm = function() {
			c.adminPlayersResource.get(c.playerFields, function(player) {
				if (c.playerFields.playerPassword != "") {
					player.playerPassword = c.playerFields.playerPassword;
				}
				updatePlayer = player;
				updatePlayer.$updatePlayer(player, function() {
					c.playerFields.playerId = player.playerId;
					c.playerFields.playerName = player.playerName;
					c.playerFields.playerPassword = player.playerPassword;
					c.playerFields.playerEmail = player.playerEmail;
					c.updatePlayerTableDiv = true;		
					c.updatePlayerExceptionDiv = false;	
				})
			},function(response, defaultMessage) {
				c.handleError(response, "Request could not be completed");
				c.updatePlayerExceptionDiv = true;	
				c.updatePlayerTableDiv = false;		
			});
		}
		
		
		c.getPlayerForm = function() {
			c.adminPlayersResource.get(c.playerFields, function(player) {
				c.playerrFields = player; 
				c.getPlayerTableDiv = true;		
				c.getPlayerExceptionDiv = false;	
			}, function(response, defaultMessage) {
				c.handleError(response, "Request could not be completed");
				c.getPlayerTableDiv = false;		
				c.getPlayerExceptionDiv = true;	
			});
		}
		
		
		c.allPlayers = [];
		
		c.getAllPlayersForm = function() {
			c.allPlayers = c.adminAllPlayersResource.query(c.playerFields, function() {
				if (c.allPlayers.length > 0) {
					c.allPlayers.forEach(function(player){
						c.getAllPlayersTableDiv = true;		
						c.getAllPlayersExceptionDiv = false;	
						})
				} else {
					c.error("No Players were found");
					c.getAllPlayersExceptionDiv = true;	
					c.getAllPlayersTableDiv = false;		
				}
				}, function(response, defaultMessage) {
					c.handleError(response, "Request could not be completed");
					c.getAllPlayersExceptionDiv = true;	
					c.getAllPlayersTableDiv = false;		
				});
		}

//=============================================================================================================================
							
		
		//Bookie recourse for Bet by ID management
		c.bookieResourceID = $resource("api/bookie/getbet/:id",{'id':'@id'},{
		"updateBet": {method:"PUT"}
		});
		
		//Bookie recourse for Bets management
		c.bookieResource = $resource("api/bookie/getbookiebets");

		
		c.createBetForm = function() {
			c.bookieResourceID.save(c.betFields, function(bet) {
				c.betFields.betId = bet.betId;
				c.betFields.BetTitle = bet.betTitle;
				c.betFields.sport = bet.sport;
				c.betFields.eventDate = bet.eventDate;
				c.betFields.betWager = bet.betWager;
				c.betFields.betOdds = bet.betOdds;
				c.betFields.betImage = bet.betImage;
				c.betFields.betReceipt = bet.betReceipt;
				c.createBetTableDiv = true;		
				c.createBetExceptionDiv = false;	
			}, function(response, defaultMessage) {
				c.handleError(response, "Request could not be completed");
				c.createBetExceptionDiv = true;	
				c.createBetTableDiv = false;		
			});
		}
		
		
		c.showAllBetsResource = $resource("api/admin/bets");
		
		c.showAllBets = [];
		
		c.showBookieBetsForm = function() {
			c.showAllBets = c.showAllBetsResource.query(c.betFields, function() {
				if (c.showAllBets.length > 0) {
					c.showAllBets.forEach(function(coupon){
						c.getBookieBetsTableDiv = true;		
						c.getBookieBetsExceptionDiv = false;	
						c.removeBetSuccessDiv = false;			
						c.removeBetExceptionDiv = false;			
						})
				} else {
					c.error("No bets were found");
					c.getBookieBetsExceptionDiv = true;	
					c.getBookieBetsTableDiv = false;		
					c.removeBetSuccessDiv = false;			
					c.removeBetExceptionDiv = false;			
				}
				}, function(response, defaultMessage) {
					c.handleError(response, "Request could not be completed");
					c.getBookieBetsExceptionDiv = true;	
					c.getBookieBetsTableDiv = false;		
					c.removeBetSuccessDiv = false;			
					c.removeBetExceptionDiv = false;			
				});
		}
		
		c.removeBetForm = function(bet) {
			c.bookieResourceID.delete(bet, function() {
				c.info("Operation completed successfully");
				c.removeBetSuccessDiv = true;			
				c.removeBetExceptionDiv = false;			
				c.getBookieBetsTableDiv = false;		
				c.getBookieBetsExceptionDiv = false;	
			}, function(response, defaultMessage) {
				c.handleError(response, "Request could not be completed");
				c.removeBetExceptionDiv = true;			
				c.removeBetSuccessDiv = false;			
				c.getBookieBetsTableDiv = false;		
				c.getBookieBetsExceptionDiv = false;	
			});
		}
		
		c.updateBetForm = function() {
			c.bookieResourceID.get(c.betFields, function(bet) {
				if (c.betFields.betWager != "") {
					bet.betWager = c.betFields.betWager;
				}
				if (c.betFields.betOdds != "") {
					bet.betOdds = c.betFields.betOdds;
				}
				if (c.betFields.betReceipt != "") {
					bet.betReceipt = c.betFields.betReceipt;
				}
				updateBet = bet;
				updateBet.$updateBet(bet, function() {
					c.betFields.betId = bet.betId;
					c.betFields.BetTitle = bet.betTitle;
					c.betFields.sport = bet.sport;
					c.betFields.eventDate = bet.eventDate;
					c.betFields.betWager = bet.betWager;
					c.betFields.betOdds = bet.betOdds;
					c.betFields.betImage = bet.betImage;
					c.betFields.betReceipt = bet.betReceipt;
					c.updateBetTableDiv = true;		
					c.updateBetExceptionDiv = false;	
				})
				}, function(response, defaultMessage) {
					c.handleError(response, "Request could not be completed");
					c.updateBetExceptionDiv = true;	
					c.updateBetTableDiv = false;		
				});
			}
		

		c.getMyBookieForm = function() {
			c.bookieResource.get(c.bookieFields,
				function(bookie) {
					c.bookieFields = bookie; 
					c.getMyBookieTableDiv = true;		
					c.getMyBookieExceptionDiv = false;	
			}, function(response, defaultMessage) {
					c.handleError(response, "Request could not be completed");
					c.getMyBookieExceptionDiv = true;	
					c.getMyBookieTableDiv = false;		
			});
		}
		
		
		c.getBetByIdForm = function() {
			c.bookieResourceID.get(c.betFields,	function(bet) {
				if (c.betFields.betId == null) {
					c.error("Request could not be completed");
					c.getBetByIdExceptionDiv = true;		
					c.getBetByIdTableDiv = false;		
				} else {
					c.betFields = bet;
					c.getBetByIdTableDiv = true;			
					c.getBetByIdExceptionDiv = false;	
				}
		}), function(response, defaultMessage) {
				c.handleError(response, "Request could not be completed");
				c.getBetByIdExceptionDiv = true;		
				c.getBetByIdTableDiv = false;		
			}
		}

		c.getAllBetsResource = $resource("api/admin/bets");
		
		c.allBets = [];
		
		c.getAllBetsForm = function() {
			c.allBets = c.getAllBetsResource.query(c.betFields, function() {
				if (c.allBets.length > 0) {
					c.allBets.forEach(function(bet){
						c.getAllBetsTableDiv = true;			
						c.getAllBetsExceptionDiv = false;	
						})
				} else {
					c.error("No bets were found");
					c.getAllBetsExceptionDiv = true;		
					c.getAllBetsTableDiv = false;		
				}
				}, function(response, defaultMessage) {
					c.handleError(response, "Request could not be completed");
					c.getAllBetsExceptionDiv = true;		
					c.getAllBetsTableDiv = false;		
				});
		}
		

//=============================================================================================================================
		
		c.placeBetResource = $resource("api/player/getplayerbets");
		
		c.allSystemBets = [];
		
		//Show all bets
		c.showAllBetsForm = function() {
			c.allSystemBets = c.placeBetResource.query(c.betFields, function() {
				if (c.allSystemBets.length > 0) {
					c.allSystemBets.forEach(function(bet){
						c.getAllSystemBetsTableDiv = true;		
						c.getAllSystemBetsExceptionDiv = false;	
						c.placeBetSuccessDiv = false;;		
						})
				} else {
					c.error("No bets available for purchase were found");
					c.getAllSystemBetsExceptionDiv = true;	
					c.getAllSystemBetsTableDiv = false;		
					c.placeBetSuccessDiv = false;			
				}
				}, function(response, defaultMessage) {
					c.handleError(response, "Request could not be completed");
					c.getAllSystemBetsExceptionDiv = true;	
					c.getAllSystemBetsTableDiv = false;		
					c.placeBetSuccessDiv = false;			
				});
		}
		
		c.submitPurchaseResource = $resource("api/player/takebet");
		
		// Place A Bet
		c.submitPurchaseForm = function(bet) {
			c.submitPurchaseResource.save(bet, function() {
				c.info("Bet Placed Successfully");
				c.placeBetSuccessDiv = true;			
				c.getAllSystemBetsTableDiv = false;		
				c.getAllSystemBetsExceptionDiv = false;	
			}, function(response, defaultMessage) {
				c.handleError(response, "Bet Placing Failed");
				c.placeBetSuccessDiv = false;			
				c.getAllSystemBetsTableDiv = false;		
				c.getAllSystemBetsExceptionDiv = true;	
			});
		}
		
		c.allPurchasedBetsResource = $resource("api/player/getplayerbets");
		
		c.allPurchasedBets = [];
		
		c.getAllPurchasedBetsForm = function() {
			c.allPurchasedBets = c.allPurchasedBetsResource.query(c.betFields, function() {
				if (c.allPurchasedBets.length > 0) {
					c.allPurchasedBets.forEach(function(bet){
						c.getAllPurchasedBetsTableDiv = true;		
						c.getAllPurchasedBetsExceptionDiv = false;	
						})
				} else {
					c.error("No Bets were found");
					c.getAllPurchasedBetsExceptionDiv = true;	
					c.getAllPurchasedBetsTableDiv = false;		
				}
				}, function(response, defaultMessage) {
					c.handleError(response, "Request could not be completed");
					c.getAllPurchasedBetsExceptionDiv = true;	
					c.getAllPurchasedBetsTableDiv = false;		
				});
		}
		

//=============================================================================================================================					
		

		c.createBookieButton = function() {
			c.createBookieDiv = true;			
			c.removeBookieDiv = false;			
			c.updateBookieDiv = false;			
			c.getBookieDiv = false;			
			c.getAllBookiesDiv = false;		
			c.createPlayerDiv = false;		
			c.removePlayerDiv = false;		
			c.updatePlayerDiv = false;		
			c.getPlayerDiv = false;			
			c.getAllPlayersDiv = false;		
			c.bookieFields = {
				"bookieName" : "",
				"bookiePassword" : "",
				"bookieEmail" : ""
			};
			c.bookieTableDiv = false;			
			c.bookieExceptionDiv = false;		
		}
		
		c.createBookieResetButton = function() {
			c.bookieFields = {
					"bookieName" : "",
					"bookiePassword" : "",
					"bookieEmail" : "" 
			};
			c.bookieTableDiv = false;			
			c.bookieExceptionDiv = false;		
		}
		
		c.removeBookieButton = function() {
			c.createBookieDiv = false;			
			c.removeBookieDiv = true;			
			c.updateBookieDiv = false;			
			c.getBookieDiv = false;			
			c.getAllBookiesDiv = false;		
			c.createPlayerDiv = false;		
			c.removePlayerDiv = false;		
			c.updatePlayerDiv = false;		
			c.getPlayerDiv = false;			
			c.getAllPlayersDiv = false;		
			c.bookieFields = {
					"bookieId" : null
			};
			c.removeBookieExceptionDiv = false;		
			c.removeBookieSuccessDiv = false;			
			c.showAllBookiesTableDiv = false;			
			c.showAllBookiesExceptionDiv = false;		
		}

		c.updateBookieButton = function() {
			c.createBookieDiv = false;			
			c.removeBookieDiv = false;			
			c.updateBookieDiv = true;			
			c.getBookieDiv = false;			
			c.getAllBookiesDiv = false;		
			c.createPlayerDiv = false;		
			c.removePlayerDiv = false;		
			c.updatePlayerDiv = false;		
			c.getPlayerDiv = false;			
			c.getAllPlayersDiv = false;		
			c.bookieFields = {
					"bookieName" : "",
					"bookiePassword" : "",
					"bookieEmail" : "" 
				};
			c.updateBookieTableDiv = false;			
			c.updateBookieExceptionDiv = false;		
		}
		
		c.updateBookieResetButton = function() {
			c.bookieFields = {
					"bookieName" : "",
					"bookiePassword" : "",
					"bookieEmail" : "" 
				};
			c.updateBookieTableDiv = false;			
			c.updateBookieExceptionDiv = false;		
		}

		c.getBookieButton = function() {
			c.createBookieDiv = false;			
			c.removeBookieDiv = false;			
			c.updateBookieDiv = false;			
			c.getBookieDiv = true;				
			c.getAllBookiesDiv = false;		
			c.createPlayerDiv = false;		
			c.removePlayerDiv = false;		
			c.updatePlayerDiv = false;		
			c.getPlayerDiv = false;			
			c.getAllPlayersDiv = false;		
			c.bookieFields = {
					"bookieId" : null
				};
			c.getBookieTableDiv = false;		
			c.getBookieExceptionDiv = false;	
		}
		
		c.getBookieResetButton = function() {
			c.bookieFields = {
					"bookieId" : null
				};
			c.getBookieTableDiv = false;		
			c.getBookieExceptionDiv = false;	
		}

		c.getAllBookiesButton = function() {
			c.createBookieDiv = false;				
			c.removeBookieDiv = false;				
			c.updateBookieDiv = false;				
			c.getBookieDiv = false;				
			c.getAllBookiesDiv = true;			
			c.createPlayerDiv = false;			
			c.removePlayerrDiv = false;			
			c.updatePlayerrDiv = false;			
			c.getPlayerrDiv = false;				
			c.getAllPlayersDiv = false;			
			c.getAllBookiessTableDiv = false;		
			c.getAllBookiesExceptionDiv = false;	
		}

		c.createPlayerButton = function() {
			c.createBookieDiv = false;				
			c.removeBookieDiv = false;				
			c.updateBookieDiv = false;				
			c.getBookieDiv = false;				
			c.getAllBookiesDiv = false;			
			c.createPlayerDiv = true;				
			c.removePlayerDiv = false;			
			c.updatePlayerDiv = false;			
			c.getCustomerDiv = false;				
			c.getAllPlayersDiv = false;			
			c.playerFields = {
					"playerName" : "",
					"playerPassword" : "",
					"playerEmail" :""
			};
			c.playerTableDiv = false;			
			c.playerExceptionDiv = false;		
		}
		
		c.createPlayerResetButton = function() {
			c.playerFields = {
					"playerName" : "",
					"playerPassword" : "",
					"playerEmail" :""
			};
			c.playerTableDiv = false;			
			c.playerExceptionDiv = false;		
		}

		c.removePlayerButton = function() {
			c.createBookieDiv = false;			
			c.removeBookieDiv = false;			
			c.updateBookieDiv = false;			
			c.getBookieDiv = false;			
			c.getAllBookiesDiv = false;		
			c.createPlayerDiv = false;		
			c.removePlayerDiv = true;			
			c.updatePlayerDiv = false;		
			c.getPlayerDiv = false;			
			c.getAllPlayersDiv = false;		
			c.playerFields = {
					"playerId" : null
			};
			c.removePlayerExceptionDiv = false;	
			c.removePlayerSuccessDiv = false;		
			c.showAllPlayersTableDiv = false;		
			c.showAllPlayersExceptionDiv = false;	
		}

		c.updatePlayerButton = function() {
			c.createBookieDiv = false;				
			c.removeBookieDiv = false;				
			c.updateBookieDiv = false;				
			c.getBookieDiv = false;				
			c.getAllBookiesDiv = false;			
			c.createPlayerDiv = false;			
			c.removePlayerDiv = false;			
			c.updatePlayerDiv = true;				
			c.getPlayerDiv = false;				
			c.getAllPlayersDiv = false;			
			c.playerFields = {
					"playerId" : null,
					"playerPassword" : "",
					"playerEmail" :""
			};
			c.updatePlayerExceptionDiv = false;	
			c.updatePlayerTableDiv = false;		
		}
		
		c.updatePlayerResetButton = function() {
			c.playerFields = {
					"playerId" : null,
					"playerPassword" : "",
					"playerEmail" :""
			};
			c.updatePlayerExceptionDiv = false;	
			c.updatePlayerTableDiv = false;		
		}

		c.getPlayerButton = function() {
			c.createBookieDiv = false;				
			c.removeBookieDiv = false;				
			c.updateBookieDiv = false;				
			c.getBookieDiv = false;				
			c.getAllBookiesDiv = false;			
			c.createPlayerDiv = false;			
			c.removePlayerDiv = false;			
			c.updatePlayerDiv = false;			
			c.getPlayerDiv = true;				
			c.getAllPlayersDiv = false;			
			c.playerFields = {
					"playerId" : null
			};
			c.getPlayerTableDiv = false;		
			c.getPlayerExceptionDiv = false;	
		}
		
		c.getPlayerResetButton = function() {
			c.playerFields = {
					"playerId" : null
			};
			c.getPlayerTableDiv = false;		
			c.getPlayerExceptionDiv = false;	
		}

		c.getAllPlayersButton = function() {
			c.createBookieDiv = false;				
			c.removeBookieDiv = false;				
			c.updateBookieDiv = false;				
			c.getBookieDiv = false;				
			c.getAllBookiesDiv = false;			
			c.createPlayerDiv = false;			
			c.removePlayerDiv = false;			
			c.updatePlayerDiv = false;			
			c.getPlayerDiv = false;				
			c.getAllPlayersDiv = true;			
			c.getAllPlayersTableDiv = false;		
			c.getAllPlayersExceptionDiv = false;	
		}

		c.createBetButton = function() {
			c.createBetDiv = true;				
			c.removeBetDiv = false;				
			c.updateBeDiv = false;				
			c.getThisBetDiv = false;			
			c.getBetByIdDiv = false;				
			c.getAllBetsDiv = false;				
			c.betFields = {
					"betId" : null,
					"betTitle"	:	"",
					"sport" : "",	
					"eventDate" : "",
					"betWager" : "",
					"betOdds" : "",
					"betImage" : "",
					"betReceipt" : ""
			};
			c.createBetTableDiv = false;		
			c.createBetExceptionDiv = false;	
		}
		
		c.createBetResetButton = function() {
			c.betFields = {
					"betId" : null,
					"betTitle"	:	"",
					"sport" : "",	
					"eventDate" : "",
					"betWager" : "",
					"betOdds" : "",
					"betImage" : "",
					"betReceipt" : ""
			};
			c.createBetTableDiv = false;		
			c.createBetExceptionDiv = false;	
		}

		c.removeBetButton = function() {
			c.createBetDiv = false;		
			c.removeBetDiv = true;		
			c.updateBetDiv = false;		
			c.getThisBookieDiv = false;	
			c.getBetByIdDiv = false;		
			c.getAllBetsDiv = false;		
			c.betFields = {
					"betId" : null
			};
			c.removeBetExceptionDiv = false;			
			c.removeBetSuccessDiv = false;			
			c.getBookieBetsTableDiv = false;		
			c.getBookieBetsExceptionDiv = false;	
		}

		c.updateBetButton = function() {
			c.createBetDiv = false;			
			c.removeBetDiv = false;			
			c.updateBetDiv = true;			
			c.getThisBookieDiv = false;		
			c.getBetByIdDiv = false;			
			c.getAllBetsDiv = false;			
			c.betFields = {
					"betId" : null,
					"eventDate" : "",
					"betImage" : "",
			};
			c.updateBetTableDiv = false;		
			c.updateBetExceptionDiv = false;	
		}
		
		c.updateBetResetButton = function() {
			c.betFields = {
					"id" : null,
					"eventDate" : "",
					"betImage" : "",
			};
			c.updateBetTableDiv = false;		
			c.updateBetExceptionDiv = false;	
		}

		c.getThisBookieButton = function() {
			c.createBetDiv = false;			
			c.removeBetDiv = false;			
			c.updateBetDiv = false;			
			c.getThisBookieDiv = true;			
			c.getBetByIdDiv = false;			
			c.getAllBetsDiv = false;			
			c.getMyBookieTableDiv = false;		
			c.getMyBookieExceptionDiv = false	
		}

		c.getBetButton = function() {
			c.createBetDiv = false;		
			c.removeBetDiv = false;		
			c.updateBetDiv = false;		
			c.getThisBookieDiv = false;	
			c.getBetByIdDiv = true;		
			c.getAllBetsDiv = false;		
			c.betFields = {
					"betId" : null
			};
			c.getBetByIdTableDiv = false;		
			c.getBetByIdExceptionDiv = false;	
		}
		
		c.getBetByIdResetButton = function() {
			c.betFields = {
					"betId" : null
			};
			c.getBetByIdTableDiv = false;		
			c.getBetByIdExceptionDiv = false;	
		}

		c.getBookieBetsButton = function() {
			c.createBetDiv = false;		
			c.removeBetDiv = false;		
			c.updateBetDiv = false;		
			c.getThisBookieDiv = false;	
			c.getBetByIdDiv = false;		
			c.getAllBetsDiv = true;		
			
			c.getAllBetsTableDiv = false;		
			c.getAllBetsExceptionDiv = false;	
		}


		c.purchaseCouponButton = function() {
			c.purchaseCouponDiv = true;						
			c.getAllPurchasedCouponsDiv = false;			
			c.getAllPurchasedCouponsByTypeDiv = false;		
			c.getAllPurchasedCouponsByPriceDiv = false;		
			
			c.getAllSystemCouponsTableDiv = false;		
			c.purchaseCouponSuccessDiv = false;			
			c.getAllSystemCouponExceptionDiv = false;	
		}

		c.getAllPurchasedCouponsButton = function() {
			c.purchaseCouponDiv = false;					
			c.getAllPurchasedCouponsDiv = true;				
			c.getAllPurchasedCouponsByTypeDiv = false;		
			c.getAllPurchasedCouponsByPriceDiv = false;		
			
			c.getAllPurchasedCouponsTableDiv = false;		
			c.getAllPurchasedCouponsExceptionDiv = false;	
		}

		
	});