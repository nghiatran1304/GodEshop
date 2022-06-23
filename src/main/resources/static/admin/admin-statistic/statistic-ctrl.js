app.controller("statistic-ctrl", function($scope, $http) {
	// ---------------- Start Product statistic --------------
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


	// --------- Xu ly du lieu statistic cua tung Product va hien thi Chart bang du lieu cua Product do ----------------
	
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
		
		//-- Phan trang cho product statistic detail
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
		
		// -- Tao chart statistic tu data cua product da xu ly
		myChart = new Chart(
		    document.getElementById('myChart'),
		    config
		);
	}
	
	// ---------------- End Product statistic --------------
	
	// ---------------- Start User statistic --------------
	
	var userTab = document.getElementById('users-tab')
	userTab.addEventListener('click', () => {
		$scope.itemUsers = [];
		$scope.initializeUser = function() {
			$http.get("/rest/statistic/users").then(resp => {
				$scope.itemUsers = resp.data;
			});
		};

		$scope.initializeUser();
	
		$scope.findByNameDto = function(username) {
			username.length == 0 ? $scope.initializeUser() : $http.get(`/rest/statistic/find1User/${username}`).then(resp => {
				$scope.itemUsers = resp.data;
			});
		};
	
		$scope.userPager = {
			page: 0,
			size: 5,
			get items() {
				var start = this.page * this.size;
				return $scope.itemUsers.slice(start, start + this.size);
			},
			get count() {
				return Math.ceil(1.0 * $scope.itemUsers.length / this.size);
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
		
		$scope.detailUser = function(userId) {
			$('#exampleModal2').modal('show')
			$scope.userDetails = [];
			
			$http.get(`/rest/statistic/user/${userId}`).then(resp => {
				$scope.userDetails = resp.data;
			})
			
			$scope.userDetailPager = {
				page: 0,
				size: 10,
				get items() {
					var start = this.page * this.size;
					return $scope.userDetails.slice(start, start + this.size);
				},
				get count() {
					return Math.ceil(1.0 * $scope.userDetails.length / this.size);
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
		}
		
		
		
		
	})
	
	// ---------------- End user statistic --------------
	
	
	
	// ----------------Start Statistic --------------
	var statTab = document.getElementById('statistic-tab')
	statTab.addEventListener('click', () => {
		// Thong ke user theo gioi tinh
		// Xu ly Du lieu
		$scope.totalAccount = 0;
		$scope.totalAccountByMale = 0;
		
		$http.get(`/rest/statistic/totalAccount`).then(respTotalAccount => {
			$scope.totalAccount = respTotalAccount.data;
			$http.get(`/rest/statistic/totalMaleAccount`).then(respTotalAccountByMale => {
				$scope.totalAccountByMale = respTotalAccountByMale.data;
				const dataBuyStatByGender = {
					    labels: [
							'Nam',
						    'Nữ'
					 	],
					  	datasets: [{
					    	label: '',
					    	data: [Math. round($scope.totalAccountByMale/$scope.totalAccount*100), 100-Math. round($scope.totalAccountByMale/$scope.totalAccount*100)],
					    	backgroundColor: [
						  		'rgb(54, 162, 235)',
					      		'rgb(255, 99, 132)',
					    	],
					  	  	hoverOffset: 4
					  	}]
				};
		
				const configBuyStatByGender = {
				  type: 'pie',
				  data: dataBuyStatByGender,
				  
				};
		
				myChart = new Chart(
				    document.getElementById('buyStatByGender'),
				    configBuyStatByGender
				);
			})
		});
		
		
		
		
		// Thong ke tai khoan da dat hang hay chua
		// Xu ly du lieu
		$scope.totalAccount = 0;
		$scope.totalAccountOrdered = 0;
		$http.get(`/rest/statistic/totalAccount`).then(respTotalAccount => {
			$scope.totalAccount = respTotalAccount.data;
			$http.get(`/rest/statistic/totalAccountOrdered`).then(respTotalAccountOrdered => {
				$scope.totalAccountOrdered = respTotalAccountOrdered.data;
				const dataBuyStatByUser = {
					  labels: [
					    'Đã mua',
					    'Chưa mua'
					  ],
					  datasets: [{
					    label: '',
					    data: [Math.round($scope.totalAccountOrdered/$scope.totalAccount*100), 100-Math.round($scope.totalAccountOrdered/$scope.totalAccount*100)],
					    backgroundColor: [
					      'rgb(0,128,0)',
					      'rgb(176,196,222)'
					    ],
					    hoverOffset: 4
					  }]
				};
				
				const configBuyStatByUser = {
				  type: 'pie',
				  data: dataBuyStatByUser,
				  
				};
				
				myChart = new Chart(
				    document.getElementById('buyStatByUser'),
				    configBuyStatByUser
				);
			})
		})
		
		
		// Thong ke don hang theo trang thai
		// Xu ly du lieu
		$scope.totalOrder = 0;
		$scope.totalPending = 0;
		$scope.totalConfirm = 0;
		$scope.totalDelivery = 0;
		$scope.totalComplete = 0;
		$http.get(`/rest/statistic/totalOrder`).then(a => {
			$scope.totalOrder = a.data;
			$http.get(`/rest/statistic/totalPendingOrder`).then(b => {
				$scope.totalPending = b.data;
				$http.get(`/rest/statistic/totalConfirmOrder`).then(c => {
					$scope.totalConfirm = c.data;
					$http.get(`/rest/statistic/totalDeliveryOrder`).then(d => {
						$scope.totalDelivery = d.data;
						$http.get(`/rest/statistic/totalCompleteOrder`).then(e => {
							$scope.totalComplete = e.data;
							$scope.pending = Math.round($scope.totalPending/$scope.totalOrder*100);
							$scope.confirm = Math.round($scope.totalConfirm/$scope.totalOrder*100);
							$scope.delivery = Math.round($scope.totalDelivery/$scope.totalOrder*100);
							$scope.complete = Math.round($scope.totalComplete/$scope.totalOrder*100);
							$scope.cancel = 100-$scope.pending-$scope.confirm-$scope.delivery-$scope.complete;
							const dataOrderStatusStatistic = {
								  labels: [
								    'Pending',
								    'Confỉm',
								    'Delivery',
								    'Complete',
								    'Cancel'
								  ],
								  datasets: [{
								    label: '',
								    data: [$scope.pending, $scope.confirm, $scope.delivery, $scope.complete, $scope.cancel],
								    backgroundColor: [
									  'rgb(255,140,0)',
								      'rgb(0,191,255)',
								      'rgb(0,0,255)',
								      'rgb(50,205,50)',
								      'rgb(255,20,147)'
								    ],
								    hoverOffset: 4
								  }]
							};
							
							const configOrderStatusStatistic = {
							  type: 'pie',
							  data: dataOrderStatusStatistic,
							  
							};
							
							myChart = new Chart(
							    document.getElementById('orderStatusStatistic'),
							    configOrderStatusStatistic
							);
						})
					})
				})
			})
		})
	});
	
	
	// ---------------- End Statistic --------------
	
	// ---------------- Start Best seller nam --------------
	var bestSellerNam = document.getElementById('bestseller-male-tab');
	bestSellerNam.addEventListener('click', () => {
		$scope.itemBestSellerNam = [];
		$http.get(`/rest/statistic/productsByMale/1`).then(resp => {
			$scope.itemBestSellerNam = resp.data;
			$http.get(`/rest/products/image`).then(resp2 => {
				for (var i = 0; i < resp.data.length; i++) {
					if (resp.data.productId == resp2.data.productId) {
						$scope.itemBestSellerNam[i].imageId = resp2.data[i].imageId;
					}
				}	
			});
			
		});
		$scope.sort();
		$scope.bestSellerNamPager = {
				page: 0,
				size: 5,
				get items() {
					var start = this.page * this.size;
					return $scope.itemBestSellerNam.slice(start, start + this.size);
				},
		}
	});
	
	// ---------------- End Best seller nam --------------
	
	// ---------------- Start Best seller nu --------------
	var bestSellerNu = document.getElementById('bestseller-female-tab');
	bestSellerNu.addEventListener('click', () => {
		$scope.itemBestSellerNu = [];
		$http.get(`/rest/statistic/productsByMale/0`).then(resp => {
			$scope.itemBestSellerNu = resp.data;
			$http.get(`/rest/products/image`).then(resp2 => {
				for (var i = 0; i < resp.data.length; i++) {
					if (resp.data.productId == resp2.data.productId) {
						$scope.itemBestSellerNu[i].imageId = resp2.data[i].imageId;
					}
				}
					
			})
		});
		$scope.sort();
		$scope.bestSellerNuPager = {
				page: 0,
				size: 5,
				get items() {
					var start = this.page * this.size;
					return $scope.itemBestSellerNu.slice(start, start + this.size);
				},
		}
		
	});
	
	// ---------------- End Best seller nu --------------
	
	// --------- Cac ham xu ly du lieu -----------
	// Xu ly lay cac thang trong khoang (ngay dau tien product co order - ngay cuoi cung product co order)
	function monthsBetween(...args) {
    	let [a, b] = args.map(arg => arg.split("-").slice(0, 2)
    				.reduce((y, m) => m - 1 + y * 12));
    	return Array.from({length: b - a + 1}, _ => a++)
    				.map(m => ("0" + (m % 12 + 1)).slice(-2) + "/" + ~~(m / 12));
    }
     
    // Xu ly datetime cua du lieu query tu database thanh kieu mm/yyyy de hien thi	
	function getFormattedDate(date) {
		var year = date.getFullYear();
				
		var month = (1 + date.getMonth()).toString();
		month = month.length > 1 ? month : '0' + month;
				
		var day = date.getDate().toString();
		day = day.length > 1 ? day : '0' + day;
				  
		return month + '/' + year;
	}
			
	// Xu ly du lieu khi query tu database (cac san pham duoc mua trong 1 thang se gop lai thanh 1 thang, so luong cac san pham se cong lai voi nhau)
	function filterData(dataToHandling) {
		var tempData = [];
	           
		dataToHandling.forEach((item) => {
			var noMatch = true;
			if (tempData.length > 0) {
				tempData.forEach((tempItem, i) => {
					if (item.key === tempItem.key) {
						tempData[i].value += item.value;
						noMatch = false;
					}
				});
			}
			return (noMatch) ? tempData.push(item) : null;
		});
		return tempData;
	}
	
	// Sort array theo so san pham da ban duoc
	$scope.sort = function() {
		document.getElementById('sort').dispatchEvent(new Event('click'));
		document.getElementById('sort').dispatchEvent(new Event('click'));
	}
	
	
	// ---------------- Start export excel --------------
	// Xuat bao cao thong ke Products
	$scope.exportProducts = function() {
		const table = document.getElementById("TableToExport");
      	const wb = XLSX.utils.table_to_book(table);
      	XLSX.writeFile(wb, "report.xlsx");
	}
	
	// Xuat bao cao thong ke Users
	$scope.exportUsers = function() {
		console.log("b");
	}
	
	// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nam
	$scope.exportBestSellerMale = function() {
		
	}
	
	// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nu
	$scope.exportBestSellerFemale = function() {
		
	}
	
	// Xuat bao cao bieu do ty le user (nam/nu), user (da mua hang/ chua mua hang), don hang (pending/confirm/delivery/complete/cancel))
	$scope.exportStatistic = function() {
		console.log("e");
	}
});


























