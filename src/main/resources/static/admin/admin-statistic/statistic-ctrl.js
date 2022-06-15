app.controller("statistic-ctrl", function($scope, $http) {
	// --- Product statistic ---	
	$scope.itemsProduct = [];

	$scope.initialize = function() {
		// load products
		$http.get("/rest/statistic/products").then(resp => {
			$scope.itemsProduct = resp.data;
			$http.get("/rest/products/image").then(resp2 => {
				for (var i = 0; i < resp.data.length; i++) {
					if (resp.data.productId == resp2.data.productId) {
						$scope.itemsProduct[i].imageId = resp2.data[i].imageId;
					}
				}	
			})
		});
	};

	$scope.initialize();

	$scope.findByNameDto = function(a) {
		a.length == 0 ? $scope.initialize() : $http.get(`/rest/statistic/products/${a}`).then(resp => {
			$scope.itemsProduct = resp.data;
			$http.get(`/rest/products/image/${a}`).then(resp2 => {
				for (var i = 0; i < resp.data.length; i++) {
					if (resp.data.productId == resp2.data.productId) {
						$scope.itemsProduct[i].imageId = resp2.data[i].imageId;
					}
				}	
			})
		});
	};
	
	// --- End product statistic ---	


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


	// --------- Chart ----------------
	
	var labels = [];

	  var data = {};
	
	  var config = {};
	  
	var myChart = null;
	$scope.itemProduct = [];
	$scope.itemProduct.orderCreateDate = new Date();
	$scope.detail = function (id) {
		if(myChart!=null){
        myChart.destroy();
   		 }
   		 labels = [
    'January',
    'February',
    'March',
    'April',
    'May',
    'June',
  ];
  
  data = {
	    labels: labels,
	    datasets: [{
	      label: 'Statistic in month',
	      backgroundColor: 'rgb(255, 99, 132)',
	      borderColor: 'rgb(255, 99, 132)',
	      data: [0, 100, 521, 24, 202, 304, 405],
	    }]
	  };
	  
	  config = {
	    type: 'line',
	    data: data,
	    options: {
			legend: {
	          display: false
	        },
		}
	  };
  
		
			$('#exampleModal').modal('toggle');
			$http.get(`/rest/statistic/product/${id}`).then(resp => {
			$scope.itemProduct = resp.data;
			$scope.itemProduct.forEach(item => {
				item.orderCreateDate = new Date(item.orderCreateDate);
			});
			
			$http.get(`/rest/products/singleimage/${id}`).then(resp2 => {
				for (var i = 0; i < resp.data.length; i++) {
					$scope.itemProduct[i].imageId = resp2.data[0].imageId;
				}	
			})
			
		});
		$scope.pagerProduct = {
		page: 0,
		size: 4,
		get items() {
			var start = this.page * this.size;
			return $scope.itemProduct.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemProduct.length / this.size);
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
		myChart = new Chart(
		    document.getElementById('myChart'),
		    config
		  );
		  
	}
});






























