app.controller("brand-ctrl", function($scope, $http) {
	$(document).keypress(
		function(event) {
			if (event.which == '13') {
				event.preventDefault();
			}
		});
	$scope.items = [];
	$scope.form = {};
	$scope.showInsert = false;
	$scope.showUpdate = false;

	$scope.initialize = function() {
		$scope.showInsert = true;
		// load brands
		$http.get(`/rest/brands`).then(resp => {
			$scope.items = resp.data;
		});
	};

	$scope.initialize();

	$scope.findByName = function() {
		var nameSearched = $scope.searchByName;
		$http.get(`/rest/brands/${nameSearched}`).then(resp => {
			$scope.items = resp.data;
		});
	}

	// xóa form
	$scope.reset = function() {
		$scope.showInsert = true;
		$scope.showUpdate = false;
		$scope.form = {};
	}

	// hiển thị lên form
	$scope.edit = function(item) {
		$scope.showInsert = false;
		$scope.showUpdate = true;
		$scope.form = angular.copy(item);
	}

	// thêm sản phẩm
	$scope.create = function() {
		var item = angular.copy($scope.form);
		$http.post(`/rest/create`, item).then(resp => {
			$scope.initialize();
			$scope.reset();
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Thành công',
				showConfirmButton: false,
				timer: 500
			})
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Lỗi !!!",
			});
			console.log("Error product : ", error);
		});
	}

	// cập nhật 
	$scope.update = function() {
		// product
		var item = angular.copy($scope.form);
		$http.put(`/rest/update/${item.id}`, item).then(resp => {
			$scope.initialize();
			$scope.reset();
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Thành công',
				showConfirmButton: false,
				timer: 500
			})
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Lỗi !!!",
			});
			console.log("Error product : ", error);
		});

	}

	// xóa sản phẩm
	$scope.delete = function(item) {
		var brandId = angular.copy(item);
		$http.delete(`/rest/delete/${brandId.id}`).then(resp => {
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

	// phân trang
	$scope.pager = {
		page: 0,
		size: 5,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
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