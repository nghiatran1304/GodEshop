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
	  var orderDateTime = [];
	  var startDate;
	  var endDate;
	  
	var myChart = null;
	$scope.itemProduct = [];
	$scope.detail = function (id) {
		if(myChart!=null){
        	myChart.destroy();
        	orderDateTime = [];
        	labels = [];
   		}
  
	  
		  data = {
		    labels: labels,
		    datasets: [{
		      label: 'Statistic in month',
		      backgroundColor: 'rgb(255, 99, 132)',
		      borderColor: 'rgb(255, 99, 132)',
		      data: [],
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
			
			for (var i = 0; i < $scope.itemProduct.length; i++) {
				orderDateTime.push({'time' : $scope.itemProduct[i].createDate, 'revenue': $scope.itemProduct[i].quantity});
			}
			orderDateTime.sort((a,b) => Date.parse(b.time) - Date.parse(a.time));
			startDate = orderDateTime[orderDateTime.length-1].time;
			endDate = orderDateTime[0].time;
        	
			var months = monthsBetween(startDate, endDate);
	        months.forEach(item => {
				labels.push(item);
			})
			
			var tempDateTime = [];
			

			orderDateTime.forEach(item => {
				const tempDate = new Date(item.time);
				tempDateTime.push({key: getFormattedDate(tempDate), value: item.revenue});
			})
			
			
			// mang rong
			var dataForChart = [];
			
			var filterdData = filterData(tempDateTime);
			
			for(var i = 0; i < months.length; i++){
				var tempValue = 0;
				for(var j = 0; j < filterdData.length; j++){
					if(months[i] === filterdData[j].key){
						tempValue = filterdData[j].value;
					}
				}
				dataForChart.push({time: months[i], revenue: tempValue});
			}
			
			//---------------------------------
			data.datasets.forEach(item => {
				dataForChart.forEach(item2 => {
					item.data.push(item2.revenue);
				})
			})
			
			$http.get(`/rest/products/singleimage/${id}`).then(resp2 => {
				for (var i = 0; i < resp.data.length; i++) {
					$scope.itemProduct[i].imageId = resp2.data[0].imageId;
					
				}	
			});
			
			
			
			
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
	
	function monthsBetween(...args) {
            let [a, b] = args.map(arg => arg.split("-").slice(0, 2)
                                            .reduce((y, m) => m - 1 + y * 12));
            return Array.from({length: b - a + 1}, _ => a++)
                .map(m => ("0" + (m % 12 + 1)).slice(-2) + "/" + ~~(m / 12));
        	}
        	
        	function getFormattedDate(date) {
			  var year = date.getFullYear();
			
			  var month = (1 + date.getMonth()).toString();
			  month = month.length > 1 ? month : '0' + month;
			
			  var day = date.getDate().toString();
			  day = day.length > 1 ? day : '0' + day;
			  
			  return month + '/' + year;
			}
			
			function filterData(dataToHandling) {
			var tempData = [];
           
            dataToHandling.forEach((item) => {
                var noMatch = true; // temp created match conditioner
                if (tempData.length > 0) {
                    tempData.forEach((tempItem, i) => {
                        if (item.key === tempItem.key) {
                            tempData[i].value += item.value;
                            noMatch = false; // make noMatch = false
                        }
                    });
                }
                return (noMatch) ? tempData.push(item) : null;
            });
            return tempData;
        }
});


























