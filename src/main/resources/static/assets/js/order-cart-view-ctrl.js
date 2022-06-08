
app.controller("order-cart-view-ctrl", function($scope, $http) {
	$scope.orders = [];
	
	
	let username = document.getElementById('current-user').innerText;
	$scope.init = function() {
	$http.get(`rest/orderListDto/${username}`).then(resp => {
			
			$scope.orders = resp.data
		})
	}
	$scope.init();
	$scope.edit = function(id) {
	$scope.total=0;
		$http.get(`/rest/orderCartViewDto/${id}`).then(resp => {
			$scope.products = resp.data;
			$scope.status = resp.data[0].orderStatus;
			angular.forEach($scope.products, function(item) {
				$scope.total += item.orderPrice*item.orderQuantity;
			} )
			
			$scope.getPhotos = resp.data.productPhoto;
		});
		$scope.cancel = function() {
			
		$http.put(`/rest/order-update-cancel/${id}`).then(
			window.location.reload()
		);
		
	}
	

	}
	$scope.pager = {
		page: 0,
		size: 5,
		get orders() {
			var start = this.page * this.size;
			return $scope.orders.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.orders.length / this.size);
		},
		first() {
			this.page = 0;
		},
		pre() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}

	}

});
