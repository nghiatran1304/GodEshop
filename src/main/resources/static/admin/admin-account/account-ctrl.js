app.controller("account-ctrl", function($scope, $http) {
	
	$scope.items = [];
	$scope.lstRole = [];
	$scope.form = {};
	$scope.isEdit = false;
	
	$scope.hasImage = 'a';
	
	$scope.initialize = function() {
		// load account
		$http.get("/rest/info-accounts").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.userDob = new Date(item.userDob);
			});
		});
		
		// load role
		$http.get("/rest/roles").then(resp => {
			$scope.lstRole = resp.data;
		})

	};

	$scope.initialize();
	
	$scope.findByName = function(){
		var nameSearched = $scope.searchByUsername;
		$http.get(`/rest/accounts/${nameSearched}`).then(resp => {
			$scope.items = resp.data;
		});
	}
	

	// xóa form
	$scope.reset = function() {
		$scope.isEdit = false;
		$scope.form = {};
		$scope.hasImage = 'a';
	}
	
	
	// hiển thị lên form
	$scope.edit = function(item) {
		$scope.isEdit = true;
		$scope.hasImage = 'b';
		$scope.form = angular.copy(item);
	}
	
	$scope.change = function(a){
		$scope.form.accountIsDeleted = a;
	}
	
	$scope.create = function(){
		
	}
	
	$scope.update = function(){
		var item = angular.copy($scope.form);
		console.log(item);
		$http.put(`/rest/update-account/${item.accountUsername}`, item).then(resp => {
			$scope.reset();
			$scope.initialize();
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Success',
				showConfirmButton: false,
				timer: 500
			})
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Failed !!!",
			});			
		});
	}
	
	$scope.delete = function(item){
		var a = angular.copy(item);
		$http.delete(`/rest/delete-account/${a.accountUsername}`).then(resp => {
			// var index = $scope.items.findIndex(p => p.productId == item.productId);
			$scope.initialize();
			$scope.reset();
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Success',
				showConfirmButton: false,
				timer: 1000
			})
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Failed!!!",
			});
		});
	}
	

	// phân trang
	$scope.pager = {
		page: 0,
		size: 10,
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