var app = angular.module("admin-app", ["ngRoute"]);
app.config(function($routeProvider) {
	$routeProvider
		.when("/admin/order", {
			templateUrl: "/admin/admin-order/index.html",
			controller: "order-ctrl"
		})
		.when("/admin/management/watch", {
			templateUrl: "/admin/admin-watch/index.html",
			controller: "watch-ctrl"
		})
		.when("/admin/management/accessory", {
			templateUrl: "/admin/admin-accessory/index.html",
			controller: "accessory-ctrl"
		})
		.when("/admin/management/account", {
			templateUrl: "/admin/admin-account/index.html",
			controller: "account-ctrl"
		})
		.when("/admin/management/brand", {
			templateUrl: "/admin/admin-brand/index.html",
			controller: "brand-ctrl"
		})
		.when("/admin/management/category", {
			templateUrl: "/admin/admin-category/index.html",
			controller: "category-ctrl"
		})
		.when("/admin/discount", {
			templateUrl: "/admin/admin-discount/index.html",
			controller: "discount-ctrl"
		})
		.when("/admin/statistic", {
			templateUrl: "/admin/admin-statistic/index.html",
			controller: "statistic-ctrl"
		})
		.otherwise({
			templateUrl: "/admin/admin-home/index.html"
		});
});