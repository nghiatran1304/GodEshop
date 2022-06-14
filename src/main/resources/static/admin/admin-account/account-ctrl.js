app.controller("account-ctrl", function($scope, $http) {

	$scope.items = [];
	$scope.lstRole = [];
	$scope.form = {};
	$scope.isEdit = false;
	var uploadImage = new FormData();

	$scope.hasImage = 'a';

	$scope.initialize = function() {
		// load account
		$http.get("/rest/info-accounts").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.userDob = new Date(item.userDob);
			});
		});

		// load role
		$http.get("/rest/roles").then(resp => {
			$scope.lstRole = resp.data;
		})

	};

	$scope.initialize();

	$scope.findByName = function() {
		var nameSearched = $scope.searchByUsername;
		$http.get(`/rest/accounts/${nameSearched}`).then(resp => {
			$scope.items = resp.data;
		});
	}


	// xóa form
	$scope.reset = function() {
		$scope.hasImage = 'a';
		$scope.isEdit = false;
		$scope.form = {};
	}


	// hiển thị lên form
	$scope.edit = function(item) {
		$scope.isEdit = true;
		$scope.hasImage = 'b';
		$scope.form = angular.copy(item);
	}

	$scope.change = function(a) {
		$scope.form.accountIsDeleted = a;
	}

	$scope.create = function() {
		var item = angular.copy($scope.form);
		$http.post(`/rest/create-account`, item).then(resp => {
			Swal.fire({
				icon: 'success',
				confirmButtonColor: '#3085d6',
				title: 'Success !',
			}).then(result => {
				if (result.isConfirmed) {
					var imgtag = document.getElementById("myID1");
					imgtag.src = "/upload/noImage.jpg";
					$scope.reset();
					$scope.initialize();
				}
			});
		})
	}

	$scope.update = function() {
		var item = angular.copy($scope.form);
		console.log(item);
		$http.put(`/rest/update-account/${item.accountUsername}`, item).then(resp => {
			$scope.reset();
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
		});
	}

	$scope.onFileSelected1 = function(files, event) {
		/*	
		var selectedFile = event.target.files[0];
		var reader = new FileReader();
		var imgtag = document.getElementById("myID1");
		console.log(imgtag);
		imgtag.title = selectedFile.name;
		reader.onload = function(event) {
			imgtag.src = event.target.result;
		};
		reader.readAsDataURL(selectedFile);
		*/
		var data = new FormData();
		data.append('file', files[0]);
		$http.post(`/rest/upload-user/UserImages`, data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.userPhoto = resp.data.name;
			var selectedFile = event.target.files[0];
			var reader = new FileReader();
			var imgtag = document.getElementById("myID1");
			imgtag.title = selectedFile.name;
			reader.onload = function(event) {
				imgtag.src = event.target.result;
			};
			reader.readAsDataURL(selectedFile);

		})

	}

	$scope.onFileSelected = function(files, event) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post(`/rest/upload-user/UserImages`, data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.userPhoto = resp.data.name;
			var selectedFile = event.target.files[0];
			var reader = new FileReader();
			var imgtag = document.getElementById("myID");
			imgtag.title = selectedFile.name;
			reader.onload = function(event) {
				imgtag.src = event.target.result;
			};
			reader.readAsDataURL(selectedFile);


			var itemPhoto = angular.copy($scope.form);
			$http.put(`/rest/photo-user/`, itemPhoto).then(resp => {
				Swal.fire({
					icon: 'success',
					confirmButtonColor: '#3085d6',
					title: 'Success! Avatar was changed',
				}).then(result => {
					if (result.isConfirmed) {
						$scope.initialize();
					}
				});
			});


		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Failed !!!",
			});
			console.log("Error", error);
		});


	}

	$scope.delete = function(item) {
		var a = angular.copy(item);
		$http.delete(`/rest/delete-account/${a.accountUsername}`).then(resp => {
			// var index = $scope.items.findIndex(p => p.productId == item.productId);
			$scope.initialize();
			$scope.reset();
			Swal.fire({
				position: 'top-end',
				icon: 'success',
				title: 'Success',
				showConfirmButton: false,
				timer: 1000
			})
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: "Failed!!!",
			});
		});
	}


	// phân trang
	$scope.pager = {
		page: 0,
		size: 10,
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
});