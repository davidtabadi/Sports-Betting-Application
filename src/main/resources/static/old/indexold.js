angular.module('sportsBettingApp', ['ngResource'])
	.controller('SportsController', function($resource) {
	
	var c = this;
	
//	3 Login Resources
	
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
	
	
	c.infoMessage  = "";
	c.errorMessage = "";
	
	c.info = function(message){
		c.infoMessage  = message;
		c.errorMessage = "";
	}
	
	c.error = function(message){
		c.infoMessage  = "";
		c.errorMessage = message;
	}

	c.handleError = function (response, defaultMessage){
		if(response.data != null && response.data.message != null){
			c.error("Error: " + response.data.message);
		}else{
			c.error(defaultMessage); 
		}
	}
	
	c.isAdmin = function() {
		 return c.isLoggedIn == true && c.loginFields.clientType == "ADMINSPORT" 
	}
	
	c.isBookie = function() {
		return c.isLoggedIn == true && c.loginFields.clientType == "BOOKIE" 
	}
	
	c.isPlayer = function() {
		return c.isLoggedIn == true && c.loginFields.clientType == "PLAYER" 
	}
	
	
//	Login method for all  clientTypes
	
	c.login = function() {
		if (c.loginFields.clientType == "ADMINSPORT") {
			c.adminLoginResource.get(c.loginFields, function(response){
				c.isLoggedIn = true;
				c.adminDiv = true;
				c.info("Successfull AdminSport Login");
			}, 
			function(response) {
				c.handleError(response, "Error while logging in");
			})
		} else if (c.loginFields.clientType == "BOOKIE") {
			c.adminLoginResource.get(c.loginFields, function(response){
				c.isLoggedIn = true;
				c.bookieDiv = true;
				c.info("Successfull Bookie Login");
			}, 
			function(response) {
				c.handleError(response, "Error while logging in");
			})
			} else if (c.loginFields.clientType == "PLAYER") {
				c.adminLoginResource.get(c.loginFields, function(response){
					c.isLoggedIn = true;
					c.playerDiv = true;
					c.info("Successfull Bookie Login");
				}, 
				function(response) {
					c.handleError(response, "Error while logging in");
				})
				}
			}
	
	
//	3 Logout Resources

	c.adminLogoutResource = $resource("api/admin/logout");
	
	c.bookieLogoutResource = $resource("api/bookie/logout");
	
	c.playerLogoutResource = $resource("api/player/logout");
	
	
// Logout methods for each clientType
	
	c.adminLogout = function() {
		c.adminLogoutResource.get(function(response) {
			c.loginFields = {
				"username" : "",
				"password" : "",
				"clientType" : ""
			};
			c.loginDiv = true;
			c.playerDiv = false;
			c.adminDiv = false;
			c.bookieDiv = false;
		});
	}
	
	
	c.bookieLogout = function() {
		c.adminLogoutResource.get(function(response) {
			c.loginFields = {
				"username" : "",
				"password" : "",
				"clientType" : ""
			};
			c.loginDiv = true;
			c.playerDiv = false;
			c.adminDiv = false;
			c.bookieDiv = false;
		});
	}
	
	
	c.playerLogout = function() {
		c.adminLogoutResource.get(function(response) {
			c.loginFields = {
				"username" : "",
				"password" : "",
				"clientType" : ""
			};
			c.loginDiv = true;
			c.playerDiv = false;
			c.adminDiv = false;
			c.bookieDiv = false;
		});
	}
		
	c.allBookies = [];
	
	c.allPlayers = [];
	
	
	// Admin resource for Bookies management
	
	c.adminBookiesResource = $resource("api/admin/bookies/:id",{'id':'@id'},{
	"updateBookie": {method:"PUT"}
	});

	c.adminAllBookiesResource = $resource("api/admin/bookies");
	
	// Admin recourse for players management
	
	c.adminPlayersResource = $resource("api/admin/players/:id",{'id':'@id'},{
	"updatePlayer": {method:"PUT"}
	});

	c.adminAllPlayersResource = $resource("api/admin/players");
	

c.newBookie = {
		"name":"",
		"password":"",
		"email":""
			};
	
	c.addBookie = function(){
		c.adminBookiesResource.save(
				c.newBookie, 
			// on success
			function(bookie){
					c.bookieFields.bookieName = c.newBookie.name;
					c.bookieFields.bookiePassword = c.newBookie.password;
					c.bookieFields.bookieEmail = c.newBookie.email;
					c.loadBookies();
					c.info("Successfully created new bookie");
			},
			// on error
			function(response){
				c.loadBookies();
				c.handleError(response, "Error while creating new bookie");
			});
	}
	
	
//	c.addBookie = function() {
//		c.adminAllBookiesResource.save(c.newBookie, function(bookie) {
//			c.newBookie.bookieId = bookie.bookieId;
//			c.newBookie.bookieName = bookie.bookieName;
//			c.newBookie.bookiePassword = bookie.bookiePassword;
//			c.newBookie.bookieEmail = bookie.bookieEmail;
//		}, function() {
//			c.error("Bookie Creation Failed");
//			
//		});
//	}

	c.removeBookie = function() {
		c.adminBookiesResource.delete(c.bookieFields, function() {
			c.info("Operation completed successfully");
		}, function() {
			c.error("Request could not be completed");
		});
	}
		
	c.updateBookie = function() {
		c.adminBookiesResource.put(c.bookieyFields, function(bookie) {
			if (c.bookieFields.bookiePassword != "") {
				bookie.bookiePassword = c.bookieFields.bookiePassword;
			}
			if (c.bookieFields.bookieEmail != "") {
				bookie.bookieEmail = c.bookieFields.bookieEmail;
			}
			updateBookie = bookie;
			updateBookie.$updateBookie(bookie, function() {
				c.bookieFields.bookieId = bookie.bookieId;
				c.bookieFields.bookieName = bookie.bookieName;
				c.bookieFields.bookiePassword = bookie.bookiePassword;
				c.bookieFields.bookieEmail = bookie.bookieEmail;
			})
		},function() {
			c.error("Request could not be completed");
		});
	}
		
	c.getBookie = function() {
		c.adminBookiesResource.get(c.bookieFields, function(bookie) {
			c.bookieFields = bookie; 
		}, function() {
			c.error("Request could not be completed");
		});
	}

	
//	c.loadBookies = function() {
//		c.allBookies = c.adminBookiesResource.query(function(){
//			c.allBookies.forEach(function(bookie){
//				c.bookieFields = bookie;
//				console.log("Bookie: " + c.bookieFields)
//			});
//		});
//		}
	
	
	

	
	

	c.addPlayer = function() {
		c.adminPlayersResource.save(c.playerFields, function(player) {
			c.playerFields.playerId = player.playerId;
			c.playerFields.playerName = player.playerName;
			c.playerFields.playerPassword = player.playerPassword;
			c.playerFields.playerEmail = player.playerEmail;
		}, function() {
			c.error("Player Creation Failed");
		});
	}
	
	c.removePlayerForm = function() {
		c.adminPlayersResource.delete(c.playerFields, function() {
			console.log("Field ID: " + c.playerFields.playerId);
			c.info("Operation completed successfully");
		}, function() {
			c.error("Request could not be completed");
		});
	}
	
	c.updatePlayerForm = function() {
		c.adminPlayersResource.get(c.playerFields, function(player) {
			if (c.playerFields.playerPassword != "") {
				player.playerPassword = c.playerFields.playerPassword;
			}
			if (c.playerFields.playerEmail != "") {
				player.playerEmail = c.playerFields.playerEmail;
			}
			updatePlayer = player;
			updatePlayer.$updatePlayer(player, function() {
				c.playerFields.playerId = player.playerId;
				c.playerFields.playerName = player.playerName;
				c.playerFields.playerPassword = player.playerPassword;
				c.playerFields.playerEmail = player.playerEmail;
			})
		},function() {
			c.error("Request could not be completed");
		});
	}
	
	
	c.getPlayer = function() {
		c.adminPlayersResource.get(c.playerFields, function(player) {
			console.log("Player ID: " + player.playerId);
			if (player.playerId != 0) {
				c.playerFields = player; 
			} else {
				c.error("Request could not be completed");
			}
		}, function() {
			c.error("Request could not be completed");
		});
	}
	
	
	c.getAllPlayers = function() {
		c.allPlayers = c.adminAllPlayersResource.query(c.playerFields, function() {
			if (c.allPlayers.length > 0) {
				c.allPlayers.forEach(function(player){
					c.playerFields = player;
					})
			} else {
				c.error("No Players were found");
			}
			}, function() {
				c.error("Request could not be completed");
			});
	}
	

	
	
	
	
});
