app.controller("discount-ctrl", function($scope, $http) {

	$scope.form = {};
	$scope.items = {};

	$scope.productNonDiscounts = [];
	$scope.productOnSale = [];

	$scope.isEdit = null;
	var adminUsername = "";
	$scope.initialize = function() {
		adminUsername = $("#adminUsername").text();
		// product non discount
		$http.get(`/rest/product-none-discount`).then(resp => {
			$scope.productNonDiscounts = resp.data;
		});

		// product on sale
		$http.get(`/rest/product-onsale`).then(resp => {
			$scope.productOnSale = resp.data;
			$scope.productOnSale.forEach(item => {
				item.productDiscountCreateDate = new Date(item.productDiscountCreateDate);
			});
			$scope.productOnSale.forEach(item => {
				item.productDiscountEndDate = new Date(item.productDiscountEndDate);
			});
		})

	}

	$scope.initialize();
	$scope.productDiscountCreateDate = null;
	$scope.productDiscountEndDate = null;
	$scope.productId = null;
	$scope.productDiscountPercent = null;

	$scope.reset = function() {
		$scope.productDiscountCreateDate = null;
		$scope.productDiscountEndDate = null;
		$scope.productId = null;
		$scope.productDiscountPercent = null;
		$scope.form = {};
		$scope.isEdit = null;
		$scope.initialize();
	}

	$scope.editProductOnSale = function(item) {
		$scope.isEdit = true;
		$scope.form = angular.copy(item);
		$scope.productDiscountId = $scope.form.productDiscountId;
		$scope.productId = $scope.form.productId;
		$scope.productDiscountPercent = $scope.form.productDiscountPercent;
		$scope.productDiscountCreateDate = new Date($scope.form.productDiscountCreateDate);
		$scope.productDiscountEndDate = new Date($scope.form.productDiscountEndDate);
	}

	$scope.editProductNoneDiscount = function(item) {
		$scope.isEdit = false;
		$scope.form = angular.copy(item);
		$scope.productId = $scope.form.id;
		$scope.productDiscountPercent = 0;
		$scope.productDiscountCreateDate = new Date();
		$scope.productDiscountEndDate = new Date();
	}

	$scope.create = function() {
		$scope.getForm = {};
		$scope.getForm.productId = $scope.productId;
		$scope.getForm.productDiscountPercent = $scope.productDiscountPercent;
		$scope.getForm.username = adminUsername;
		$scope.getForm.productDiscountCreateDate = $scope.productDiscountCreateDate;
		$scope.getForm.productDiscountEndDate = $scope.productDiscountEndDate;

		$http.post(`/rest/create-discount`, $scope.getForm).then(resp => {
			$scope.initialize();
			$scope.reset();
			$scope.getForm = {};
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Thành công',
				showConfirmButton: false,
				timer: 500
			})
		})

	}

	$scope.update = function() {
		$scope.getForm = {};
		var pdId = angular.copy($scope.productDiscountId);
		$scope.getForm.productDiscountId = $scope.productDiscountId;
		$scope.getForm.productId = $scope.productId;
		$scope.getForm.productDiscountPercent = $scope.productDiscountPercent;
		$scope.getForm.username = adminUsername;
		$scope.getForm.productDiscountCreateDate = $scope.productDiscountCreateDate;
		$scope.getForm.productDiscountEndDate = $scope.productDiscountEndDate;

		$http.put(`/rest/update-discount/${pdId}`, $scope.getForm).then(resp => {
			$scope.initialize();
			$scope.reset();
			$scope.getForm = {};
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Thành công',
				showConfirmButton: false,
				timer: 500
			})
		})
	}

	$scope.delete = function() {
		var pdId = angular.copy($scope.productDiscountId);
		$http.delete(`/rest/delete-discount/${pdId}`).then(resp => {
			// var index = $scope.items.findIndex(p => p.productId == item.productId);
			$scope.initialize();
			$scope.reset();
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Thành công',
				showConfirmButton: false,
				timer: 1000
			})
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Lỗi xóa !!!",
			});
			console.log("Error", error);
		});
	}


	$scope.pagerOnSale = {
		page: 0,
		size: 5,
		get productOnSale() {
			var start = this.page * this.size;
			return $scope.productOnSale.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.productOnSale.length / this.size);
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


	$scope.pager = {
		page: 0,
		size: 5,
		get productNonDiscounts() {
			var start = this.page * this.size;
			return $scope.productNonDiscounts.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.productNonDiscounts.length / this.size);
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