app.controller("statistic-ctrl", function($scope, $http) {
	$scope.itemsProduct = [];


	$scope.initialize = function() {
		// load products
		$http.get("/rest/statistic/products").then(resp => {
			$scope.itemsProduct = resp.data;
		});
	};

	$scope.initialize();

	$scope.findByNameDto = function(a) {
		a.length == 0 ? ' ' : $http.get(`/rest/products/search/${a}`).then(resp => {
			$scope.itemsProduct = resp.data;
		});
	};


	// phân trang
	$scope.pager = {
		page: 0,
		size: 5,
		get items() {
			var start = this.page * this.size;
			return $scope.itemsProduct.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsProduct.length / this.size);
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
	
	const labels = [
    'January',
    'February',
    'March',
    'April',
    'May',
    'June',
  ];

	  const data = {
	    labels: labels,
	    datasets: [{
	      label: 'My First dataset',
	      backgroundColor: 'rgb(255, 99, 132)',
	      borderColor: 'rgb(255, 99, 132)',
	      data: [0, 10, 5, 2, 20, 30, 45],
	    }]
	  };
	
	  const config = {
	    type: 'line',
	    data: data,
	    options: {}
	  };
	$scope.detail = function (id) {
		console.log(id)
		const myChart = new Chart(
		    document.getElementById('myChart'),
		    config
		  );
	}
});






























