const app = angular.module("shopping-cart-app", []);
app.controller("shopping-cart-ctrl", function($scope, $http) {
	$scope.cart = {
		items: [],

		// thêm sản phẩm vào cart
		add(productId) {
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
			})
		},

		// xóa sản phẩm
		remove(productId) {
			var index = this.items.findIndex(item => item.productId == productId);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
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
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem("cart", json);
		},

		// đọc sản phẩm từ local storage
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
		}
	}
	// end $scope.cart

	// gọi hàm loadFromLocalStorage 
	$scope.cart.loadFromLocalStorage();

	//------------------------- ORDER ---------------------------

});






































































































