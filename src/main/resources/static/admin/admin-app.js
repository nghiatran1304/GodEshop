var app = angular.module("admin-app", ["ngRoute"])

app.config(function($routeProvider) {
	$routeProvider
		.when("/order", {
			templateUrl: "/admin/admin-order/index.html",
			controller: "order-ctrl"
		})
		.when("/management/watch", {
			templateUrl: "/admin/admin-watch/index.html",
			controller: "watch-ctrl"
		})
		.when("/management/accessory", {
			templateUrl: "/admin/admin-accessory/index.html",
			controller: "accessory-ctrl"
		})
		.when("/management/account", {
			templateUrl: "/admin/admin-account/index.html",
			controller: "account-ctrl"
		})
		.when("/management/brand", {
			templateUrl: "/admin/admin-brand/index.html",
			controller: "brand-ctrl"
		})
		.when("/management/glassmaterial", {
			templateUrl: "/admin/admin-glassmaterial/index.html",
			controller: "glassmaterial-ctrl"
		})
		.when("/management/braceletmaterial", {
			templateUrl: "/admin/admin-braceletmaterial/index.html",
			controller: "braceletmaterial-ctrl"
		})
		.when("/management/machineinside", {
			templateUrl: "/admin/admin-machineinside/index.html",
			controller: "machineinside-ctrl"
		})
		.when("/management/category", {
			templateUrl: "/admin/admin-category/index.html",
			controller: "category-ctrl"
		})
		.when("/discount", {
			templateUrl: "/admin/admin-discount/index.html",
			controller: "discount-ctrl"
		})
		.when("/statistic", {
			templateUrl: "/admin/admin-statistic/index.html",
			controller: "statistic-ctrl"
		})
		.otherwise({
			templateUrl: "/admin/admin-order/index.html",
			controller: "order-ctrl"
		});
		// $locationProvider.html5Mode(true);
});

