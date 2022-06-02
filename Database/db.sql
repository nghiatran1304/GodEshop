
	USE MASTER
	GO
	DROP DATABASE GodShop
	GO


CREATE DATABASE GodShop
GO

USE GodShop
GO

CREATE TABLE Roles(
	Id VARCHAR(15) PRIMARY KEY,
	Name NVARCHAR(100) NOT NULL
);
GO

CREATE TABLE Accounts(
	Username VARCHAR(50) PRIMARY KEY,
	Password VARCHAR(250) NOT NULL,
	IsDelete BIT DEFAULT 0,
	RoleId VARCHAR(15) NOT NULL,
	FOREIGN KEY (RoleId) REFERENCES Roles(Id)
);
GO

CREATE TABLE Users(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Fullname NVARCHAR(50) NOT NULL,
	Email NVARCHAR(150) NOT NULL,
	Gender INT DEFAULT 0, -- 0 nữ | 1 nam | 2 unisex
	Dob DATE,
	Phone VARCHAR(12) NOT NULL,
	Photo NVARCHAR(250) NULL,
	Address NVARCHAR(250) NOT NULL,
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

--===============================
--======== PRODUCTS
CREATE TABLE Brands(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IsDeleted BIT DEFAULT 0,
	Name NVARCHAR(250) NOT NULL
);
GO

CREATE TABLE Categories(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(250) NOT NULL
);
GO

CREATE TABLE Products(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(250) NOT NULL,
	IsDeleted BIT DEFAULT 0, -- 0 là chưa xóa mặc định | 1 là đã xóa
	Quantity INT DEFAULT 0, -- số lượng
	Price FLOAT DEFAULT 0, -- Giá
	CreateDate DATE NOT NULL, -- ngày tạo sản phẩm
	Warranty INT DEFAULT 0, -- Bảo hành tính theo tháng
	MadeIn NVARCHAR(50) NULL,
	Detail NVARCHAR(MAX) NULL, -- mô tả sản phẩm
	BrandId INT NOT NULL, -- thương hiệu
	CategoryId INT NULL, 
	FOREIGN KEY (CategoryId) REFERENCES Categories(Id),
	FOREIGN KEY (BrandId) REFERENCES Brands(Id)
);
go


CREATE TABLE ProductPhotos(
	Id VARCHAR(100) PRIMARY KEY,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id)
);
GO

CREATE TABLE ProductDiscounts(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Discount INT DEFAULT 0,
	CreateDate DATETIME NOT NULL,
	EndDate DATETIME NOT NULL,
	ProductId INT DEFAULT NULL,
	CreateBy VARCHAR(50) NOT NULL,
	FOREIGN KEY (CreateBy) REFERENCES Accounts(Username),
	FOREIGN KEY (ProductId) REFERENCES Products(Id)
);
GO

--== Chất liệu mặt kính
CREATE TABLE GlassMaterials(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(50) NOT NULL
);
GO
--== Chất liệu dây đồng hồ
CREATE TABLE BraceletMaterials(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(50) NOT NULL
);
GO
--== Hệ thống bên trong
CREATE TABLE MachineInsides(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE Watches(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Gender INT DEFAULT 0, -- -- 0 Nữ | 1 Nam | 2 Unisex | 3 Couple | 4 Kid
	GlassSizes INT DEFAULT 29, -- đường kính mặt kính
	ATM INT DEFAULT 0, -- Chống nước
	GlassColors NVARCHAR(10), --  màu mặt đồng hồ
	CaseColors NVARCHAR(10), -- màu vỏ đồng hồ
	ProductId INT NOT NULL,
	GlassmaterialId INT,
	BraceletmaterialId INT,
	MachineinsideId INT,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),
	FOREIGN KEY (GlassmaterialId) REFERENCES GlassMaterials(Id),
	FOREIGN KEY (BraceletmaterialId) REFERENCES BraceletMaterials(Id),
	FOREIGN KEY (MachineinsideId) REFERENCES MachineInsides(Id),
);
GO


--==================================
--== Phụ kiện
CREATE TABLE Accessories(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Colors NVARCHAR(20),
	BraceletmaterialId INT,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),
	FOREIGN KEY (BraceletmaterialId) REFERENCES BraceletMaterials(Id),
);
GO

--=====================================
--===== ORDERS
CREATE TABLE OrderStatuses(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(25)
);
GO

CREATE TABLE OrderMethods(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(100)
);
GO

CREATE TABLE Orders(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	CreateDate DATE NOT NULL,
	Address NTEXT NOT NULL,
	Username VARCHAR(50) NOT NULL,
	OrderstatusId INT NOT NULL,
	OrdermethodId INT NOT NULL,
	FOREIGN KEY (OrderstatusId) REFERENCES OrderStatuses(Id),
	FOREIGN KEY (Username) REFERENCES Accounts(Username),
	FOREIGN KEY (OrdermethodId) REFERENCES OrderMethods(Id)
);
GO

CREATE TABLE OrderDetails(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	OrderId INT NOT NULL,
	ProductId INT NOT NULL,
	Price FLOAT NOT NULL,
	Quantity INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),
	FOREIGN KEY (OrderId) REFERENCES Orders(Id)
);
GO


--=====================================
-- COMMENT - REPLY
CREATE TABLE ProductLikes(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IsLiked BIT DEFAULT 0,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

CREATE TABLE ProductEvaluations(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Evaluation INT DEFAULT 0,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

CREATE TABLE ProductComments(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	TopicUsername VARCHAR(50),
	FOREIGN KEY (TopicUsername) REFERENCES Accounts(Username),
	CommentContent NTEXT,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),

);
GO

CREATE TABLE CommentPhotos(
	Id VARCHAR(100) PRIMARY KEY,
	CommentId INT,
	FOREIGN KEY (CommentId) REFERENCES ProductComments(Id),
);
GO

CREATE TABLE ProductReplies(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	CommentId INT,
	ReplyContent NTEXT,
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (CommentId) REFERENCES ProductComments(Id),
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO


--==============================================================


--==============================================================

INSERT INTO Roles(Id, Name) VALUES
('Customer', N'Khách hàng'),
('Admin', N'Quản trị viên')
GO

INSERT INTO Accounts(Username, Password, IsDelete, RoleId) VALUES
('admin01','123',0, 'Admin'),
('admin02','123',0, 'Admin'),
('admin03','123',0, 'Admin'),
('cust01','123',0, 'Customer'),
('cust02','123',0, 'Customer'),
('cust03','123',0, 'Customer'),
('cust04','123',1, 'Customer'),
('cust05','123',0, 'Customer'),
('cust06','123',0, 'Customer'),
('cust07','123',0, 'Customer'),
('cust08','123',0, 'Customer'),
('cust09','123',0, 'Customer'),
('cust10','123',0, 'Customer'),
('cust11','123',0, 'Customer'),
('cust12','123',0, 'Customer'),
('cust13','123',0, 'Customer'),
('cust14','123',0, 'Customer'),
('cust15','123',0, 'Customer'),
('cust16','123',0, 'Customer'),
('cust17','123',0, 'Customer'),
('cust18','123',0, 'Customer'),
('cust19','123',0, 'Customer'),
('cust20','123',0, 'Customer'),
('cust21','123',0, 'Customer'),
('cust22','123',0, 'Customer'),
('cust23','123',0, 'Customer'),
('cust24','123',0, 'Customer'),
('cust25','123',0, 'Customer'),
('cust26','123',0, 'Customer'),
('cust27','123',0, 'Customer'),
('cust28','123',0, 'Customer'),
('cust29','123',0, 'Customer'),
('cust30','123',0, 'Customer'),
('cust31','123',0, 'Customer'),
('cust32','123',0, 'Customer'),
('cust33','123',0, 'Customer'),
('cust34','123',0, 'Customer'),
('cust35','123',0, 'Customer')
GO

INSERT INTO Users(Fullname, Email, Gender, Dob, Phone, Photo, Address, Username) VALUES
(N'Trần Trung Nghĩa', 'nghiattps14820@fpt.edu.vn', 1, '1997-04-13', '0366888470', 'nghia.jpg', N'Tân Bình', 'admin01'),
(N'Trần Trung Tính', 'tinhttps14444@fpt.edu.vn', 1, '2001-04-06', '0366888471', 'admin02.jpg', N'Bình Trị Đông A', 'admin02'),
(N'Trần Nguyên Hội', 'hointps15555@fpt.edu.vn', 1, '1999-01-13', '0366889911', 'admin03.jpg', N'Bình Trị Đông B', 'admin03'),
(N'Doãn Hoài Nam', 'namdh123@gmail.com', 1, '1996-01-02', '0989878787', 'nam.jpg', N'Quận 11', 'cust01'),
(N'Trần Nguyên Hải', 'haitn123@gmail.com', 1, '1992-01-02', '0387465739', 'hai.jpg', N'Quận Bình Thạnh', 'cust02'),
(N'Lê Quý Vương', 'vuonglq123@gmail.com', 1, '1995-01-02', '0988767512', 'vuong.jpg', N'Quận 2', 'cust03'),
(N'Hồ Trung Tính', 'tinhht321@gmail.com', 1, '2000-01-02', '0976352435', 'tinh.jpg', N'Quận 11', 'cust04'),
(N'Trần Thị Hoàn', 'hoantt@gmail.com', 0, '2001-03-31', '0398767652', 'hoan.jpg', N'Quận 12', 'cust05'),
(N'Trần Thanh Khiêm', 'cust06@gmail.com', 1, '1996-01-03', '0976484999', 'cust06.jpg', N'Số 3, D1, Khu tập thể Dệt Kim Đông Xuân, Phường Đồng Nhân, Quận Hai Bà Trưng, Hà Nội', 'cust06'),
(N'Trịnh Thị Minh Ngọc', 'cust07@gmail.com', 0, '1992-02-20', '0824414555', 'cust07.jpg', N'Số 8 ngõ 83 Yên Duyên, Phường Yên Sở, Quận Hoàng Mai, Hà Nội', 'cust07'),
(N'Nguyễn Khánh Hân', 'cust08@gmail.com', 0, '1994-03-26', '0879462468', 'cust08.jpg', N'Số 119 phố Nguyễn An Ninh, Phường Tương Mai, Quận Hoàng Mai, Hà Nội', 'cust08'),
(N'Nguyễn Tuấn Dũng', 'cust09@gmail.com', 1, '1984-04-21', '0372958288', 'cust09.jpg', N'Số 70 ngõ 172 đường Phú Diễn, Phường Phú Diễn, Quận Bắc Từ Liêm, Hà Nội', 'cust09'),
(N'Đỗ Chiếm Dương', 'cust10@gmail.com', 1, '1974-05-18', '0979985490', 'cust10.jpg', N'Số 42 đường Nguyễn Khuyến, Phường Văn Quán, Quận Hà Đông, Hà Nội', 'cust10'),
(N'Ngô Quốc Vinh', 'cust11@gmail.com', 1, '1976-06-17', '0972267861', 'cust11.jpg', N'Số 5A Hoàng Văn Thụ, Phường Minh Khai, Quận Hồng Bàng, Hải Phòng', 'cust11'),
(N'Võ Minh Thịnh', 'cust12@gmail.com', 1, '1922-07-15', '0963651231', 'cust12.jpg', N'Số S5.04 Vinhome Marina, đường Võ Nguyên Giáp, Phường Vĩnh Niệm, Quận Lê Chân, Hải Phòng', 'cust12'),
(N'Từ Thị Yến Nhi', 'cust13@gmail.com', 0, '1997-08-14', '0964792996', 'cust13.jpg', N'96 Lý Thường Kiệt,, Phường Hoàng Văn Thụ, Quận Hồng Bàng, Hải Phòng', 'cust13'),
(N'Trần Quốc Bảo', 'cust14@gmail.com', 1, '2003-09-13', '0965725673', 'cust14.jpg', N'Số 56 đường số 5B, Khu đô thị Waterfront,, Phường Vĩnh Niệm, Quận Lê Chân, Hải Phòng', 'cust14'),
(N'Dương Đình Thiện Vũ', 'cust15@gmail.com', 1, '1982-11-11', '0977474140', 'cust15.jpg', N'88 Bàu Gia Thượng 2,, Phường Hoà Thọ Đông, Quận Cẩm Lệ, Đà Nẵng', 'cust15'),
(N'Trần Văn Đức', 'cust16@gmail.com', 1, '1985-12-10', '0964250417', 'cust16.jpg', N'Lô 11, Khu LK04A, khu đô thị Hòa Quý,, Phường Hoà Quý, Quận Ngũ Hành Sơn, Đà Nẵng', 'cust16'),
(N'Võ Đức Huy', 'cust17@gmail.com', 1, '1988-05-09', '0966465470', 'cust17.jpg', N'51 Tống Phước Phổ, Phường Hoà Cường Bắc, Quận Hải Châu, Đà Nẵng', 'cust17'),
(N'Nguyễn Thị Anh Thư', 'cust18@gmail.com', 0, '1956-01-08', '0964633324', 'cust18.jpg', N'48 Nguyễn Chí Thanh, Phường Thạch Thang, Quận Hải Châu, Đà Nẵng', 'cust18'),
(N'Võ Thị Yến Linh', 'cust19@gmail.com', 0, '1992-07-07', '0962788874', 'cust19.jpg', N' 132/78 Hùng Vương,, Phường Thới Bình, Quận Ninh Kiều, Cần Thơ', 'cust19'),
(N'Trần Trung Sơn', 'cust20@gmail.com', 1, '1970-03-30', '0967343037', 'cust20.jpg', N'14 Trần Văn Hoài, Phường Xuân Khánh, Quận Ninh Kiều, Cần Thơ', 'cust20'),
(N'Đinh Tuấn Anh', 'cust21@gmail.com', 1, '1976-01-11', '0966551354', 'cust21.jpg', N'Số 356 Đội Cấn, Phường Cống Vị, Quận Ba Đình, Hà Nội', 'cust21'),
(N'Lê Hoàng Phúc', 'cust22@gmail.com', 1, '1972-02-28', '0967870466', 'cust22.jpg', N'Số 6 Đặng Dung, Phường Trúc Bạch, Quận Ba Đình, Hà Nội', 'cust22'),
(N'Nguyễn Viết Hoàng Thắng', 'cust23@gmail.com', 1, '1985-03-22', '0978604070', 'cust23.jpg', N'Số 28, ngõ 6, đường Võng Thị, Phường Bưởi, Quận Tây Hồ, Hà Nội', 'cust23'),
(N'Phan Hoàng Phúc', 'cust24@gmail.com', 1, '2001-03-01', '0984496748', 'cust24.jpg', N'Số 606 Lạc Long Quân, Phường Nhật Tân, Quận Tây Hồ, Hà Nội', 'cust24'),
(N'Nguyễn Hoàng Minh Tú', 'cust25@gmail.com', 1, '1973-03-25', '0987798493', 'cust25.jpg', N'Số nhà 52, ngõ 230, phố Lạc Trung, Phường Thanh Lương, Quận Hai Bà Trưng, Hà Nội', 'cust25'),
(N'Doãn Hoài Nam', 'cust26@gmail.com', 1, '1976-06-03', '0964858380', 'cust26.jpg', N'Số 1529B đường 30/4, Phường 12, Thành Phố Vũng Tàu, Bà Rịa - Vũng Tàu', 'cust26'),
(N'Giang Hạc Minh', 'cust27@gmail.com', 1, '1989-12-05', '0967509510', 'cust27.jpg', N'Tổ 5, ấp Phước Lập, Xã Mỹ Xuân, Huyện Tân Thành, Bà Rịa - Vũng Tàu', 'cust27'),
(N'Nguyễn Thùy Ngọc Hân', 'cust28@gmail.com', 0, '1991-03-07', '0969038041', 'cust28.jpg', N'Số 159 Võ Thị Sáu, Khu phố Long Nguyên, Thị trấn Long Điền, Huyện Long Điền, Bà Rịa - Vũng Tàu', 'cust28'),
(N'Huỳnh Thị Đào Ty', 'cust29@gmail.com', 1, '1992-06-09', '0973421342', 'cust29.jpg', N'22D2 Tống Duy Tân, Phường 9, Thành Phố Vũng Tàu, Bà Rịa - Vũng Tàu', 'cust29'),
(N'Phan Hoàng Trâm Anh', 'cust30@gmail.com', 0, '1983-05-11', '0972303635', 'cust30.jpg', N'34 Sao Biển, Phường Vĩnh Hải, Thành phố Nha Trang, Khánh Hòa', 'cust30'),
(N'Trần Ngọc Tố Ny', 'cust31@gmail.com', 0, '1991-07-13', '0965051054', 'cust31.jpg', N'212 Trần Quý Cáp, Phường Phương Sài, Thành phố Nha Trang, Khánh Hòa', 'cust31'),
(N'Hoàng Văn Chinh', 'cust32@gmail.com', 1, '1992-02-15', '0968304230', 'cust32.jpg', N'43/1 Phước Long, Phường Phước Long, Thành phố Nha Trang, Khánh Hòa', 'cust32'),
(N'Phạm Văn Khang', 'cust33@gmail.com', 1, '1993-04-17', '0983797594', 'cust33.jpg', N'34/2/28 Nguyễn Thiện Thuật, Phường Tân Lập, Thành phố Nha Trang, Khánh Hòa', 'cust33'),
(N'Dương Văn Vĩ', 'cust34@gmail.com', 1, '1994-03-19', '0965787830', 'cust34.jpg', N' Số Nhà 199, Tổ 1 Phố Vàng,, Thị trấn Thanh Sơn, Huyện Thanh Sơn, Phú Thọ', 'cust34'),
(N'Trần Thị Diễm My', 'cust35@gmail.com', 0, '1957-03-25', '0965368987', 'cust35.jpg', N'Số nhà 06, Khu Phú Lợi, Phường Phong Châu, Thị xã Phú Thọ, Phú Thọ', 'cust35')
GO

INSERT INTO Brands(Name) VALUES
('Baby-G'),
('Bulova'),
('Calvin Klein'),
('Jacob & Co'),
('Casio'),
('Cityzen'),
('Daniel Wellington'),
('Doxa'),
('Fossil'),
('G-Shock'),
('Longines'),
('Pulsar'),
('Saga'),
('Seiko'),
('Rolex')
GO

INSERT INTO Categories(Name) VALUES
(N'Analog Watch'),
(N'Digital Watch'),
(N'Automatic Watch'),
(N'Chronograph Watch'),
(N'Diving Watch'),
(N'Dress Watch'),
(N'Quartz Watch'),
(N'Mechanical Watch'),
(N'Pilot Watch'),
(N'Field Watch'),
(N'Smart Watch'),
(N'Luxury Watch'),
(N'Phụ kiện')
GO

INSERT INTO GlassMaterials(Name) VALUES
(N'Kính Acrylic'),
(N'Kính Sapphire'),
(N'Kính Mineral'),
(N'Kính Hardlex')
GO

INSERT INTO BraceletMaterials(Name) VALUES
(N'Dây da'),
(N'Dây kim loại'),
(N'Dây lưới'),
(N'Dây vải'),
(N'Dây da cá sấu'),
(N'Dây nhựa/ cao su'),
(N'Dây cacbon'),
(N'Dây titanium')
GO

INSERT INTO MachineInsides(Name) VALUES
(N'Pin(Quartz)'),
(N'Cơ(Automatic)'),
(N'Năng lượng mặt trời(Eco Drive)')
GO

INSERT INTO Products(Name,Quantity,Price,CreateDate,Warranty,MadeIn,Detail,BrandId,CategoryId) VALUES

(N'BA-130-7A1DR',1,2,'2022-05-15',60,N'Mỹ',N'Từ BABY-G, dòng đồng hồ thường ngày dành cho nữ giới năng động, chúng tôi đã cho ra mắt các mẫu mới để tô điểm cho thời trang đường phố.
Mẫu cơ bản là BA-130, phiên bản nhỏ gọn của thiết kế G-SHOCK được ưa chuộng. Sắc trắng và hồng của mẫu sản phẩm này kết hợp với màu kim loại của các bộ phận mặt đồng hồ đem đến diện mạo tươi sáng. Tông màu đơn sắc tinh tế của mẫu đồng hồ màu đen để lại ấn tượng về vẻ ngoài sành điệu.
Thiết kế vỏ nam tính kết hợp với các màu sắc kim loại nữ tính tạo nên thiết kế đồng hồ vô cùng phù hợp với thời trang đường phố.',1,1),

(N'BA-120TG-4ADR',3,5,'2022-05-15',60,N'Mỹ',N'Thuộc dòng BA-120LP-7A2DR – dòng baby-g kim số được mệnh danh là G-shock GA-120 phiên bản nữ; BA-120LP-7A2 với ,ặt số ở vị trí 6 giờ và 9 giờ của mẫu đồng hồ kim-số này được bố trí dễ đọc.
Điều này hơn nữa tạo một thiết kế dày dặn rất phù hợp với trang phục xuống phố cũng như thời trang thường ngày.',1,2),

(N'BGD-560CF-7DR',5,10,'2022-05-15',60,N'Mỹ',N'Lấy cảm hứng từ phong cách bãi biển phía Tây Hoa Kỳ.',1,3),

(N'BGA-151EF-1BDR',7,15,'2022-05-15',60,N'Mỹ',N'Ba mặt số cung cấp thông tin về ngày tháng, Giờ thế giới, đồng hồ bấm giờ và nhiều hơn nữa.Từ dòng BGA-150 mặt to phổ biến đã xuất hiện các mẫu mới với số Ả Rập tuyệt vời cho bốn vạch giờ. Mẫu màu hồng nhạt và trắng kết hợp vạch giờ số Ả Rập màu vàng hồng mang đến họa tiết màu sắc nữ tính và thanh lịch. Ba mặt số cung cấp thông tin về ngày tháng, Giờ thế giới, đồng hồ bấm giờ và nhiều hơn nữa.
Thiết kế nữ tính cực kỳ phù hợp với thời trang nữ với các tính năng và chức năng tiện dụng và dễ đọc.',1,12),

(N'BGA-190-3BDR',7,2,'2022-05-15',60,N'Mỹ',N'Với tính năng Dual Dial World Time (Giờ thế giới hai vòng quay), mẫu này rất lý tưởng cho những phụ nữ thích đi du lịch thế giới ngày nay. Bạn có thể xem nhanh thời gian ở vị trí hiện tại của bạn (kim chính) và một vị trí khác (mặt số ở vị trí 6 giờ), hoán đổi mặt số chính và mặt số giờ bằng cách đơn giản là nhấn vào nút phía dưới bên phải.
Màu của mẫu này đã được lựa chọn đặc biệt để phù hợp với các khu nghỉ dưỡng trên bãi biển trên toàn cầu và họa tiết du lịch được nhấn mạnh hơn bằng kim phút hình máy bay trong mặt số và thiết kế mặt đồng hồ hình bản đồ thế giới.',1,4),

(N'Bulova 96L257',9,200,'2022-05-16',60,N'Mỹ',N'Bulova 96L257 - Đồng Hồ Nữ - Pin / Quartz - Kính Khoáng - Size Mặt 33mm - Bảo Hành 5 Năm - Chính Hãng 100%',2,7),

(N'Bulova 98A187',1,250,'2022-05-16',60,N'Mỹ',N'Bulova 98A187 - Đồng Hồ Nam - Cơ - Automatic - Kính Khoáng - Size Mặt 41mm - Bảo Hành 5 Năm - Chính Hãng 100%',2,3),

(N'BULOVA 98A137',3,200,'2022-05-16',60,N'Mỹ',N'BULOVA 98A137 - Đồng Hồ Nam - Pin / Quartz - Kính Khoáng - Bảo Hành 5 Năm - Chính Hãng 100%',2,7),

(N'Bulova 97B165',5,200,'2022-05-16',60,N'Mỹ',N'Bulova 97B165 - Đồng Hồ Nam - Pin / Quartz - Kính Khoáng - Size Mặt 37mm - Bảo Hành 5 Năm - Chính Hãng 100%',2,7),

(N'Bulova 96C132',5,250,'2022-05-16',60,N'Mỹ',N'Bulova 96C132 - Đồng Hồ Nam - Cơ - Automatic - Kính Khoáng - Size Mặt 42mm - Bảo Hành 5 Năm - Chính Hãng 100%',2,3),

(N'CALVIN KLEIN K3M51T5N',9,100,'2022-05-17',60,N'Thụy Sỹ',N'CALVIN KLEIN K3M51T5N - Đồng Hồ Nam - Pin / Quartz - Kính Khoáng - Size Mặt 40mm - Bảo Hành 5 Năm - Chính Hãng 100%',3,7),

(N'Calvin Klein K3M51152',7,100,'2022-05-17',60,N'Thụy Sỹ',N'Calvin Klein K3M51152 - Đồng Hồ Nam - Pin / Quartz - Kính Khoáng - Size Mặt 40mm - Bảo Hành 5 Năm - Chính Hãng 100%',3,8),

(N'Calvin Klein K2G236X6',5,100,'2022-05-17',60,N'Thụy Sỹ',N'Đồng Hồ Nữ - Pin / Quartz - Kính Khoáng - Size Mặt 31mm - Bảo Hành 5 Năm - Chính Hãng 100%',3,6),

(N'Calvin Klein K4D221CY',3,100,'2022-05-17',60,N'Thụy Sỹ',N'Calvin Klein K4D221CY - Đồng Hồ Nữ - Pin / Quartz - Kính Khoáng - Size Mặt 32mm - Bảo Hành 5 Năm - Chính Hãng 100%',3,8),

(N'Calvin Klein K6R23526',1,100,'2022-05-17',60,N'Thụy Sỹ',N'Calvin Klein K6R23526 - Đồng Hồ Nữ - Pin / Quartz - Kính Khoáng - Size Mặt 32mm - Bảo Hành 5 Năm - Chính Hãng 100%',3,9),

(N'Jacob & Co Astronomia Solar Baguette',1,649702,'2022-05-18',60,N'Mỹ',N'Mang sự sáng tạo đánh tan những chuẩn mực thiết kế thông thường 
vẫn luôn là cách mà thương hiệu đồng hồ Jacob & Co ghi dấu ấn mạnh mẽ trong lòng người hâm mộ trên thế giới có một không hai, không thể nhầm lẫn.',4,12),

(N'Jacob & Co Bugatti Chiron Sapphire',1,349702,'2022-05-18',60,N'Mỹ',N'Jacob & Co Bugatti Chiron Tourbillon BU800.30.BE.UA.ABRUA này thì
chúng ta có thêm bộ vỏ nạm đá Tsavorite màu xanh lá cực kỳ nổi bật.',4,12),

(N'Jacob & Co Brilliant Flying Tourbillon',1,649702,'2022-05-18',60,N'Mỹ',N'Là một chiếc đồng hồ thuộc bộ sưu tập High Jewelry Masterpiece, Jacob & Co Brilliant Flying
Tourbillon Arlequino nổi bật với hàng trăm viên đá quý lấp lánh được nạm trên cả mặt số và viền bezel đồng hồ tạo nên một sự cuốn hút ngay từ cái nhìn đầu tiên.',4,12),

(N'JACOB & CO Astronomia Solar',1,300,'2022-05-18',60,N'Mỹ',N'JACOB & CO Astronomia Solar  với cổ máy Tourbillon lấy cảm hứng từ dãi thiên Hà quay quanh hệ mặt trời .',4,12),

(N'JACOB & CO EPIC SF24 FLYING TOURBILLON',1,500,'2022-05-18',60,N'Mỹ',N'Jacob & Co Epic SF24 Flying Tourbillon là một trong những thiết kế đáng kinh ngạc của Jacob & Co khi mang tới một cỗ máy đầy thể thao cùng thiết kế mặt số skeleton đầy táo bạo cùng những đường nét thủ công không thể tuyệt vời hơn.',4,10),


(N'CASIO Unisex W-218HC-4AVDF',5,25,'2022-05-19',60,N'Nhật Bản',N'Đồng hồ được trang bị khung viền và dây đeo nhựa có trọng lượng nhẹ, chịu lực tốt, hạn chế hư hỏng và tạo cảm giác mềm mại khi đeo.',5,9),

(N'CASIO AEQ-120W-9AVDF',3,20,'2022-05-19',60,N'Nhật Bản',N'Mẫu đồng hồ này đến từ thương hiệu đồng hồ Casio nổi tiếng Nhật Bản. Thương hiệu với nhiều mẫu mã đa dạng phù hợp nhiều đối tượng sử dụng.',5,8),

(N'CASIO LA680WGA-9DF',5,35,'2022-05-19',60,N'Nhật Bản',N'Mang thương hiệu đồng hồ Casio chất lượng và lâu đời của Nhật Bản, quen thuộc với người Việt Nam. Bộ sản phẩm gồm: Hộp, Thẻ bảo hành, Hướng dẫn sử dụng',5,5),

(N'CASIO LRW-200H-4B2VDF',5,30,'2022-05-19',60,N'Nhật Bản',N'Mang thương hiệu đồng hồ Casio chất lượng và lâu đời của Nhật Bản, quen thuộc với người Việt Nam. Bộ sản phẩm gồm: Hộp, Thẻ bảo hành, Hướng dẫn sử dụng',5,2),

(N'CASIO LTP-1308D-2AVDF ',5,25,'2022-05-19',60,N'Nhật Bản',N'Mang thương hiệu đồng hồ Casio chất lượng và lâu đời của Nhật Bản, quen thuộc với người Việt Nam. Bộ sản phẩm gồm: Hộp, Thẻ bảo hành, Hướng dẫn sử dụng',5,4),


-----------------Nam------------------
(N'Citizen NH8352-53P',2,220,'2022-05-14',60,N'Nhật Bản',N'Đồng Hồ Citizen NH8352-53P Cho Nam là chiếc đồng hồ cao cấp đến từ thương hiệu Citizen nổi tiếng. Sở hữu gam màu nổi bật ngay từ khi có mặt trên thị trường Citizen NH8352-53P được nhiều tín đồ thời trang săn đón.',6,1), --== nam
(N'CITIZEN BM683809X',4,170,'2022-05-14',60,N'Nhật Bản',N'Đồng hồ CITIZEN Chandler Military, được đánh bóng và thoải mái, là sự bổ sung hoàn hảo cho bộ sưu tập đồng hồ của bạn. Nổi bật ở đây là vỏ bằng thép không gỉ, dây đeo màu nâu sẫm với mặt số và ngày tháng màu xanh lá cây. Với công nghệ Eco-Drive của chúng tôi - được cung cấp bởi ánh sáng, bất kỳ ánh sáng nào.',6,10),  --==nam
(N'Citizen NH8365-19F',6,200,'2022-05-14',60,N'Nhật Bản',N'Đồng hồ Citizen NH8365-19F cho Nam có thiết kế đơn giản nhưng nam tính, với mặt màu đen sử dụng số La Mã và dây được làm từ chất liệu da cao cấp, mang đến nét mạnh mẽ cho người đeo. Đường kính mặt 41mm với độ dày 11mm phù hợp với mọi cổ tay nam giới.',6,3), --== nam
(N'Citizen AN8167-53X',8,160,'2022-05-14',60,N'Nhật Bản',N'Đồng Hồ Citizen AN8167-53X Nam Chronograph Màu Đen Mặt Nâu đỏ là chiếc đồng hồ cao cấp đến từ thương hiệu Citizen nổi tiếng. Sở hữu thiết kế hiện đại cùng gam màu nổi bật Citizen AN8167-53X cho người dùng vẻ sang trọng, lịch lãm.',6,4), --==nam
(N'Citizen ER0212-50P',10,135,'2022-05-14',60,N'Nhật Bản',N'Đồng Hồ Citizen ER0212-50P Cho Nữ là chiếc đồng hồ cao cấp đến từ thương hiệu Citizen nổi tiếng. Khi sở hữu siêu phẩm này bạn sẽ thấy như cả thế giới ở trên cổ tay của mình. Ngay từ khi có mặt trên thị trường Citizen ER0212-50P đã được rất nhiều tín đồ thời trang yêu thích. ',6,6), --==nữ

(N'Daniel Wellington 0102DW',2,145,'2022-05-14',60,N'Thụy Điển',N'Đồng hồ Daniel Wellington 0102DW có vỏ kim loại màu vàng sang trọng bao quanh nền số màu trắng trang nhã, kim chỉ và vạch số mỏng nổi bật trên nền số màu trắng trang nhã, logo DW được đặt ngay vị trí 12h nổi bật.',7,1), --== nam
(N'Daniel Wellington 0952DW',2,145,'2022-05-14',60,N'Thụy Điển',N'Đồng Hồ Daniel Wellington 0952DW được thiết kế tinh xảo đến từng chi tiết cùng dây vải Nato cao cấp với màu sắc ngọt ngào, nữ tính giúp phụ nữ thêm phần nổi bật',7,6), --==nữ
(N'Daniel Wellington Men’s 0206DW',2,150,'2022-05-14',60,N'Thụy Điển',N'Đồng hồ Daniel Wellington Men’s 0206DW với dây da màu đen sang trọng, lịch sự và nam tính giúp bạn luôn tự tin mỗi khi xuất hiện. Chiếc đồng hồ Daniel Wellington gọn nhẹ dễ dàng kết hợp với các loại trang phục khác nhau.',7,7), --== nam
(N'Daniel Wellington DW00100130',2,155,'2022-05-14',60,N'Nhật Bản',N'Đồng hồ Daniel Wellington DW00100130 là chiếc đồng hồ đẹp xuất sắc từ thương hiệu Daniel Wellington sở hữu thiết kế tối giản với mặt số đơn giản ít chi tiết, dây da có phần cổ điển, lịch lãm.',7,3), --== nam
(N'Daniel Wellington 0902DW',2,120,'2022-05-14',60,N'Nhật Bản',N'Đồng hồ Daniel Wellington 0902DW với dây da cao cấp khỏe khoắn, trẻ trung và hiện đại giúp phái nữ luôn nổi bật với sự xuất hiện của mình ',7,6), --== nữ

(N'DOXA D154TWH',2,6500,'2022-05-14',60,N'Thụy Sĩ',N'Mẫu Doxa D154TWH phiên bản giới hạn 1000 chiếc trên toàn thế giới, sự kết hợp cách điệu giữa các vạch số cùng chữ số la mã được mạ vàng sang trọng, điểm nhấn với nền mặt số Skeleton lộ máy.',8,12), --== nam
(N'DOXA D221RSV',4,1800,'2022-05-14',60,N'Thụy Sĩ',N'Ẩn bên dưới mặt kính Sapphire với nền mặt số được gia công tinh xảo đính các viên kim cương tạo nên phụ kiện thời trang sang trọng dành cho phái đẹp với phiên bản Doxa D221RSV.',8,3), --== nữ
(N'DOXA D203SBU',6,750,'2022-05-14',60,N'Thụy Sĩ',N'Mẫu Doxa D203SBU kiểu dáng mỏng mang lại vẻ ngoài trẻ trung tinh tế với nền mặt số được tạo hình hoa văn nổi phối cùng tông màu xanh nổi bật thời trang trước nền mặt kính Sapphire.',8,7), --== nam
(N'DOXA D215SWH',8,2350,'2022-05-14',60,N'Thụy Sĩ',N'Bên dưới mặt kính Sapphire nổi bật với thiết kế máy cơ lộ tim mang lại vẻ đẹp độc đáo trên nền mặt số xà cứ size 35mm phong cách thời trang cho phái đẹp với mẫu Doxa D215SWH.',8,12), --== nữ
(N'DOXA ĐÔI(D183TSV – D184TSV)',10,6500,'2022-05-14',60,N'Thụy Sĩ',N'Mẫu Doxa đôi với chi tiết núm vặn cùng kim chỉ mạ vàng nổi bật, nền mặt số trắng với thiết kế họa tiết trải tia tạo nên vẻ trẻ trung.',8,12), --==đôi

(N'FOSSIL ES4571',2,185,'2022-05-14',60,N'Mỹ',N'Mẫu Fossil ES4571 chi tiết kế kim chỉ cùng cọc vạch số tạo nét thanh mảnh nữ tính phối tone màu vàng hồng trẻ trung sang trọng cho phái đẹp.',9,1), --== nữ
(N'FOSSIL FS5325',4,160,'2022-05-14',60,N'Mỹ',N'Mẫu Fossil FS5325 với nền mặt số được phối tông màu xanh mang lại làng gió mới tạo nên vẻ thời trang cho phái nam, phối cùng bộ dây đeo chất liệu bằng da nâu trơn tăng thêm vẻ trẻ trung.',9,7), --== nam
(N'FOSSIL ES3284',6,185,'2022-05-14',60,N'Mỹ',N'Mẫu đồng hồ nữ thời trang sang trọng Fossil ES3284 được thiết kế tinh tế với các hạt pha lê được đính xung quanh đồng hồ còn có mạ vàng.',9,6), --== nữ
(N'FOSSIL ME3167',8,225,'2022-05-14',60,N'Mỹ',N'Fossil ME3167 là một trong những bộ máy hiếm hoi trong bộ sưu tập Fossil khi được trang bị máy cơ – một trong những bộ máy có nhiều tầng nghĩa, rất có ý nghĩa với ngành công nghiệp đồng hồ và đòi hỏi người dùng phải có một chút kiến thức trong quá trình sử dụng.',9,3), --== nam
(N'FOSSIL ME3159',10,350,'2022-05-14',60,N'Mỹ',N'Trẻ trung đầy lịch lãm với mẫu dây da được phối tông màu nâu với mẫu Fossil ME3159 mang trên mình phong cách độc đáo với nền mặt số kính phô diễn ra bộ máy cơ mạnh mẽ.',9,3), --== nam

(N'G-SHOCK GA-700-1ADR',2,165,'2022-05-14',60,N'Nhật Bản',N'Đồng hồ G-Shock GA-700-1ADR với thiết kế vỏ máy bằng nhựa kết hợp cùng dây đeo cao su khả năng chống nước cao, theo phong cách thể thao kết hợp mặt số điện tử với những tính năng tiện dụng.',10,7), --== nam
(N'G-SHOCK GA-2000-1A2DR',2,225,'2022-05-14',60,N'Nhật Bản',N'Mẫu G-Shock GA-2000-1A2DR phần vỏ viền ngoài tạo hình nền cọc số mang lại vẻ thể thao năng động cùng các ô số điện tử hiện thị chức năng lịch và đo thời gian.',10,7), --== nam
(N'BABY-G BSA-B100MC-8ADR',2,222,'2022-05-14',60,N'Nhật Bản',N'Mẫu Baby-G BSA-B100MC-8ADR nổi bật tính năng Bluetooth khả năng kết nối điện thoại, thiết kế dây vỏ nhựa chịu va đập cùng khả năng chịu nước lên đến 10atm.',10,7), --== nữ
(N'BABY-G BGA-150FL-7ADR',2,165,'2022-05-14',60,N'Nhật Bản',N'Mẫu Baby-G BGA-150FL-7ADR dây vỏ nhựa phối tone màu trắng kết hợp cùng nền mặt số tạo hình họa tiết đóa hoa tạo nên vẻ ngoài thời trang.',10,7), --== nam
(N'BABY-G BA-110CP-4ADR',2,235,'2022-05-14',60,N'Nhật Bản',N'Mẫu Baby-G BA-110CP-4ADR với phiên bản tổng thể vỏ máy cùng dây đeo phối tone màu hồng nhạt chủ đạo nổi bật vẻ thời trang cá tính cho các bạn nữ, nền mặt số điện tử hiện thị đa chức năng tăng thêm vẻ ngoài đầy năng động.',10,7),
--------------VƯƠNG----------------------

(N'Longines Heritage Classic',3,3500,'2018-07-09',60,N'Thuỵ Sỹ',N'Bao Gồm Vòng Đeo Tay Hạt Gạo Bằng Thép Không Gỉ Với Móc Cài Của Người Triển Khai Có Chữ Ký. Cũng Bao Gồm Hộp Bên Trong Và Bên Ngoài, Dây Da Nâu Có Chữ Ký Với Khóa Ghim Bằng Thép Có Chữ Ký, Hộp Đựng Thẻ Da, Sách, Dụng Cụ, Thẻ Đo Thời Gian, Thẻ Bảo Hành Và Chứng Nhận Phiên Bản Giới Hạn',11,1),
(N'Longines Legend Diver',5,2000,'2019-09-02',60,N'Thuỵ Sỹ',N'Là phiên bản tái hiện của một chiếc đồng hồ thợ lặn những năm 1960, The Longines Legend Diver chiếm một vị trí đặc biệt trong phân khúc Di sản của thương hiệu',11,2),
(N'Longines Conquest Heritage',2,2550,'2021-12-12',60,N'Thuỵ Sỹ',N'Longines Conquest Heritage mang đến mọi thứ mà người chơi đồng hồ chuyên nghiệp cần. Trong đó có thể kể đến một vài tính năng, tiện ích và thông tin kỹ thuật như sau: chronograph, đồng hồ 24h, lịch trăng,… Đặc biệt hơn, thời gian trữ cót đã được cải tiến lên đến 54 giờ',11,5),
(N'Longines HydroConquest',7,1250,'2022-02-02',60,N'Thuỵ Sỹ', N'Đồng hồ Longines HydroConquest có mặt số tròn với viền mỏng tinh tế, nền số màu trắng trang nhã cùng kim chỉ và chữ số chỉ giờ La Mã mỏng phủ màu đen nổi bật, dây đeo kim loại',11,7),
(N'Longines Spirit Zulu Time',2,3450,'2020-12-10',60,N'Thuỵ Sỹ',N'Longines là thương hiệu thời trang hàng đầu hiện nay của Thụy Sỹ, thuộc sở hữu Tập đoàn Swatch. Các sản phẩm đồng hồ đeo tay chủ yếu tập trung vào phân khúc giá cận cao cấp và cao cấp',11,12),
--
(N'Pulsar SR SG8884',10,3350,'2022-01-02',60,N'Nhật Bản',N'Mẫu SR SG8884.4102AT dây da nâu với phiên bản tạo hình vân lịch lãm trẻ trung với cọc số la mã được tạo hình mỏng trên nền mặt số trắng kích thước nam tính 42mm',12,3),
(N'PULSAR PS9251X1',5,120,'2022-05-05',36,N'Nhật Bản',N'Ba Ưu Điểm Khó Cưỡng Của Đồng Hồ Pulsar Chính Hãng Với tư cách là thương hiệu con của nhà sản xuất đồng hồ lớn nhất Nhật Bản Seiko, đồng hồ Pulsar nam chính hãng mang trong mình gần như toàn bộ những gì tốt đẹp nhất của Seiko nhưng chỉ với một mức',12,9),
(N'Nữ PULSAR PEGG76X1',20,150,'2022-06-06',36,N'Nhật Bản',N'Giới thiệu về thương hiệu PULSAR “Thương hiệu đồng hồ PulSar ra đời vào năm 1970, mãi đến năm 1972 thì những chiếc đồng hồ Pulsar mới có mặt trên thị trường. Lúc bấy giờ rất ít khách hàng chú ý đến thương hiệu này, nhưng với định hướng phát triển Pulsar...',12,8),
(N'Pulsar PT3067X1',8,75,'2022-03-06',12,N'Nhật Bản',N'Giới thiệu về thương hiệu PULSAR “Thương hiệu đồng hồ PulSar ra đời vào năm 1970, mãi đến năm 1972 thì những chiếc đồng hồ Pulsar mới có mặt trên thị trường. Lúc bấy giờ rất ít khách hàng chú ý đến thương hiệu này, nhưng với định hướng phát triển Pulsar đã làm',12,6),
(N'PULSAR PP6076X1',2,135,'2022-03-06',12,N'Nhật Bản',N'Giới thiệu về thương hiệu PULSAR “Thương hiệu đồng hồ PulSar ra đời vào năm 1970, mãi đến năm 1972 thì những chiếc đồng hồ Pulsar mới có mặt trên thị trường. Lúc bấy giờ rất ít khách hàng chú ý đến thương hiệu này, nhưng với định hướng phát triển Pulsar đã làm',12,4),
--
(N'SAGA 71865 SVMWSV-2S',10,125,'2022-01-02',60,N'Mỹ',N'SAGA 71865 SVMWSV-2S – NỮ – QUARTZ (PIN) – DÂY KIM LOẠI – MẶT SỐ 20MM',13,5),
(N'SAGA 13323',10,275,'2022-01-02',60,N'Mỹ',N'Mẫu Saga 13323 SVBLSV-1 thiết kế 3 núm điều chỉnh tính năng Chronograph (đo thời gian) tạo nên kiểu dáng đồng hồ 6 kim trên nền mặt số xanh thời trang với tạo hình họa tiết Guilloche.',13,4),
(N'SAGA 53375 SVMWPK-2',10,550,'2022-01-06',60,N'Mỹ',N'Mẫu Saga 53375 SVMWPK-2 phiên bản 12 viên đá Swarovski được đính tương ứng 12 múi giờ hiện thị trên nền mặt số kích thước 34mm với tone màu trắng xà cừ tạo nên vẻ ngoài thời trang sang trọng',13,5),
(N'SAGA 53578 SVSVBK-2',10,250,'2022-08-02',36,N'Mỹ',N'Mẫu Saga 53578 SVSVBK-2 phiên bản 4 viên pha lê Swarovski đính trên mặt số, các chi tiết kim chỉ cọc vạch số được tạo nét mỏng trên nền mặt số size 35mm với thiết kế họa tiết trải tia nhẹ.',13,9),
(N'SAGA 53768 GPZSVGP-2',10,110,'2022-01-01',60,N'Mỹ',N'SAGA 53768 GPZSVGP-2 – NỮ – QUARTZ (PIN) – DÂY KIM LOẠI – MẶT SỐ 30MM',13,5),
--
(N'SEIKO SSB351P1 – NAM',2,340,'2021-12-10',60,N'Nhật Bản',N'Mẫu Seiko SSB351P1 phiên bản nam tính với tính năng đo thời gian tạo nên kiểu dáng đồng hồ 6 kim trên nền mặt số lớn kích thước 43.9mm.',14,11),
(N'SEIKO NH400-20H',9,340,'2021-12-10',60,N'Nhật Bản',N'Mẫu đồng hồ Dress toát lên vẻ trang nhã, cổ điển là lựa chọn lý tưởng cho những sự kiện ấm cúng. Với dây da thật cao cấp mềm mại, pha họa tiết nổi tinh tế tạo điểm nhấn và nét đặc trưng riêng của thời trang hiện đại.',14,6),
(N'SEIKO NH8390-20H',3,340,'2021-12-10',60,N'Nhật Bản',N'Vẻ ngoài thiết kế kiểu dáng dày dặn khả năng chịu nước lên đến 20 ATM cùng với trải nghiệm của bộ máy cơ là những điểm nổi bật tạo nên mẫu đồng hồ nam Seiko SRPA21K1 phong cách thể thao.',14,8),
(N'SEIKO SSA295K1',4,310,'2021-09-11',60,N'Nhật Bản',N'Đồng hồ nam Seiko SSA295K1 với kiểu thiết kế cổ điển dành cho nam, kim chỉ và vạch số to rõ nổi bật trên nền trắng trang trọng, kết hợp cùng với dây đeo bằng da nâu tạo vẻ hoài cổ lịch lãm.',14,7),
(N'Seiko SRN054P1',2,390,'2021-09-11',60,N'Nhật Bản',N'Mẫu Seiko SRN054P1 các chi tiết kim chỉ cùng cọc chấm tròn vạch số tạo hình dày dặn phủ dạ quang trên nền mặt số kích thước 42,5mm với thiết kế họa tiết.',14,1),
--
(N'Rolex Yacht-Master',2,25000,'2022-08-12',60,N'Thuỵ Sỹ',N'Đồng hồ Rolex Yacht-Master 116621 Mặt Số Đen (Like New)',15,12),
(N'Rolex Datejust 36',2,35000,'2022-09-11',60,N'Thuỵ Sỹ',N'Caliber Rolex 4130 - Trữ cót 72 giờ',15,12),
(N'Rolex Cosmograph Daytona ',1,99600,'2021-10-11',60,N'Thuỵ Sỹ',N'Mặt Số Chocolate Nạm Kim Cương (Like New 2021).',15,12),
(N'Rolex Oyster Perpetual Day',1,72500,'2021-11-11',60,N'Thuỵ Sỹ',N'Đồng hồ Rolex Oyster Perpetual Day-Date 36mm sở hữu đai kính rãnh đặc trưng riêng của Rolex. Ban đầu, đồng hồ được tạo ra để dễ dàng xoay vòng bezel và vỏ để đảm bảo khả năng chống nước tối đa nhất. ',15,12),
(N'Rolex Datejust 126234',4,80000,'2021-12-11',60,N'Thuỵ Sỹ',N'Đồng hồ  Rolex DateJust 126234 Blue Navy nằm trên cùng một kệ với mẫu đồng hồ mới được ra mắt tại Baselworld 2019 là Rolex Datejust 36mm 126234-0037. ',15,12),

--------PHỤ KIỆN------
(N'HỘP CẦM TAY ĐEN PK-HOPCAMTAY-D',3,20,'2022-05-25',60,N'Việt Nam',N'Hộp da smilia cực đẹp. Kích thước hộp: 19cm x 9cm x 6 cm Bên trong có lớp đệm nhung rất dày chống sốc',3,13),
(N'BỘ DỤNG CỤ LÀM SẠCH ĐỒNG HỒ',5,15,'2022-05-25',60,N'Việt Nam',N'Dây và vỏ đồng hồ sau một thời gian sử dụng nhất định sẽ xuất hiện tình trạng cũ bẩn – Đây là điều hoàn toàn không thể tránh khỏi bất luận đó là đồng hồ cao cấp hay bình dân.Bộ dụng cụ này bao gồm:    
Bàn chải gấp khúc, Tool thay dây da,Tool cắt mắt dây kim loại,Bình dung dịch làm sạch tạo độ bóng',3,13),
(N'HỘP 1 XOAY VÂN CARBON PK-HOP1XOAY-V2',3,20,'2022-05-25',60,N'Việt Nam',N'Chất liệu sản phẩm: Vân Carbon, kính Mica cao cấp, da PU,nội thất bọc da PU sang trọng,bản lề Niken không gỉ,động cơ vận hành êm ái, không gây tiếng ồn,2 chế độ xoay nghỉ hợp lý, có xoay đảo chiều',3,13),
(N'DÂY ĐỒNG HỒ DA TAY CÁ SẤU DH-608N',3,7,'2022-05-25',60,N'Việt Nam',N'Dây đồng hồ chất liệu từ da tay cá sấu với tông màu nâu đậm. Dạng vân da cá sấu có ngấn sâu hoặc các lớp vảy tạo nên một chiếc dây đồng hồ nổi bật',3,13),
(N'HIRSCH JAMES',3,100,'2022-05-25',60,N'Ý',N'Mẫu dây da Hirsch JAMES da bò nước Ý mềm mại được thuộc thảo mộc tự nhiên với cấu trúc da độc đáo',3,13),
(N'HỘP CẦM TAY NÂU PK-HOPCAMTAY-N',3,15,'2022-05-25',60,N'Nhật Bản',N'Hộp cầm tay PK-HOPCAMTAY-N sẽ thay bạn bảo vệ, bảo dưỡng chiếc đồng hồ yêu quý của bạn đó là việc cất giữ, bảo quản tránh trầy xước, oxy hóa, chống bụi bẩn, từ trường, ẩm mốc và đặc biệt là duy trì sự ổn định của sản phẩm',2,13),
(N'Dây Đeo Tpu Mềm Cho Đồng Hồ Thể Thao Casio Mw-240',6,20,'2022-05-25',60,N'Nhật Bản',N' Đây là phụ kiện thay thế cho các dòng đồng hồ casio MQ24. Dây là phụ kiện chính hãng casio nên mềm mỏng và bền như dây theo đồng hồ không phải là loại cứng và dễ gãy như dây rẻ tiền trung quốc.',5,13),
(N'Dây vải NATO sọc đỏ trắng Navy',9,20,'2022-05-25',60,N'Mỹ',N'Dây vải Nato là dây đeo đồng hồ được sử dụng để thay thế cho các mẫu dây đồng hồ da, cao su, kim loại. Dây được làm từ sợi nylon chống thấm nước và đem lại cảm giác thoải mái khi đeo. Dây dài 28cm có 13 lỗ đảm bảo phù hợp với mọi cỡ cổ tay. Dây thường được sử dụng trong quân đội nhưng hoàn toàn phù hợp với thời trang hàng ngày và bơi lặn.',7,13),
(N'Dây đồng hồ Inox 11-14mm Voguestrap TX363W',8,20,'2022-05-25',60,N'Thuỵ Sỹ',N'Dây Inox cho đồng hồ phù hợp với mọi loại đồng hồ cỡ khóa dây 11mm-14mm. Dây sản xuất từ thép Inox không rỉ đảm bảo độ sáng bóng và tuổi thọ của sản phẩm.',2,13),
(N'Dây da đồng hồ casio MTP-V002L',5,25,'2022-05-25',60,N'Nhật Bản',N'Dây da đồng hồ chính hãng casio dành cho đồng hồ mã MTP-V002L. Dây chính hãng nên chất lượng dây sẽ là y hệt với dây nguyên bản theo đồng hồ.',5,13)
GO

INSERT INTO Watches(Gender,GlassSizes,ATM,GlassColors,CaseColors,ProductId,GlassmaterialId,BraceletmaterialId,MachineinsideId) VALUES
-------TÍNH-------
(0,29,1,N'Trắng',N'Trắng',1,1,4,1),
(0,29,1,N'Hồng',N'Hồng',2,1,4,1),
(0,29,1,N'Trắng',N'Trắng',3,1,4,1),
(0,29,1,N'Đen',N'Đen',4,1,4,2),
(0,29,1,N'Lục',N'Lục',5,3,4,3),

(0,29,1,N'Trắng',N'Bạc',6,1,4,1),
(1,29,1,N'Trắng',N'Đen',7,3,4,2),
(1,29,1,N'Trắng',N'Vàng',8,3,6,1),
(1,29,1,N'Đen',N'Nâu Vàng',9,1,6,1),
(1,29,1,N'Đen',N'Nâu Vàng',10,3,2,2),

(1,29,1,N'Đen',N'Lam',11,1,2,1),
(1,29,1,N'Trắng',N'Bạc',12,1,2,1),
(0,29,1,N'Trắng',N'Hồng Cam',13,1,4,1),
(0,29,1,N'Đen',N'Đen',14,3,4,1),
(0,29,1,N'Trắng',N'Vàng',15,3,4,2),

(0,44,1,N'Vàng',N'Hồng',16,1,4,1),
(0,44,1,N'Bạc',N'Lục',17,1,4,1),
(0,44,1,N'Bạc',N'7 Màu',18,1,4,1),
(0,44,1,N'Trắng',N'Vàng',19,1,4,1),
(0,44,1,N'Trắng',N'Vàng Đen',20,1,4,1),

(2,29,1,N'Trắng',N'Hồng',21,2,1,2),
(2,29,1,N'Trắng',N'Đen',22,2,1,2),
(2,29,1,N'Trắng',N'Vàng',23,2,1,2),
(2,29,1,N'Trắng',N'Hồng',24,2,1,2),
(2,29,1,N'Trắng',N'Bạc',25,2,1,2),


-----------NAM---------------
(1,40,5,N'Vàng',N'Vàng',26,1,2,2),
(1,42,5,N'Bạc',N'Bạc',27,1,1,2),
(1,41,5,N'Đen',N'Đen',28,1,1,2),
(1,44,5,N'Nâu Đỏ',N'Đen',29,1,2,3),
(0,30,5,N'Trắng',N'Vàng',30,2,2,1),

(2,40,3,N'Trắng',N'Vàng',31,1,4,1),
(0,34,3,N'Trắng',N'Vàng',32,1,1,1),
(2,40,3,N'Trắng',N'Bạc',33,1,1,1),
(1,40,5,N'Đen',N'Bạc',34,1,1,2),
(0,26,3,N'Trắng',N'Vàng',35,1,1,1),

(1,40,3,N'Trắng',N'Vàng',36,2,1,2),
(0,31,5,N'Trắng',N'Bạc',37,2,2,2),
(1,39,5,N'Xanh',N'Bạc',38,2,1,1),
(0,35,5,N'Trắng',N'Bạc',39,2,2,2),
(2,45,3,N'Bạc',N'Bạc',40,2,2,2),

(0,36,3,N'Vàng Hồng',N'Vàng Hồng',41,1,2,1),
(1,44,5,N'Xanh',N'Bạc',42,1,1,1),
(0,29,5,N'Trằng',N'Vàng Hồng',43,1,2,1),
(1,44,5,N'Trắng',N'Bạc',44,1,2,2),
(1,42,5,N'Xanh',N'Bạc',45,1,1,2),

(1,57,20,N'Đen',N'Đen',46,1,6,3),
(1,51,20,N'Đen',N'Đen',47,1,6,3),
(0,49,10,N'Trắng',N'Xám',48,1,6,1),
(0,47,10,N'Trắng',N'Trắng',49,1,6,1),
(2,46,10,N'Trắng',N'Trắng Hồng',50,1,6,1),
--------------VƯƠNG------------------
(0,39,5,N'Bạc',N'Bạc',51,2,8,3),
(1,42,20,N'Xám',N'Xám',52,2,8,3),
(1,40,10,N'Xám',N'Bạc',53,2,1,1),
(1,39,20,N'Bạc',N'Đen',54,2,2,1),
(1,40,20,N'Bạc',N'Bạc',55,2,8,3),
(1,42,5,N'Bạc',N'Nâu',56,1,2,1),
(1,44,5,N'Xanh',N'Nâu',57,1,1,1),
(0,16,5,N'Trắng',N'Vàng',58,2,2,2),
(1,42,5,N'Đen',N'Bạc',59,4,1,2),
(0,34,10,N'Đồng',N'Đồng',60,3,2,3),
(0,60,10,N'Đồng',N'Đồng',61,3,3,1),
(0,18,5,N'Vàng',N'Vàng',62,1,2,1),
(0,34,10,N'Vàng',N'Hồng',63,1,1,1),
(0,34,10,N'Trắng',N'Bạc',64,1,1,1),
(0,60,10,N'Đồng',N'Đồng',65,3,3,1),
(1,42,20,N'Xanh',N'Đen',66,4,3,3),
(1,42,20,N'Đen',N'Bạc',67,2,1,3),
(1,42,20,N'Đen',N'Đen',68,4,2,3),
(1,44,20,N'Nâu',N'Bạc',69,2,1,1),
(1,42,20,N'Đen',N'Vàng',70,1,1,1),
(1,40,20,N'Đen',N'Hồng',71,4,8,3),
(0,38,20,N'Hồng',N'Hồng',72,4,8,3),
(1,41,20,N'Nâu',N'Hồng',73,4,8,3),
(0,36,20,N'Nâu',N'Hồng',74,4,8,3),
(1,42,20,N'Xanh',N'Bạc',75,4,8,3)
GO

INSERT INTO ProductPhotos VALUES
--------TÍNH--------
('babyg-analog-p1-1.jpg',1),
('babyg-analog-p1-2.jpg',1),
('babyg-analog-p1-3.jpg',1),
('babyg-analog-p1-4.jpg',1),

('babyg-digital-p2-1.jpg',2),
('babyg-digital-p2-2.jpg',2),
('babyg-digital-p2-3.jpg',2),
('babyg-digital-p2-4.jpg',2),

('babyg-automatic-p3-1.jpg',3),
('babyg-automatic-p3-2.jpg',3),
('babyg-automatic-p3-3.jpg',3),
('babyg-automatic-p3-4.jpg',3),


('babyg-luxury-p4-1.jpg',4),
('babyg-luxury-p4-2.jpg',4),
('babyg-luxury-p4-3.jpg',4),
('babyg-luxury-p4-4.jpg',4),


('babyg-chronograph-p5-1.jpg',5),
('babyg-chronograph-p5-2.jpg',5),
('babyg-chronograph-p5-3.jpg',5),
('babyg-chronograph-p5-4.jpg',5),

('bulova-quartz-p6-1.jpg',6),
('bulova-quartz-p6-2.jpg',6),
('bulova-quartz-p6-3.jpg',6),
('bulova-quartz-p6-4.jpg',6),


('bulova-automatic-p7-1.jpg',7),
('bulova-automatic-p7-2.jpg',7),
('bulova-automatic-p7-3.jpg',7),
('bulova-automatic-p7-4.jpg',7),


('bulova-quartz-p8-1.jpg',8),
('bulova-quartz-p8-2.jpg',8),
('bulova-quartz-p8-3.jpg',8),
('bulova-quartz-p8-4.jpg',8),


('bulova-quartz-p9-1.jpg',9),
('bulova-quartz-p9-2.jpg',9),
('bulova-quartz-p9-3.jpg',9),
('bulova-quartz-p9-4.jpg',9),

('bulova-automatic-p10-1.jpg',10),
('bulova-automatic-p10-2.jpg',10),
('bulova-automatic-p10-3.jpg',10),
('bulova-automatic-p10-4.jpg',10),


('calvinklein-quartz-p11-1.jpg',11),
('calvinklein-quartz-p11-2.jpg',11),
('calvinklein-quartz-p11-3.jpg',11),
('calvinklein-quartz-p11-4.jpg',11),


('calvinklein-phukien-p12-1.jpg',12),
('calvinklein-phukien-p12-2.jpg',12),
('calvinklein-phukien-p12-3.jpg',12),
('calvinklein-phukien-p12-4.jpg',12),

('calvinklein-dress-p13-1.jpg',13),
('calvinklein-dress-p13-2.jpg',13),
('calvinklein-dress-p13-3.jpg',13),
('calvinklein-dress-p13-4.jpg',13),

('calvinklein-mechanical-p14-1.jpg',14),
('calvinklein-mechanical-p14-2.jpg',14),
('calvinklein-mechanical-p14-3.jpg',14),
('calvinklein-mechanical-p14-4.jpg',14),


('calvinklein-pilot-p15-1.jpg',15),
('calvinklein-pilot-p15-2.jpg',15),
('calvinklein-pilot-p15-3.jpg',15),
('calvinklein-pilot-p15-4.jpg',15),


('jacobnco-luxury-p16-1.jpg',16),
('jacobnco-luxury-p16-2.jpg',16),
('jacobnco-luxury-p16-3.jpg',16),
('jacobnco-luxury-p16-4.jpg',16),

('jacobnco-luxury-p17-1.jpg',17),
('jacobnco-luxury-p17-2.jpg',17),
('jacobnco-luxury-p17-3.jpg',17),
('jacobnco-luxury-p17-4.jpg',17),

('jacobnco-luxury-p18-1.jpg',18),

('jacobnco-luxury-p19-1.jpg',19),
('jacobnco-luxury-p19-2.jpg',19),
('jacobnco-luxury-p19-3.jpg',19),
('jacobnco-luxury-p19-4.jpg',19),


('jacobnco-field-p20-1.jpg',20),
('jacobnco-field-p20-2.jpg',20),
('jacobnco-field-p20-3.jpg',20),
('jacobnco-field-p20-4.jpg',20),

('casio-pilot-p21-1.jpg',21),
('casio-pilot-p21-2.jpg',21),
('casio-pilot-p21-3.jpg',21),
('casio-pilot-p21-4.jpg',21),

('casio-mechanical-p22-1.jpg',22),
('casio-mechanical-p22-2.jpg',22),
('casio-mechanical-p22-3.jpg',22),
('casio-mechanical-p22-4.jpg',22),

('casio-driving-p23-1.jpg',23),
('casio-driving-p23-2.jpg',23),
('casio-driving-p23-3.jpg',23),
('casio-driving-p23-4.jpg',23),

('casio-digital-p24-1.jpg',24),
('casio-digital-p24-2.jpg',24),
('casio-digital-p24-3.jpg',24),
('casio-digital-p24-4.jpg',24),

('casio-chronograph-p25-1.jpg',25),
('casio-chronograph-p25-2.jpg',25),
('casio-chronograph-p25-3.jpg',25),
('casio-chronograph-p25-4.jpg',25),

-----------------NAM---------------------

('citizen-analog-p26-1.jpg',26),
('citizen-analog-p26-2.jpg',26),
('citizen-analog-p26-3.jpg',26),

('citizen-field-p27-1.jpg',27),
('citizen-field-p27-2.jpg',27),
('citizen-field-p27-3.jpg',27),

('citizen-automatic-p28-1.jpg',28),
('citizen-automatic-p28-2.jpg',28),
('citizen-automatic-p28-3.jpg',28),

('citizen-automatic-p29-1.jpg',29),
('citizen-automatic-p29-2.jpg',29),
('citizen-automatic-p29-3.jpg',29),

('citizen-dress-p30-1.jpg',30),
('citizen-dress-p30-2.jpg',30),
('citizen-dress-p30-3.jpg',30),


('daniel wellington-analog-p31-1.jpg',31),
('daniel wellington-analog-p31-2.jpg',31),
('daniel wellington-analog-p31-3.jpg',31),
('daniel wellington-dress-p32-1.jpg',32),
('daniel wellington-dress-p32-2.jpg',32),
('daniel wellington-dress-p32-3.jpg',32),
('daniel wellington-quartz-p33-1.jpg',33),
('daniel wellington-quartz-p33-2.jpg',33),
('daniel wellington-quartz-p33-3.jpg',33),
('daniel wellington-automatic-p34-1.jpg',34),
('daniel wellington-automatic-p34-2.jpg',34),
('daniel wellington-automatic-p34-3.jpg',34),
('daniel wellington-dress-p35-1.jpg',35),
('daniel wellington-dress-p35-2.jpg',35),
('daniel wellington-dress-p35-3.jpg',35),

('doxa-automatic-p36-1.jpg',36),
('doxa-automatic-p36-2.jpg',36),
('doxa-automatic-p36-3.jpg',36),
('doxa-luxury-p37-1.jpg',37),
('doxa-luxury-p37-2.jpg',37),
('doxa-luxury-p37-3.jpg',37),
('doxa-quartz-p38-1.jpg',38),
('doxa-quartz-p38-2.jpg',38),
('doxa-quartz-p38-3.jpg',38),
('doxa-luxury-p39-1.jpg',39),
('doxa-luxury-p39-2.jpg',39),
('doxa-luxury-p39-3.jpg',39),
('doxa-luxury-p40-1.jpg',40),

('fossil-analog-p41-1.jpg',41),
('fossil-analog-p41-2.jpg',41),
('fossil-analog-p41-3.jpg',41),
('fossil-quartz-p42-1.jpg',42),
('fossil-quartz-p42-2.jpg',42),
('fossil-quartz-p42-3.jpg',42),
('fossil-dress-p43-1.jpg',43),
('fossil-dress-p43-2.jpg',43),
('fossil-dress-p43-3.jpg',43),
('fossil-automatic-p44-1.jpg',44),
('fossil-automatic-p44-2.jpg',44),
('fossil-automatic-p44-3.jpg',44),
('fossil-automatic-p45-1.jpg',45),
('fossil-automatic-p45-2.jpg',45),
('fossil-automatic-p45-3.jpg',45),

('gshock-quartz-p46-1.jpg',46),
('gshock-quartz-p46-2.jpg',46),
('gshock-quartz-p46-3.jpg',46),
('gshock-quartz-p47-1.jpg',47),
('gshock-quartz-p47-2.jpg',47),
('gshock-quartz-p47-3.jpg',47),
('gshock-quartz-p48-1.jpg',48),
('gshock-quartz-p48-2.jpg',48),
('gshock-quartz-p48-3.jpg',48),
('gshock-quartz-p49-1.jpg',49),
('gshock-quartz-p49-2.jpg',49),
('gshock-quartz-p49-3.jpg',49),
('gshock-quartz-p50-1.jpg',50),
('gshock-quartz-p50-2.jpg',50),
('gshock-quartz-p50-3.jpg',50),


---------------VƯƠNG------------------
('longines-analog-p1-1.jpg',51),
('longines-analog-p1-2.jpg',51),
('longines-analog-p1-3.jpg',51),
('longines-analog-p1-4.jpg',51),
('longines-digital-p2-1.jpg',52),
('longines-digital-p2-2.jpg',52),
('longines-digital-p2-3.jpg',52),
('longines-digital-p2-4.jpg',52),
('longines-diving-p3-1.jpg',53),
('longines-diving-p3-2.jpg',53),
('longines-diving-p3-3.jpg',53),
('longines-diving-p3-4.jpg',53),
('longines-quartz-p4-1.jpg',54),
('longines-quartz-p4-2.jpg',54),
('longines-quartz-p4-3.jpg',54),
('longines-quartz-p4-4.jpg',54),
('longines-luxury-p5-1.jpg',55),
('longines-luxury-p5-2.jpg',55),
('longines-luxury-p5-3.jpg',55),
('longines-luxury-p5-4.jpg',55),
('pulsar-automatic-p1-1.jpg',56),
('pulsar-automatic-p1-2.jpg',56),
('pulsar-automatic-p1-3.jpg',56),
('pulsar-automatic-p1-4.jpg',56),
('pulsar-quartz-p1.jpg',57),
('pulsar-quartz-p2.jpg',57),
('pulsar-quartz-p3.jpg',57),
('pulsar-quartz-p4.jpg',57),
('pulsar-mechanical-p1.jpg',58),
('pulsar-mechanical-p2.jpg',58),
('pulsar-mechanical-p3.jpg',58),
('pulsar-mechanical-p4.jpg',58),
('pulsar-diving-p1.jpg',59),
('pulsar-diving-p2.jpg',59),
('pulsar-diving-p3.jpg',59),
('pulsar-diving-p4.jpg',59),
('pulsar-chronograph-p1.jpg',60),
('pulsar-chronograph-p2.jpg',60),
('pulsar-chronograph-p3.jpg',60),
('pulsar-chronograph-p4.jpg',60),
('saga-p1-1.jpg',61),
('saga-p1-2.jpg',61),
('saga-p1-3.jpg',61),
('saga-p1-4.jpg',61),
('saga-chronograph-p2-1.jpg',62),
('saga-chronograph-p2-2.jpg',62),
('saga-chronograph-p3-3.jpg',62),
('saga-chronograph-p4-4.jpg',62),
('saga-diving-p3-1.jpg',63),
('saga-diving-p3-2.jpg',63),
('saga-diving-p3-3.jpg',63),
('saga-diving-p3-4.jpg',63),
('saga-pilot-p4-1.jpg',64),
('saga-pilot-p4-2.jpg',64),
('saga-pilot-p4-3.jpg',64),
('saga-pilot-p4-4.jpg',64),
('saga-p5-1.jpg',65),
('saga-p5-2.jpg',65),
('saga-p5-3.jpg',65),
('saga-p5-4.jpg',65),
('seiko-smart-p1-1.jpg',66),
('seiko-smart-p1-2.jpg',66),
('seiko-smart-p1-3.jpg',66),
('seiko-smart-p1-4.jpg',66),
('seiko-dress-p2-1.jpg',67),
('seiko-dress-p2-2.jpg',67),
('seiko-dress-p2-3.jpg',67),
('seiko-dress-p2-4.jpg',67),
('seiko-mechanical-p3-1.jpg',68),
('seiko-mechanical-p3-2.jpg',68),
('seiko-mechanical-p3-3.jpg',68),
('seiko-mechanical-p3-4.jpg',68),
('seiko-quartz-p4-1.jpg',69),
('seiko-quartz-p4-2.jpg',69),
('seiko-quartz-p4-3.jpg',69),
('seiko-quartz-p4-4.jpg',69),
('seiko-analog-p5-1.jpg',70),
('seiko-analog-p5-2.jpg',70),
('seiko-analog-p5-3.jpg',70),
('seiko-analog-p5-4.jpg',70),
--rolex
('rolex-luxury-p1-1.jpg',71),
('rolex-luxury-p1-2.jpg',71),
('rolex-luxury-p1-3.jpg',71),
('rolex-luxury-p1-4.jpg',71),
('rolex-luxury-p2-1.jpg',72),
('rolex-luxury-p2-2.jpg',72),
('rolex-luxury-p2-3.jpg',72),
('rolex-luxury-p2-4.jpg',72),
('rolex-luxury-p3-1.jpg',73),
('rolex-luxury-p3-2.jpg',73),
('rolex-luxury-p3-3.jpg',73),
('rolex-luxury-p3-4.jpg',73),
('rolex-luxury-p4-1.jpg',74),
('rolex-luxury-p4-2.jpg',74),
('rolex-luxury-p4-3.jpg',74),
('rolex-luxury-p4-4.jpg',74),
('rolex-luxury-p5-1.jpg',75),
('rolex-luxury-p5-2.jpg',75),
('rolex-luxury-p5-3.jpg',75),
('rolex-luxury-p5-4.jpg',75),

('phukien-p1-1.jpg',76),
('phukien-p1-2.jpg',76),
('phukien-p1-3.jpg',76),
('phukien-p1-4.jpg',76),

('phukien-p2-1.jpg',77),
('phukien-p2-2.jpg',77),
('phukien-p2-3.jpg',77),
('phukien-p2-4.jpg',77),

('phukien-p3-1.jpg',78),
('phukien-p3-2.jpg',78),
('phukien-p3-3.jpg',78),
('phukien-p3-4.jpg',78),

('phukien-p4-1.jpg',79),
('phukien-p4-2.jpg',79),
('phukien-p4-3.jpg',79),
('phukien-p4-4.jpg',79),

('phukien-p5-1.jpg',80),
('phukien-p5-2.jpg',80),
('phukien-p5-3.jpg',80),
('phukien-p5-4.jpg',80),
--NAM--
('phukien-p6-1.jpg',81),
('phukien-p6-2.jpg',81),
('phukien-p6-3.jpg',81),
('phukien-p6-4.jpg',81),

('phukien-p7-1.jpg',82),
('phukien-p7-2.jpg',82),
('phukien-p7-3.jpg',82),
('phukien-p7-4.jpg',82),

('phukien-p8-1.jpg',83),
('phukien-p8-2.jpg',83),
('phukien-p8-3.jpg',83),
('phukien-p8-4.jpg',83),

('phukien-p9-1.jpg',84),
('phukien-p9-2.jpg',84),
('phukien-p9-3.jpg',84),
('phukien-p9-4.jpg',84),

('phukien-p10-1.jpg',85),
('phukien-p10-2.jpg',85),
('phukien-p10-3.jpg',85),
('phukien-p10-4.jpg',85)
GO

---- Product Discount
INSERT INTO ProductDiscounts(Discount,CreateDate,EndDate,ProductId,CreateBy) VALUES
(3,'2022-05-15','2022-05-25',1,'admin01'),
(3,'2022-06-16','2022-06-26',2,'admin01'),
(3,'2022-06-22','2022-06-27',3,'admin01'),
(3,'2022-04-14','2022-04-21',4,'admin01'),
(3,'2022-07-09','2022-07-11',5,'admin01'),
(3,'2022-05-08','2022-05-22',6,'admin01'),
(3,'2022-08-15','2022-08-25',7,'admin01'),
(3,'2022-09-15','2022-09-22',8,'admin01'),
(4,'2022-07-22','2022-07-29',9,'admin01'),
(4,'2022-06-15','2022-06-21',10,'admin01'),
(4,'2022-06-01','2022-06-07',11,'admin01'),
(4,'2022-07-22','2022-07-2',12,'admin01'),
(4,'2022-09-24','2022-09-30',13,'admin01'),
(4,'2022-10-11','2022-05-18',14,'admin01'),
(4,'2022-12-12','2022-12-19',15,'admin01'),
(4,'2022-12-17','2022-12-24',16,'admin01'),
(4,'2022-11-01','2022-11-08',17,'admin01'),
(4,'2022-10-14','2022-10-21',18,'admin01'),
(4,'2022-06-21','2022-06-28',19,'admin01'),
(4,'2022-05-19','2022-05-26',20,'admin01'),
(4,'2022-05-13','2022-05-20',21,'admin01'),
(5,'2022-06-23','2022-06-30',22,'admin01'),
(5,'2022-02-15','2022-02-22',23,'admin01'),
(5,'2022-05-16','2022-05-23',24,'admin01'),
(5,'2022-06-23','2022-06-29',25,'admin01'),
(5,'2022-06-01','2022-06-08',26,'admin01'),
(5,'2022-06-15','2022-06-21',27,'admin01'),
(5,'2022-07-01','2022-07-08',28,'admin01'),
(5,'2022-07-01','2022-07-08',29,'admin01'),
(6,'2022-07-09','2022-07-16',30,'admin01'),
(6,'2022-07-09','2022-07-16',31,'admin01'),
(6,'2022-07-17','2022-07-24',32,'admin01'),
(6,'2022-07-24','2022-07-30',33,'admin01'),
(6,'2022-08-01','2022-08-08',34,'admin01'),
(6,'2022-08-09','2022-08-09',35,'admin01'),
(6,'2022-08-09','2022-08-16',36,'admin01'),
(6,'2022-08-16','2022-08-23',37,'admin01'),
(6,'2022-09-01','2022-09-15',38,'admin01'),
(6,'2022-09-01','2022-09-08',39,'admin01'),
(6,'2022-10-15','2022-10-22',40,'admin01')
GO


---OrderStatus---
INSERT INTO OrderStatuses(NAME) VALUES
(N'Chờ xác nhận'),
(N'Đã xác nhận'),
(N'Giao hàng'),
(N'Hoàn thành')
GO

-- payment
INSERT INTO OrderMethods(NAME) VALUES
(N'Thanh toán khi nhận hàng - COD'),
(N'Thanh toán qua Paypal'),
(N'Thanh toán qua Momo'),
(N'Thanh toán qua thẻ ghi nợ'),
(N'Chuyển khoản ngân hàng')
GO

---- Order
INSERT INTO Orders(CreateDate,Address,Username,OrderstatusId,OrdermethodId) VALUES
--cust01
('2022-05-16',N'383 Nguyễn Duy Trinh,Tp.Thủ Đức, Hồ Chí Minh','cust01',1,2),
('2022-05-15',N'10 Điện Biên Phủ, Bình Thạnh, Hồ Chí Minh','cust01',2,1),
('2022-05-12',N'38 Trần Não, Phường Bình An, Tp.Thủ Đức, Hồ Chí Minh','cust01',3,1),
('2020-01-10',N'33 Lê Anh Xuân, Quận 1, Hồ Chí minh','cust01',4,1),
--cust02
('2022-05-18',N'B21/7, Tổ 27, Ấp 2B, Xã Vĩnh Lộc B, Huyện Bình Chánh, TP Hồ Chí Minh','cust02',1,3),
('2022-05-17',N'25 Bàu Cát 2, Phường 14, Quận Tân Bình, TP Hồ Chí Minh','cust02',2,1),
('2022-05-11',N'13/1 Đường 12, Phường Linh Chiểu, Thành phố Thủ Đức, TP Hồ Chí Minh','cust02',3,1),
('2021-02-01',N'Tòa Nhà HM Town, 412 Nguyễn Thị Minh Khai, Phường 05, Quận 3, TP Hồ Chí Minh','cust02',4,1),
--cust03
('2022-05-17',N'18 Hoa Lan, Phường 02, Quận Phú Nhuận, TP Hồ Chí Minh','cust03',1,4),
('2022-05-11',N'145 Nguyễn Thái Bình, Phường Nguyễn Thái Bình, Quận 1, TP Hồ Chí Minh','cust03',2,1),
('2022-05-12',N'14/9 Thái Thị Nhạn, Phường 10, Quận Tân Bình, TP Hồ Chí Minh','cust03',3,1),
('2019-05-02',N'208 Trần Quang Khải, Phường Tân Định, Quận 1, TP Hồ Chí Minh','cust03',4,5),
--cust04
('2022-05-17',N'2279/8 Huỳnh Tấn Phát, Khu phố 7, Thị Trấn Nhà Bè, Huyện Nhà Bè, TP Hồ Chí Minh','cust04',1,5),
('2022-05-15',N'B208A KP3 Đông Hưng Thuận 13, Phường Tân Hưng Thuận, Quận 12, TP Hồ Chí Minh','cust04',2,3),
('2022-05-13',N'269/11 Ngô Chí Quốc, Phường Bình Chiểu, Thành phố Thủ Đức, TP Hồ Chí Minh','cust04',3,1),
('2020-05-10',N'61 Lâm Quang Vy, Phường Thạnh Mỹ Lợi, Thành phố Thủ Đức, TP Hồ Chí Minh','cust04',4,1),
--cust05
('2022-05-18',N'307 Phan Huy Ích, Phường 14, Quận Gò Vấp, TP Hồ Chí Minh','cust05',1,2),
('2022-05-16',N'948 Phạm Văn Đồng, Khu Phố 9, Phường Hiệp Bình Chánh, Thành phố Thủ Đức, TP Hồ Chí Minh','cust05',2,1),
('2022-05-13',N'101 Hồ Bá Kiện, Phường 15, Quận 10, TP Hồ Chí Minh','cust05',3,1),
('2021-12-11',N'67 Hàn Thuyên, Phường Bình Thọ, Thành phố Thủ Đức, TP Hồ Chí Minh','cust05',4,2),
--cust06
('2022-04-18',N'Số 3, D1, Khu tập thể Dệt Kim Đông Xuân, Phường Đồng Nhân, Quận Hai Bà Trưng, Hà Nội','cust06',4,2),
('2022-03-13',N'Số 3, D1, Khu tập thể Dệt Kim Đông Xuân, Phường Đồng Nhân, Quận Hai Bà Trưng, Hà Nội','cust06',4,1),
('2021-02-11',N'Số 3, D1, Khu tập thể Dệt Kim Đông Xuân, Phường Đồng Nhân, Quận Hai Bà Trưng, Hà Nội','cust06',4,5),
--cust07
('2021-04-18',N'Số 8 ngõ 83 Yên Duyên, Phường Yên Sở, Quận Hoàng Mai, Hà Nội','cust07',4,2),
('2021-03-13',N'Số 8 ngõ 83 Yên Duyên, Phường Yên Sở, Quận Hoàng Mai, Hà Nội','cust07',4,3),
('2022-02-11',N'Số 8 ngõ 83 Yên Duyên, Phường Yên Sở, Quận Hoàng Mai, Hà Nội','cust07',4,1),
--cust08
('2022-01-19',N'Số 8 ngõ 83 Yên Duyên, Phường Yên Sở, Quận Hoàng Mai, Hà Nội','cust08',4,1),
('2022-03-22',N'Số 8 ngõ 83 Yên Duyên, Phường Yên Sở, Quận Hoàng Mai, Hà Nội','cust08',4,2),
('2021-08-11',N'Số 8 ngõ 83 Yên Duyên, Phường Yên Sở, Quận Hoàng Mai, Hà Nội','cust08',4,5),
--cust09
('2022-05-07',N'Số 70 ngõ 172 đường Phú Diễn, Phường Phú Diễn, Quận Bắc Từ Liêm, Hà Nội','cust09',4,1),
('2022-05-13',N'Số 70 ngõ 172 đường Phú Diễn, Phường Phú Diễn, Quận Bắc Từ Liêm, Hà Nội','cust09',4,1),
('2021-12-11',N'Số 70 ngõ 172 đường Phú Diễn, Phường Phú Diễn, Quận Bắc Từ Liêm, Hà Nội','cust09',4,5),
--cust10
('2022-04-07',N'Số 42 đường Nguyễn Khuyến, Phường Văn Quán, Quận Hà Đông, Hà Nội','cust10',4,1),
('2022-01-13',N'Số 42 đường Nguyễn Khuyến, Phường Văn Quán, Quận Hà Đông, Hà Nội','cust10',4,1),
('2021-12-08',N'Số 42 đường Nguyễn Khuyến, Phường Văn Quán, Quận Hà Đông, Hà Nội','cust10',4,5),
('2021-02-08',N'Số 42 đường Nguyễn Khuyến, Phường Văn Quán, Quận Hà Đông, Hà Nội','cust10',4,2),
--cust11 
('2021-04-07',N'Số 5A Hoàng Văn Thụ, Phường Minh Khai, Quận Hồng Bàng, Hải Phòng','cust11',4,1),
('2021-01-13',N'Số 5A Hoàng Văn Thụ, Phường Minh Khai, Quận Hồng Bàng, Hải Phòng','cust11',4,1),
('2020-12-08',N'Số 5A Hoàng Văn Thụ, Phường Minh Khai, Quận Hồng Bàng, Hải Phòng','cust11',4,2),
--cust12  
('2019-07-07',N'Số S5.04 Vinhome Marina, đường Võ Nguyên Giáp, Phường Vĩnh Niệm, Quận Lê Chân, Hải Phòng','cust12',4,1),
('2020-08-13',N'Số S5.04 Vinhome Marina, đường Võ Nguyên Giáp, Phường Vĩnh Niệm, Quận Lê Chân, Hải Phòng','cust12',4,2),
('2021-10-08',N'Số S5.04 Vinhome Marina, đường Võ Nguyên Giáp, Phường Vĩnh Niệm, Quận Lê Chân, Hải Phòng','cust12',4,5),
--cust13 
('2020-08-07',N'96 Lý Thường Kiệt,, Phường Hoàng Văn Thụ, Quận Hồng Bàng, Hải Phòng','cust13',4,1),
('2021-06-15',N'96 Lý Thường Kiệt,, Phường Hoàng Văn Thụ, Quận Hồng Bàng, Hải Phòng','cust13',4,2),
('2018-10-19',N'96 Lý Thường Kiệt,, Phường Hoàng Văn Thụ, Quận Hồng Bàng, Hải Phòng','cust13',4,5),
--cust14 
('2021-03-09',N'Số 56 đường số 5B, Khu đô thị Waterfront, Phường Vĩnh Niệm, Quận Lê Chân, Hải Phòng','cust14',4,1),
('2022-04-11',N'Số 56 đường số 5B, Khu đô thị Waterfront, Phường Vĩnh Niệm, Quận Lê Chân, Hải Phòng','cust14',4,2),
('2019-08-21',N'Số 56 đường số 5B, Khu đô thị Waterfront, Phường Vĩnh Niệm, Quận Lê Chân, Hải Phòng','cust14',4,5),
--cust15 
('2018-03-09',N' 88 Bàu Gia Thượng 2, Phường Hoà Thọ Đông, Quận Cẩm Lệ, Đà Nẵng','cust15',4,1),
('2020-02-11',N' 88 Bàu Gia Thượng 2, Phường Hoà Thọ Đông, Quận Cẩm Lệ, Đà Nẵng','cust15',4,2),
('2019-01-21',N' 88 Bàu Gia Thượng 2, Phường Hoà Thọ Đông, Quận Cẩm Lệ, Đà Nẵng','cust15',4,5),

--cust16 
('2021-04-12',N'Lô 11, Khu LK04A, khu đô thị Hòa Quý,Phường Hoà Quý, Quận Ngũ Hành Sơn, Đà Nẵng','cust16',4,1),
('2022-03-16',N'Lô 11, Khu LK04A, khu đô thị Hòa Quý, Phường Hoà Quý, Quận Ngũ Hành Sơn, Đà Nẵng','cust16',4,1),
('2020-05-20',N'Lô 11, Khu LK04A, khu đô thị Hòa Quý, Phường Hoà Quý, Quận Ngũ Hành Sơn, Đà Nẵng','cust16',4,5),
--cust17 
('2021-06-12',N'51 Tống Phước Phổ, Phường Hoà Cường Bắc, Quận Hải Châu, Đà Nẵng','cust17',4,1),
('2020-09-16',N'51 Tống Phước Phổ, Phường Hoà Cường Bắc, Quận Hải Châu, Đà Nẵng','cust17',4,5),
('2019-07-20',N'51 Tống Phước Phổ, Phường Hoà Cường Bắc, Quận Hải Châu, Đà Nẵng','cust17',4,2),

--cust18
('2020-09-21',N'48 Nguyễn Chí Thanh, Phường Thạch Thang, Quận Hải Châu, Đà Nẵng','cust18',4,2),
('2021-10-20',N'48 Nguyễn Chí Thanh, Phường Thạch Thang, Quận Hải Châu, Đà Nẵng','cust18',4,1),
('2019-08-10',N'48 Nguyễn Chí Thanh, Phường Thạch Thang, Quận Hải Châu, Đà Nẵng','cust18',4,5),
--cust19 
('2021-07-31',N'132/78 Hùng Vương,, Phường Thới Bình, Quận Ninh Kiều, Cần Thơ','cust19',4,2),
('2020-02-20',N'132/78 Hùng Vương,, Phường Thới Bình, Quận Ninh Kiều, Cần Thơ','cust19',4,2),
('2019-05-10',N'132/78 Hùng Vương,, Phường Thới Bình, Quận Ninh Kiều, Cần Thơ','cust19',4,1),
--cust20 tới đây
('2020-09-20',N'14 Trần Văn Hoài, Phường Xuân Khánh, Quận Ninh Kiều, Cần Thơ','cust20',4,1),
('2021-11-20',N'14 Trần Văn Hoài, Phường Xuân Khánh, Quận Ninh Kiều, Cần Thơ','cust20',4,2),
('2019-02-10',N'14 Trần Văn Hoài, Phường Xuân Khánh, Quận Ninh Kiều, Cần Thơ','cust20',4,4),
--cust21 
('2021-06-28',N'Số 356 Đội Cấn, Phường Cống Vị, Quận Ba Đình, Hà Nội','cust21',4,2),
('2020-02-20',N'Số 356 Đội Cấn, Phường Cống Vị, Quận Ba Đình, Hà Nội','cust21',4,1),
('2019-08-07',N'Số 356 Đội Cấn, Phường Cống Vị, Quận Ba Đình, Hà Nội','cust21',4,1),
--cust22: 
('2019-05-23',N'Số 6 Đặng Dung, Phường Trúc Bạch, Quận Ba Đình, Hà Nội','cust22',4,1),
('2020-03-20',N'Số 6 Đặng Dung, Phường Trúc Bạch, Quận Ba Đình, Hà Nội','cust22',4,2),
('2021-01-07',N'Số 6 Đặng Dung, Phường Trúc Bạch, Quận Ba Đình, Hà Nội','cust22',4,1),
--cust23 : 
('2018-12-22',N'Số 28, ngõ 6, đường Võng Thị, Phường Bưởi, Quận Tây Hồ, Hà Nội','cust23',4,2),
('2019-10-20',N'Số 28, ngõ 6, đường Võng Thị, Phường Bưởi, Quận Tây Hồ, Hà Nội','cust23',4,1),
('2020-04-07',N'Số 28, ngõ 6, đường Võng Thị, Phường Bưởi, Quận Tây Hồ, Hà Nội','cust23',4,1),
--cust24
('2019-08-11',N'Số 606 Lạc Long Quân, Phường Nhật Tân, Quận Tây Hồ, Hà Nội','cust24',4,1),
('2020-09-20',N'Số 606 Lạc Long Quân, Phường Nhật Tân, Quận Tây Hồ, Hà Nội','cust24',4,2),
('2021-10-07',N'Số 606 Lạc Long Quân, Phường Nhật Tân, Quận Tây Hồ, Hà Nội','cust24',4,4),
--cust25 : 
('2022-02-14',N'Số nhà 52, ngõ 230, phố Lạc Trung, Phường Thanh Lương, Quận Hai Bà Trưng, Hà Nội','cust25',4,1),
('2021-12-20',N'Số nhà 52, ngõ 230, phố Lạc Trung, Phường Thanh Lương, Quận Hai Bà Trưng, Hà Nội','cust25',4,3),
('2020-11-07',N'Số nhà 52, ngõ 230, phố Lạc Trung, Phường Thanh Lương, Quận Hai Bà Trưng, Hà Nội','cust25',4,5),
--cust26: 
('2021-04-13',N'Số 1529B đường 30/4, Phường 12, Thành Phố Vũng Tàu, Bà Rịa - Vũng Tàu','cust26',4,2),
('2020-09-20',N'Số 1529B đường 30/4, Phường 12, Thành Phố Vũng Tàu, Bà Rịa - Vũng Tàu','cust26',4,1),
('2019-10-07',N'Số 1529B đường 30/4, Phường 12, Thành Phố Vũng Tàu, Bà Rịa - Vũng Tàu','cust26',4,5),
--cust27 :
('2018-11-12',N'Tổ 5, ấp Phước Lập, Xã Mỹ Xuân, Huyện Tân Thành, Bà Rịa - Vũng Tàu','cust27',4,1),
('2019-12-20',N'Tổ 5, ấp Phước Lập, Xã Mỹ Xuân, Huyện Tân Thành, Bà Rịa - Vũng Tàu','cust27',4,1),
('2020-02-05',N' Tổ 5, ấp Phước Lập, Xã Mỹ Xuân, Huyện Tân Thành, Bà Rịa - Vũng Tàu','cust27',4,5),
--cust28: 
('2020-01-10',N'Số 159 Võ Thị Sáu, Khu phố Long Nguyên, Thị trấn Long Điền, Huyện Long Điền, Bà Rịa - Vũng Tàu','cust28',4,1),
('2021-02-24',N'Số 159 Võ Thị Sáu, Khu phố Long Nguyên, Thị trấn Long Điền, Huyện Long Điền, Bà Rịa - Vũng Tàu','cust28',4,1),
('2022-03-02',N'Số 159 Võ Thị Sáu, Khu phố Long Nguyên, Thị trấn Long Điền, Huyện Long Điền, Bà Rịa - Vũng Tàu','cust28',4,5),
--cust29 : 
('2021-04-12',N'22D2 Tống Duy Tân, Phường 9, Thành Phố Vũng Tàu, Bà Rịa - Vũng Tàu','cust29',4,1),
('2018-05-11',N'22D2 Tống Duy Tân, Phường 9, Thành Phố Vũng Tàu, Bà Rịa - Vũng Tàu','cust29',4,1),
('2019-06-14',N'22D2 Tống Duy Tân, Phường 9, Thành Phố Vũng Tàu, Bà Rịa - Vũng Tàu','cust29',4,5),
--cust30 : 
('2020-07-14',N'34 Sao Biển, Phường Vĩnh Hải, Thành phố Nha Trang, Khánh Hòa','cust30',4,1),
('2021-07-22',N'34 Sao Biển, Phường Vĩnh Hải, Thành phố Nha Trang, Khánh Hòa','cust30',4,1),
('2019-08-25',N'34 Sao Biển, Phường Vĩnh Hải, Thành phố Nha Trang, Khánh Hòa','cust30',4,5),
--cust31: 
('2020-09-23',N'212 Trần Quý Cáp, Phường Phương Sài, Thành phố Nha Trang, Khánh Hòa','cust31',4,1),
('2019-10-20',N'212 Trần Quý Cáp, Phường Phương Sài, Thành phố Nha Trang, Khánh Hòa','cust31',4,2),
('2022-01-02',N'212 Trần Quý Cáp, Phường Phương Sài, Thành phố Nha Trang, Khánh Hòa','cust31',4,1),
--cust32 : 
('2020-12-10',N'43/1 Phước Long, Phường Phước Long, Thành phố Nha Trang, Khánh Hòa','cust32',4,2),
('2022-02-20',N'43/1 Phước Long, Phường Phước Long, Thành phố Nha Trang, Khánh Hòa','cust32',4,1),
('2019-01-11',N'43/1 Phước Long, Phường Phước Long, Thành phố Nha Trang, Khánh Hòa','cust32',4,5),
--cust33:
('2022-01-12',N'34/2/28 Nguyễn Thiện Thuật, Phường Tân Lập, Thành phố Nha Trang, Khánh Hòa','cust33',4,4),
('2020-04-22',N'34/2/28 Nguyễn Thiện Thuật, Phường Tân Lập, Thành phố Nha Trang, Khánh Hòa','cust33',4,2),
('2021-03-07',N'34/2/28 Nguyễn Thiện Thuật, Phường Tân Lập, Thành phố Nha Trang, Khánh Hòa','cust33',4,5),
--cust34:  
('2022-04-13',N'Số Nhà 199, Tổ 1 Phố Vàng,, Thị trấn Thanh Sơn, Huyện Thanh Sơn, Phú Thọ','cust34',4,2),
('2020-08-20',N'Số Nhà 199, Tổ 1 Phố Vàng,, Thị trấn Thanh Sơn, Huyện Thanh Sơn, Phú Thọ','cust34',4,1),
('2019-01-02',N'Số Nhà 199, Tổ 1 Phố Vàng,, Thị trấn Thanh Sơn, Huyện Thanh Sơn, Phú Thọ','cust34',4,1),
--cust35: 
('2022-06-10',N'Số nhà 06, Khu Phú Lợi, Phường Phong Châu, Thị xã Phú Thọ, Phú Thọ','cust35',4,5),
('2019-02-20',N'Số nhà 06, Khu Phú Lợi, Phường Phong Châu, Thị xã Phú Thọ, Phú Thọ','cust35',4,1),
('2020-10-07',N'Số nhà 06, Khu Phú Lợi, Phường Phong Châu, Thị xã Phú Thọ, Phú Thọ','cust35',4,1)

GO

--ORDERDETAILS
INSERT INTO OrderDetails(OrderId,ProductId,Price,Quantity) values
(1,75,80000,1),
(2,74,72500,1),
(3,73,99600,1),
(4,72,35000,1),
(5,71,25000,1),
(6,70,390,1),
(7,69,310,1),
(8,68,340,1),
(9,67,340,1),
(10,66,340,1),
(11,65,110,1),
(12,9,200,1),
(13,8,200,1),
(14,7,250,1),
(15,6,200,1),
(16,5,2,1),
(17,4,15,1),
(18,3,10,1),
(19,2,5,1),
(20,1,2,1),
(21,10,250,1),
(22,11,100,1),
(23,12,60,1),
--cust07
(24,13,100,1),
(25,14,100,1),
(26,15,100,1),
--cust08
(27,16,649702,1),
(28,17,349702,1),
(29,18,649702,1),
--cust09
(30,19,300,1),
(31,20,500,1),
(32,21,25,1),
--cust10
(33,22,20,1),
(34,23,35,1),
(35,24,30,1),
(35,25,25,1),
--cust11 
(36,26,220,1),
(37,27,170,1),
(38,28,200,1),
--cust12
(39,29,160,1),
(40,30,135,1),
(41,31,145,1),
--cust13
(42,32,145,1),
(43,33,150,1),
(44,34,155,1),
--cust14
(45,35,120,1),
(46,36,6500,1),
(47,37,1800,1),
--cust15
(48,38,750,1),
(49,39,2350,1),
(50,40,6500,1),
--cust16
(51,41,185,1),
(52,42,160,1),
(53,43,185,1),
--cust17
(54,44,225,1),
(55,45,350,1),
(56,46,165,1),
--cust18
(57,47,225,1),
(58,48,222,1),
(59,49,165,1),
--cust19
(60,50,235,1),
-- tới đây
(61,51,3500,1),
(61,52,2000,1),
--cust20
(63,53,2550,1),
(64,54,1250,1),
(65,55,3450,1),
--cust21
(66,56,3350,1),
(67,57,120,1),
(68,58,150,1),
--cust22
(69,59,75,1),
(70,60,135,1),
(71,61,125,1),
--cust23
(72,62,275,1),
(73,63,550,1),
(74,64,250,1),
--cust24
(75,65,110,1),
(76,66,340,1),
(77,67,340,1),
--cust25
(78,68,340,1),
(79,69,310,1),
(80,70,390,1),
--cust26
(81,71,25000,1),
(82,72,35000,1),
(83,73,99600,1),
--cust27
(84,74,72500,1),
(85,75,80000,1),
(86,75,80000,1),
--cust28
(87,74,72500,1),
(88,73,99600,1),
(89,72,35000,1),
--cust29
(90,71,25000,1),
(91,70,390,1),
(92,69,310,1),
--cust30
(93,68,340,1),
(94,67,340,1),
(95,66,340,1),
--cust31
(96,65,110,1),
(97,64,250,1),
(98,63,550,1),
--cust32
(99,62,275,1),
(101,61,125,1),
(102,60,135,1),
--cust33
(103,59,75,1),
(104,8,150,1),
(105,57,120,1),
--cust34
(106,56,3350,1),
(107,55,3450,1),
(108,54,1250,1),
--cust35
(109,53,2250,1),
(110,52,2000,1),
(11,51,3500,1)
GO

---ProductLikes
INSERT INTO ProductLikes(IsLiked,ProductId,Username) VALUES
(0,1,'cust01'),
(1,2,'cust01'),
(1,3,'cust01'),
(1,4,'cust01'),
(1,5,'cust01'),
(1,6,'cust01'),
(1,7,'cust01'),
(1,8,'cust01'),
(1,9,'cust01'),
(0,10,'cust02'),
(0,11,'cust02'),
(1,12,'cust02'),
(1,13,'cust02'),
(1,14,'cust02'),
(1,15,'cust02'),
(1,16,'cust02'),
(1,17,'cust02'),
(1,18,'cust02'),
(1,19,'cust02'),
(0,20,'cust02'),
(0,21,'cust03'),
(1,22,'cust03'),
(1,23,'cust03'),
(1,24,'cust03'),
(1,25,'cust03'),
(1,26,'cust03'),
(1,27,'cust03'),
(1,28,'cust03'),
(1,29,'cust03'),
(0,30,'cust04'),
(0,31,'cust04'),
(1,32,'cust04'),
(1,33,'cust04'),
(1,34,'cust04'),
(1,35,'cust04'),
(1,36,'cust04'),
(1,37,'cust04'),
(1,38,'cust04'),
(1,39,'cust04'),
(0,40,'cust05'),
(0,41,'cust05'),
(1,42,'cust05'),
(1,43,'cust05'),
(1,44,'cust05'),
(1,45,'cust05'),
(1,46,'cust05'),
(1,47,'cust05'),
(1,48,'cust05'),
(1,49,'cust05'),
(0,50,'cust05')
GO

----ProductEvaluations
INSERT INTO ProductEvaluations(Evaluation,ProductId,Username) VALUES
(4,1,'cust01'),
(4,2,'cust01'),
(4,3,'cust01'),
(4,4,'cust01'),
(4,5,'cust01'),
(4,6,'cust02'),
(4,7,'cust02'),
(5,8,'cust02'),
(5,9,'cust02'),
(5,10,'cust02'),
(5,11,'cust03'),
(5,12,'cust03'),
(5,13,'cust03'),
(5,14,'cust03'),
(5,15,'cust03'),
(5,16,'cust04'),
(3,17,'cust04'),
(3,18,'cust04'),
(3,19,'cust04'),
(3,20,'cust04'),
(3,21,'cust05'),
(5,22,'cust05'),
(5,23,'cust05'),
(5,24,'cust05'),
(5,25,'cust05'),
(3,26,'cust06'),
(5,27,'cust06'),
(5,28,'cust06'),
(5,29,'cust06'),
(5,30,'cust06'),

(5,26,'cust07'),
(5,27,'cust07'),
(5,28,'cust07'),
(5,29,'cust07'),
(5,30,'cust07'),

(5,26,'cust08'),
(5,27,'cust08'),
(5,28,'cust08'),
(5,29,'cust08'),
(5,30,'cust08'),

(5,26,'cust10'),
(5,27,'cust10'),
(5,28,'cust10'),
(5,29,'cust10'),
(5,30,'cust10'),
(3,70,'cust09'),
(5,71,'cust09'),
(5,72,'cust09'),
(5,73,'cust09'),
(5,74,'cust09'),

(5,75,'cust11'),
(5,74,'cust11'),
(5,73,'cust11'),
(5,72,'cust11'),
(5,71,'cust11'),

(5,70,'cust13'),
(5,71,'cust13'),
(5,72,'cust13'),
(5,73,'cust13'),
(5,74,'cust13'),

(5,46,'cust14'),
(5,47,'cust14'),
(5,48,'cust14'),
(5,49,'cust14'),
(5,50,'cust14'),

(5,46,'cust15'),
(5,47,'cust15'),
(5,48,'cust15'),
(5,49,'cust15'),
(5,50,'cust15'),
(5,46,'cust15'),

(5,47,'cust16'),
(5,48,'cust16'),
(5,49,'cust16'),
(5,50,'cust16'),
(5,46,'cust16'),

(5,40,'cust17'),
(5,41,'cust17'),
(5,42,'cust17'),
(5,43,'cust17'),
(5,44,'cust17'),
(5,26,'cust18'),
(5,45,'cust18'),
(5,46,'cust18'),
(5,47,'cust18'),
(5,48,'cust18'),
(5,49,'cust18'),
(5,50,'cust18'),
(5,51,'cust19'),
(5,52,'cust19'),
(5,53,'cust19'),
(5,54,'cust19'),
(5,55,'cust19'),
(5,56,'cust19'),
(5,57,'cust20'),
(5,58,'cust20'),
(5,59,'cust20'),
(5,60,'cust20'),
(5,61,'cust20'),
(5,62,'cust20'),
(5,62,'cust20'),
(5,63,'cust21'),
(5,64,'cust21'),
(5,65,'cust21'),
(5,66,'cust21'),
(5,67,'cust21'),
(5,68,'cust21'),
(5,69,'cust21'),
(5,66,'cust21'),
(5,45,'cust21'),
(5,56,'cust22'),
(5,57,'cust22'),
(5,58,'cust22'),
(5,59,'cust22'),
(5,60,'cust22'),
(5,55,'cust22'),
(5,54,'cust22'), 
(5,31,'cust23'),
(5,32,'cust24'),
(5,33,'cust25'),
(5,34,'cust26'),
(5,35,'cust27'),
(5,36,'cust28'),
(5,37,'cust29'),
(5,38,'cust30'),
(5,39,'cust31'),
(5,40,'cust32'),
(5,41,'cust33'),
(5,43,'cust34'),
(5,44,'cust35')
GO

--PRODUCT COMMENTS
INSERT INTO ProductComments(TopicUsername,CommentContent,ProductId) VALUES
(N'cust01',N'Sản phẩm quá đẹp, tuyệt vời',1),
(N'cust01',N'Siêu phẩm năm nay',2),
(N'cust01',N'Sản phẩm đẹp, nhẹ, sang trọng',3),
(N'cust01',N'Tôi đã tham khảo rất nhiều nơi nhưng sản phẩm ở đây bán thật sự rất tốt',4),
(N'cust01',N'Sản phẩm này được ra mắt khi nào vậy ạ?',5),
--user2
(N'cust02',N'Chiếc này và chiếc SE-1002 khác nhau chỗ nào vậy ạ',6),
(N'cust02',N'Sản phẩm có hỗ trợ đổi trả không ??',7),
(N'cust02',N'Sản phẩm free ship ở HCM không admin ơi?',8),
(N'cust02',N'Chiếc này dây đeo bằng cacbon phải không shop?',9),
(N'cust02',N'Địa chỉ shop ở đâu ạ',10),
--user3
(N'cust03',N'Shop có phiên bản giới hạn của chiếc này không?',11),
(N'cust03',N'Khi nào hàng về ạ?',12),
(N'cust03',N'Sản phẩm này có khuyến mãi kèm dây đeo không?',13),
(N'cust03',N'Admin cho mình hỏi mình mua chiếc này giờ muốn thay dây thì làm thế nào ạ',14),
(N'cust03',N'Khi nào có phiên bản v2 của sản phẩm này vậy ạ?',15)
GO
--Product Photo
INSERT INTO CommentPhotos(ID,CommentId) VALUES
('babyg-analog-p1-1.jpg',1),
('babyg-automatic-p3-1.jpg',2),
('babyg-digital-p2-1.jpg',3)
GO

INSERT INTO ProductReplies(CommentId,ReplyContent,Username) VALUES
(1,N'Cảm ơn bạn đã chia sẻ','admin01'),
(2,N'Godshop cảm ơn bạn đã ủng hộ','admin01'),
(3,N'Cảm ơn thông tin đánh giá của bạn, mong bạn luôn ủng hộ shop','admin01'),
(4,N'Cảm ơn (User01) đánh giá cao sản phẩm của GodShop','admin01'),
(5,N'Dạ Sản Phẩm này được ra mắt vào thời điểm tháng 03-2022, đây là sản phẩm mới nhất của Shop ạ','admin01'),
--reply user02
(6,N'Dạ chiếc này sử dụng dây đeo tay bằng cacbon sợi và chiếc SE-1002 sử dụng dây đeo là titanium, 2 chất liệu dây khác nhau thôi ạ','admin02'),
(7,N'Sản phẩm của GodShop đảm bảo giá tốt nhất từ hãng, hỗ trợ đổi trả sản phẩm trong 7 ngày nếu gặp lỗi, hỗ trợ thu lại giá = 90% trong 30 ngày đầu tiên','admin02'),
(8,N'Shop miễn phí ship khắp khu vực Miền Nam và 1 số tỉnh thành phía Bắc bạn nhé','admin02'),
(9,N'Dạ đúng rồi bạn, dây đeo tay bằng cacbon sợi','admin02'),
(10,N'Địa chỉ của shop ở 112 Tên Lửa, Bình Trị Đông B, Quận Bình Tân,TP.HCM bạn nhé, mong bạn ghé ủng hộ','admin02'),

--reply user03
(11,N'Cảm ơn bạn đã quan tâm tới sản phẩm của chúng tôi, rất tiếc hiện tại chưa có thông tin nào về bản giới hạn của sản phẩm này ạ','admin03'),
(12,N'Sản phẩm có sẵn tại shop, rất vui được phục vụ bạn','admin03'),
(13,N'Sản phẩm không có kèm khuyến mại dây đeo, tuy nhiên GodShop có rất nhiều dây đeo chính hãng kiểu dáng lịch sự, hiện đại.. bạn có thể tham khảo trực tiếp tại cửa hàng ạ','admin03'),
(14,N'Bạn vui để lòng giữ điện thoại, sẽ có nhân viên liên hệ với bạn để hướng dẫn','admin03'),
(15,N'Phiên bản V2 sẽ được ra mắt trong tháng 11 năm nay, GodShop sẽ gửi 1 email tới bạn khi có sản phẩm V2, cảm ơn bạn đã quan tâm','admin03')
GO


--=====================phụ kiện===============================
INSERT INTO Accessories(Colors,BraceletmaterialId,ProductId) VALUES
(N'Xanh',1,76),
(N'Đen',2,77),
(N'Đen',3,78),
(N'Nâu',5,79),
(N'Nâu Cam',4,80),
(N'Nâu Đen',1,81),
(N'Đen',6,82),
(N'Đỏ Trắng',4,83),
(N'Bạc',2,84),
(N'Đen',1,85)
GO
--==================================================== 
UPDATE Products set IsDeleted = 1 where id = 70 or id = 26;


--==================================================== 

		
select c.name, b.name, p.name, pp.Id, w.* from products as p
inner join Watches as w on p.id = w.ProductId
inner join ProductPhotos as pp on pp.ProductId = p.id
inner join Categories as c on p.CategoryId = c.Id
inner join Brands as b on p.BrandId = b.Id
WHERE p.name = 'BA-130-7A1DR' OR p.name = 'OK'
order by p.Price asc

select * from Watches

select p.name, w.* from Products as p
inner join Watches as w on p.id = w.ProductId
WHERE p.name = 'OK'

select * from Products as p
WHERE p.name = 'OK'


select * from Accounts

select p.name, pp.Id from products as p
inner join ProductPhotos as pp on pp.ProductId = p.id
WHERE p.name = 'BA-130-7A1DR'
