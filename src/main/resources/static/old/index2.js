angular
		.module('sportsBettingApp', [])
		.run(function($rootScope) {

			$rootScope.toString = function(obj) {
				console.log("casted toString()")
				var s = "{"
				for (key in obj) {
					s += key + ":" + obj[key] + ", ";
				}
				s += "}";
				return s;
			}
		})

		.service('clientService', function() {

			var restURL = "";
			var isloggedIn = false;
			var client = null;
			var loginFailed = false;

		})

		// ================================ LOGIN CTRL
		.controller(
				'loginCtrl',
				function($http, $scope, $rootScope, clientService) {

					$scope.isLoggedIn = function() {
						return clientService.isloggedIn;
					}

					$scope.loginFailed = function() {
						return clientService.loginFailed;
					}

					$scope.init = function() {
						clientService.restURL = "api/";
						clientService.isloggedIn = false;
						clientService.loginFailed = false;
						clientService.client = {
							name : "",
							password : "",
							clientType : ""
						};
						console.log("loginCtrl.init() called, isloggedIn = "
								+ clientService.isloggedIn);
					}

					$scope.login = function(name, password, clientType) {
						console.log("loginCTRL.login() called. with : " + name
								+ "," + password + "," + clientType);
						switch (clientType) {
						case "PLAYER":
							loginURL = "player/";
							break;
						case "BOOKIE":
							loginURL = "bookie/";
							break;
						case "ADMINSPORT":
							loginURL = "admin/";
							break;
						}
						console.log("clientService.restURL : "
								+ clientService.restURL);
						console.log("loginURL : " + loginURL);
						var url = clientService.restURL + loginURL + "login/"
								+ name + "/" + password;
						console.log("sending HTTP/GET : " + url);
						$http
								.get(url)
								.then(
										function(response) {
											console
													.log("HTTP/200 received, successfull login for : "
															+ name
															+ ","
															+ password
															+ ","
															+ clientType);
											clientService.isloggedIn = true;
											if (clientService.loginFailed == true) {
												clientService.loginFailed = false;
											}

											clientService.client = response.data;
											console
													.log("clientService.isloggedIn = "
															+ clientService.isloggedIn);
											console
													.log("clientService.client = name-"
															+ clientService.client.name
															+ " password-"
															+ clientService.client.password
															+ " clientType-"
															+ clientService.client.clientType);
										},
										function(error) {
											clientService.loginFailed = true;
											console.log(error);
											console
													.log("login failed  = "
															+ clientService.loginFailed);
											alert("Login failed - try again");
										});
					}
			})
					// ================================ PLAYER CTRL
					.controller(
							'playerCtrl',
							function($http, $scope, $rootScope, clientService) {

								$scope.isPlayer = function() {
									return clientService.isloggedIn == true
											&& clientService.client.clientType == "PLAYER"
								}

								$scope.getPlayer = function() {
									return clientService.client;
								}

								$scope.init = function() {
									$scope.sport = "ALL";
									$scope.bet = null;
									$scope.maxWager = 0;
									$scope.displayMode = null;
									$scope.selectedRow = null;
								}

								$scope.isPlacingButtonEnabled = function() {
									return $scope.displayMode == "Available";
								}

								$scope.setClickedRow = function(index, bet) {
									$scope.bet = bet;
									$scope.selectedRow = index;
								}

								$scope.getplayerbets = function(sport) {
									var url = null;
									if (sport == null || sport == ""
											|| sport == "ALL") {
										url = clientService.restURL
												+ "player/getplayerbets";
									} else if (sport == "UPTO_WAGER") {
										url = clientService.restURL
												+ "player/player/getplayerbetsuptowager/"
												+ $scope.wager;
									} else {
										url = clientService.restURL
												+ "/player/getplayerbetsbysport/"
												+ sport;
									}
									console.log("getting placed bets from : " + url);
									$http.get(url).then(function(response) {
										console.log("Ok. HTTP/200 - response received");
										$scope.selectedRow = null;
										$scope.bet = null;
										$scope.playerBets = response.data;
										$scope.displayMode = "Placed";
									});
								}

								$scope.getAllAvailableBets = function(sport) {
									var url = null;
									if (sport == null || sport == ""
											|| sport == "ALL") {
										url = clientService.restURL
												+ "player/getallavailabledbets";
									} else if (sport == "UPTO_WAGER") {
										url = clientService.restURL
												+ "player/getallavailabledbetsuptowager/"
												+ $scope.maxWager;
									} else {
										url = clientService.restURL
												+ "player/getallavailabledbetsbysport/"
												+ sport;
									}
									console.log("getting available bets from : " + url);
									$http.get(url).then(function(response) {
										console.log("Ok. HTTP/200 - response received");
										$scope.selectedRow = null;
										$scope.bet = null;
										$scope.playerBets = response.data;
										$scope.displayMode = "Available";
									});
								}

								$scope.placeBet = function(bet) {
									var url = clientService.restURL
											+ "player/takebet";
									$http
											.post(url, $scope.bet)
											.then(
													function(response) {
														alert($scope.bet.betTitle
																+ " : placed successfuly.");
														console
																.log("HTTP/200 ? response received");
														$scope.selectedRow = null;
														$scope.bet = null;
														$scope
																.getAllPlayerBets("ALL");
														$scope.displayMode = "Placed";
													},
													function(error) {
														$scope.operationFailed = true;
														alert("Error!! "
																+ $scope.bet.betTitle
																+ " bet allready placed by you");
													});
								}
							})
				
							// ================== BOOKIE CTRL
		.controller(
				'bookieCtrl',
				function($http, $scope, $rootScope, clientService) {

					$scope.isBookie = function() {
						return clientService.isloggedIn == true
								&& clientService.client.clientType == "BOOKIE"
					}

					$scope.getBookie = function() {
						return clientService.client;
					}

					$scope.init = function() {
						$scope.sport = "ALL";
						$scope.sport = null;
						$scope.betId = "";
						$scope.displayMode = null;
						$scope.selectedRow = null;
						$scope.operationFailed = false;
					}

					$scope.setClickedRow = function(index, bet) {
						$scope.bet = bet;
						$scope.selectedRow = index;
					}

					$scope.clearBet = function() {
						$scope.bet = null;
						$scope.selectedRow = null;
						$scope.betId = "";
					}

					$scope.isOperationFailed = function() {
						return $scope.operationFailed;
					}

					$scope.getBookieBets = function(sport) {
						var url = null;
						if (sport == null || sport == ""
								|| sport == "ALL") {
							url = clientService.restURL
									+ "bookie/getbookiebets";
						} else if (sport == "BY_ID") {
							url = clientService.restURL + "bookie/getbet/"
									+ $scope.betId;
						} else if (sport == "UPTO_WAGER") {
							url = clientService.restURL
									+ "bookie/getbookiebetsuptowager/"
									+ wager;
						} else {
							url = clientService.restURL
									+ "bookie/getbookiebetsbysport/"
									+ sport;
						}
						console.log("getting bets from : " + url);
						$http
								.get(url)
								.then(
										function(response) {
											console
													.log("Ok. HTTP/200 - response received");
											$scope.operationFailed = false;
											$scope.selectedRow = null;
											$scope.bet = null;

											if (angular.isArray(response.data)) {
												console.log("got an array");
												$scope.playerBets = response.data;
											} else {
												console
														.log("got single bet, clearing the old playerBets array, and get response to playerBets[0]");
												$scope.playerBets = [];
												$scope.playerBets[0] = response.data;
											}
											console
													.log("response.data.Title = "
															+ response.data.betTitle);
										});
					}

					
					$scope.createBet = function(bet) {
						var url = clientService.restURL
								+ "bookie/createbet";
						$http
								.post(url, $scope.bet)
								.then(
										function(response) {
											alert($scope.bet.betTitle
													+ " : created successfuly.");
											console
													.log("HTTP/200 response received, bet created, initializing");
											$scope.selectedRow = null;
											$scope.bet = null;
											$scope.operationFailed = false;
											$scope.getBookieBets("ALL");
										},
										function(error) {
											$scope.operationFailed = true;
											console
													.log("creating bet failed");
										});
					}


					$scope.removeBet = function(bet) {
						var url = clientService.restURL
								+ "bookie/removebet";
						$http({
							method : "DELETE",
							url : url,
							data : $scope.bet,
							headers : {
								'Content-Type' : 'application/json'
							}
						})
								.then(
										function(response) {
											alert($scope.bet.betTitle
													+ " : removed successfuly.");
											console
													.log("HTTP/200 response received, bet removed, initializing");
											$scope.operationFailed = false;
											$scope.selectedRow = null;
											$scope.bet = null;
											$scope.getBookieBets("ALL");
										});
					}


					$scope.updateBet = function(bet) {
						var url = clientService.restURL
								+ "bookie/updatebet/"
								+ $scope.bet.betId;
						$http
								.put(url, $scope.bet, $scope.betId)
								.then(
										function(response) {
											alert($scope.bet.betTitle
													+ " : updated successfuly.");
											console
													.log("HTTP/200 response received, bet updated, initializing");
											$scope.selectedRow = null;
											$scope.bet = null;
											$scope.operationFailed = false;
											$scope.getBookieBets("ALL");
										},
										function(error) {
											$scope.operationFailed = true;
											console
													.log("updating bet failed");
										});
					}

				})

					// ========================== ADMIN CTRL
		.controller(
				'adminCtrl',
				function($http, $scope, $rootScope, clientService) {

					$scope.isAdmin = function() {
						return clientService.isloggedIn == true
								&& clientService.client.clientType == "ADMINSPORT"
					}

					$scope.getAdmin = function() {
						return clientService.client;
					}

					$scope.init = function() {
						console.log("admin init() called.");
						$scope.bookiesMode = "ALL";
						$scope.bookie = null;
						$scope.bookieId = null;
						$scope.player = null;
						$scope.playerId = "";
						$scope.properties = null;
						$scope.displayMode = null;
						$scope.bookiesSelectedRow = null;
						$scope.operationFailed = false;
					}

					$scope.setBookieClickedRow = function(index, bookie) {
						$scope.bookie = bookie;
						$scope.bookiesSelectedRow = index;
					}

					$scope.clearBookie = function() {
						$scope.bookie = null;
						$scope.bookiesSelectedRow = null;
						$scope.bookieId = "";
					}

					$scope.isOperationFailed = function() {
						return $scope.operationFailed;
					}

					$scope.setPlayerClickedRow = function(index, player) {
						$scope.player = player;
						$scope.playersSelectedRow = index;
					}

					$scope.clearPlayer = function() {
						$scope.player = null;
						$scope.playersSelectedRow = null;
						$scope.playerId = "";
					}

					$scope.getBookies = function(bookiesMode) {
						var url = null;
						if (bookiesMode == null || bookiesMode == ""
								|| bookiesMode == "ALL") {
							url = clientService.restURL + "admin/bookies";
						} else if (bookiesMode == "BY_ID") {
							url = clientService.restURL + "admin/bookies/"
									+ $scope.bookieId;
						}
						console.log("getting bookies from : " + url);
						$http
								.get(url)
								.then(
										function(response) {
											console
													.log("Ok. HTTP/200 - response received");
											$scope.operationFailed = false;
											$scope.bookiesSelectedRow = null;
											$scope.bookie = null;

											if (angular.isArray(response.data)) {
												console.log("got an array");
												$scope.bookies = response.data;
											} else {
												console
														.log("got single bookie, clearing the old bookies array, and get response to bookies[0]");
												$scope.bookies = [];
												$scope.bookies[0] = response.data;
											}
										});
					}

					$scope.createBookie = function(bookie) {
						var url = clientService.restURL + "admin/bookies";
						$http
								.post(url, $scope.bookie)
								.then(
										function(response) {
											alert($scope.bookie.bookieName
													+ " : created successfuly.");
											console
													.log("HTTP/200 response received, bookie created, initializing");
											$scope.bookiesSelectedRow = null;
											$scope.bookie = null;
											$scope.operationFailed = false;
											$scope.getBookies("ALL");
										},
										function(error) {
											$scope.operationFailed = true;
											console
													.log("create bookie failed");
										});
					}

					
					$scope.removeBookie = function(bookie) {
						var url = clientService.restURL + "admin/bookies"+ $scope.bookie.bookieId;
						$http({
							method : "DELETE",
							url : url,
							data : (url, $scope.bookie, $scope.bookieId),
							headers : {
								'Content-Type' : 'application/json'
							}
						})
								.then(
										function(response) {
											alert($scope.bookie.bookieName
													+ " : removed successfuly.");
											console
													.log("HTTP/200 response received, bookie removed, initializing");
											$scope.bookiesSelectedRow = null;
											$scope.bookie = null;
											$scope.operationFailed = false;
											$scope.getBookies("ALL");
										});
					}


					$scope.updateBookie = function(bookie) {
						var url = clientService.restURL + "admin/bookies/"
								+ $scope.bookie.bookieId;
						$http
								.put(url, $scope.bookie, $scope.bookieId)
								.then(
										function(response) {
											alert($scope.bookie.bookieName
													+ " : updated successfuly.");
											console
													.log("HTTP/200 response received, bookie updated, initializing");
											$scope.bookiesSelectedRow = null;
											$scope.bookie = null;
											$scope.operationFailed = false;
											$scope.getBookies("ALL");
										},
										function(error) {
											$scope.operationFailed = true;
											console
													.log("updating bookie failed");
										});
					}


					$scope.getPlayers = function(playersMode) {
						var url = null;
						if (playersMode == null || playersMode == ""
								|| playersMode == "ALL") {
							url = clientService.restURL + "admin/players";
						} else if (playersMode == "BY_ID") {
							url = clientService.restURL + "admin/players/"
									+ $scope.playerId;
						}
						console.log("getting players from : " + url);
						$http
								.get(url)
								.then(
										function(response) {
											console
													.log("Ok. HTTP/200 - response received");
											$scope.operationFailed = false;
											$scope.playersSelectedRow = null;
											$scope.player = null;

											if (angular.isArray(response.data)) {
												console.log("got an array");
												$scope.players = response.data;
											} else {
												console
														.log("got single player, clearing the old players array, and get response to players[0]");
												$scope.players = [];
												$scope.players[0] = response.data;
											}
										});
					}


					$scope.createPlayer = function(player) {
						var url = clientService.restURL + "admin/players";
						$http.post(url, $scope.player).
						then(
								function(response) {
									alert($scope.player.playerName
											+ " : created successfuly.");
									console.log("HTTP/200. object received : "
											+ $rootScope
													.toString(response.data));
									$scope.playersSelectedRow = null;
									$scope.player = null;
									$scope.operationFailed = false;
									$scope.getPlayers("ALL");
									
								}, function(error) {
									$scope.operationFailed = true;
									console.log("create player failed");
								});
					}


					$scope.removePlayer = function(player) {
						var url = clientService.restURL + "admin/players";
						$http({
							method : "DELETE",
							url : url,
							data : $scope.player,
							headers : {
								'Content-Type' : 'application/json'
							}
						}).then(
								function(response) {
									alert($scope.player.playerName
											+ " : removed successfuly.");
									console.log("HTTP/200. object received : "
											+ $rootScope
													.toString(response.data));
									$scope.playersSelectedRow = null;
									$scope.player = null;
									$scope.operationFailed = false;
									$scope.getPlayers("ALL");
								});
					}


					$scope.updatePlayer = function(player) {
						var url = clientService.restURL + "admin/players/"
								+ $scope.player.playerId;
						$http.put(url, $scope.player, $scope.playerId).
						then(
								function(response) {
									alert($scope.player.playerName
											+ " : updated successfuly.");
									console.log("HTTP/200. object received : "
											+ $rootScope
													.toString(response.data));
									$scope.playersSelectedRow = null;
									$scope.player = null;
									$scope.operationFailed = false;
									$scope.getPlayers("ALL");
									
								}, function(error) {
									$scope.operationFailed = true;
									console.log("updating player failed");
								});
					}

//			============ Get Properties - to check what it is ============
					$scope.getSystemProperties = function() {
						if ($scope.properties == null) {
							var url = clientService.restURL + "admin/getConfig";
							$http
									.get(url)
									.then(
											function(response) {
												console
														.log("HTTP/200. object received : "
																+ $rootScope
																		.toString(response.data));
												$scope.properties = response.data;
											});
						}
					}
//					============ Get Properties - to check what it is ============

					
					
				})
				
		// ================================ LOGOUT CTRL
		.controller(
				'logoutCtrl',
				function($http, $scope, clientService) {
					$scope.isLoggedIn = function() {
						return clientService.isloggedIn;
					}

					$scope.logout = function() {
						var logoutURL = null;
						console
								.log("logoutCTRL.logout() called. service clientType is = "
										+ clientService.client.clientType);
						switch (clientService.client.clientType) {
						case "PLAYER":
							logoutURL = "player/";
							break;
						case "BOOKIE":
							logoutURL = "bookie/";
							break;
						case "ADMINSPORT":
							logoutURL = "admin/";
							break;
						}
						console.log("clientService.restURL : "
								+ clientService.restURL);
						console.log("logoutURL : " + logoutURL);
						
						var url = clientService.restURL + logoutURL + "logout";
						console.log("sending HTTP/GET : " + url);
						$http
								.get(url)
								.then(
										function(response) {
											console
													.log("HTTP/200 received, successfull logout for : "
															+ clientService.client.clientType);

											clientService.isloggedIn = false;

											clientService.client = {
												name : "",
												password : "",
												clientType : ""
											};
											console
													.log("clientService.isloggedIn = "
															+ clientService.isloggedIn);
											console
													.log("logged out. client in context is now : "
															+ clientService.client.name
															+ ","
															+ clientService.client.password
															+ ","
															+ clientService.client.clientType);
											
										}).error(function(err) {
									console.log(err);
								});
					}
				});
				
			
			
			
		
		
		
	