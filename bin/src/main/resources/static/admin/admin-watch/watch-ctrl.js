app.controller("watch-ctrl", function($scope, $http) {

	$scope.items = [];
	$scope.lstCategory = [];
	$scope.lstBrands = [];
	$scope.lstBraceletMaterial = [];
	$scope.lstGlassMaterial = [];
	$scope.lstMachineInside = [];

	$scope.form = {};
	$scope.formProduct = {};
	$scope.formWatch = {};
	$scope.formProductPhoto = {};

	var uploadImage = new FormData();

	$scope.initialize = function() {
		// load products
		$http.get("/rest/products").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.productCreateDate = new Date(item.productCreateDate);
			});

		});
		// load categories
		$http.get("/rest/categories").then(resp => {
			$scope.lstCategory = resp.data;
		});
		// load brands
		$http.get("/rest/brands").then(resp => {
			$scope.lstBrands = resp.data;
		});
		// load bracelet
		$http.get("/rest/bracelets").then(resp => {
			$scope.lstBraceletMaterial = resp.data;
		});
		// load glass
		$http.get("/rest/glasses").then(resp => {
			$scope.lstGlassMaterial = resp.data;
		});
		// load brands
		$http.get("/rest/machines").then(resp => {
			$scope.lstMachineInside = resp.data;
		});
	};

	$scope.initialize();

	// xóa form
	$scope.reset = function() {
		$scope.formProduct = {
			createDate: new Date(),
		};
		$scope.formProductPhoto.imageId = null;
		$scope.formWatch = {};
	}

	// hiển thị lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);

		// product
		$scope.formProduct.productId = $scope.form.productId;
		$scope.formProduct.productCreateDate = $scope.form.productCreateDate;
		$scope.formProduct.productDetail = $scope.form.productDetail;
		$scope.formProduct.productMadeIn = $scope.form.productMadeIn;
		$scope.formProduct.productName = $scope.form.productName;
		$scope.formProduct.productIsDeteled = $scope.form.productIsDeteled;
		$scope.formProduct.productPrice = $scope.form.productPrice;
		$scope.formProduct.productQuantity = $scope.form.productQuantity;
		$scope.formProduct.productWarranty = $scope.form.productWarranty;
		$scope.formProduct.brandId = $scope.form.brandId;
		$scope.formProduct.categoryId = $scope.form.categoryId;


		// product image
		$scope.formProductPhoto.imageId = $scope.form.imageId;

		// watch
		$scope.formWatch.watchId = $scope.form.watchId;
		$scope.formWatch.watchATM = $scope.form.watchATM;
		$scope.formWatch.watchCaseColor = $scope.form.watchCaseColor;
		$scope.formWatch.watchGender = $scope.form.watchGender;
		$scope.formWatch.watchGlassColor = $scope.form.watchGlassColor;
		$scope.formWatch.watchGlassSize = $scope.form.watchGlassSize;
		$scope.formWatch.braceletMaterialId = $scope.form.braceletMaterialId;
		$scope.formWatch.glassMaterialId = $scope.form.glassMaterialId;
		$scope.formWatch.machineInsideId = $scope.form.machineInsideId;

		$scope.slt = 'a';
		$(".nav-tabs a:eq(0)").tab('show');

	}

	function sleep(time) {
		return new Promise((resolve) => setTimeout(resolve, time));
	}

	// thêm sản phẩm
	$scope.create = function() {
		var productItem = angular.copy($scope.formProduct);
		// var productItem = angular.copy($scope.form);
		$http.post(`/rest/products`, productItem).then(resp => {
			// resp.data.createDate = new Date(resp.data.createDate);
			resp.data.createDate = new Date(resp.data.createDate);
			$scope.initialize();
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Thành công',
				showConfirmButton: false,
				timer: 500
			})
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Lỗi !!!",
			});
			console.log("Error insert product : ", error);
		});

		sleep(100).then(() => {
			// Do something after the sleep!

			// $scope.formWatch.product = $scope.formProduct;
			var watchItem = angular.copy($scope.formWatch);
			$http.post(`/rest/watches`, watchItem).then(resp => {
				console.log("insert WATCH");
				$scope.initialize();
			}).catch(error => {
				Swal.fire({
					icon: 'error',
					title: 'Oops...',
					text: "Lỗi !!!",
				});
				console.log("Error insert watch : ", error);
			});

			// image
			$http.post(`/rest/upload/ProductImages`, uploadImage, {
				transformRequest: angular.identity,
				headers: { 'Content-Type': undefined }
			}).then(resp => {
				$scope.formProductPhoto.imageId = resp.data.name;
				var itemProductPhoto = angular.copy($scope.formProductPhoto);
				$http.post(`/rest/photo/`, itemProductPhoto).then(resp => {
					console.log("insert first image !");
					$scope.initialize();
				});
			}).catch(error => {
				Swal.fire({
					icon: 'error',
					title: 'Oops...',
					text: "Lỗi tải ảnh!!!",
				});
				console.log("Error", error);
			});
		});

	}

	// cập nhật sản phẩm
	$scope.update = function() {
		// product
		var itemProduct = angular.copy($scope.formProduct);
		$http.put(`/rest/products/${itemProduct.productId}`, itemProduct).then(resp => {
			// var index = $scope.items.findIndex(p => p.productId == itemProduct.productId);
			// $scope.items[index] = itemProduct;
			$scope.initialize();
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Thành công',
				showConfirmButton: false,
				timer: 500
			})
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Lỗi !!!",
			});
			console.log("Error product : ", error);
		});

		// watch
		var itemWatch = angular.copy($scope.formWatch);
		$http.put(`/rest/watches/${itemWatch.watchId}`, itemWatch).then(resp => {
		}).catch(error => {
			console.log("Error watch : ", error);
		});


	}

	// xóa sản phẩm
	$scope.delete = function(item) {
		// alert("Delete");
		var item = angular.copy($scope.form);
		$http.delete(`/rest/products/${item.productId}`).then(resp => {
			var index = $scope.items.findIndex(p => p.productId == item.productId);
			$scope.initialize();
			$scope.reset();
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Thành công',
				showConfirmButton: false,
				timer: 1000
			})
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Lỗi xóa sản phẩm!!!",
			});
			console.log("Error", error);
		});
	};

	// thêm hình đầu tiên vào 
	// upload thêm hình

	$scope.onFileSelected = function(files, event) {
		uploadImage.append('file', files[0]);
		var selectedFile = event.target.files[0];
		var reader = new FileReader();

		var imgtag = document.getElementById("myID1");
		console.log(imgtag);
		imgtag.title = selectedFile.name;
		reader.onload = function(event) {
			imgtag.src = event.target.result;
		};
		reader.readAsDataURL(selectedFile);
	}


	// upload thêm hình
	$scope.imageChanged = function(files, event) {
		$scope.formProductPhoto.imageId = null;
		var data = new FormData();
		data.append('file', files[0]);
		$http.post(`/rest/upload/ProductImages`, data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.formProductPhoto.imageId = resp.data.name;
			var selectedFile = event.target.files[0];
			var reader = new FileReader();
			var imgtag = document.getElementById("myID");
			imgtag.title = selectedFile.name;
			reader.onload = function(event) {
				imgtag.src = event.target.result;
			};
			reader.readAsDataURL(selectedFile);

			$scope.formProductPhoto.productId = $scope.form.productId;
			var itemProductPhoto = angular.copy($scope.formProductPhoto);
			$http.post(`/rest/photo/`, itemProductPhoto).then(resp => {
			});

		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Lỗi tải ảnh!!!",
			});
			console.log("Error", error);
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

	// -------------------- TEST ---------------------
	var url = `http://localhost:8080/rest/files/images`;
	$scope.url = function(filename) {
		return `${url}/${filename}`;
	};

	$scope.list = function() {
		$http.get(url).then(resp => {
			$scope.filenames = resp.data;
		}).catch(error => {
			console.log("Errors : ", error);
		});
	};

	$scope.upload = function(files) {
		var form = new FormData();
		for (var i = 0; i < files.length; i++) {
			form.append("files", files[i]);
		}

		$http.post(url, form, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.filenames.push(...resp.data);
		}).catch(error => {
			console.log("Error : " + error);
		});
	};


	$scope.delete = function(filename) {
		$http.delete(`${url}/${filename}`).then(resp => {
			let i = $scope.filenames.findIndex(name => name == filename);
			$scope.filenames.splice(i, 1);
		}).catch(error => {
			console.log("Errors : ", error);
		});
	};

	$scope.list();

});