var app = angular.module('infobahn', [ 'ngRoute', 'ngAnimate' ]);

app.config([ '$httpProvider', '$routeProvider',
		function($httpProvider, $routeProvider) {

			// CORS

			$httpProvider.defaults.useXDomain = true;
			delete $httpProvider.defaults.headers.common['X-Requested-With'];

			// Route

			$routeProvider.when('/home', {
				templateUrl : 'partials/home.html',
				controller : 'dashboardController'
			}).when('/device/:id', {
				templateUrl : 'partials/device.html',
				controller : 'deviceController'
			}).otherwise({
				redirectTo : '/home'
			});

		} ]);