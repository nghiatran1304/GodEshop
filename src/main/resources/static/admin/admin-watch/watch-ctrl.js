app.controller("watch-ctrl", function($scope, $http) {

	$scope.items = [];
	$scope.lstCategory = [];
	$scope.lstBrands = [];
	$scope.lstBraceletMaterial = [];
	$scope.lstGlassMaterial = [];
	$scope.lstMachineInside = [];

	$scope.filenames = [];
	var getProductIdEdit;

	$scope.form = {};
	$scope.formProduct = {};
	$scope.formWatch = {};
	$scope.formProductPhoto = {};
	$scope.formProductPhoto.imageId = 'a';
	var uploadImage = new FormData();

	$scope.showInsert = false;
	$scope.showUpdate = false;

	$scope.initialize = function() {
		$scope.showInsert = true;
		$scope.formProductPhoto.imageId = 'a';
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


	$scope.findByNameDto = function(a) {
		a.length == 0 ? ' ' : $http.get(`/rest/products/search/${a}`).then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.productCreateDate = new Date(item.productCreateDate);
			});
		});
	};


	// xóa form
	$scope.reset = function() {
		$scope.showInsert = true;
		$scope.showUpdate = false;
		$scope.form = {};
		$scope.formProduct = {};
		$scope.formWatch = {};
		$scope.formProductPhoto = {};
		$scope.filenames = [];
		$scope.formProductPhoto.imageId = 'a';
	}


	// hiển thị lên form
	$scope.edit = function(item) {
		$scope.showInsert = false;
		$scope.showUpdate = true;
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

		// images
		getProductIdEdit = angular.copy($scope.formProduct.productId);

		var pid = angular.copy($scope.formProduct.productId);
		$scope.loadAllImage(pid);

		$scope.slt = 'a';
		$(".nav-tabs a:eq(0)").tab('show');

	}

	$scope.loadAllImage = function(pid) {
		$http.get(`/rest/getImages/${pid}`).then(resp => {
			$scope.filenames = resp.data;
		}).catch(error => {
			console.log("Errors : ", error);
		});
	}

	function sleep(time) {
		return new Promise((resolve) => setTimeout(resolve, time));
	}

	// thêm sản phẩm
	$scope.create = function() {
		var productItem = angular.copy($scope.formProduct);
		$http.post(`/rest/products`, productItem).then(resp => {
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
					$scope.formProductPhoto.imageId = 1;
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
			$scope.formProductPhoto.imageId = 1;
			$scope.reset();
		});
	}

	// cập nhật sản phẩm
	$scope.update = function() {
		// product
		var itemProduct = angular.copy($scope.formProduct);
		$http.put(`/rest/products/${itemProduct.productId}`, itemProduct).then(resp => {
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
	$scope.deleteProduct = function(item) {
		var pid = angular.copy($scope.formProduct.productId);
		$http.delete(`/rest/delete/products/${pid}`).then(resp => {
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
	}

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
		$scope.formProductPhoto.imageId = 'a';
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
			$http.post(`/rest1/photo/`, itemProductPhoto).then(resp => {
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

	// -------------------- UPLOAD MULTI IMAGE---------------------
	$scope.uploadImage = function(files) {
		var formImages = new FormData();
		for (var i = 0; i < files.length; i++) {
			formImages.append("files", files[i]);
		}
		$http.post(`/rest/uploadImages/${getProductIdEdit}`, formImages, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined },
		}).then(resp => {
			$scope.filenames.push(...resp.data);
			$scope.reset();
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Thêm ảnh thành công',
				showConfirmButton: false,
				timer: 1000
			})
		}).catch(error => {
			console.log("Error : " + error);
		});

	};

	$scope.deleteImage = function(filename) {
		$http.delete(`/rest/deleteImage/${filename}`).then(resp => {
			var i = $scope.filenames.findIndex(name => name == filename);
			$scope.filenames.splice(i, 1);
		}).catch(error => {
			console.log("Errors : ", error);
		});
		// $scope.loadAllImage(getProductIdEdit);
		// $scope.initialize();
	};

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


});



























