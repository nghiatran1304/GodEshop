import { font } from './font.js'

app.controller("statistic-ctrl", function($scope, $http) {
	
	// ---------------- Start Product statistic --------------
	$scope.itemsProduct = [];
	$scope.exportProductsData = [];
	$scope.disabledFlag = true;
	$scope.disabledFlagToggle = function() {
		$scope.disabledFlag = false;
		$scope.getDate('startDateProducts', 'endDateProducts');
	}
	$scope.clear = function() {
		$scope.newStart = null;
		$scope.newEnd = null;
		$("input[type=date]").val("");
		$scope.disabledFlag = true;
		$scope.initialize();
	}
	
	$scope.initialize = function() {
	$scope.start;
	$scope.newStart;
	$scope.end;
	$scope.newEnd;
		if ($scope.newStart == null & $scope.newEnd == null) {
			$http.get("/rest/statistic/products").then(resp => {
				$scope.itemsProduct = resp.data;
				$scope.itemsProduct.forEach(item => {
					$scope.exportProductsData.push({
						ID: item.productId,
						Name: item.productName,
						Stock: item.quantityleft,
						Sold: item.quantity});
				})
				
				$http.get("/rest/products/image").then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemsProduct.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemsProduct[index2].imageId = item.imageId;
							}
						})
					})
				});
				
				
				
				$scope.exportPdfProducts = function() {
					var doc = new jsPDF();
					var header = function () {
	                    doc.setFontSize(18);
	                    doc.setTextColor(255,0,0);
	                    doc.setFontStyle('normal');
	                    doc.text("PRODUCT REPORT", 80 , 10);
               		};
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					var testrows = [];
					$scope.exportProductsData.forEach(item => {
						testrows.push([item.ID, item.Name, item.Stock, item.Sold]);
					})
					doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
					doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}, didDrawPage: header});
			        doc.save('productReport.pdf');
	  				
				}
			});
		}
		$scope.historyByTime = function() {
			$scope.itemsProduct = [];
			$scope.exportProductsData = [];
			$scope.urlFetch = '/rest/statistic/productsByTime/start=' + $scope.newStart + "&end=" +$scope.newEnd;
			$http.get($scope.urlFetch).then(resp => {
				$scope.itemsProduct = resp.data;
				$scope.itemsProduct.forEach(item => {
					$scope.exportProductsData.push({
						ID: item.productId,
						Name: item.productName,
						Stock: item.quantityleft,
						Sold: item.quantity});
				})
				
				$http.get("/rest/products/image").then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemsProduct.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemsProduct[index2].imageId = item.imageId;
							}
						})
					})
				});
				
				// Xuat bao cao thong ke Products
				
				$scope.exportPdfProducts = function() {
					var doc = new jsPDF();
					var header = function () {
	                    doc.setFontSize(18);
	                    doc.setTextColor(255,0,0);
	                    doc.setFontStyle('normal');
	                    doc.text("PRODUCT REPORT", 80 , 10);
               		};
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					var testrows = [];
					$scope.exportProductsData.forEach(item => {
						testrows.push([item.ID, item.Name, item.Stock, item.Sold]);
					})
					doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
					doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}, didDrawPage: header});
			        doc.save('productReport.pdf');
				}
			});
		}
	};

	$scope.initialize();

	$scope.findByNameDto = function(a) {
		$scope.exportProductsData = [];
		if ($scope.newStart == null & $scope.newStart == null) {
			a.length == 0 ? $scope.initialize() : $http.get(`/rest/statistic/products/${a}`).then(resp => {
				$scope.itemsProduct = resp.data;
				$scope.itemsProduct.forEach(item => {
					$scope.exportProductsData.push({
						ID: item.productId,
						Name: item.productName,
						Stock: item.quantityleft,
						Sold: item.quantity});
				})
				$http.get(`/rest/products/image/${a}`).then(resp2 => {
					for (var i = 0; i < resp.data.length; i++) {
						if (resp.data.productId == resp2.data.productId) {
							$scope.itemsProduct[i].imageId = resp2.data[i].imageId;
						}
					}	
				})
			});
		}else {
			a.length == 0 ? $scope.initialize() : $http.get(`/rest/statistic/productsByTime/${a}/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(resp => {
				$scope.itemsProduct = resp.data;
				$scope.itemsProduct.forEach(item => {
					$scope.exportProductsData.push({
						ID: item.productId,
						Name: item.productName,
						Stock: item.quantityleft,
						Sold: item.quantity});
				})
				$http.get(`/rest/products/image/${a}`).then(resp2 => {
					for (var i = 0; i < resp.data.length; i++) {
						if (resp.data.productId == resp2.data.productId) {
							$scope.itemsProduct[i].imageId = resp2.data[i].imageId;
						}
					}	
				})
			});
		}
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
				label: '',
				backgroundColor: 'rgb(255, 99, 132)',
				borderColor: 'rgb(255, 99, 132)',
				data: [],
				}]
		};
		
		$('#exampleModal').modal('toggle');
		if ($scope.newStart == null && $scope.newEnd == null) {
			$http.get(`/rest/statistic/product/${id}`).then(resp => {
				$scope.itemProduct = resp.data;
				for (var i = 0; i < $scope.itemProduct.length; i++) {
					orderDateTime.push({'time' : $scope.itemProduct[i].createDate, 'revenue': $scope.itemProduct[i].quantity});
				}
				orderDateTime.sort((a,b) => Date.parse(b.time) - Date.parse(a.time));
				
				startDate = orderDateTime[orderDateTime.length-1].time;
				endDate = orderDateTime[0].time;
				var months = [];
				var tempDateTime = [];
				
				var dateDiffHanled = dateDiff(startDate, endDate)
				
		        if (dateDiffHanled <= 30) {
					if (dateDiffHanled == 0){
						var {day} = datesBetween(startDate, endDate)
			            day.forEach(item => {
							months.push(item);
						})
						orderDateTime.forEach(item => {
							const tempDate = new Date(item.time);
							tempDateTime.push({key: getFormattedFullDate(tempDate), value: item.revenue});
						})
					}else {
						var {day} = datesBetween(startDate, endDate)
			            day.forEach(item => {
							months.push(item);
						})
						orderDateTime.forEach(item => {
							const tempDate = new Date(item.time);
							tempDateTime.push({key: getFormattedFullDate(tempDate), value: item.revenue});
						})
					}
		        }else if (dateDiffHanled <= 900) {
		            var {month} = datesBetween(startDate, endDate)
		            month.forEach(item => {
						months.push(item);
					})
					orderDateTime.forEach(item => {
						const tempDate = new Date(item.time);
						tempDateTime.push({key: getFormattedDate(tempDate), value: item.revenue});
					})
		        }else {
		            var {year} = datesBetween(startDate, endDate)
		            year.forEach(item => {
						months.push(item);
					})
					orderDateTime.forEach(item => {
						const tempDate = new Date(item.time);
						tempDateTime.push({key: getFormattedYear(tempDate), value: item.revenue});
					})
		        }
				
			    months.forEach(item => {
					labels.push(item);
				})
				
				
					
				var dataForChart = [];
				
				var filterdData = filterData(tempDateTime);
				
				for(var i = 0; i < months.length; i++){
					var tempValue = 0;
					for(var j = 0; j < filterdData.length; j++){
						if(months[i] == filterdData[j].key){
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
				
				  
			  	config = {
			    	type: 'line',
			    	data: data,
			    	options: {
						legend: {
			          		display: false
			       	 	},
					}
			  	};
			  	
			  	// -- Tao chart statistic tu data cua product da xu ly
				myChart = new Chart(
				    document.getElementById('myChart'),
				    config
				);
					
				$http.get(`/rest/products/singleimage/${id}`).then(resp2 => {
					for (var i = 0; i < resp.data.length; i++) {
						$scope.itemProduct[i].imageId = resp2.data[0].imageId;
					}	
				});
			});
		}else {
			$http.get(`/rest/statistic/productByTime/${id}/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(resp => {
				$scope.itemProduct = resp.data;
				
				// ---- start xu ly chart
				for (var i = 0; i < $scope.itemProduct.length; i++) {
					orderDateTime.push({'time' : $scope.itemProduct[i].createDate, 'revenue': $scope.itemProduct[i].quantity});
				}
				orderDateTime.sort((a,b) => Date.parse(b.time) - Date.parse(a.time));
				startDate = orderDateTime[orderDateTime.length-1].time;
				endDate = orderDateTime[0].time;

				var months = [];
				var tempDateTime = [];
				
				var dateDiffHanled = dateDiff($scope.newStart, $scope.newEnd)
				
		        if (dateDiffHanled <= 30) {
		            var {day} = datesBetween($scope.newStart, $scope.newEnd)
		            day.forEach(item => {
						months.push(item);
					})
					orderDateTime.forEach(item => {
						const tempDate = new Date(item.time);
						tempDateTime.push({key: getFormattedFullDate(tempDate), value: item.revenue});
					})
		        }else if (dateDiffHanled <= 900) {
		            var {month} = datesBetween($scope.newStart, $scope.newEnd)
		            month.forEach(item => {
						months.push(item);
					})
					orderDateTime.forEach(item => {
						const tempDate = new Date(item.time);
						tempDateTime.push({key: getFormattedDate(tempDate), value: item.revenue});
					})
		        }else {
		            var {year} = datesBetween($scope.newStart, $scope.newEnd)
		            year.forEach(item => {
						months.push(item);
					})
					orderDateTime.forEach(item => {
						const tempDate = new Date(item.time);
						tempDateTime.push({key: getFormattedYear(tempDate), value: item.revenue});
					})
		        }
				
			    months.forEach(item => {
					labels.push(item);
				})
					
				var dataForChart = [];
				
				var filterdData = filterData(tempDateTime);
				
				for(var i = 0; i < months.length; i++){
					var tempValue = 0;
					for(var j = 0; j < filterdData.length; j++){
						if(months[i] == filterdData[j].key){
							tempValue = filterdData[j].value;
						}
					}
					dataForChart.push({time: months[i], revenue: tempValue});
				}
				// ---- end xu ly chart
					
					//---------------------------------
				data.datasets.forEach(item => {
					dataForChart.forEach(item2 => {
						item.data.push(item2.revenue);
					})
				})
				
				  
			  	config = {
			    	type: 'line',
			    	data: data,
			    	options: {
						legend: {
			          		display: false
			       	 	}
					}
			  	};
			  	
			  	// -- Tao chart statistic tu data cua product da xu ly
				myChart = new Chart(
				    document.getElementById('myChart'),
				    config
				);
					
				$http.get(`/rest/products/singleimage/${id}`).then(resp2 => {
					for (var i = 0; i < resp.data.length; i++) {
						$scope.itemProduct[i].imageId = resp2.data[0].imageId;
					}	
				});
			});
		}
		
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
		
	}
	
	var productTab = document.getElementById('products-tab');
	productTab.addEventListener('click', () => {
		$scope.clearKword();
		$scope.initialize();
	});
	
	// ---------------- End Product statistic --------------
	
	// ---------------- Start User statistic --------------
	
	var userTab = document.getElementById('users-tab');
	userTab.addEventListener('click', () => {
		$scope.clearKword();
		var myChartUser = null;
		$scope.disabledUserFlag = true;
		$scope.disabledUserFlagToggle = function() {
			$scope.disabledUserFlag = false;
			$scope.getDate('startDateUsers', 'endDateUsers');
		}
		$scope.clearUser = function() {
			$scope.newStart = null;
			$scope.newEnd = null;
			$("input[type=date]").val("");
			$scope.disabledUserFlag = true;
			$scope.initializeUser();
		}
		$scope.newStart = null;
		$scope.newEnd = null;
		$scope.itemUsers = [];
		var userStartDate;
		var userEndDate;
		$scope.exportUsersData = []
		$scope.initializeUser = function() {
			if ($scope.newStart == null & $scope.newEnd == null) {
				$http.get("/rest/statistic/users").then(resp => {
					$scope.itemUsers = resp.data;
					
					$scope.itemUsers.forEach(item => {
						$scope.exportUsersData.push({
							Id: item.id,
							Username: item.username,
							Fullname: item.fullname,
							Orders: item.orders,
							TotalBills: item.amount});
					})
				});
				
				// Xuat bao cao thong ke Users
				
				$scope.exportPdfUsers = function() {
					var testcolumns = ["ID", "Username", "Fullname", "Orders", "Total bill"];
					var testrows = [];
					$scope.exportUsersData.forEach(item => {
						testrows.push([item.Id, item.Username, item.Fullname, item.Orders, item.TotalBills]);
					})
				    
				    var doc = new jsPDF();
				    var header = function () {
	                    doc.setFontSize(18);
	                    doc.setTextColor(255,0,0);
	                    doc.setFontStyle('normal');
	                    doc.text("USER REPORT", 80 , 10);
               		};
			        doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}, didDrawPage: header});
	  				doc.save('userReport.pdf');
				}
			}
			$scope.historyByTimeUser = function() {
				$scope.itemUsers = [];
				$scope.exportUsersData = []
				$scope.getDate('startDateUsers', 'endDateUsers');
				$http.get('/rest/statistic/usersByTime/start=' + $scope.newStart + '&end=' + $scope.newEnd).then(resp => {
					$scope.itemUsers = resp.data;
					
					$scope.itemUsers.forEach(item => {
						$scope.exportUsersData.push({
							Id: item.id,
							Username: item.username,
							Fullname: item.fullname,
							Orders: item.orders,
							TotalBills: item.amount});
					})
				});
				
				// Xuat bao cao thong ke Users
				
				$scope.exportPdfUsers = function() {
					var testcolumns = ["ID", "Username", "Fullname", "Orders", "Total bill"];
					var testrows = [];
					$scope.exportUsersData.forEach(item => {
						testrows.push([item.Id, item.Username, item.Fullname, item.Orders, item.TotalBills]);
					})
				    
				    var doc = new jsPDF();
				    var header = function () {
	                    doc.setFontSize(18);
	                    doc.setTextColor(255,0,0);
	                    doc.setFontStyle('normal');
	                    doc.text("USER REPORT", 80 , 10);
               		};
			        doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}, didDrawPage: header});
	  				doc.save('userReport.pdf');
				}
			}
		};

		$scope.initializeUser();
	
		$scope.findByNameDto = function(username) {
			$scope.exportUsersData = []
			if ($scope.newStart == null & $scope.newEnd == null) {
				username.length == 0 ? $scope.initializeUser() : $http.get(`/rest/statistic/find1User/${username}`).then(resp => {
					$scope.itemUsers = resp.data;
					$scope.itemUsers.forEach(item => {
						$scope.exportUsersData.push({
							Id: item.id,
							Username: item.username,
							Fullname: item.fullname,
							Orders: item.orders,
							TotalBills: item.amount});
					})
				});
			}else {
				username.length == 0 ? $scope.initializeUser() : $http.get(`/rest/statistic/find1UserByTime/${username}/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(resp => {
					$scope.itemUsers = resp.data;
					$scope.itemUsers.forEach(item => {
						$scope.exportUsersData.push({
							Id: item.id,
							Username: item.username,
							Fullname: item.fullname,
							Orders: item.orders,
							TotalBills: item.amount});
					})
				});
			}
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
			var userDateTime = [];
			var labels = [];
			var data = {};
			var config = {};
			var handledData = [];
			if (myChartUser!=null) {
				myChartUser.destroy();
				userDateTime = [];
				labels = [];
			}
			
			if ($scope.newStart == null & $scope.newEnd == null) {
				$http.get(`/rest/statistic/user/${userId}`).then(resp => {
					$scope.userDetails = resp.data;
					for (var i = 0; i < $scope.userDetails.length; i++) {
						userDateTime.push({'time': $scope.userDetails[i].createDate, 'orderbill': $scope.userDetails[i].price})
					}
					
					userDateTime.sort((a,b) => Date.parse(b.time) - Date.parse(a.time));
					userStartDate = userDateTime[userDateTime.length-1].time;
					userEndDate = userDateTime[0].time;
					
					var months = [];
					var tempUserDateTime = [];
					
					var dateDiffHanled = dateDiff(userStartDate, userEndDate)
			        if (dateDiffHanled <= 30) {
			            var {day} = datesBetween(userStartDate, userEndDate)
			            day.forEach(item => {
							months.push(item);
						})
						userDateTime.forEach(item => {
							const tempDate = new Date(item.time);
							tempUserDateTime.push({key: getFormattedFullDate(tempDate), value: item.orderbill});
						})
			        }else if (dateDiffHanled <= 900) {
			            var {month} = datesBetween(userStartDate, userEndDate)
			            month.forEach(item => {
							months.push(item);
						})
						userDateTime.forEach(item => {
							const tempDate = new Date(item.time);
							tempUserDateTime.push({key: getFormattedDate(tempDate), value: item.orderbill});
						})
			        }else {
			            var {year} = datesBetween(userStartDate, userEndDate)
			            year.forEach(item => {
							months.push(item);
						})
						userDateTime.forEach(item => {
							const tempDate = new Date(item.time);
							tempUserDateTime.push({key: getFormattedYear(tempDate), value: item.orderbill});
						})
			        }
					
				    months.forEach(item => {
						labels.push(item);
					})
					
					var handledUserData = filterData(tempUserDateTime);
					var dataForUserChart = [];
					
					for (var i = 0; i < months.length; i++) {
						var tempValue = 0;
						for (var j = 0; j < handledUserData.length; j++) {
							if (months[i] == handledUserData[j].key) {
								tempValue = handledUserData[j].value;
							}
						}
						dataForUserChart.push({time: months[i], totalbill: tempValue});
					}
					
					
					data = {
					    labels: labels,
					    datasets: [{
					      label: '',
					      backgroundColor: 'rgb(0, 255, 0)',
					      borderColor: 'rgb(255, 99, 132)',
					      data: [],
					    }]
					};
					
					data.datasets.forEach(item => {
						dataForUserChart.forEach(item2 => {
							item.data.push(item2.totalbill);
						})
					})

			  		config = {
					    type: 'bar',
					    data: data,
					    options: {
							legend: {
				          		display: false
				       	 	},
				       	 	tooltips: {
							    callbacks: {
							        label: function (tooltipItem) {
							            return (new Intl.NumberFormat('en-US', {
							                style: 'currency',
							                currency: 'USD',
							            })).format(tooltipItem.value);
							        }
							    }
							},
							scales: {
						      x: {
						        display: true,
						        title: {
						          display: true
						        }
						      },
						      y: {
						        display: true,
						        title: {
						          display: true,
						          text: 'Value'
						        },
						      },
						      yAxes: [{
						          ticks: {
						            beginAtZero: true,
						            callback: function(value, index, values) {
						              if(parseInt(value) >= 1000){
						                return '$' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '.00';
						              } else {
						                return '$' + value + '.00';
						              }
						            }
						          }
						      }]
						    }
						}
					};
					myChartUser = new Chart(
					    document.getElementById('myChartUser'),
					    config
					);
				})
			}else {
				$http.get(`/rest/statistic/userByTime/${userId}` + '/start=' + $scope.newStart + '&end=' + $scope.newEnd).then(resp => {
					$scope.userDetails = resp.data;
					for (var i = 0; i < $scope.userDetails.length; i++) {
						userDateTime.push({'time': $scope.userDetails[i].createDate, 'orderbill': $scope.userDetails[i].price})
					}
					
					userDateTime.sort((a,b) => Date.parse(b.time) - Date.parse(a.time));
					userStartDate = userDateTime[userDateTime.length-1].time;
					userEndDate = userDateTime[0].time;
					
					var months = [];
					var tempUserDateTime = [];
					
					var dateDiffHanled = dateDiff($scope.newStart, $scope.newEnd)
			        if (dateDiffHanled <= 30) {
			            var {day} = datesBetween($scope.newStart, $scope.newEnd)
			            day.forEach(item => {
							months.push(item);
						})
						userDateTime.forEach(item => {
							const tempDate = new Date(item.time);
							tempUserDateTime.push({key: getFormattedFullDate(tempDate), value: item.orderbill});
						})
			        }else if (dateDiffHanled <= 900) {
			            var {month} = datesBetween($scope.newStart, $scope.newEnd)
			            month.forEach(item => {
							months.push(item);
						})
						userDateTime.forEach(item => {
							const tempDate = new Date(item.time);
							tempUserDateTime.push({key: getFormattedDate(tempDate), value: item.orderbill});
						})
			        }else {
			            var {year} = datesBetween($scope.newStart, $scope.newEnd)
			            year.forEach(item => {
							months.push(item);
						})
						userDateTime.forEach(item => {
							const tempDate = new Date(item.time);
							tempUserDateTime.push({key: getFormattedYear(tempDate), value: item.orderbill});
						})
			        }
					
				    months.forEach(item => {
						labels.push(item);
					})
					
					var handledUserData = filterData(tempUserDateTime);
					var dataForUserChart = [];
					
					for (var i = 0; i < months.length; i++) {
						var tempValue = 0;
						for (var j = 0; j < handledUserData.length; j++) {
							if (months[i] == handledUserData[j].key) {
								tempValue = handledUserData[j].value;
							}
						}
						dataForUserChart.push({time: months[i], totalbill: tempValue});
					}
					
					data = {
					    labels: labels,
					    datasets: [{
					      label: '',
					      backgroundColor: 'rgb(0, 255, 0)',
					      borderColor: 'rgb(255, 99, 132)',
					      data: [],
					    }]
					};
					
					data.datasets.forEach(item => {
						dataForUserChart.forEach(item2 => {
							item.data.push(item2.totalbill);
						})
					})

			  		config = {
					    type: 'bar',
					    data: data,
					    options: {
							legend: {
				          		display: false
				       	 	},
				       	 	tooltips: {
							    callbacks: {
							        label: function (tooltipItem) {
							            return (new Intl.NumberFormat('en-US', {
							                style: 'currency',
							                currency: 'USD',
							            })).format(tooltipItem.value);
							        }
							    }
							},
							scales: {
						      x: {
						        display: true,
						        title: {
						          display: true
						        }
						      },
						      y: {
						        display: true,
						        title: {
						          display: true,
						          text: 'Value'
						        },
						      },
						      yAxes: [{
						          ticks: {
						            beginAtZero: true,
						            callback: function(value, index, values) {
						              if(parseInt(value) >= 1000){
						                return '$' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '.00';
						              } else {
						                return '$' + value + '.00';
						              }
						            }
						          }
						      }]
						    }
						}
					};
					myChartUser = new Chart(
					    document.getElementById('myChartUser'),
					    config
					);
				})
			}
			
			
			
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
		var myChartOrderStat = null;
		var myChartUserStat = null;
		
		
		$scope.disabledStatFlag = true;
		$scope.disabledStatFlagToggle = function() {
			$scope.disabledStatFlag = false;
			$scope.getDate('startDateStat', 'endDateStat');
		}
		$scope.clearStat = function() {
			if(myChartUserStat!= null || myChartOrderStat!=null) {
				myChartUserStat.destroy();
				myChartOrderStat.destroy();
			}
			
			$scope.newStart = null;
			$scope.newEnd = null;
			$("input[type=date]").val("");
			$scope.disabledStatFlag = true;
			$scope.totalAccount = 0;
			$scope.totalAccountOrdered = 0;
			$http.get(`/rest/statistic/totalAccount`).then(respTotalAccount => {
				$scope.totalAccount = respTotalAccount.data;
				$http.get(`/rest/statistic/totalAccountOrdered`).then(respTotalAccountOrdered => {
					$scope.totalAccountOrdered = respTotalAccountOrdered.data;
					const dataBuyStatByUser = {
						  labels: [
						    'Ordered',
						    'Not Order Yet'
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
					  options: {
			                tooltips: {
			                    callbacks: {
			                        label: function (tooltipItems, data) {
			                            return data.labels[tooltipItems.index] +
			                                " : " +
			                                data.datasets[tooltipItems.datasetIndex].data[tooltipItems.index] +
			                                '%';
			                        }
			                    }
			                }
			            }
					};
					myChartUserStat = new Chart(
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
								$scope.pending = Math.round(($scope.totalPending/$scope.totalOrder*100)*100)/100;
								$scope.confirm = Math.round(($scope.totalConfirm/$scope.totalOrder*100)*100)/100;
								$scope.delivery = Math.round(($scope.totalDelivery/$scope.totalOrder*100)*100)/100;
								$scope.complete = Math.round(($scope.totalComplete/$scope.totalOrder*100)*100)/100;
								$scope.cancel = Math.round((100-$scope.pending-$scope.confirm-$scope.delivery-$scope.complete)*100)/100;
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
								  options: {
						                tooltips: {
						                    callbacks: {
						                        label: function (tooltipItems, data) {
						                            return data.labels[tooltipItems.index] +
						                                " : " +
						                                data.datasets[tooltipItems.datasetIndex].data[tooltipItems.index] +
						                                '%';
						                        }
						                    }
						                }
						            }
								};
								myChartOrderStat = new Chart(
								    document.getElementById('orderStatusStatistic'),
								    configOrderStatusStatistic
								);
							})
						})
					})
				})
			})
		}
		$scope.clearChart = function() {
			myChartUserStat.destroy();
			myChartOrderStat.destroy();
		}
		$scope.newStart = null;
		$scope.newEnd = null;
		// Thong ke user theo gioi tinh
		// Xu ly Du lieu
		$scope.totalAccount = 0;
		$scope.totalAccountByMale = 0;
		if ($scope.newStart == null & $scope.newEnd == null) {
			if(myChartUserStat!= null || myChartOrderStat!=null) {
				myChartUserStat.destroy();
				myChartOrderStat.destroy();
			}
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
						    'Ordered',
						    'Not Order Yet'
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
					  options: {
			                tooltips: {
			                    callbacks: {
			                        label: function (tooltipItems, data) {
			                            return data.labels[tooltipItems.index] +
			                                " : " +
			                                data.datasets[tooltipItems.datasetIndex].data[tooltipItems.index] +
			                                '%';
			                        }
			                    }
			                }
			            }
					};
					
					myChartUserStat = new Chart(
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
								$scope.pending = Math.round(($scope.totalPending/$scope.totalOrder*100)*100)/100;
								$scope.confirm = Math.round(($scope.totalConfirm/$scope.totalOrder*100)*100)/100;
								$scope.delivery = Math.round(($scope.totalDelivery/$scope.totalOrder*100)*100)/100;
								$scope.complete = Math.round(($scope.totalComplete/$scope.totalOrder*100)*100)/100;
								$scope.cancel = Math.round((100-$scope.pending-$scope.confirm-$scope.delivery-$scope.complete)*100)/100;
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
								  options: {
						                tooltips: {
						                    callbacks: {
						                        label: function (tooltipItems, data) {
						                            return data.labels[tooltipItems.index] +
						                                " : " +
						                                data.datasets[tooltipItems.datasetIndex].data[tooltipItems.index] +
						                                '%';
						                        }
						                    }
						                }
						            }
								};
								myChartOrderStat = new Chart(
								    document.getElementById('orderStatusStatistic'),
								    configOrderStatusStatistic
								);
							})
						})
					})
				})
			})
		}
		
		$scope.showStat = function() {
			if(myChartUserStat!= null || myChartOrderStat!=null) {
				myChartUserStat.destroy();
				myChartOrderStat.destroy();
			}
	   		
			$scope.getDate('startDateStat', 'endDateStat');
			// Thong ke tai khoan da dat hang hay chua
			// Xu ly du lieu
			$scope.totalAccount = 0;
			$scope.totalAccountOrdered = 0;
			$http.get(`/rest/statistic/totalAccount`).then(respTotalAccount => {
				$scope.totalAccount = respTotalAccount.data;
				$http.get(`/rest/statistic/totalAccountOrderedByTime/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(respTotalAccountOrdered => {
					$scope.totalAccountOrdered = respTotalAccountOrdered.data;
					const dataBuyStatByUser = {
						  labels: [
						    'Ordered',
						    'Not Order Yet'
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
					  options: {
			                tooltips: {
			                    callbacks: {
			                        label: function (tooltipItems, data) {
			                            return data.labels[tooltipItems.index] +
			                                " : " +
			                                data.datasets[tooltipItems.datasetIndex].data[tooltipItems.index] +
			                                '%';
			                        }
			                    }
			                }
			            }
					};
					
					myChartUserStat = new Chart(
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
			$http.get(`/rest/statistic/totalOrderByTime/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(a => {
				$scope.totalOrder = a.data;
				$http.get(`/rest/statistic/totalPendingOrderByTime/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(b => {
					$scope.totalPending = b.data;
					$http.get(`/rest/statistic/totalConfirmOrderByTime/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(c => {
						$scope.totalConfirm = c.data;
						$http.get(`/rest/statistic/totalDeliveryOrderByTime/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(d => {
							$scope.totalDelivery = d.data;
							$http.get(`/rest/statistic/totalCompleteOrderByTime/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(e => {
								$scope.totalComplete = e.data;
								$scope.pending = Math.round(($scope.totalPending/$scope.totalOrder*100)*100)/100;
								$scope.confirm = Math.round(($scope.totalConfirm/$scope.totalOrder*100)*100)/100;
								$scope.delivery = Math.round(($scope.totalDelivery/$scope.totalOrder*100)*100)/100;
								$scope.complete = Math.round(($scope.totalComplete/$scope.totalOrder*100)*100)/100;
								$scope.cancel = Math.round((100-$scope.pending-$scope.confirm-$scope.delivery-$scope.complete)*100)/100;
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
								  options: {
						                tooltips: {
						                    callbacks: {
						                        label: function (tooltipItems, data) {
						                            return data.labels[tooltipItems.index] +
						                                " : " +
						                                data.datasets[tooltipItems.datasetIndex].data[tooltipItems.index] +
						                                '%';
						                        }
						                    }
						                }
						            }
								};
								myChartOrderStat = new Chart(
								    document.getElementById('orderStatusStatistic'),
								    configOrderStatusStatistic
								);
							})
						})
					})
				})
			})
		}
	});
	
	
	// ---------------- End Statistic --------------
	
	// ---------------- Start Best seller nam --------------
	var bestSellerNam = document.getElementById('bestseller-male-tab');
	bestSellerNam.addEventListener('click', () => {
		$scope.disabled5MaleFlag = true;
		$scope.disabled5MaleFlagToggle = function() {
			$scope.disabled5MaleFlag = false;
			$scope.getDate('startDate5Male', 'endDate5Male');
		}
		$scope.clear5Male = function() {
			$scope.newStart = null;
			$scope.newEnd = null;
			$("input[type=date]").val("");
			$scope.disabled5MaleFlag = true;
			$http.get(`/rest/statistic/productsByMale/1`).then(resp => {
					$scope.itemBestSellerNam = resp.data;
					$scope.itemBestSellerNam.forEach(item => {
						$scope.exportMaleData.push({
								Id: item.productId,
								Name: item.productName,
								Stock: item.quantityleft,
								Sold: item.quantity});
					}) 
					
					$http.get(`/rest/products/image`).then(resp2 => {
						resp2.data.forEach((item, index) => {
							$scope.itemBestSellerNam.forEach((item2, index2) => {
								if (item.productId == item2.productId) {
									$scope.itemBestSellerNam[index2].imageId = item.imageId;
								}
							})
						})	
					});
					
					
					// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nam
					
					$scope.exportPdfBestSellerMale = function() {
						var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
						
						// Can xu ly $scope.exportMaleData lay 5 thang co $scope.exportMaleData.itemProductSold cao nhat
						//
						var testrows = [];
						$scope.exportMaleData.forEach(item => {
							testrows.push([item.Id, item.Name, item.Stock, item.Sold]);
						})
					    
					    var doc = new jsPDF();
					    var header = function () {
		                    doc.setFontSize(18);
		                    doc.setTextColor(255,0,0);
		                    doc.setFontStyle('normal');
		                    doc.text("BEST SELLER BY MALE REPORT", 60 , 10);
	               		};
					    doc.addFileToVFS("MyFont.ttf", font);
				        doc.addFont("MyFont.ttf", "MyFont", "normal");
				        doc.setFont("MyFont");
					    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}, didDrawPage: header});
		  				doc.save('bestSellerMaleReport.pdf');
					}
					
				});
			
		}
		$scope.newStart = null;
		$scope.newEnd = null;
		$scope.itemBestSellerNam = [];
		$scope.exportMaleData = [];
		if ($scope.newStart == null && $scope.newEnd == null) {
				$http.get(`/rest/statistic/productsByMale/1`).then(resp => {
					$scope.itemBestSellerNam = resp.data;
					$scope.itemBestSellerNam.forEach(item => {
						$scope.exportMaleData.push({
								Id: item.productId,
								Name: item.productName,
								Stock: item.quantityleft,
								Sold: item.quantity});
					}) 
					
					$http.get(`/rest/products/image`).then(resp2 => {
						resp2.data.forEach((item, index) => {
							$scope.itemBestSellerNam.forEach((item2, index2) => {
								if (item.productId == item2.productId) {
									$scope.itemBestSellerNam[index2].imageId = item.imageId;
								}
							})
						})
					});
					
					
					// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nam
					
					$scope.exportPdfBestSellerMale = function() {
						var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
						
						// Can xu ly $scope.exportMaleData lay 5 thang co $scope.exportMaleData.itemProductSold cao nhat
						//
						var testrows = [];
						$scope.exportMaleData.forEach(item => {
							testrows.push([item.Id, item.Name, item.Stock, item.Sold]);
						})
					    
					    var doc = new jsPDF();
					    var header = function () {
		                    doc.setFontSize(18);
		                    doc.setTextColor(255,0,0);
		                    doc.setFontStyle('normal');
		                    doc.text("BEST SELLER BY MALE REPORT", 60 , 10);
	               		};
					    doc.addFileToVFS("MyFont.ttf", font);
				        doc.addFont("MyFont.ttf", "MyFont", "normal");
				        doc.setFont("MyFont");
					    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}, didDrawPage: header});
		  				doc.save('bestSellerMaleReport.pdf');
					}
					
				});
				
		}
		
		$scope.best5Male = function() {
			$scope.itemBestSellerNam = [];
			$scope.exportMaleData = [];
			$scope.getDate('startDate5Male', 'endDate5Male');
			$http.get(`/rest/statistic/productsByMaleByTime/1/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(resp => {
				$scope.itemBestSellerNam = resp.data;
				$scope.itemBestSellerNam.forEach(item => {
					$scope.exportMaleData.push({
								Id: item.productId,
								Name: item.productName,
								Stock: item.quantityleft,
								Sold: item.quantity});
				}) 
				
				$http.get(`/rest/products/image`).then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemBestSellerNam.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemBestSellerNam[index2].imageId = item.imageId;
							}
						})
					})
				});
				
				
				// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nam
				
				$scope.exportPdfBestSellerMale = function() {
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					
					// Can xu ly $scope.exportMaleData lay 5 thang co $scope.exportMaleData.itemProductSold cao nhat
					//
					var testrows = [];
					$scope.exportMaleData.forEach(item => {
						testrows.push([item.Id, item.Name, item.Stock, item.Sold]);
					})
				    
				    var doc = new jsPDF();
				    var header = function () {
						doc.setFontSize(18);
						doc.setTextColor(255,0,0);
						doc.setFontStyle('normal');
						doc.text("BEST SELLER BY MALE REPORT", 60 , 10);
	               	};
				    doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}, didDrawPage: header});
	  				doc.save('bestSellerMaleReport.pdf');
				}
			});
		}
		
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
		$scope.disabled5FemaleFlag = true;
		$scope.disabled5FemaleFlagToggle = function() {
			$scope.disabled5FemaleFlag = false;
			$scope.getDate('startDate5Female', 'endDate5Female');
			
		}
		$scope.clear5Female = function() {
			$scope.newStart = null;
			$scope.newEnd = null;
			$("input[type=date]").val("");
			$scope.disabled5FemaleFlag = true;
			$http.get(`/rest/statistic/productsByMale/0`).then(resp => {
				$scope.itemBestSellerNu = resp.data;
				
				$scope.itemBestSellerNu.forEach(item => {
					$scope.exportFemaleData.push({
							Id: item.productId,
							Name: item.productName,
							Stock: item.quantityleft,
							Sold: item.quantity});
				}) 
				
				$http.get(`/rest/products/image`).then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemBestSellerNu.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemBestSellerNu[index2].imageId = item.imageId;
							}
						})
					})
				})
				// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nu
				
				$scope.exportPdfBestSellerFemale = function() {
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					// Can xu ly $scope.exportFemaleData lay 5 thang co $scope.exportMaleData.itemProductSold cao nhat
					//
					var testrows = [];
					$scope.exportFemaleData.forEach(item => {
						testrows.push([item.Id, item.Name, item.Stock, item.Sold]);
					})
				    
				    var doc = new jsPDF();
				    var header = function () {
						doc.setFontSize(18);
						doc.setTextColor(255,0,0);
						doc.setFontStyle('normal');
		            	doc.text("BEST SELLER BY MALE REPORT", 60 , 10);
	               	};
				    doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}, didDrawPage: header});
	  				doc.save('bestSellerFemaleReport.pdf');
				}
			});
			
		}
		
		$scope.newStart = null;
		$scope.newEnd = null;
		$scope.itemBestSellerNu = [];
		$scope.exportFemaleData = [];
		if ($scope.newStart == null & $scope.newEnd == null) {
			$http.get(`/rest/statistic/productsByMale/0`).then(resp => {
				$scope.itemBestSellerNu = resp.data;
				$scope.itemBestSellerNu.forEach(item => {
					$scope.exportFemaleData.push({
							Id: item.productId,
							Name: item.productName,
							Stock: item.quantityleft,
							Sold: item.quantity});
				}) 
				
				$http.get(`/rest/products/image`).then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemBestSellerNu.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemBestSellerNu[index2].imageId = item.imageId;
							}
						})
					})
				})
				// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nu
				
				$scope.exportPdfBestSellerFemale = function() {
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					// Can xu ly $scope.exportFemaleData lay 5 thang co $scope.exportMaleData.itemProductSold cao nhat
					//
					var testrows = [];
					$scope.exportFemaleData.forEach(item => {
						testrows.push([item.Id, item.Name, item.Stock, item.Sold]);
					})
				    
				    var doc = new jsPDF();
				    var header = function () {
						doc.setFontSize(18);
						doc.setTextColor(255,0,0);
						doc.setFontStyle('normal');
						doc.text("BEST SELLER BY FEMALE REPORT", 60 , 10);
	               	};
				    doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}, didDrawPage: header});
	  				doc.save('bestSellerFemaleReport.pdf');
				}
			});
		}
		
		$scope.best5Female = function() {
			$scope.itemBestSellerNu = [];
			$scope.exportFemaleData = [];
			$scope.getDate('startDate5Female', 'endDate5Female');
			$http.get(`/rest/statistic/productsByMaleByTime/0/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(resp => {
				$scope.itemBestSellerNu = resp.data;
				$scope.itemBestSellerNu.forEach(item => {
					$scope.exportFemaleData.push({
							Id: item.productId,
							Name: item.productName,
							Stock: item.quantityleft,
							Sold: item.quantity});
				}) 
				
				$http.get(`/rest/products/image`).then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemBestSellerNu.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemBestSellerNu[index2].imageId = item.imageId;
							}
						})
					})
				})
				// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nu
				
				$scope.exportPdfBestSellerFemale = function() {
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					//
					var testrows = [];
					$scope.exportFemaleData.forEach(item => {
						testrows.push([item.Id, item.Name, item.Stock, item.Sold]);
					})
				    
				    var doc = new jsPDF();
				    var header = function () {
						doc.setFontSize(18);
						doc.setTextColor(255,0,0);
						doc.setFontStyle('normal');
						doc.text("BEST SELLER BY FEMALE REPORT", 60 , 10);
	               	};
				    doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}, didDrawPage: header});
	  				doc.save('bestSellerFemaleReport.pdf');
				}
			});
		}
		
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
	
	// ----------------Start sale revenue --------------
	var myChartRevenue = null;
	var labels = [];
	var data = {};
	var config = {};
	var saleRevenueTab = document.getElementById('salerevenue-tab');
	let urlGetRevenueAndOrders = 'http://localhost:8080/rest/statistic/revenueAndOrders/date='
	let urlGetRevenueByTime = 'http://localhost:8080/rest/statistic/getAllRevenueByTime/start='
	let urlAllTheRevenue = 'http://localhost:8080/rest/statistic/getAllRevenue'
	let urlGetAllStat = 'http://localhost:8080/rest/statistic/revenueByAll'
	let urlGetStatByTime = 'http://localhost:8080/rest/statistic/revenue/start='
	saleRevenueTab.addEventListener('click', () => {
		$scope.startDateInput = "";
		$scope.endDateInput = "";
		$scope.revenueToday = 0;
		$scope.revenueYesterday = 0;
		$scope.revenueDiff = 0;
		$scope.ordersToday = 0;
		$scope.ordersYesterday = 0;
		$scope.ordersDiff = 0;
		var today = new Date();
		var dd = String(today.getDate()).padStart(2, '0');
		var mm = String(today.getMonth() + 1).padStart(2, '0');
		var yyyy = today.getFullYear();
		today = yyyy + '-' + mm + '-' + dd;
		$http.get(urlGetRevenueAndOrders + today).then(resp => {
			$scope.revenueToday = resp.data.totalRevenueToday;
			$scope.revenueYesterday = resp.data.totalRevenueYesterday;
			$scope.ordersToday = resp.data.totalOrdersToday;
			$scope.ordersYesterday = resp.data.totalOrdersYesterday;
			
			if ($scope.revenueYesterday == 0) {
				$scope.revenueDiff = Math.floor(($scope.revenueToday/1)*100);
				$scope.revenueDiffPercent = $scope.revenueDiff + '%';
			}else if ($scope.revenueToday > $scope.revenueYesterday){
				$scope.revenueDiff = Math.floor((($scope.revenueToday-$scope.revenueYesterday)/$scope.revenueYesterday)*100);
				$scope.revenueDiffPercent = $scope.revenueDiff + '%';
			}else {
				$scope.revenueDiff = Math.floor(($scope.revenueYesterday/$scope.revenueToday)*100);
				$scope.revenueDiffPercent = '0%';
			}
			
			
			if ($scope.ordersYesterday == 0) {
				$scope.ordersDiff = Math.floor(($scope.ordersToday/1)*100);
				$scope.ordersDiffPercent = $scope.ordersDiff + '%';
			}else if ($scope.ordersToday > $scope.ordersYesterday){
				$scope.ordersDiff = Math.floor((($scope.ordersToday-$scope.ordersYesterday)/$scope.ordersYesterday)*100);
				$scope.ordersDiffPercent = $scope.ordersDiff + '%';
			}else {
				$scope.ordersDiff = Math.floor(($scope.ordersYesterday/$scope.ordersToday)*100);
				$scope.ordersDiffPercent = '0%';
			}
			
		})
		
		
		$scope.newStart;
		$scope.newEnd;
		$scope.disabledFlag = true;
		$scope.disabledFlagToggle = function() {
			$scope.disabledFlag = false;
			$scope.getDate('revenueStart', 'revenueEnd');
		}
	
		$scope.clearRevenueChart = function() {
			$scope.newStart;
			$scope.newEnd;
			$("input[type=date]").val("");
			$scope.disabledStatFlag = true;
			$scope.initRevenueChart();
		}
		
		$scope.totalRevenue = [];
		var startDateRevenue;
		var endDateRevenue;
		$scope.initRevenueChart = function() {
			if (myChartRevenue!=null) {
				myChartRevenue.destroy();
				labels = [];
				data = {};
			}
			
			$http.get(urlGetAllStat).then(resp => {
				$scope.totalRevenue = resp.data;
			})
			
			$scope.allRevenueData = [];
			$scope.allRevenueCompletedData = [];
			$scope.allRevenueCanceledData = [];
			$http.get(urlAllTheRevenue).then(resp => {
				resp.data.forEach(item => {
					if (item.type === 'Total') {
						$scope.allRevenueData.push({'createDate': item.createDate, 'totalRevenue' : item.totalRevenue});
					}else if (item.type === 'Completed') {
						$scope.allRevenueCompletedData.push({'createDate': item.createDate, 'totalRevenue' : item.totalRevenue});
					}else {
						$scope.allRevenueCanceledData.push({'createDate': item.createDate, 'totalRevenue' : item.totalRevenue});
					}
					console.log($scope.allRevenueCompletedData)
				})
				
				
				startDateRevenue = $scope.allRevenueData[0].createDate;
				endDateRevenue = $scope.allRevenueData[$scope.allRevenueData.length-1].createDate;
				var months = [];
				
				var dateDiffHanled = dateDiff(startDateRevenue, endDateRevenue)
		        if (dateDiffHanled <= 30) {
					if (dateDiffHanled == 0){
						var {day} = datesBetween(startDateRevenue, endDateRevenue)
			            day.forEach(item => {
							months.push(item);
						})
					}else {
						var {day} = datesBetween(startDateRevenue, endDateRevenue)
			            day.forEach(item => {
							months.push(item);
						})
					}
		        }else if (dateDiffHanled <= 900) {
		            var {month} = datesBetween(startDateRevenue, endDateRevenue)
		            month.forEach(item => {
						months.push(item);
					})
		        }else {
		            var {year} = datesBetween(startDateRevenue, endDateRevenue)
		            year.forEach(item => {
						months.push(item);
					})
		        }
		        
		        $scope.allRevenueDataHandled = [];
				$scope.allRevenueCompletedDataHandled = [];
				$scope.allRevenueCanceledDataHandled = [];
		        
		        $scope.allRevenueData.forEach(item => {
					const tempDate = new Date(item.createDate);
					$scope.allRevenueDataHandled.push({key: getFormattedYear(tempDate), value: item.totalRevenue});
				})
				
				$scope.allRevenueCompletedData.forEach(item => {
					const tempDate = new Date(item.createDate);
					$scope.allRevenueCompletedDataHandled.push({key: getFormattedYear(tempDate), value: item.totalRevenue});
				})
				
				$scope.allRevenueCanceledData.forEach(item => {
					const tempDate = new Date(item.createDate);
					$scope.allRevenueCanceledDataHandled.push({key: getFormattedYear(tempDate), value: item.totalRevenue});
				})
				
				
				
			    months.forEach(item => {
					labels.push(item);
				})
				
				var handledRevenueData = filterData($scope.allRevenueDataHandled);
				var allRevenueForChart = [];
				
							
				for (var i = 0; i < months.length; i++) {
					var tempAllValue = 0;
					for (var j = 0; j < handledRevenueData.length; j++) {
						if (months[i] == handledRevenueData[j].key) {
							tempAllValue = handledRevenueData[j].value;
						}
					}
					allRevenueForChart.push({createDate: months[i], totalRevenue: tempAllValue});
				}
				
				var handledRevenueFromCompletedData = filterData($scope.allRevenueCompletedDataHandled);
				var revenueFromCompletedForChart = [];
				
							
				for (var i = 0; i < months.length; i++) {
					var tempCompletedValue = 0;
					for (var j = 0; j < handledRevenueFromCompletedData.length; j++) {
						if (months[i] == handledRevenueFromCompletedData[j].key) {
							tempCompletedValue = handledRevenueFromCompletedData[j].value;
						}
					}
					revenueFromCompletedForChart.push({createDate: months[i], totalRevenue: tempCompletedValue});
				}
				
				var handledRevenueFromCanceledData = filterData($scope.allRevenueCanceledDataHandled);
				var revenueFromCanceledForChart = [];
				
							
				for (var i = 0; i < months.length; i++) {
					var tempCanceledValue = 0;
					for (var j = 0; j < handledRevenueFromCanceledData.length; j++) {
						if (months[i] == handledRevenueFromCanceledData[j].key) {
							tempCanceledValue = handledRevenueFromCanceledData[j].value;
						}
					}
					revenueFromCanceledForChart.push({createDate: months[i], totalRevenue: tempCanceledValue});
				}
				
				data = {
				  labels: labels,
				  datasets: [
				    {
				      label: 'Tổng doanh thu',
				      data: [],
				      borderColor: 'rgb(0,0,255)',
				      fill: false,
				      cubicInterpolationMode: 'monotone',
				      tension: 0.4
				    }, {
				      label: 'Tổng doanh thu đơn hàng thành công',
				      data: [],
				      borderColor: 'rgb(0,255,0)',
				      fill: false,
				      tension: 0.4
				    }, {
				      label: 'Tổng doanh thu đơn hàng huỷ',
				      data: [],
				      borderColor: 'rgb(255, 99, 132)',
				      fill: false
				    }
				  ]
				};
							
				allRevenueForChart.forEach(item => {
					data.datasets[0].data.push(item.totalRevenue);
				})
				
				revenueFromCompletedForChart.forEach(item => {
					data.datasets[1].data.push(item.totalRevenue);
				})
				
				revenueFromCanceledForChart.forEach(item => {
					data.datasets[2].data.push(item.totalRevenue);
				})
				
				config = {
				  type: 'line',
				  data: data,
				  options: {
				    responsive: false,
				    plugins: {
				      title: {
				        display: true,
				        text: 'Biểu đồ doanh thu'
				      },
				    },
				    tooltips: {
						callbacks: {
							label: function (tooltipItem) {
								return (new Intl.NumberFormat('en-US', {
									style: 'currency',
									currency: 'USD',
							    })).format(tooltipItem.value);
							 }
						}
					},
				    interaction: {
				      intersect: false,
				    },
				    scales: {
				      x: {
				        display: true,
				        title: {
				          display: true
				        }
				      },
				      y: {
				        display: true,
				        title: {
				          display: true,
				          text: 'Value'
				        },
				      },
				      yAxes: [{
				          ticks: {
				            beginAtZero: true,
				            callback: function(value, index, values) {
				              if(parseInt(value) >= 1000){
				                return '$' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '.00';
				              } else {
				                return '$' + value + '.00';
				              }
				            }
				          }
				      }]
				    }
				  },
				};
				
				myChartRevenue = new Chart(
					document.getElementById('myChartRevenue'),
					config
				);
			})
		}
		
		$scope.initRevenueChart();
		
		
		$scope.showRevenueChart = function() {
			if (myChartRevenue!=null) {
				myChartRevenue.destroy();
				labels = [];
				data = {};
			}
			
			$http.get(urlGetStatByTime + $scope.newStart + '&end=' + $scope.newEnd).then(resp => {
				$scope.totalRevenue = resp.data;
			})
			
			$scope.allRevenueData = [];
			$scope.allRevenueCompletedData = [];
			$scope.allRevenueCanceledData = [];
			var allRevenueDataTemp = [];
			var allRevenueCompletedDataTemp = [];
			var allRevenueCanceledDataTemp = [];
			$http.get(urlGetRevenueByTime + $scope.newStart + '&end=' + $scope.newEnd).then(resp => {
				resp.data.forEach(item => {
					if (item.type === 'Total') {
						$scope.allRevenueData.push({createDate: item.createDate, totalRevenue : item.totalRevenue});
					}else if (item.type === 'Completed') {
						$scope.allRevenueCompletedData.push({createDate: item.createDate, totalRevenue : item.totalRevenue});
					}else {
						$scope.allRevenueCanceledData.push({createDate: item.createDate, totalRevenue : item.totalRevenue});
					}
				})
				
				var months = [];
				
				var dateDiffHanled = dateDiff($scope.newStart, $scope.newEnd);
				
		        if (dateDiffHanled <= 30) {
					if (dateDiffHanled == 0){
						var {day} = datesBetween($scope.newStart, $scope.newEnd);
			            day.forEach(item => {
							months.push(item);
						})
					}else {
						var {day} = datesBetween($scope.newStart, $scope.newEnd);
			            day.forEach(item => {
							months.push(item);
						})
						
					}
					
					$scope.allRevenueData.forEach(item => {
						const tempDate = new Date(item.createDate);
						allRevenueDataTemp.push({key: getFormattedFullDate(tempDate), value: item.totalRevenue});
					})
										
					$scope.allRevenueCompletedData.forEach(item => {
						const tempDate = new Date(item.createDate);
						allRevenueCompletedDataTemp.push({key: getFormattedFullDate(tempDate), value: item.totalRevenue});
					})
										
					$scope.allRevenueCanceledData.forEach(item => {
						const tempDate = new Date(item.createDate);
						allRevenueCanceledDataTemp.push({key: getFormattedFullDate(tempDate), value: item.totalRevenue});
					})
		        }else if (dateDiffHanled <= 900) {
		            var {month} = datesBetween($scope.newStart, $scope.newEnd);
		            month.forEach(item => {
						months.push(item);
					})
					$scope.allRevenueData.forEach(item => {
						const tempDate = new Date(item.createDate);
						allRevenueDataTemp.push({key: getFormattedDate(tempDate), value: item.totalRevenue});
					})
										
					$scope.allRevenueCompletedData.forEach(item => {
						const tempDate = new Date(item.createDate);
						allRevenueCompletedDataTemp.push({key: getFormattedDate(tempDate), value: item.totalRevenue});
					})
										
					$scope.allRevenueCanceledData.forEach(item => {
						const tempDate = new Date(item.createDate);
						allRevenueCanceledDataTemp.push({key: getFormattedDate(tempDate), value: item.totalRevenue});
					})
		        }else {
		            var {year} = datesBetween($scope.newStart, $scope.newEnd);
		            year.forEach(item => {
						months.push(item);
					})
					
					$scope.allRevenueData.forEach(item => {
						const tempDate = new Date(item.createDate);
						allRevenueDataTemp.push({key: getFormattedYear(tempDate), value: item.totalRevenue});
					})
										
					$scope.allRevenueCompletedData.forEach(item => {
						const tempDate = new Date(item.createDate);
						allRevenueCompletedDataTemp.push({key: getFormattedYear(tempDate), value: item.totalRevenue});
					})
										
					$scope.allRevenueCanceledData.forEach(item => {
						const tempDate = new Date(item.createDate);
						allRevenueCanceledDataTemp.push({key: getFormattedYear(tempDate), value: item.totalRevenue});
					})
		        }
		        
			    months.forEach(item => {
					labels.push(item);
				})
				
				var filterdRevenueData = filterData(allRevenueDataTemp);
				var dataRevenueForChart = [];
				
				for(var i = 0; i < months.length; i++){
					var tempValue = 0;
					for(var j = 0; j < filterdRevenueData.length; j++){
						if(months[i] == filterdRevenueData[j].key){
							tempValue = filterdRevenueData[j].value;
						}
					}
					dataRevenueForChart.push({key: months[i], value: tempValue});
				}
				
				var filterdCompletedData = filterData(allRevenueCompletedDataTemp);
				var dataCompletedForChart = [];
				
				for(var i = 0; i < months.length; i++){
					var tempValue = 0;
					for(var j = 0; j < filterdCompletedData.length; j++){
						if(months[i] == filterdCompletedData[j].key){
							tempValue = filterdCompletedData[j].value;
						}
					}
					dataCompletedForChart.push({key: months[i], value: tempValue});
				}
				
				var filterdCanceledData = filterData(allRevenueCanceledDataTemp);
				var dataCanceledForChart = [];
				
				for(var i = 0; i < months.length; i++){
					var tempValue = 0;
					for(var j = 0; j < filterdCanceledData.length; j++){
						if(months[i] == filterdCanceledData[j].key){
							tempValue = filterdCanceledData[j].value;
						}
					}
					dataCanceledForChart.push({key: months[i], value: tempValue});
				}
				
				
				data = {
				  labels: labels,
				  datasets: [
				    {
				      label: 'Tổng doanh thu',
				      data: [],
				      borderColor: 'rgb(0,0,255)',
				      fill: false,
				      cubicInterpolationMode: 'monotone',
				      tension: 0.4
				    }, {
				      label: 'Tổng doanh thu đơn hàng thành công',
				      data: [],
				      borderColor: 'rgb(0,255,0)',
				      fill: false,
				      tension: 0.4
				    }, {
				      label: 'Tổng doanh thu đơn hàng huỷ',
				      data: [],
				      borderColor: 'rgb(255, 99, 132)',
				      fill: false
				    }
				  ]
				};
				
				dataRevenueForChart.forEach(item => {
					data.datasets[0].data.push(item.value);
				})
				
				dataCompletedForChart.forEach(item => {
					data.datasets[1].data.push(item.value);
				})
				
				dataCanceledForChart.forEach(item => {
					data.datasets[2].data.push(item.value);
				})
				
				config = {
				  type: 'line',
				  data: data,
				  options: {
				    responsive: false,
				    plugins: {
				      title: {
				        display: true,
				        text: 'Biểu đồ doanh thu'
				      },
				    },tooltips: {
						callbacks: {
							label: function (tooltipItem) {
								return (new Intl.NumberFormat('en-US', {
									style: 'currency',
									currency: 'USD',
							    })).format(tooltipItem.value);
							 }
						}
					},
				    interaction: {
				      intersect: false,
				    },
				    scales: {
				      x: {
				        display: true,
				        title: {
				          display: true
				        }
				      },
				      y: {
				        display: true,
				        title: {
				          display: true,
				          text: 'Value'
				        },
				      },
				      yAxes: [{
				          ticks: {
				            beginAtZero: true,
				            callback: function(value, index, values) {
				              if(parseInt(value) >= 1000){
				                return '$' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '.00';
				              } else {
				                return '$' + value + '.00';
				              }
				            }
				          }
				      }]
				    }
				  },
				};
				
				myChartRevenue = new Chart(
					document.getElementById('myChartRevenue'),
					config
				);
				
			})
		}
	})
	// ----------------End sale revenue --------------
	
	// --------- Cac ham xu ly du lieu -----------
	// Xu ly lay cac thang trong khoang (ngay dau tien product co order - ngay cuoi cung product co order)
	function monthsBetween(...args) {
    	let [a, b] = args.map(arg => arg.split("-").slice(0, 2)
    				.reduce((y, m) => m - 1 + y * 12));
    	return Array.from({length: b - a + 1}, _ => a++)
    				.map(m => (~~(m / 12) + '-' + ("0" + (m % 12 + 1)).slice(-2)));
    }
     
    // Xu ly datetime cua du lieu query tu database thanh kieu mm/yyyy de hien thi	
    // Chuyen kieu Date thanh yyyy de xu ly
    function getFormattedYear(date) {
		var year = date.getFullYear();
				
		var month = (1 + date.getMonth()).toString();
		month = month.length > 1 ? month : '0' + month;
				
		var day = date.getDate().toString();
		day = day.length > 1 ? day : '0' + day;
				  
		return year;
	}
	
	// Chuyen kieu Date thanh yyyy-mm de xu ly
	function getFormattedDate(date) {
		var year = date.getFullYear();
				
		var month = (1 + date.getMonth()).toString();
		month = month.length > 1 ? month : '0' + month;
				
		var day = date.getDate().toString();
		day = day.length > 1 ? day : '0' + day;
				  
		return year + '-' + month;
	}
	
	// Chuyen kieu Date thanh yyyy-mm-dd de xu ly
	function getFormattedFullDate(date) {
		var year = date.getFullYear();
				
		var month = (1 + date.getMonth()).toString();
		month = month.length > 1 ? month : '0' + month;
				
		var day = date.getDate().toString();
		day = day.length > 1 ? day : '0' + day;
				  
		return year + '-' + month + '-' + day;
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
	
	// Chuyen image sang Base64
	function convertImgToBase64(img, outputFormat){
	    // clear canvas
	    canvas.width = img.width;
	    canvas.height = img.height;
	    // draw image
	    ctx.drawImage(img, 0, 0);
	
	    // get data url of output format or defaults to jpeg if not set
	    return canvas.toDataURL("image/" + (outputFormat || "jpeg"));
	}
	
	// Xu ly date thanh kieu dd-mm-yyyy
	$scope.getDate = function(a, b) {
		
		$scope.start1 = document.getElementById(a).value;
		$scope.newStart = $scope.start1.substring(0,4)  + '-' + $scope.start1.substring(5,7) + '-' + $scope.start1.substring(8,10);
		$scope.end1 = document.getElementById(b).value;
		$scope.newEnd = $scope.end1.substring(0,4) + '-' + $scope.end1.substring(5,7) + '-' + $scope.end1.substring(8,10);
	}
	
	// Tinh so ngay tu 2 ngay de xu ly date len chartjs
	function dateDiff(string1, string2) {
		var date1 = new Date(string1)
        var date2 = new Date(string2)
        var dateDiffBySec = date2 - date1
		var convertSecToDay = 1000*60*60*24
		var dateDiffHandled = Math.floor(dateDiffBySec / convertSecToDay)
		return dateDiffHandled
    }

	// Xu ly lay tat ca cac ngay/thang/nam tu khoang thoi gian cho truoc
	function datesBetween(startDate, endDate) {
		var check = new Date(endDate) - new Date(startDate)
		if (check == 0) {
			const day = []
			
			
			let date = startDate
			let newDate = new Date(date)
			newDate = newDate.setDate(newDate.getDate()-30)
			let newStartDate = new Date(newDate)
			newStartDate = newStartDate.toISOString().slice(0,10)
			let dateMove = new Date(newStartDate)
			
			while (newStartDate < endDate){
				newStartDate = dateMove.toISOString().slice(0,10)
				day.push(newStartDate)
				dateMove.setDate(dateMove.getDate()+1)
			}
			return {day}
		}else {
			const day = [],  
			month = new Set(),
			year = new Set()
			
			const dateMove = new Date(startDate)
			let date = startDate
			while (date < endDate){
				date = dateMove.toISOString().slice(0,10)
				                
				month.add(date.slice(0, 7))
				year.add(date.slice(0, 4))
				day.push(date)
				dateMove.setDate(dateMove.getDate()+1)
			}
			return {year: [...year], month: [...month], day}
		}
		
	}
	
	// Xu ly image url thanh base64
	const getBase64FromUrl = async (url) => {
            const data = await fetch(url);
            const blob = await data.blob();
            return new Promise((resolve) => {
                const reader = new FileReader();
                reader.readAsDataURL(blob); 
                reader.onloadend = () => {
                const base64data = reader.result;   
                resolve(base64data);
                }
            });
    }
    
    $scope.excelProducts = function() {
		var btnExport = document.getElementById("excelExportProducts");
		var inpKword = document.getElementById("kwordInput1").value;
		if ($scope.newStart == null & $scope.newEnd == null) {
			if (inpKword === undefined || inpKword === "") {
				btnExport.href = '/export/products';
				btnExport.addEventListener('click', () => {});
			}else {
				btnExport.href = '/export/products/name=' + inpKword;
				btnExport.addEventListener('click', () => {});
			}
		}else {
			if (inpKword === undefined || inpKword === "") {
				btnExport.href = '/export/products/start=' + $scope.newStart + '&end=' + $scope.newEnd;
				btnExport.addEventListener('click', () => {});
			}else {
				btnExport.href = '/export/products/name=' + inpKword + '/start=' + $scope.newStart + '&end=' + $scope.newEnd;
				btnExport.addEventListener('click', () => {});
			}
			
		}
	}
	
	$scope.excelUsers = function() {
		var btnExport = document.getElementById("excelExportUsers");
		var inpKword = document.getElementById("kwordInput2").value;
		if ($scope.newStart == null & $scope.newEnd == null) {
			if (inpKword === undefined || inpKword === "") {
				btnExport.href = '/export/users';
				btnExport.addEventListener('click', () => {});
			}else {
				btnExport.href = '/export/users/name=' + inpKword;
				btnExport.addEventListener('click', () => {});
			}
		}else {
			if (inpKword === undefined || inpKword === "") {
				btnExport.href = '/export/users/start=' + $scope.newStart + '&end=' + $scope.newEnd;
				btnExport.addEventListener('click', () => {});
			}else {
				btnExport.href = '/export/users/name=' + inpKword + '/start=' + $scope.newStart + '&end=' + $scope.newEnd;
				btnExport.addEventListener('click', () => {});
			}
			
		}
	}
	
	$scope.excelByMale = function() {
		var btnExport = document.getElementById("excelExportByMale");
		if ($scope.newStart == null & $scope.newEnd == null) {
			btnExport.href = '/export/productsByMale';
			btnExport.addEventListener('click', () => {});
		}else {
			btnExport.href = '/export/productsByMale/start=' + $scope.newStart + '&end=' + $scope.newEnd;
			btnExport.addEventListener('click', () => {});
		}
	}
	
	$scope.excelByFemale = function() {
		var btnExport = document.getElementById("excelExportByFemale");
		if ($scope.newStart == null & $scope.newEnd == null) {
			btnExport.href = '/export/productsByFemale';
			btnExport.addEventListener('click', () => {});
		}else {
			btnExport.href = '/export/productsByFemale/start=' + $scope.newStart + '&end=' + $scope.newEnd;
			btnExport.addEventListener('click', () => {});
		}
	}
	
	$scope.clearKword = function() {
		$scope.kwords1 = "";
		document.getElementById('kwordInput1').value = "";
		$scope.kwords2 = "";
		document.getElementById('kwordInput2').value = "";
	}
	
});


























