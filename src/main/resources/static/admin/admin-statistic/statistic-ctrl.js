app.controller("statistic-ctrl", function($scope, $http) {
	$scope.items = [];

	$scope.formProductPhoto = {};

	$scope.initialize = function() {
		$scope.formProductPhoto.imageId = 'a';
		// load products
		$http.get("/rest/products").then(resp => {
			$scope.items = resp.data;
		});
	};

	$scope.initialize();

	$scope.findByNameDto = function(a) {
		a.length == 0 ? ' ' : $http.get(`/rest/products/search/${a}`).then(resp => {
			$scope.items = resp.data;
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

	//----------------- SORT -------------------------------
	$scope.sortType = "";

	var sortCName = true;
	$scope.sortByCategoryName = function(sortChoose) {
		if (sortCName) {
			$scope.sortType = sortChoose;
			sortCName = false;
		} else {
			$scope.sortType = '-' + sortChoose;
			sortCName = true;
		}
	}


	// -------------------------------


});




























