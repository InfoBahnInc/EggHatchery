app.controller('dashboardController', function($scope, $http, $interval) {

	function refreshDashboard() {

		$(".tile").toggleClass("active");

		$http({
			method : 'GET',
			url : 'http://130.130.165.88:8080/rest/snapshot/recent'
		}).success(function(pData) {
			// attach this data to the scope
			formatServerResponse(pData, $scope.Devices[0]);

			// clear the error messages
			$scope.error = '';
		}).error(function(data, status) {
			if (status === 404)
				$scope.error = 'That repository does not exist';
			else
				$scope.error = 'Error: ' + status;
		});
	}

	function formatServerResponse(pData, pDevice) {
		pDevice.clear();
		pData.forEach(function(pDatum) {
			pDevice.addReading(new CReading(new Date(pDatum.recorded), Math
					.round(pDatum.temperature * 10) / 10, // rounded to 1
			// decimal place
			Math.round(pDatum.humidity * 10) / 10, // rounded to 1 decimal
			// place
			Math.round(pDatum.vibration * 10) / 10)); // rounded to 1 decimal
			// place
		});
	}
	;

	// initialize the model
	$scope.Devices = [
			new CDevice(1, [ new CReading(new Date(), 16.6, 78, 0) ], 'Hackagong'),
			new CDevice(2, [ new CReading(new Date(), 37.7, 64, 1) ], 'East Barn'),
			new CDevice(3, [ new CReading(new Date(), 34.4, 68, 0) ], 'West Barn'),
			new CDevice(4, [ new CReading(new Date(), 34.3, 62, 0) ], 'North Barn'),
			new CDevice(5, [ new CReading(new Date(), 37.2, 65, 0) ], 'South Barn'),
			new CDevice(5, [ new CReading(new Date(), 35.2, 63, 0) ], 'Barn #2'),
			new CDevice(5, [ new CReading(new Date(), 36.2, 67, 0) ], 'Barn #3'),
			new CDevice(5, [ new CReading(new Date(), 33.2, 66, 0) ], 'Barn #4'),
			new CDevice(5, [ new CReading(new Date(), 37.7, 59, 0) ], 'Barn #5'),
			new CDevice(5, [ new CReading(new Date(), 37.5, 63, 0) ], 'Barn #6'),
			new CDevice(5, [ new CReading(new Date(), 37.9, 61, 0) ], 'Barn #7'),
			new CDevice(5, [ new CReading(new Date(), 36.3, 65, 0) ], 'Barn #8') ];

	refreshDashboard();

	var cancelInterval = $interval(refreshDashboard, 5000);

	$scope.$on("$destroy", function() {
		if (cancelInterval !== undefined) {
			$interval.cancel(cancelInterval);
		}
	});

});
