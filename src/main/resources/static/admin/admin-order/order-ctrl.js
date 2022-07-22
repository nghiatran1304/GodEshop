app.controller("order-ctrl", function($scope, $http) {

	$(document).keypress(
		function(event) {
			if (event.which == '13') {
				event.preventDefault();
			}
		});

	$scope.items = [];
	$scope.itemsPending = [];
	$scope.itemsConfirmed = [];
	$scope.itemsDelivery = [];
	$scope.itemsSuccess = [];
	$scope.itemsCancel = [];


	$scope.form = {};

	$scope.orderInfoDto = {};

	$scope.init = function() {
		$http.get(`/rest/allOrders`).then(resp => {
			$scope.items = resp.data
		})

		$http.get(`/rest/order-pending`).then(resp => {
			$scope.itemsPending = resp.data;
		});

		$http.get(`/rest/order-confirmed`).then(resp => {
			$scope.itemsConfirmed = resp.data;
		});

		$http.get(`/rest/order-delivery`).then(resp => {
			$scope.itemsDelivery = resp.data;
		});

		$http.get(`/rest/order-success`).then(resp => {
			$scope.itemsSuccess = resp.data;
		});

		$http.get(`/rest/order-cancel`).then(resp => {
			$scope.itemsCancel = resp.data;
		});

	}

	$scope.init();

	$scope.getUsername;
	$scope.getFullname;
	$scope.getPhone;
	$scope.getEmail;
	$scope.getAddress;
	$scope.getOrderMethodName;
	$scope.getNotes;
	$scope.getOrderStatus;

	var getItemPending = null;
	$scope.edit = function(item) {
		getItemPending = item.id;
		$scope.form = angular.copy(item);
		var id = angular.copy(item.id);
		$http.get(`/rest/order-infoDto/${id}`).then(resp => {
			$scope.orderInfoDto = resp.data
			$scope.getUsername = $scope.orderInfoDto[0].orderUsername;
			$scope.getFullname = $scope.orderInfoDto[0].userFullname;
			$scope.getPhone = $scope.orderInfoDto[0].userPhone;
			$scope.getEmail = $scope.orderInfoDto[0].userEmail;
			$scope.getAddress = $scope.orderInfoDto[0].orderAddress;
			$scope.getOrderMethodName = $scope.orderInfoDto[0].orderMethodName;
			$scope.getNotes = $scope.orderInfoDto[0].orderNotes;
			$scope.getOrderStatus = $scope.orderInfoDto[0].orderStatusId;
		});

	}

	$scope.confirm = function() {
		var o = angular.copy($scope.form);
		$scope.getOrderStatus = 2;
		$http.put(`/rest/order-update-confirm/${o.id}`, o).then(resp => {
			$scope.init();
			$(".nav-tabs a:eq(2)").tab('show');
		})
	}

	$scope.cancelOrder = function() {
		$http.put(`/rest/admin-cancel/${getItemPending}`, "cancel order").then(resp => {
			$scope.init();
			$(".nav-tabs a:eq(5)").tab('show');
			$scope.edit($scope.form);
			// location.reload();
		});
	}

	$scope.delivery = function() {
		var o = angular.copy($scope.form);
		$scope.getOrderStatus = 3;
		$http.put(`/rest/order-update-delivery/${o.id}`, o).then(resp => {
			$scope.form = {};
			$scope.init();
			$(".nav-tabs a:eq(3)").tab('show');
		})
	}

	$scope.success = function() {
		var o = angular.copy($scope.form);
		$scope.getOrderStatus = 4;
		$http.put(`/rest/order-update-success/${o.id}`, o).then(resp => {
			$scope.init();
			$(".nav-tabs a:eq(4)").tab('show');
		})
	}


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


	$scope.pagerPending = {
		page: 0,
		size: 5,
		get itemsPending() {
			var start = this.page * this.size;
			return $scope.itemsPending.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsPending.length / this.size);
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


	$scope.pagerConfirmed = {
		page: 0,
		size: 5,
		get itemsConfirmed() {
			var start = this.page * this.size;
			return $scope.itemsConfirmed.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsConfirmed.length / this.size);
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

	$scope.pagerDelivery = {
		page: 0,
		size: 5,
		get itemsDelivery() {
			var start = this.page * this.size;
			return $scope.itemsDelivery.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsDelivery.length / this.size);
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


	$scope.pagerComplete = {
		page: 0,
		size: 5,
		get itemsSuccess() {
			var start = this.page * this.size;
			return $scope.itemsSuccess.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsSuccess.length / this.size);
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



	$scope.pagerComplete = {
		page: 0,
		size: 5,
		get itemsSuccess() {
			var start = this.page * this.size;
			return $scope.itemsSuccess.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsSuccess.length / this.size);
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


	$scope.pagerCancel = {
		page: 0,
		size: 5,
		get itemsCancel() {
			var start = this.page * this.size;
			return $scope.itemsCancel.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsCancel.length / this.size);
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
