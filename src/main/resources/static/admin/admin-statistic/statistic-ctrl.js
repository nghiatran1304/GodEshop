import { font } from './font.js'

app.controller("statistic-ctrl", function($scope, $http) {
	$scope.start;
	$scope.newStart;
	$scope.end;
	$scope.newEnd;
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
		if ($scope.newStart == null & $scope.newEnd == null) {
			$http.get("/rest/statistic/products").then(resp => {
				$scope.itemsProduct = resp.data;
				$scope.itemsProduct.forEach(item => {
					$scope.exportProductsData.push({
							productId: item.productId,
							productImage: '',
							productName: item.productName,
							productStock: item.quantityleft,
							productSold: item.quantity});
				})
				
				$http.get("/rest/products/image").then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemsProduct.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemsProduct[index2].imageId = item.imageId;
								$scope.exportProductsData.imageId = item.imageId;
							}
						})
					})
				});
				
				
				// Xuat bao cao thong ke Products
				$scope.exportProducts = function() {
			      	const ws = XLSX.utils.json_to_sheet($scope.exportProductsData);
			      	const fileName = 'productsReport';
			      	var wsrows = [];
					const wb = XLSX.utils.book_new();
					XLSX.utils.book_append_sheet(wb, ws, 'Report');
					ws['!cols'] = [{ width: 10 }, { width: 40 }, { width: 40 }, { width: 15 }, { width: 15 } ]
					wsrows.push({'hpt':20})
					for (var i = 1; i <= $scope.exportProductsData.length; i++) {
						wsrows.push({'hpt':100});
					}
					ws['!rows'] = wsrows;
					XLSX.writeFile(wb, `${fileName}.xlsx`);
				}
				
				$scope.exportPdfProducts = function() {
					/* Chay thu dung may rang chiu =))
				    var doc = new jsPDF();
					for(var i = 0; i < $scope.exportProductsData.length; i++) {
						doc.autoTable({
							html: '#TableToExport',
							bodyStyles: {minCellHeight: 15},
						    didDrawCell: function(data) {
								if (data.column.index === 0 && data.cell.section === 'body') {
									var td = data.cell.raw;
									var img = td.getElementsByTagName('img')[0];
									var textPos = data.cell.textPos;
									doc.addImage(img.src, 'png', textPos.x,  textPos.y, 30, 30);
								}
							}
						});
					}
					doc.save('test.pdf');
					*/
					
					
					var doc = new jsPDF();
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					var testrows = [];
					$scope.exportProductsData.forEach(item => {
						testrows.push([item.productId, item.productName, item.productStock, item.productSold]);
					})
					doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
					doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}});
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
							productId: item.productId,
							productImage: '',
							productName: item.productName,
							productStock: item.quantityleft,
							productSold: item.quantity});
				})
				
				$http.get("/rest/products/image").then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemsProduct.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemsProduct[index2].imageId = item.imageId;
								$scope.exportProductsData.imageId = item.imageId;
							}
						})
					})
				});
				
				// Xuat bao cao thong ke Products
				$scope.exportProducts = function() {
			      	const ws = XLSX.utils.json_to_sheet($scope.exportProductsData);
			      	const fileName = 'productsReport';
			      	var wsrows = [];
					const wb = XLSX.utils.book_new();
					XLSX.utils.book_append_sheet(wb, ws, 'Report');
					ws['!cols'] = [{ width: 10 }, { width: 40 }, { width: 40 }, { width: 15 }, { width: 15 } ]
					wsrows.push({'hpt':20})
					for (var i = 1; i <= $scope.exportProductsData.length; i++) {
						wsrows.push({'hpt':100});
					}
					ws['!rows'] = wsrows;
					XLSX.writeFile(wb, `${fileName}.xlsx`);
				}
				
				$scope.exportPdfProducts = function() {
					var doc = new jsPDF();
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					var testrows = [];
					$scope.exportProductsData.forEach(item => {
						testrows.push([item.productId, item.productName, item.productStock, item.productSold]);
					})
					doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
					doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}});
			        doc.save('productReport.pdf');
	  				
				}
			});
		}
	};

	$scope.initialize();

	$scope.findByNameDto = function(a) {
		if ($scope.newStart == null & $scope.newStart == null) {
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
		}else {
			a.length == 0 ? $scope.initialize() : $http.get(`/rest/statistic/productsByTime/${a}/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(resp => {
				$scope.itemsProduct = resp.data;
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
		if ($scope.newStart == null && $scope.newEnd == null) {
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
		
		// -- Tao chart statistic tu data cua product da xu ly
		myChart = new Chart(
		    document.getElementById('myChart'),
		    config
		);
	}
	
	// ---------------- End Product statistic --------------
	
	// ---------------- Start User statistic --------------
	
	var userTab = document.getElementById('users-tab');
	userTab.addEventListener('click', () => {
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
							userId: item.id,
							userImage: item.image,
							userName: item.username,
							userFullname: item.fullname,
							userOrders: item.orders,
							userTotalBill: item.amount});
					})
				});
				
				// Xuat bao cao thong ke Users
				$scope.exportUsers = function() {
					const ws = XLSX.utils.json_to_sheet($scope.exportUsersData);
					const fileName = 'usersReport';
			      	var wsrows = [];
					const wb = XLSX.utils.book_new();
					XLSX.utils.book_append_sheet(wb, ws, 'Report');
					ws['!cols'] = [{ width: 10 }, { width: 30 }, { width: 30 }, { width: 30 }, { width: 15 }, { width: 15 } ]
					wsrows.push({'hpt':20})
					for (var i = 1; i <= $scope.exportUsersData.length; i++) {
						wsrows.push({'hpt':100});
					}
					ws['!rows'] = wsrows;
					XLSX.writeFile(wb, `${fileName}.xlsx`);
				}
				
				$scope.exportPdfUsers = function() {
					var testcolumns = ["ID", "Username", "Fullname", "Orders", "Total bill"];
					var testrows = [];
					$scope.exportUsersData.forEach(item => {
						testrows.push([item.userId, item.userName, item.userFullname, item.userOrders, item.userTotalBill]);
					})
				    
				    var doc = new jsPDF();
			        doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}});
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
							userId: item.id,
							userImage: item.image,
							userName: item.username,
							userFullname: item.fullname,
							userOrders: item.orders,
							userTotalBill: item.amount});
					})
				});
				
				// Xuat bao cao thong ke Users
				$scope.exportUsers = function() {
					const ws = XLSX.utils.json_to_sheet($scope.exportUsersData);
					const fileName = 'usersReport';
			      	var wsrows = [];
					const wb = XLSX.utils.book_new();
					XLSX.utils.book_append_sheet(wb, ws, 'Report');
					ws['!cols'] = [{ width: 10 }, { width: 30 }, { width: 30 }, { width: 30 }, { width: 15 }, { width: 15 } ]
					wsrows.push({'hpt':20})
					for (var i = 1; i <= $scope.exportUsersData.length; i++) {
						wsrows.push({'hpt':100});
					}
					ws['!rows'] = wsrows;
					XLSX.writeFile(wb, `${fileName}.xlsx`);
				}
				
				$scope.exportPdfUsers = function() {
					var testcolumns = ["ID", "Username", "Fullname", "Orders", "Total bill"];
					var testrows = [];
					$scope.exportUsersData.forEach(item => {
						testrows.push([item.userId, item.userName, item.userFullname, item.userOrders, item.userTotalBill]);
					})
				    
				    var doc = new jsPDF();
			        doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}});
	  				doc.save('userReport.pdf');
				}
			}
		};

		$scope.initializeUser();
	
		$scope.findByNameDto = function(username) {
			if ($scope.newStart == null & $scope.newEnd == null) {
				username.length == 0 ? $scope.initializeUser() : $http.get(`/rest/statistic/find1User/${username}`).then(resp => {
					$scope.itemUsers = resp.data;
				});
			}else {
				username.length == 0 ? $scope.initializeUser() : $http.get(`/rest/statistic/find1UserByTime/${username}/start=` + $scope.newStart + '&end=' + $scope.newEnd).then(resp => {
					$scope.itemUsers = resp.data;
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
					
					var months = monthsBetween(userStartDate, userEndDate);
					months.forEach(item => {
						labels.push(item);
					})
					
					var tempUserDateTime = [];
					
					userDateTime.forEach(item => {
						var newDate = new Date(item.time);
						tempUserDateTime.push({'key': getFormattedDate(newDate), 'value':item.orderbill})
					})
					
					var handledUserData = filterData(tempUserDateTime);
					var dataForUserChart = [];
					
					for (var i = 0; i < months.length; i++) {
						var tempValue = 0;
						for (var j = 0; j < handledUserData.length; j++) {
							if (months[i] === handledUserData[j].key) {
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
					
					var months = monthsBetween(userStartDate, userEndDate);
					months.forEach(item => {
						labels.push(item);
					})
					
					var tempUserDateTime = [];
					
					userDateTime.forEach(item => {
						var newDate = new Date(item.time);
						tempUserDateTime.push({'key': getFormattedDate(newDate), 'value':item.orderbill})
					})
					
					var handledUserData = filterData(tempUserDateTime);
					var dataForUserChart = [];
					
					for (var i = 0; i < months.length; i++) {
						var tempValue = 0;
						for (var j = 0; j < handledUserData.length; j++) {
							if (months[i] === handledUserData[j].key) {
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
								productId: item.productId,
								productImage: '',
								productName: item.productName,
								productStock: item.quantityleft,
								productSold: item.quantity});
					}) 
					
					$http.get(`/rest/products/image`).then(resp2 => {
						resp2.data.forEach((item, index) => {
							$scope.itemBestSellerNam.forEach((item2, index2) => {
								if (item.productId == item2.productId) {
									$scope.itemBestSellerNam[index2].imageId = item.imageId;
									$scope.exportMaleData.productImage = item.imageId;
								}
							})
						})	
					});
					
					
					// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nam
					$scope.exportBestSellerMale = function() {
						const ws = XLSX.utils.json_to_sheet($scope.exportMaleData);
				      	const fileName = 'bestSellerMaleReport';
				      	var wsrows = [];
						const wb = XLSX.utils.book_new();
						XLSX.utils.book_append_sheet(wb, ws, 'Report');
						ws['!cols'] = [{ width: 10 }, { width: 40 }, { width: 40 }, { width: 15 }, { width: 15 } ]
						wsrows.push({'hpt':20})
						for (var i = 1; i <= $scope.exportMaleData.length; i++) {
							wsrows.push({'hpt':100});
						}
						ws['!rows'] = wsrows;
						XLSX.writeFile(wb, `${fileName}.xlsx`);
					}
					
					$scope.exportPdfBestSellerMale = function() {
						var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
						
						// Can xu ly $scope.exportMaleData lay 5 thang co $scope.exportMaleData.itemProductSold cao nhat
						//
						var testrows = [];
						$scope.exportMaleData.forEach(item => {
							testrows.push([item.productId, item.productName, item.productStock, item.productSold]);
						})
					    
					    var doc = new jsPDF();
					    doc.addFileToVFS("MyFont.ttf", font);
				        doc.addFont("MyFont.ttf", "MyFont", "normal");
				        doc.setFont("MyFont");
					    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}});
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
								productId: item.productId,
								productImage: '',
								productName: item.productName,
								productStock: item.quantityleft,
								productSold: item.quantity});
					}) 
					
					$http.get(`/rest/products/image`).then(resp2 => {
						resp2.data.forEach((item, index) => {
							$scope.itemBestSellerNam.forEach((item2, index2) => {
								if (item.productId == item2.productId) {
									$scope.itemBestSellerNam[index2].imageId = item.imageId;
									$scope.exportMaleData.productImage = item.imageId;
								}
							})
						})
					});
					
					
					// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nam
					$scope.exportBestSellerMale = function() {
						const ws = XLSX.utils.json_to_sheet($scope.exportMaleData);
				      	const fileName = 'bestSellerMaleReport';
				      	var wsrows = [];
						const wb = XLSX.utils.book_new();
						XLSX.utils.book_append_sheet(wb, ws, 'Report');
						ws['!cols'] = [{ width: 10 }, { width: 40 }, { width: 40 }, { width: 15 }, { width: 15 } ]
						wsrows.push({'hpt':20})
						for (var i = 1; i <= $scope.exportMaleData.length; i++) {
							wsrows.push({'hpt':100});
						}
						ws['!rows'] = wsrows;
						XLSX.writeFile(wb, `${fileName}.xlsx`);
					}
					
					$scope.exportPdfBestSellerMale = function() {
						var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
						
						// Can xu ly $scope.exportMaleData lay 5 thang co $scope.exportMaleData.itemProductSold cao nhat
						//
						var testrows = [];
						$scope.exportMaleData.forEach(item => {
							testrows.push([item.productId, item.productName, item.productStock, item.productSold]);
						})
					    
					    var doc = new jsPDF();
					    doc.addFileToVFS("MyFont.ttf", font);
				        doc.addFont("MyFont.ttf", "MyFont", "normal");
				        doc.setFont("MyFont");
					    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}});
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
							productId: item.productId,
							productImage: '',
							productName: item.productName,
							productStock: item.quantityleft,
							productSold: item.quantity});
				}) 
				
				$http.get(`/rest/products/image`).then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemBestSellerNam.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemBestSellerNam[index2].imageId = item.imageId;
								$scope.exportMaleData.productImage = item.imageId;
							}
						})
					})
				});
				
				
				// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nam
				$scope.exportBestSellerMale = function() {
					const ws = XLSX.utils.json_to_sheet($scope.exportMaleData);
			      	const fileName = 'bestSellerMaleReport';
			      	var wsrows = [];
					const wb = XLSX.utils.book_new();
					XLSX.utils.book_append_sheet(wb, ws, 'Report');
					ws['!cols'] = [{ width: 10 }, { width: 40 }, { width: 40 }, { width: 15 }, { width: 15 } ]
					wsrows.push({'hpt':20})
					for (var i = 1; i <= $scope.exportMaleData.length; i++) {
						wsrows.push({'hpt':100});
					}
					ws['!rows'] = wsrows;
					XLSX.writeFile(wb, `${fileName}.xlsx`);
				}
				
				$scope.exportPdfBestSellerMale = function() {
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					
					// Can xu ly $scope.exportMaleData lay 5 thang co $scope.exportMaleData.itemProductSold cao nhat
					//
					var testrows = [];
					$scope.exportMaleData.forEach(item => {
						testrows.push([item.productId, item.productName, item.productStock, item.productSold]);
					})
				    
				    var doc = new jsPDF();
				    doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}});
	  				doc.save('bestSellerMaleReport.pdf');
				}
			});
		}
		
		
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
							productId: item.productId,
							productImage: '',
							productName: item.productName,
							productStock: item.quantityleft,
							productSold: item.quantity});
				}) 
				
				$http.get(`/rest/products/image`).then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemBestSellerNu.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemBestSellerNu[index2].imageId = item.imageId;
								$scope.exportFemaleData.productImage = item.imageId;
							}
						})
					})
				})
				// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nu
				$scope.exportBestSellerFemale = function() {
					const ws = XLSX.utils.json_to_sheet($scope.exportFemaleData);
			      	const fileName = 'bestSellerFemaleReport';
			      	var wsrows = [];
					const wb = XLSX.utils.book_new();
					XLSX.utils.book_append_sheet(wb, ws, 'Report');
					ws['!cols'] = [{ width: 10 }, { width: 40 }, { width: 40 }, { width: 15 }, { width: 15 } ]
					wsrows.push({'hpt':20})
					for (var i = 1; i <= $scope.exportFemaleData.length; i++) {
						wsrows.push({'hpt':100});
					}
					ws['!rows'] = wsrows;
					XLSX.writeFile(wb, `${fileName}.xlsx`);
				}
				
				$scope.exportPdfBestSellerFemale = function() {
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					// Can xu ly $scope.exportFemaleData lay 5 thang co $scope.exportMaleData.itemProductSold cao nhat
					//
					var testrows = [];
					$scope.exportFemaleData.forEach(item => {
						testrows.push([item.productId, item.productName, item.productStock, item.productSold]);
					})
				    
				    var doc = new jsPDF();
				    doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}});
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
							productId: item.productId,
							productImage: '',
							productName: item.productName,
							productStock: item.quantityleft,
							productSold: item.quantity});
				}) 
				
				$http.get(`/rest/products/image`).then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemBestSellerNu.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemBestSellerNu[index2].imageId = item.imageId;
								$scope.exportFemaleData.productImage = item.imageId;
							}
						})
					})
				})
				// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nu
				$scope.exportBestSellerFemale = function() {
					const ws = XLSX.utils.json_to_sheet($scope.exportFemaleData);
			      	const fileName = 'bestSellerFemaleReport';
			      	var wsrows = [];
					const wb = XLSX.utils.book_new();
					XLSX.utils.book_append_sheet(wb, ws, 'Report');
					ws['!cols'] = [{ width: 10 }, { width: 40 }, { width: 40 }, { width: 15 }, { width: 15 } ]
					wsrows.push({'hpt':20})
					for (var i = 1; i <= $scope.exportFemaleData.length; i++) {
						wsrows.push({'hpt':100});
					}
					ws['!rows'] = wsrows;
					XLSX.writeFile(wb, `${fileName}.xlsx`);
				}
				
				$scope.exportPdfBestSellerFemale = function() {
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					// Can xu ly $scope.exportFemaleData lay 5 thang co $scope.exportMaleData.itemProductSold cao nhat
					//
					var testrows = [];
					$scope.exportFemaleData.forEach(item => {
						testrows.push([item.productId, item.productName, item.productStock, item.productSold]);
					})
				    
				    var doc = new jsPDF();
				    doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}});
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
							productId: item.productId,
							productImage: '',
							productName: item.productName,
							productStock: item.quantityleft,
							productSold: item.quantity});
				}) 
				
				$http.get(`/rest/products/image`).then(resp2 => {
					resp2.data.forEach((item, index) => {
						$scope.itemBestSellerNu.forEach((item2, index2) => {
							if (item.productId == item2.productId) {
								$scope.itemBestSellerNu[index2].imageId = item.imageId;
								$scope.exportFemaleData.productImage = item.imageId;
							}
						})
					})
				})
				// Xuat bao cao thong ke Top 5 best Seller theo nguoi mua la Nu
				$scope.exportBestSellerFemale = function() {
					const ws = XLSX.utils.json_to_sheet($scope.exportFemaleData);
			      	const fileName = 'bestSellerFemaleReport';
			      	var wsrows = [];
					const wb = XLSX.utils.book_new();
					XLSX.utils.book_append_sheet(wb, ws, 'Report');
					ws['!cols'] = [{ width: 10 }, { width: 40 }, { width: 40 }, { width: 15 }, { width: 15 } ]
					wsrows.push({'hpt':20})
					for (var i = 1; i <= $scope.exportFemaleData.length; i++) {
						wsrows.push({'hpt':100});
					}
					ws['!rows'] = wsrows;
					XLSX.writeFile(wb, `${fileName}.xlsx`);
				}
				
				$scope.exportPdfBestSellerFemale = function() {
					var testcolumns = ["ID", "Product Name", "Product Stock", "Product Sold"];
					// Can xu ly $scope.exportFemaleData lay 5 thang co $scope.exportMaleData.itemProductSold cao nhat
					//
					var testrows = [];
					$scope.exportFemaleData.forEach(item => {
						testrows.push([item.productId, item.productName, item.productStock, item.productSold]);
					})
				    
				    var doc = new jsPDF();
				    doc.addFileToVFS("MyFont.ttf", font);
			        doc.addFont("MyFont.ttf", "MyFont", "normal");
			        doc.setFont("MyFont");
				    doc.autoTable(testcolumns, testrows, {styles: {font: "MyFont"}});
	  				doc.save('bestSellerFemaleReport.pdf');
				}
			});
		}
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
    				.map(m => (~~(m / 12) + '-' + ("0" + (m % 12 + 1)).slice(-2)));
    }
     
    // Xu ly datetime cua du lieu query tu database thanh kieu mm/yyyy de hien thi	
    function getFormattedYear(date) {
		var year = date.getFullYear();
				
		var month = (1 + date.getMonth()).toString();
		month = month.length > 1 ? month : '0' + month;
				
		var day = date.getDate().toString();
		day = day.length > 1 ? day : '0' + day;
				  
		return year;
	}
	
	function getFormattedDate(date) {
		var year = date.getFullYear();
				
		var month = (1 + date.getMonth()).toString();
		month = month.length > 1 ? month : '0' + month;
				
		var day = date.getDate().toString();
		day = day.length > 1 ? day : '0' + day;
				  
		return year + '-' + month;
	}
	
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
	
	// Sort array theo so san pham da ban duoc
	$scope.sort = function() {
		document.getElementById('sort').dispatchEvent(new Event('click'));
		document.getElementById('sort').dispatchEvent(new Event('click'));
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
	
	$scope.getDate = function(a, b) {
		$scope.start1 = document.getElementById(a).value;
		$scope.newStart = $scope.start1.substring(0,4)  + '-' + $scope.start1.substring(5,7) + '-' + $scope.start1.substring(8,10);
		$scope.end1 = document.getElementById(b).value;
		$scope.newEnd = $scope.end1.substring(0,4) + '-' + $scope.end1.substring(5,7) + '-' + $scope.end1.substring(8,10);
	}
	
	function dateDiff(string1, string2) {
		var date1 = new Date(string1)
        var date2 = new Date(string2)
        var dateDiffBySec = date2 - date1
		var convertSecToDay = 1000*60*60*24
		var dateDiffHandled = Math.floor(dateDiffBySec / convertSecToDay)
		return dateDiffHandled
    }

	function datesBetween(startDate, endDate) {
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
});


























