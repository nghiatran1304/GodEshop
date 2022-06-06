app.controller("account-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.lstRole = [];
	$scope.form = {};
	$scope.formRole={}
	$scope.initialize = function() {
		// load account
		$http.get("/rest/accounts").then(resp => {
			$scope.items = resp.data;
		});
		$http.get("/rest/role").then(resp => {
			$scope.lstRole = resp.data;
		});
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
		$scope.form = {};
	}

	// hiển thị lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$scope.formRole.roleId = $scope.form.role.id;
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