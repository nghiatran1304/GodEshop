app.controller("accessory-ctrl", function($rootScope, $scope, $http) {

	$scope.items = [];
	$scope.lstBrands = [];
	$scope.lstBraceletMaterial = [];

	$scope.filenames = [];
	var getProductIdEdit;

	$scope.form = {};
	$scope.formProduct = {};
	$scope.formAccessory = {};
	$scope.formProductPhoto = {};
	$scope.formProductPhoto.imageId = 'a';
	var uploadImage = new FormData();
	$rootScope.getProductIdAfterInsert;
	$scope.showInsert = false;
	$scope.showUpdate = false;

	$scope.isEdit = false;
	$scope.imageChose = function(filename) {
		var imgtag = document.getElementById("myID");
		imgtag.src = "/upload/ProductImages/" + filename;
	}
		$scope.setNormal = function() {
	
		$scope.formProduct.productIsDeteled = false;
		$scope.formProduct.brandId = 1;
		$scope.formAccessory.braceletMaterialId = 1;
	

	}
	$scope.initialize = function() {
	$scope.setNormal();
	$scope.formProduct.productCreateDate = new Date();
		$scope.showInsert = true;
		$scope.formProductPhoto.imageId = 'a';
		// load products
		$http.get("/rest/accessories").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.productCreateDate = new Date(item.productCreateDate);
			});

		});
		// load brands
		$http.get("/rest/brands").then(resp => {
			$scope.lstBrands = resp.data;
		});
		// load bracelet
		$http.get("/rest/bracelets").then(resp => {
			$scope.lstBraceletMaterial = resp.data;
		});

	};

	$scope.initialize();


	$scope.findByNameDto = function(a) {
		a.length == 0 ? ' ' : $http.get(`/rest/products-accessory/search/${a}`).then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.productCreateDate = new Date(item.productCreateDate);
			});
		});
	};


	// xóa form
	$scope.reset = function() {
		getProductIdEdit = null;
		$rootScope.getProductIdAfterInsert = null; 
		$scope.formProduct.productId = null;
		$scope.isEdit = false;
		$scope.showInsert = true;
		$scope.showUpdate = false;
		$scope.form = {};
		$scope.formProduct = {};
		$scope.formAccessory = {};
		$scope.formProductPhoto = {};
		$scope.filenames = [];
		$scope.formProductPhoto.imageId = 'a';
		$scope.initialize();
	}


	// hiển thị lên form
	$scope.edit = function(item) {
		try {
			$scope.isEdit = true;
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



			// product image
			$scope.formProductPhoto.imageId = $scope.form.imageId;

			// accessory
			$scope.formAccessory.accessoryId = $scope.form.accessoryId;
			$scope.formAccessory.accessoryColor = $scope.form.accessoryColor;
			$scope.formAccessory.braceletMaterialId = $scope.form.braceletMaterialId;

			// images
			getProductIdEdit = angular.copy($scope.formProduct.productId);

			var pid = angular.copy($scope.formProduct.productId);
			$scope.loadAllImage(pid);

			$scope.slt = 'a';
			$(".nav-tabs2 a:eq(0)").tab('show');
		} catch (Err) {

		}
	}

	$scope.loadAllImage = function(pid) {
		try {
			$http.get(`/rest/getImages/${pid}`).then(resp => {
				$scope.filenames = resp.data;
			}).catch(error => {
				console.log("Errors : ", error);
			});
		} catch (Err) {
		}
	}

	function sleep(time) {
		return new Promise((resolve) => setTimeout(resolve, time));
	}
	$rootScope.getProductIdAfterInsert;
	var formImages = new FormData();
	// thêm sản phẩm
	$scope.create = function() {
		var productItem = angular.copy($scope.formProduct);
		$http.post(`/rest/post-product`, productItem).then(resp => {
			resp.data.createDate = new Date(resp.data.createDate);
			$rootScope.getProductIdAfterInsert = resp.data.id;
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
				text: "Lỗi UPLOAD PRODUCT!!!",
			});
			console.log("Error insert product : ", error);
		});

		sleep(100).then(() => {
			var accessoryItem = angular.copy($scope.formAccessory);
			$http.post(`/rest/post-accessory`, accessoryItem).then(resp => {
				$scope.formProductPhoto.imageId = 'a';
				$scope.initialize();
			}).catch(error => {
				Swal.fire({
					icon: 'error',
					title: 'Oops...',
					text: "Lỗi UPLOAD ACCSESSORY!!!",
				});
				console.log("Error insert accessory : ", error);
			});


			var aId = $rootScope.getProductIdAfterInsert;
			$http.post(`/rest/uploadImages/${aId}`, formImages, {
				transformRequest: angular.identity,
				headers: { 'Content-Type': undefined },
			}).then(resp => {
				var reader = new FileReader();
				var imgtag = document.getElementById("myID1");
				imgtag.src = "/upload/noImage.jpg";
				$scope.reset();
				Swal.fire({

					icon: 'success',
					confirmButtonColor: '#3085d6',
					title: 'Success',
				}).then(result => {
					if (result.isConfirmed) {
						var reader = new FileReader();
						var imgtag = document.getElementById("myID1");
						imgtag.src = "/upload/noImage.jpg";
						$scope.reset();
					}
				});
			}).catch(error => {
				console.log("Error : " + error);
			});

			//------
			$scope.formProductPhoto.imageId = 'a';
			$scope.reset();

		});
	}


	$scope.uploadImage = function(files) {

		formImages = new FormData();

		var form = new FormData();
		for (var i = 0; i < files.length; i++) {
			form.append("files", files[i]);
			formImages.append("files", files[i]);
		}

		$http.post(url, form, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.filenames.push(...resp.data);
		}).catch(error => {
			console.log("Error : " + error);
		});
		sleep(30).then(() => {
			$http.post(`/rest/uploadImages/${getProductIdEdit}`, formImages, {
				transformRequest: angular.identity,
				headers: { 'Content-Type': undefined },
			}).then(resp => {

				Swal.fire({
					icon: 'success',
					confirmButtonColor: '#3085d6',
					title: 'Success',
				}).then(result => {
					if (result.isConfirmed) {
						$scope.filenames.push(...resp.data);
						$scope.initialize();
						$scope.edit($scope.form);
					}
				});
			}).catch(error => {
				console.log("Error : " + error);
			});
		});

	};

	// cập nhật sản phẩm
	$scope.update = function() {
		// product
		var itemProduct = angular.copy($scope.formProduct);
		$http.put(`/rest/put-product/${itemProduct.productId}`, itemProduct).then(resp => {
			$scope.initialize();
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Success',
				showConfirmButton: false,
				timer: 500
			})
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Failed !!!",
			});
			console.log("Error product : ", error);
		});

		// formAccessory
		var itemAccessory = angular.copy($scope.formAccessory);
		$http.put(`/rest/accessory/${itemAccessory.accessoryId}`, itemAccessory).then(resp => {
			$scope.initialize();
			$scope.reset();
		}).catch(error => {
			console.log("ErrorAccessory : ", error);
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
			$http.post(`/rest/post-photo-plus/`, itemProductPhoto).then(resp => {
			});

		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Failed!!!",
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


	$scope.deleteImage = function(filename) {
		$http.delete(`/rest/deleteImage/${filename}`).then(resp => {
			var i = $scope.filenames.findIndex(name => name == filename);
			$scope.filenames.splice(i, 1);
			$scope.imageChose($scope.filenames[0]);
		}).catch(error => {
			console.log("Errors : ", error);
		});

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


	// ---------------- UPLOAD FILE TAM  ---------------
	var url = `http://localhost:8080/rest/files/images`;

	$scope.url = function(filename) {
		return `${url}/${filename}`;
	};


	$scope.list1 = function() {
		$http.get(url).then(resp => {
			$scope.filenames = resp.data;
		}).catch(error => {
		});
	};


	$scope.upload1 = function(files, event) {
		uploadImage.append('file', files[0]);
		var selectedFile = event.target.files[0];
		var reader = new FileReader();
		var imgtag = document.getElementById("myID1");
		imgtag.title = selectedFile.name;
		reader.onload = function(event) {
			imgtag.src = event.target.result;
		};
		reader.readAsDataURL(selectedFile);

		formImages = new FormData();

		var form = new FormData();
		for (var i = 0; i < files.length; i++) {
			form.append("files", files[i]);
			formImages.append("files", files[i]);
		}

		$http.post(url, form, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.filenames.push(...resp.data);
		}).catch(error => {
		});
	};


	$scope.delete1 = function(filename) {
		$http.delete(`${url}/${filename}`).then(resp => {
			let i = $scope.filenames.findIndex(name => name == filename);
			$scope.filenames.splice(i, 1);
			$scope.imageChose($scope.filenames[0]);
		}).catch(error => {
		});
	};

	$scope.list1();



});




























