app
		.controller(
				'deviceController',
				function($scope, $http, $interval) {

					var ctx = $("#device-chart")[0].getContext("2d");

					var options = {
						scaleLabel : "<%= value + 'C'%>",
						responsive : true
					};

					function _dateToString(dateText) {
						var date = $.dateFromString(dateText);
						var minutes = date.getMinutes();
						if (minutes < 10) {
							minutes = "0" + minutes;
						}
						var seconds = date.getSeconds();
						if (seconds < 10) {
							seconds = "0" + seconds;
						}

						return date.getHours() + ":" + minutes + ":" + seconds;
					}

					$http
							.get('/rest/snapshot/recent')
							.success(
									function(recentService) {

										var labels = recentService
												.map(
														function(item) {
															return _dateToString(item.recorded);
														}).reverse();
										var vibrationPoints = recentService
												.map(function(item) {
													return item.vibration * 5;
												}).reverse();
										var humidityPoints = recentService.map(
												function(item) {
													return item.humidity;
												}).reverse();
										var temperaturePoints = recentService
												.map(function(item) {
													return item.temperature;
												}).reverse();
										var lastId = recentService[0].id;
										$scope.lastTemperature = recentService[0].temperature;
										$scope.lastHumidity = recentService[0].humidity;
										$scope.lastVibration = recentService[0].vibration;
										$scope.creading = new CReading(null,
												$scope.lastTemperature,
												$scope.lastHumidity,
												$scope.lastVibration);

										var data = {
											labels : labels,
											datasets : [
													{
														label : 'Vibration',
														fillColor : "rgba(135,139,182,0.2)",
														strokeColor : "rgba(135,139,182,1)",
														pointColor : "rgba(135,139,182,1)",
														pointStrokeColor : "#fff",
														data : vibrationPoints
													},
													{
														label : 'Humidity',
														fillColor : "rgba(220,220,220,0.2)",
														strokeColor : "rgba(220,220,220,1)",
														pointColor : "rgba(220,220,220,1)",
														pointStrokeColor : "#fff",
														data : humidityPoints
													},
													{
														label : "Temperature",
														fillColor : "rgba(151,187,205,0.2)",
														strokeColor : "rgba(151,187,205,1)",
														pointColor : "rgba(151,187,205,1)",
														pointStrokeColor : "#fff",
														pointHighlightFill : "#fff",
														pointHighlightStroke : "rgba(151,187,205,1)",
														data : temperaturePoints
													} ]
										};
										var myLineChart = new Chart(ctx).Line(
												data, options);

										var cancelInterval = $interval(
												function() {
													$http
															.get(
																	'/rest/snapshot/last')
															.success(
																	function(
																			lastService) {
																		if (lastId === lastService.id) {
																			return;
																		}
																		myLineChart
																				.addData(
																						[
																								lastService.vibration * 5,
																								lastService.humidity,
																								lastService.temperature ],
																						_dateToString(lastService.recorded));
																		lastId = lastService.id;
																		$scope.lastTemperature = lastService.temperature;
																		$scope.lastHumidity = lastService.humidity;
																		$scope.lastVibration = lastService.vibration;
																		$scope.creading = new CReading(
																				null,
																				$scope.lastTemperature,
																				$scope.lastHumidity,
																				$scope.lastVibration);
																		myLineChart
																				.removeData();
																	});
												}, 3000);

										$scope
												.$on(
														"$destroy",
														function() {
															if (cancelInterval !== undefined) {
																$interval
																		.cancel(cancelInterval);
															}
														});
									});

					$scope.labelFan = 'Turn Fan On';
					$scope.toggleFan = function toggleFan() {
						if ($scope.labelFan == 'Turn Fan On') {
							$scope.labelFan = 'Turn Fan Off';
							$http(
									{
										method : 'PUT',
										url : 'http://130.130.165.88:8080/rest/control/fan',
										data : {
											start : true
										}
									})
									.error(
											function(data, status) {
												if (status === 404)
													$scope.error = 'That repository does not exist';
												else
													$scope.error = 'Error: '
															+ status;
											});
						} else {
							$scope.labelFan = 'Turn Fan On';
							$http(
									{
										method : 'PUT',
										url : 'http://130.130.165.88:8080/rest/control/fan',
										data : {
											stop : true
										}
									})
									.error(
											function(data, status) {
												if (status === 404)
													$scope.error = 'That repository does not exist';
												else
													$scope.error = 'Error: '
															+ status;
											});
						}
					};

					$scope.labelHeater = 'Turn Heater On';
					$scope.toggleHeater = function toggleHeater() {
						if ($scope.labelHeater == 'Turn Heater On') {
							$scope.labelHeater = 'Turn Heater Off';
							$http(
									{
										method : 'PUT',
										url : 'http://130.130.165.88:8080/rest/control/heater',
										data : {
											start : true
										}
									})
									.error(
											function(data, status) {
												if (status === 404)
													$scope.error = 'That repository does not exist';
												else
													$scope.error = 'Error: '
															+ status;
											});
						} else {
							$scope.labelHeater = 'Turn Heater On';
							$http(
									{
										method : 'PUT',
										url : 'http://130.130.165.88:8080/rest/control/heater',
										data : {
											stop : true
										}
									})
									.error(
											function(data, status) {
												if (status === 404)
													$scope.error = 'That repository does not exist';
												else
													$scope.error = 'Error: '
															+ status;
											});
						}
					};

					$scope.toggleServo = function toggleServo() {
						var angle = 0;
						if ($scope.LabelRotate == 'Rotate Left') {
							$scope.LabelRotate = 'Rotate Right';
							angle = -30;
						} else {
							$scope.LabelRotate = 'Rotate Left';
							angle = 30;
						}

						$http(
								{
									method : 'PUT',
									url : 'http://130.130.165.88:8080/rest/control/servo',
									data : {
										"angle" : angle
									}
								})
								.error(
										function(data, status) {
											if (status === 404)
												$scope.error = 'That repository does not exist';
											else
												$scope.error = 'Error: '
														+ status;
										});

					};
				});
