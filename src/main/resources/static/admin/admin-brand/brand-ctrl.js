app.controller("brand-ctrl", function($scope, $http) {

	$scope.items = [];
	$scope.form = {};

	$scope.initialize = function() {
		// load brands
		$http.get("/rest/brands").then(resp => {
			$scope.items = resp.data;
		});
	};

	$scope.initialize();

	// xóa form
	$scope.reset = function() {
	$scope.form ={};
	}

	// hiển thị lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
	}


	// thêm brands
	$scope.create = function() {
		var item = angular.copy($scope.form);
		$http.post(`/rest/create`, item).then(resp => {
			$scope.initialize();
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

	// cập nhật brands
	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/update/${item.id}`, item).then(resp => {
			$scope.initialize();
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

	// xóa Brand
	$scope.delete = function(item) {
		// alert("Delete");
		var brandId = angular.copy(item);
		$http.delete(`/rest/delete/${brandId.id}`).then(resp => {
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
				text: "Lỗi xóa thương hiệu!!",
			});
			console.log("Error", error);
		});
	};

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