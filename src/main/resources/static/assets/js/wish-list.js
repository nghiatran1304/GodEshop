
app.controller("single-product-ctrl", function($scope, $http) {

		$scope.addToWishList = function(productId) {
			var item = {};
		
		$http.put(`/rest/add-wishList/${productId}`, item).then(resp => {
			
			location.reload();
		
		})
	}
});