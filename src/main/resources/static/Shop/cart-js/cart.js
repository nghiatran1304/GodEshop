const app = angular.module("shopping-cart-app", []);
app.controller("shopping-cart-ctrl", function($scope, $http) {

	$scope.account = {};
	var us = $("#username").text();
	$scope.getUser = function() {
		$http.get(`/rest/getUserInfomation/${us}`).then(resp => {
			$scope.account = resp.data;
			$scope.order.address = resp.data.address;
			$scope.orderPaypal.address = resp.data.address;
		});
	}
	$scope.getUser();

	$scope.getProductQuantity = {};

	$scope.canOrder = false;

	$scope.cart = {
		items: [],
		outOfStock() {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Sản phẩm đã hết hàng !!!",
			});
		},
		// add product to cart
		add(productId) {
			$http.get(`/rest/get-products/${productId}`).then(resp => {
				$scope.getQuantityProduct = resp.data;
				if ($scope.getQuantityProduct.quantity <= 0) {
					Swal.fire({
						icon: 'error',
						title: 'Oops...',
						text: "Sản phẩm đã hết hàng !!!",
					});
				} else {
					$scope.canOrder = true;
					var item = this.items.find(item => item.productId == productId);
					if (item) {
						item.qty++;
						this.saveToLocalStorage();
					} else {
						$http.get(`/rest/products/${productId}`).then(resp => {
							resp.data.qty = 1;
							this.items.push(resp.data);
							this.saveToLocalStorage();
						});
					};
					Swal.fire({
						position: 'top-end',
						icon: 'success',
						title: 'Success',
						showConfirmButton: false,
						timer: 800
					});
				}
			});

		},

		// xóa sản phẩm
		remove(productId) {
			var index = this.items.findIndex(item => item.productId == productId);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
			if (this.items.length <= 0) {
				$scope.canOrder = false;
			} else {
				$scope.canOrder = true;
			}
		},

		// xóa tất cả sản phẩm
		clear() {
			this.items = [];
			this.saveToLocalStorage();
		},

		// lấy số lượng sản phẩm trong cart
		get count() {
			return this.items.map(item => item.qty).reduce((total, qty) => total += qty, 0);
		},
		// tổng tiền
		get amount() {
			// return this.items.map(item => item.qty * (item.productPrice - (item.productPrice * (item.productDiscount == null ? 0 : item.productDiscount) / 100)) ).reduce((total, qty) => total += qty, 0);
			return this.items.map(item => item.qty * item.finalPrice).reduce((total, qty) => total += qty, 0);
		},

		// lưu vào local storage
		saveToLocalStorage() {
			var checkQuantity = true;
			var itemQuantity = 0;
			for (var i = 0; i < this.items.length; i++) {
				if (this.items[i].qty > this.items[i].productQuantity) {
					itemQuantity = this.items[i].productQuantity;
					this.items[i].qty = itemQuantity;
					checkQuantity = false;
				}
			}
			if (checkQuantity) {
				var json = JSON.stringify(angular.copy(this.items));
				localStorage.setItem("cart", json);
			} else {
				Swal.fire({
					icon: 'error',
					title: 'Oops...',
					text: "Sản phẩm này chỉ còn: " + itemQuantity + " sản phẩm !",
				});
			}

		},

		// đọc sản phẩm từ local storage
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
			if (this.items.length <= 0) {
				$scope.canOrder = false;
			} else {
				$scope.canOrder = true;
			}
		}
	}
	// end $scope.cart

	// gọi hàm loadFromLocalStorage 
	$scope.cart.loadFromLocalStorage();

	//------------------------- ORDER ---------------------------


	$scope.items = [];
	$scope.payments = 1;
	$scope.initialize = function() {
		// load brands
		$http.get(`/rest/payments`).then(resp => {
			$scope.items = resp.data;
		});
		$scope.payments = 1;
	};

	$scope.initialize();

	var pay = 0;
	$scope.choosePayment = function() {
		pay = angular.copy($scope.payments);
	}

	$scope.order = {
		createDate: new Date(),
		note: "",
		address: "",
		account: { username: $("#username").text() },
		orderStatus: { id: 1 },
		orderMethod: { id: 1 },
		get orderDetails() {
			return $scope.cart.items.map(item => {
				return {
					product: { id: item.productId },
					price: item.finalPrice,
					quantity: item.qty
				}
			});
		},
		purchase() {
			if (this.address == null || this.address == undefined || this.address.length <= 0) {
				Swal.fire({
					icon: 'error',
					title: 'Oops...',
					text: "Failed !!!",
				});
			} else {
				var order = angular.copy(this);
				$http.post("/rest/orders", order).then(resp => {
					if (resp.data.id != null) {
						Swal.fire({
							icon: 'success',
							title: 'Sucsess',
							showConfirmButton: false,
							timer: 1500
						}).then((result) => {
							$scope.cart.clear();
							location.href = "/information";
						});
					} else {
						Swal.fire({
							icon: 'error',
							title: 'Oops...',
							text: "This product is out of stock !!!",
						});
					}
				}).catch(error => {
					Swal.fire({
						icon: 'error',
						title: 'Oops...',
						text: "Failed !!!",
					});
					console.log(" >> Error purchase shopping-cart-app.js : " + error);
				});
			}
		}
	}


	$scope.orderPaypal = {
		createDate: new Date(),
		note: "",
		address: $scope.order.address,
		account: { username: $("#username").text() },
		orderStatus: { id: 1 },
		orderMethod: { id: 2 },
		get orderDetails() {
			return $scope.cart.items.map(item => {
				return {
					product: { id: item.productId },
					price: item.finalPrice,
					quantity: item.qty
				}
			});
		},
		purchase() {

			if (this.address == null || this.address == undefined || this.address.length <= 0 || $scope.order.address == null || $scope.order.address == undefined || $scope.order.address.length <= 0) {
				Swal.fire({
					icon: 'error',
					title: 'Oops...',
					text: "Failed !!!",
				});
			} else {
				var order = angular.copy(this);
				$http.post("/rest/orders", order).then(resp => {
					$scope.cart.clear();
				}).catch(error => {
					Swal.fire({
						icon: 'error',
						title: 'Oops...',
						text: "Failed !!!",
					});
					console.log(" >> Error purchase shopping-cart-app.js : " + error);
				});
			}

		}
	}

});






















































































