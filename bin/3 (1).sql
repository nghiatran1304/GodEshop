/*
	USE MASTER
	GO
	DROP DATABASE GodShop
	GO
*/

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
	CreateDate DATETIME NOT NULL,
	IsDeleted BIT DEFAULT 0,
	RoleId VARCHAR(15) NOT NULL,
	FOREIGN KEY (RoleId) REFERENCES Roles(Id)
);
GO

CREATE TABLE RefAccounts(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Refer VARCHAR(50), -- người nhập mã giới thiệu
	Receiver VARCHAR(50), -- người giới thiệu
	IsReward BIT DEFAULT 0,
	FOREIGN KEY (Receiver) REFERENCES Accounts(Username),
);
GO

CREATE TABLE Authorities(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Username VARCHAR(50) NOT NULL,
	RoleId VARCHAR(15) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username),
	FOREIGN KEY (RoleId) REFERENCES Roles(Id)
);
GO

CREATE TABLE Users(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Fullname NVARCHAR(50) NOT NULL,
	Dob DATE NOT NULL,
	Gender INT DEFAULT 1, -- 0 Nữ | 1 Nam | 2 Others 
	Phone VARCHAR(12) NOT NULL,
	Email VARCHAR(100) NOT NULL,
	Photo VARCHAR(255) NULL,
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);


CREATE TABLE Vouchers(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name VARCHAR(20) NOT NULL,
	Discount INT DEFAULT 0,
	CreateDate DATETIME NOT NULL,
	EndDate DATETIME NOT NULL,
	CreateBy VARCHAR(50),
	FOREIGN KEY (CreateBy) REFERENCES Accounts(Username)
);
GO

CREATE TABLE Gcoins(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Gcoin INT DEFAULT 0, -- số lượng gcoin trong 1 tài khoản
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

-- Danh sách các voucher trong ví của tài khoản
CREATE TABLE VoucherLists(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IsUsed BIT DEFAULT 0,
	VoucherID INT,
	FOREIGN KEY (VoucherID) REFERENCES Vouchers(Id),
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

/*
CREATE TABLE Wallets(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	GcoinId INT,
	VoucherListId INT,
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username),
	FOREIGN KEY (GcoinId) REFERENCES Gcoins(id),
	FOREIGN KEY (VoucherListId) REFERENCES VoucherLists(id)
);
GO
*/

CREATE TABLE Brands(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(250) NOT NULL
);
GO

CREATE TABLE Categories(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE SubCategories(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(50) NOT NULL,
	CategoryId INT,
	FOREIGN KEY (CategoryId) REFERENCES Categories(Id)
);
GO

CREATE TABLE Products(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(100) NOT NULL,
	Price FLOAT DEFAULT 0,
	Material NVARCHAR(200) NOT NULL, -- chất liệu
	MadeIn NVARCHAR(50) NOT NULL,
	CreateDate DATETIME NOT NULL,
	Gender INT DEFAULT 0, -- 0 Nữ | 1 Nam | 2 Unisex 
	Detail NTEXT, -- Mô tả thêm thông tin
	BrandId INT NOT NULL,
	CategoryId INT NULL,
	SubCategoryId INT NULL,
	FOREIGN KEY (SubCategoryId) REFERENCES SubCategories(Id),
	FOREIGN KEY (CategoryId) REFERENCES Categories(Id),
	FOREIGN KEY (BrandId) REFERENCES Brands(Id)
);
GO

-- Giảm giá sản phẩm
CREATE TABLE ProductDiscounts(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Discount INT DEFAULT 0,
	CreateDate DATETIME NOT NULL,
	EndDate DATETIME NOT NULL,
	ProductId INT,
	CreateBy VARCHAR(50) NOT NULL,
	FOREIGN KEY (CreateBy) REFERENCES Accounts(Username),
	FOREIGN KEY (ProductId) REFERENCES Products(Id)
);
GO

CREATE TABLE ProductPhotos(
	Id VARCHAR(100) PRIMARY KEY,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id)
);
GO

CREATE TABLE ProductDetails(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	DetailKey NVARCHAR(150) NOT NULL,
	DetailValue NVARCHAR(150) NOT NULL,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id)
);
GO

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

CREATE TABLE Orders(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	CreateDate DATE NOT NULL,
	Address NTEXT NOT NULL,
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
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


CREATE TABLE Histories(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IdObject NVARCHAR(50) NOT NULL,
	ObjectName NVARCHAR(50) NOT NULL,
	DetailKey NVARCHAR(150) NOT NULL,
	DetailValue NVARCHAR(150) NOT NULL,
	CreateDate DATETIME NOT NULL,
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

--==============================================================================================================
--======= Roles
INSERT INTO Roles(Id, Name) VALUES
('CUSTOMER', N'Khách hàng'),
('MANAGER', N'Quản lý'),
('ADMIN', N'Chủ cửa hàng')
GO

--======= Accounts
INSERT INTO Accounts(Username, Password, CreateDate, IsDeleted, RoleId) VALUES
('admin01', 'admin01', '2021-07-19', 0, 'ADMIN'),
('manager01', 'manager01', '2021-07-19', 0, 'MANAGER'),
('manager02', '123', '2021-07-19', 0, 'MANAGER'),
('customer1', 'customer1', '2021-12-13', 0, 'CUSTOMER'),
('customer2', '123', '2022-02-26', 0, 'CUSTOMER'),
('customer3', '123', '2022-02-26', 0, 'CUSTOMER'),
('customer4', '123', '2022-02-26', 0, 'CUSTOMER'),
('customer5', '123', '2021-12-13', 0, 'CUSTOMER'),
('customer6', '123', '2021-12-13', 0, 'CUSTOMER'),
('customer7', '123', '2021-12-13', 0, 'CUSTOMER'),
('customer8', '123', '2021-12-13', 0, 'CUSTOMER'),
('customer9', '123', '2022-04-15', 0, 'CUSTOMER'),
('customer10', '123', '2022-04-15', 0, 'CUSTOMER'),
('customer11', '123', '2022-04-13', 0, 'CUSTOMER'),
('customer12', '123', '2022-03-15', 0, 'CUSTOMER'),
('customer13', '123', '2022-01-15', 0, 'CUSTOMER')
GO

--======= RefAccounts
INSERT INTO RefAccounts(Refer, Receiver, IsReward) VALUES
('customer2', 'customer1', 0),
('customer3', 'customer1', 0),
('customer4', 'customer1', 0),
('customer5', 'customer1', 0),
('customer6', 'customer1', 0),
('customer7', 'customer2', 0),
('customer8', 'customer2', 0),
('customer9', 'customer3', 0),
('customer10', 'customer4', 0)
GO

--======= Authorities
INSERT INTO Authorities(Username, RoleId) VALUES
('admin01', 'ADMIN'),
('manager01', 'MANAGER'),
('manager02', 'MANAGER'),
('customer1', 'CUSTOMER'),
('customer2', 'CUSTOMER'),
('customer3', 'CUSTOMER'),
('customer4', 'CUSTOMER'),
('customer5', 'CUSTOMER'),
('customer6', 'CUSTOMER'),
('customer7', 'CUSTOMER'),
('customer8', 'CUSTOMER'),
('customer9', 'CUSTOMER'),
('customer10', 'CUSTOMER'),
('customer11', 'CUSTOMER'),
('customer12', 'CUSTOMER'),
('customer13', 'CUSTOMER')
GO

--======= Users
INSERT INTO Users(Fullname, Dob, Gender, Phone, Email, Photo, Username) VALUES
(N'Trần Trung Nghĩa', '1997-04-13', 1, '0366888470', 'nghiattps14820@fpt.edu.vn', 'nghia.jpg', 'admin01')

GO

--======= Vouchers
INSERT INTO Vouchers(Name, Discount, CreateDate, EndDate, CreateBy) VALUES
('DISCOUNT01', 10, '20220501 10:00:09 AM', '20220830 10:00:09 AM', 'manager01'),
('DISCOUNT02', 5, '20220501 10:00:09 AM', '20220830 10:00:09 AM', 'manager01'),
('DISCOUNT03', 20, '20220213 07:00:00 AM', '20220213 11:00:00 PM', 'manager02'),
('DISCOUNT04', 15, '20220111 06:30:00 AM', '20220112 06:30:00 AM', 'manager02'),
('DISCOUNT05', 10, '20220315 12:00:00 AM', '20220316 12:00:00 PM', 'manager01')
GO

--======= Gcoins
INSERT INTO Gcoins(Gcoin, Username) VALUES
(10, 'customer1'),
(5, 'customer2'),
(20, 'customer3'),
(10, 'customer4'),
(0, 'customer5'),
(0, 'customer6'),
(10, 'customer7'),
(3, 'customer8'),
(7, 'customer9'),
(9, 'customer10'),
(1, 'customer11'),
(0, 'customer12'),
(0, 'customer13')
GO

--======= VoucherLists
INSERT INTO VoucherLists(VoucherID, Username) VALUES
(1, 'customer1'),
(2, 'customer1'),
(3, 'customer1'),
(4, 'customer1'),
(1, 'customer2'),
(3, 'customer2'),
(4, 'customer4'),
(5, 'customer5')
GO

--======= Brands
INSERT INTO Brands(Name) VALUES
--======= A
('Avène'),
('Atelier Cologne'),
('Asics'),
('Artistic&Co'),
('Artist Couture'),
('Armitron'),
('Armaf'),
('Aristino'),
('Ariana Grande'),
('Aokang'),
('Anta'),
('Anne Klein'),
('Anna Sui'),
('Anello'),
('AndZ'),
('Andrew Marc'),
('Anastasia Beverly Hills'),
('Amouage'),
('AmorePacific'),
('Amiri'),
('Amika'),
('American Eagle'),
('Am Young'),
('Alterna'),
('Algenist'),
('Alexander Mcqueen'),
('Aldo'),
('Albion'),
('Alaia Paris'),
('Al Haramain'),
('Afnan Perfumes'),
('Adwoa Beauty'),
('Adidas'), --==33
('Acqua Di Parma'),
('Acmé De La Vie'),
('Abercrombie & Fitch'),
('A-Derma'),
('Aroma'),
--======= B 39 ==>
('Byredo'),
('By Far'),
('Bvlgari'),
('Burt s Bees'),
('Burgi'),
('Burberry'),
('Bumble And Bumble'),
('Bulova'),
('Bullko'),
('Brut'),
('Britney Spears'),
('Briogeo'),
('Bread beauty'),
('Braun'),
('Bottega Veneta'),
('Bonpoint'),
('Bond No.9'),
('Boldr'),
('Bobbi Brown'),
('Blue Lizard'),
('Bite Beauty'),
('Biologique Recherche'),
('Bioderma'),
('Better Not Younger'),
('Bershka'),
('Bentley'),
('Benkii'),
('Benefit Cosmetics'),
('Belulu'),
('Beentrill'),
('Beautyblender'),
('BeautyBio'),
('Be Classy'),
('Bape'),
('Balmain'),
('Bally'),
('Balenciaga'),
--======= C 76==>
('Cure'),
('Crocs'),
('Cristiano Ronaldo'),
('Creed'),
('Cosrx'),
('Corthe'),
('Corèle V'),
('Converse'),
('Comly'),
('Comfort Zone'),
('Cole Haan'),
('Coach'),
('Clive Christian'),
('Clinique'),
('Clean Reserve'),
('Clé De Peau'),
('Clarins'),
('Clae'),
('Civasan'),
('Citizen'),
('Cinema Secrets'),
('Christina'),
('Christian Louboutin'),
('Chopard'),
('Chloé'),
('Chaumet'),
('Charriol'),
('Charlotte Tilbury'),
('Charles & Keith'),
('Chanel'),
('Champion'),
('Cellcosmet & Cellmen'),
('Celine'),
('Caudalie'),
('Casmara'),
('Casio'),
('Cartier'),
('Carolina Herrera'),
('Calvin Klein'),
('Callaway'),

--======= D 116=>
('Dyson'),
('Dunhill'),
('Ducray'),
('DSquared2'),
('Drunk Elephant'),
('Dr. Barbara Sturm'),
('Dr Dennis Gross'),
('dpHUE'),
('Dooney & Bourke'),
('Domba'),
('Dolce & Gabbana'), --== 126
('Dockers'),
('DKNY'),
('Dita'),
('Diptyque'),
('Dior'),
('Diesel'),
('Dickies'),
('DHC'),
('DevaCurl'),
('Dermeden'),
('Dermaquest'),
('Dermalogica'),
('Dermaceutic'),
('Dermaceleb'),
('Decorté'),
('DBH'),
('Davidoff'),
('Darphin'),
('Daniel Wellington'),
('Danessa Myricks Beauty'),

--=== E 147 =>
('Express'),
('Exideal'),
('Eve Lom'),
('Eucerin'),
('Etat Libre dOrange'),
('Estée Lauder'),
('Ermenegildo Zegna'),
('Enzo Ferrari'),
('Endocare'),
('Emporio Armani'),
('Emile Chouriet'),
('EltaMD'),
('Elly'),
('Ellis Brooklyn'),
('Elizabeth Taylor'),
('Elizabeth Arden'),
('Elisabetta Franchi'),
('Elie Saab'),
('Edox'),
('Edition'),

--=== F 167=>
('Fusion Meso'),
('Furla'),
('Fresh'),
('Frederique Constant'),
('Frederic Malle'),
('Fred Perry'),
('Freck Beauty'),
('Franck Muller'),
('Franck Boclet'),
('Fossil'),
('Forvr Mood'),
('Foreo'),
('Floral Street'),
('Find Kapoor'),
('Fila'),
('Ferre Milano'),
('Fenty Beauty'),
('Fenty Beauty'),
('Fear Of God - FOG'),
('FaceGym'),

--=== G 187=> 
('Guess'),
('Guerlain'),
('Gucci'),
('Gruppo Gamma'),
('Grès'),
('Goyard'),
('Gosto'),
('GoodnDoc'),
('Glow Recipe'),
('GLO Science'),
('GlamGlow'),
('Givenchy'),
('Giovanni'),
('Giorgio Armani'),
('GiGi'),
('Gianni Conti'),
('Ghala Zayed'),
('Gevril'),
('Geox'),
('Gentle Monster'),
('Genie'),

--===== H 208=>
('Hydrogen'),
('Huxley'),
('Hugo Boss'),
('Huda Beauty'),
('Hublot'),
('Hourglass'),
('HoMedics'),
('Hollister'),
('Hitachi'),
('Histolab'),
('Hermès'),
('Herbivore Botanicals'),
('HeraDG'),
('Henry London'),
('Heliocare'),
('HBB'),
('Hanboro'),

--==== I 225 =>
('It Cosmetics'),
('Issey Miyake'),
('Isle Of Paradise'),
('iS Clinical'),
('Invicta'),
('InstaNatural'),
('Initio Parfums Prives'),
('Inglot'),
('Image'),
('ILIA'),

--==== J 235=>
('Jw Pei'),
('Juvera Cosmetic'),
('Just Cavalli'),
('Julius'),
('Juliette Has A Gun'),
('Juicy Couture'),
('Josie Maran'),
('John Varvatos'),
('Jo Malone'),
('Jimmy Choo'),
('Jemmia Diamond'),
('Jean Paul Gaultier'),
('Jaguar'),
('Jacquemus'),
('Jack Black'),

--=====K 250 =>
('KVD Beauty'),
('Knot'),
('Kilian'),
('Kiehl s'),
('Kérastase'),
('Kenzo'),
('Kenneth Cole'),
('Kate Spade'),
('Kate Somerville'),
('Karl Lagerfeld'),
('Kalos'),
('Kakkuda'),
('Kaja'),

--==== L 263=>
('Lyn'),
('Lucien Piccard'),
('Louis Vuitton'),
('LACOSTE'),
('Laura Mercier'),
('L Oreal'),
('Lanvin'),
('Longchamp'),
('Lolita Lempicka'),
('LifeWork'),
('Li-Ning'),
('Levi s'),
('La Prairie'),
('Lộc Phúc Fine Jewelry'),
('Le Labo'),

--==== M 278=>
('Murad'),
('MTG'),
('Movado'),
('Moschino'),
('M.A.C'),
('Maison Martin Margiela'),
('Mango'),
('Maurice Lacroix'),
('Masque'),
('Meez'),
('MLB'),
('Molton Brown'),
('Montale'),
('Melvita'),
('Meishoku'),

--==== N 293 =>
('Nails Inc'),
('NuFace'),
('Nike'),
('New Era'),
('New Balance'),
('Nest New York'),
('NBS'),
('Neil Barrett'),
('Nasomatto'),
('Nina Ricci'),
('Nars'),
('Neoretin'),
('Nautica'),
('Nishane'),
('Native'),

--=== O 308 =>
('Off-White'),
('Obagi Medical'),
('Oakley'),
('Ogival'),
('Ohui'),
('OleHenriksen'),
('Omate'),
('Omega'),
('Orient'),
('Origins'),
('Oriza L. Legrand'),
('Ormonde Jayne'),
('Ouai'),

--===== P 321 =>
('Pureology'),
('Puma'), --==322
('Pull&Bear'),
('PrettyFit'),
('Prada'),
('PNJ'),
('Pixi'),
('Piaget'),
('Phillip Lim'),
('Philipp Plein'),
('Pedro'),
('Paco Rabanne'),
('Palm Angels'),
('Pandora'),
('Parker'),
('Peace Out'),

--==== Q 337=>
('QMS Medicosmetics'),
('Q&Q'),



--==== R 339 =>
('Rolex'),
('Royal Crown'),
('Rossano Ferretti'),
('Rosie Jane'),
('RoC'),
('Roja Parfums'),
('Robert Wayne'),
('RMS Beauty'),
('Reebok'),
('Repetto Paris'),
('Ralph Lauren'),
('Rado'),
('Railtek'),
('Raymond Weil'),
('Rimowa'),

--==== S 354 =>
('Saint laurent'),
('SVR'),
('Swarovski'),
('SY Medipharm'),
('Supergoop'),
('Supperdry'),
('Stuart Weitzman'),
('SkinClinic'),
('Skinbetter Science'),
('SK-II'),
('Shiseido'),
('SevenFriday'),
('Seiko'),
('Samsung'),
('Salvatore Ferragamo'),

--==== T 369=>
('Trussardi'),
('TruSkin Naturals'),
('Triumph'),
('Transino'),
('Tory Burch'),
('Topman'),
('Too Faced'),
('Tommy Hilfiger'),
('Tom Ford'),
('Tissot'),
('Timberland'),
('Tiffany & Co'),
('The Perfect Derma Peel'),
('The North Face'),
('The Body Shop'),

--==== U 384 =>
('Urban Decay'),
('Uniqlo'),
('Under Armour'),

--=== V 387 =>
('Voluspa'),
('Virtue'),
('Vionic'),
('Violet Voss'),
('Viniciobelt'),
('Viktor & Rolf'),
('Victorinox'),
('Vichy'),
('Versace'),
('Venuco'),
('Vascara'),
('Vans'),
('Valentino'),
('Victoria s Secret'),
('Van Cleef & Arpels'),

--==== W 402 =>
('WULFUL'),
('Wilson'),
('Whoo'),
('WHOAU'),
('Westman Atelier'),
('Wellmaxx'),


--=== X 408 =>
('Xerjoff'),
('X-Ray'),

--==== Y 410=>
('YSL'),
('Yaman'),

--==== 412 =>
('Zo Skin Health'),
('Ziozia'),
('Zenith'),
('Zelos'),
('Zara'),
('ZamST'),
('Zadig & Voltaire')
GO


--======= Categories
INSERT INTO Categories(Name) VALUES
(N'ĐỒNG HỒ'),
(N'TÚI XÁCH'),
(N'NƯỚC HOA'),
(N'MỸ PHẨM'),
(N'GIÀY'),
(N'THỜI TRANG'),
(N'MŨ NÓN'),
(N'KÍNH MĂT'),
(N'SON MÔI'),
(N'TRANG SỨC')
--======

GO
--======= SubCategories
INSERT INTO SubCategories (Name,CategoryId) VALUES

--== Đồng hồ (4)
(N'Đồng hồ cơ',1),
(N'Đồng hồ điện tử',1),
(N'Đồng hồ đôi',1),
(N'Đồng hồ trẻ em',1),

--== Túi xách(18)
(N'Túi đeo chéo',2),
(N'Túi xách tay',2),
(N'Balo',2),
(N'Túi cầm tay',2),
(N'Túi tote',2),
(N'Cặp xách tay',2),
(N'Túi laptop',2),
(N'Ví',2),
(N'Túi trống',2),
(N'Vali',2),
(N'Túi đeo vai',2),
(N'Túi đeo hông',2),
(N'Túi đeo Golf',2),
(N'Túi đeo cổ',2),
(N'Ví cáng dài',2),
(N'Túi du lịch',2),
(N'Túi đựng đồ',2),
(N'Túi tennnis',2),

--== Nước hoa(2)
(N'Nước hoa xe hơi',3),
(N'Xịt dưỡng thể',3),


--== Mỹ phẩm (29)
(N'Kem dưỡng da',4),
(N'Tinh chất/Serium',4),
(N'Kem/Sữa dưỡng thể',4),
(N'Kem/Gel trị mụn',4),
(N'Nước/Dầu tẩy trang',4),
(N'Kem/Sữa dưỡng ẩm',4),
(N'Nước hoa hồng/Toner',4),
(N'Phấn phủ',4),
(N'Dầu gội/Dầu xả',4),
(N'Nước dưỡng/Lotion',4),
(N'Tẩy tế bào chết',4),
(N'Mascara',4),
(N'Dưỡng mắt',4),
(N'Nước thần',4),
(N'Sữa tắm',4),
(N'Set trắng da trị nám',4),
(N'Dưỡng trắng',4),
(N'Xịt khoáng',4),
(N'Dầu dưỡng',4),
(N'Kem tan mỡ',4),
(N'Gel dưỡng',4),
(N'Phấn phủ',4),
(N'Xịt trang điểm',4),
(N'Kem dưỡng da tay',4),
(N'Kem dưỡng da chân',4),
(N'Kem dưỡng môi',4),
(N'Tẩy tế bào chết môi',4),
(N'Dưỡng móng tay',4),
(N'Dưỡng mi',4),

--== Giày (15)
(N'Giày thể thao',5),
(N'Giày cao gót',5),
(N'Giày cổ thấp',5),
(N'Giày cổ cao',5),
(N'Giày lười',5),
(N'Giày tây',5),
(N'Giày da',5),
(N'Giày bóng đá',5),
(N'Giày vải',5),
(N'Giày cao su',5),
(N'Ủng',5),
(N'Guốc',5),
(N'Xăng đan và dép',5),
(N'Giày đế bằng',5),
(N'Sneakers',5),

--== tt-at-p1-1
--== Thời trang ( 69- >)
(N'Áo thun',6),
(N'Áo sơ mi',6),
(N'Áo dài tay',6),
(N'Áo phông',6),
(N'Áo khoác',6),
(N'Áo hoddie',6),
(N'Quần tây',6),
(N'Quần kaki',6),
(N'Quần đùi',6),
(N'Quần jean',6),
(N'Đồ thể thao',6),
(N'Váy',6),

--== tt-mn-p1-1
--==Mũ,Nón 81 =>
(N'Mũ len',7),
(N'Mũ lưỡi trai',7),
(N'Mũ tròn',7),
(N'Mũ golf',7),
(N'Mũ/Nón bucket',7),

--== Kính mắt 86=>
(N'Kính mắt cận',8),
(N'Kính chống nắng',8),
(N'Kính cận thời trang',8),


--== Son môi 89 =>
(N'Son lì',9),
(N'Son kem',9),
(N'Son dưỡng môi',9),
(N'Son nước',9),
(N'Son bóng',9),
(N'Son nhũ',9),


--== Trang sức 95
(N'Dây chuyền',10),
(N'Khuyên tai',10),
(N'Vòng đeo tay',10),
(N'Cài áo',10),
(N'Nhẫn',10),
(N'Nhẫn cưới',10),
(N'Nhẫn đính hôn',10),
(N'Nhẫn cầu hôn',10),
(N'Nhẫn kim cương',10),
(N'Hạt vòng charm',10)

GO

--======= Products
INSERT INTO Products ( Name, Price, Material, MadeIn, CreateDate, Gender, Detail, BrandId, CategoryId, SubCategoryId) VALUES
--== Đồng hồ
( 'Đồng Hồ Đôi Citizen AU1062-56E - GA1052-55E', 8450000, N'Mạ Vàng', N'Nhật Bản','20220501 10:00:09 AM', 2, 'Đồng Hồ Đôi Citizen AU1062-56E - GA1052-55E có thiết kế sang trọng với 2 tông màu quyền lực: đen - vàng. Mặt số trơn với nền mặt đen, nổi bật là 2 kim mảnh, dây đồng hồ Citizen mạ vàng.', 94, 1, 3),


--== Thời trang

( N'Áo Thun Nam Adidas Graphic Trefoil Series Màu Trắng Đục', 40 , N'Cotton', N'Đức','20220501 10:00:09 AM', 1, N'Áo Thun Nam Adidas Graphic Trefoil Series Màu Trắng Đục là một chiếc áo đến từ thương hiệu Adidas nổi tiếng của Đức.', 33, 6, 69),
( N'Áo Thun Dolce & Gabbana Short Sleeve T-Shirt Màu Xám Đen', 250 , N'Cotton', N'Ý','20220501 10:00:09 AM', 1, N'Áo Thun Dolce & Gabbana Short Sleeve T-Shirt Màu Xám Đen được làm từ chất vải cotton, polyester thoáng mát, thấm hút mồ hôi tốt mang lại cảm giác thoải mái cho người mặc.', 126, 6, 69),
( N'Áo Thun Puma T-Shirt SS Photoprint Màu Đen', 40 , N'Cotton', N'Đức','20220501 10:00:09 AM', 1, N'Áo Thun Puma T-Shirt SS Photoprint Màu Đen là chiếc áo dành cho nam đến từ thương hiệu Puma nổi tiếng', 322, 6, 69),
( N'Áo Thun Moschino Women Toy Teddy T-shirt', 110 , N'Cotton', N'Ý','20220501 10:00:09 AM', 0, N'Áo Thun Moschino Women Toy Teddy T-shirt Màu Trắng là mẫu áo thun cao cấp được thiết kế với chất liệu 100% cotton mềm mịn , tạo cảm giác thư giãn, thoải mái cho người mặc.', 281, 6, 69),
( N'Áo Thun Nam Adidas Tết Manchester United Màu Đen', 45 , N'Cotton', N'Đức','20220501 10:00:09 AM', 2, N'Áo Thun Nam Adidas Tết Manchester United Màu Đen Size M là một chiếc áo đến từ thương hiệu Adidas nổi tiếng của Đức.', 33, 6, 69),

( N'Áo Sơ Mi Burberry Check Cotton Poplin Shirt', 40 , N'Cotton', N'Anh','20220501 10:00:09 AM', 1, N'Áo Sơ Mi Burberry Check Cotton Poplin Shirt Sọc Nâu được thiết kế cổ bẻ vuông vắn, tay dài, họa tiết sọc vàng tạo nên sự năng động.', 44, 6, 70),
( N'Áo Sơ Mi MLB Classic Monogram Denim Short Sleeves Shirt New York Yankees 3ADRMN123-50BLL Xanh Nhạt', 125 , N'Cotton', N'Hàn Quốc','20220501 10:00:09 AM', 1, N'Áo Sơ Mi MLB Classic Monogram Denim Short Sleeves Shirt New York Yankees 3ADRMN123-50BLL Xanh Nhạt được may bởi chất liệu vải cotton cao cấp, mềm mại và thoáng mát tạo cho bạn cảm giác dễ chịu và thoải mái khi mặc.', 288, 6, 70),
( N'Áo Sơ Mi Dài Tay Giovanni MS140-BU', 55 , N'Cotton', N'Đức','20220501 10:00:09 AM', 1, N'Chiếc áo của Giovanni có đường chỉ may tỉ mỉ, tinh tế. Sản phẩm được thiết kế dáng dễ mặc, phù hợp với đa số dáng người.s.', 199, 6, 70),
( N'Áo Sơ Mi Dolce & Gabbana Crown Print Fitted Shirt Black', 500 , N'Cotton', N'Ý','20220501 10:00:09 AM', 1, N'Áo Sơ Mi Dolce & Gabbana Crown Print Fitted Shirt Black được làm từ chất vải cotton thoáng mát, thấm hút mồ hôi tốt mang lại cảm giác thoải mái cho người mặc.', 126, 6, 70),
( N'Áo Sơ Mi Nữ Levi s Three-Quarter Standard-Regular', 55 , N'Cotton', N'Mỹ','20220501 10:00:09 AM', 0, N'Áo Sơ Mi Levi s Three-Quarter Standard-Regular được may từ chất liệu vải cotton cao cấp với nhiều ưu điểm nổi trội như bề mặt vải mịn, nhẹ, không xù, không co khi giặt.', 274, 6, 70),

( N'Áo Nỉ Sweatshirt MLB Tay Dài Cổ Tròn Simple Logo', 55 , N'Cotton', N'Hàn Quốc','20220501 10:00:09 AM', 2, N'Áo Nỉ Sweatshirt MLB mang màu sắc đơn giản dễ dàng kết hợp với các trang phục khác nhau theo sở thích của bản thân.', 288, 6, 71),
( N'Áo Thun Dài Tay Dsquared2 Printed Surf Fit', 220 , N'Cotton', N'Ý','20220501 10:00:09 AM', 1, N'Áo Dsquared2 Printed Surf Fit T-Shirt White được làm từ chất liệu vải cao cấp, form áo được may với các đường nét vô cùng tỉ mỉ và chắc chắn. ', 119, 6, 71),
( N'Áo Dài Tay Adidas Badge Of Sport FR6613', 45 , N'Cotton', N'Mỹ','20220501 10:00:09 AM', 1, N'Áo Dài Tay Adidas Badge Of Sport FR6613 là một chiếc áo dài tay đến từ thương hiệu Adidas nổi tiếng của Đức.', 33, 6, 71),
( N'Áo Thun Dài Tay Levi Women Long Sleeve Perfect Tee 87452-0012', 50 , N'Cotton', N'Mỹ','20220501 10:00:09 AM', 0, N'Áo Levi Women Long Sleeve Perfect Tee 87452-0012 Màu Trắng được may từ chất liệu vải Cotton cao cấp với nhiều ưu điểm nổi trội như bề mặt vải mịn, nhẹ, không xù, không co khi giặt, thoáng khí.', 274, 6, 71),
( N'Áo Dài Tay Nữ Giovanni DS006-PK', 500 , N'Cotton', N'Mỹ','20220501 10:00:09 AM', 0, N'Áo Dài Tay Nữ Giovanni DS006-PK được may từ chất liệu vải cao cấp với nhiều ưu điểm nổi trội như bề mặt vải mịn, nhẹ, không xù, không co khi giặt.', 199, 6, 71),

( N'Áo Phông MLB Logo LA Dodgers 3ATSM8023-07WHS', 55 , N'Cotton', N'Hàn Quốc','20220501 10:00:09 AM', 0, N'Áo Phông MLB Logo LA Dodgers 3ATSM8023-07WHS Màu Trắng là chiếc áo thời trang đến từ thương hiệu MLB nổi tiếng của Mỹ.', 288, 6, 72),
( N'Áo Phông Nam Giovanni UT229-RE', 50 , N'Cotton', N'Mỹ','20220501 10:00:09 AM', 1, N'Áo Phông Nam Giovanni UT229-RE được làm từ chất liệu vải cao cấp với nhiều ưu điểm như độ đàn hồi tốt, thoáng khí, thấm hút mồ hôi tốt.', 199, 6, 72),
( N'Áo Phông Polo Adidas Tennis Top Solid Heat.Rdy ', 45 , N'Cotton', N'Đức','20220501 10:00:09 AM', 0, N'Áo Phông Polo Adidas Tennis Top Solid Heat.Rdy Màu Đen là một chiếc áo đến từ thương hiệu Adidas nổi tiếng của Đức. Đây là chiếc áo thể thao đang được rất nhiều bạn trẻ yêu thích hiện nay.', 33, 6, 72),
( N'Áo Phông Burberry Logo Print T-Shirt', 550 , N'Cotton', N'Anh','20220501 10:00:09 AM', 1, N'Áo Phông Burberry Logo Print T-Shirt Màu Trắng được thiết kế cổ tròn, tay ngắn tạo nên sự năng động, trẻ trung cho người mặc. Với chất liệu 100% cotton, áo có mềm, mịn, thông thoáng tạo cảm giác thoải mái cho người mặc.', 44, 6, 72),
( N'Áo Phông Moschino Roman Teddy Bear', 145 , N'Cotton', N'Ý','20220501 10:00:09 AM', 0, N'Áo Phông Moschino Roman Teddy Bear T-Shirt với hình chú gấu Teddy rất đáng yêu đang là Hot trend. Chất liệu 100% Cotton luôn thoáng mát và tạo cảm giác vô cùng thoải mái khi mặc.', 281, 6, 72),


( N'Áo Khoác Puma Essentials+ Padded Men Jacket', 110 , N'Vải cao cấp', N'Ý','20220501 10:00:09 AM', 0, N'Áo Khoác Puma Essentials+ Padded Men Jacket được làm từ chất liệu vải cao cấp, mang lại sự thoải mái cho người mặc. Form áo chuẩn đẹp từng đường kim mũi chỉ đảm bảo hài lòng ngay cả với khách hàng khó tính nhất.', 322, 6, 73),
( N'Áo Khoác Versace Bomber Jacket Màu Đen Họa Tiết Vàng', 145 , N'Vải cao cấp', N'Ý','20220501 10:00:09 AM', 1, N'Áo Khoác Versace Bomber Jacket Màu Đen Họa Tiết Vàng là chiếc áo thời trang đến từ thương hiệu Versace nổi tiếng của Ý. Áo sở hữu gam màu thanh lịch, cùng chất vải cao cấp mang lại sự thoải mái cho người mặc.', 395, 6, 73),
( N'Áo Khoác Adidas Essential Double Knit', 50 , N'Vải cao cấp', N'Đức','20220501 10:00:09 AM', 1, N'Áo Khoác Adidas Essential Double Knit GP8603 Màu Đen là một chiếc áo khoác đến từ thương hiệu Adidas nổi tiếng của Đức. Mang vẻ đẹp trẻ trung nhưng không kém phần lịch lãm, Adidas Essential Double Knit GP8603 đã chiếm được cảm tình của phái mạnh.', 33, 6, 73),
( N'Áo Khoác Nike Mens Basic Polyester Zip Jacket', 42 , N'Vải cao cấp', N'Mỹ','20220501 10:00:09 AM', 0, N'Áo khoác Nike Mens Basic Polyester Zip Jacket được làm từ chất liệu vải cao cấp, mang lại sử thoải mái, thoáng mát cho người mặc. Form áo chuẩn đẹp từng đường kim mũi chỉ đảm bảo hài lòng ngay cả với khách hàng khó tính nhất.', 295, 6, 73),
( N'Áo Khoác Burberry Collarless Bomber Jacket Red Gold', 525 , N'Vải cao cấp', N'Anh','20220501 10:00:09 AM', 2, N'Áo Khoác Burberry Collarless Bomber Jacket Red Gold Màu Đỏ là chiếc áo dành cho nam được thiết kế vô cùng thời trang đến từ thương hiệu Burberry nổi tiếng. Sở hữu gam màu thanh lịch cùng chất liệu cao cấp áo Burberry Collarless Bomber Jacket Red Gold Màu Đỏ mang đến cho người mặc cảm giác thoải mái nhất.', 44, 6, 73),

( N'Áo Hoodie Puma Rebel Men  Full Zip', 65 , N'Vải cao cấp', N'Đức','20220501 10:00:09 AM', 1, N'Áo Hoodie Puma Rebel Men Full Zip là chiếc áo thời trang đến từ thương hiệu Puma nổi tiếng của Đức. Mang vẻ đẹp trẻ trung nhưng không kém phần khoẻ khoắn.', 322, 6, 74),
( N'Áo Hoodie Stretch Angels Semi-Crop Hood Zip-up Off White', 155 , N'Vải cao cấp', N'Hàn Quốc','20220501 10:00:09 AM', 0, N'Áo Hoodie Stretch Angels Semi-Crop Hood Zip-up mang màu sắc đơn giản dễ dàng kết hợp với các trang phục khác nhau theo sở thích của bản thân.', 308, 6, 74),
( N'Áo Nike Sportswear Men Tie-Dye Pullover Hoodie', 85 , N'Vải cao cấp', N'Mỹ','20220501 10:00:09 AM', 1, N'Áo Nike Sportswear Men Tie-Dye mang màu sắc đơn giản dễ dàng kết hợp với các trang phục khác nhau theo sở thích của bản thân. Đây là một mẫu áo thời trang sang trọng dành cho những chàng trai yêu thích sự đơn giản nhưng không kém phần nổi bật và cuốn hút.', 295, 6, 74),
( N'Áo Khoác Burberry Hoodie With All-Over Tb Band', 455 , N'Vải cao cấp', N'Anh','20220501 10:00:09 AM', 1, N'Áo Khoác Burberry Hoodie With All-Over Tb Band có màu sắc đơn giản dễ kết hợp với các trang phục khác nhau theo sở thích của bản thân. Đây là một mẫu áo thời trang sang trọng dành cho những người thích sự đơn giản nhưng không kém phần nổi bật và cuốn hút.', 44, 6, 74),
( N'Áo Khoác Nữ Adidas Big Badge Of Sport Full-Zip Hoodie', 45 , N'Vải cao cấp', N'Đức','20220501 10:00:09 AM', 1, N'Áo Khoác Nữ Adidas Big Badge Of Sport Full-Zip Hoodie GC6976 Màu Xám là một chiếc áo khoác đến từ thương hiệu Adidas nổi tiếng của Đức. Mang vẻ đẹp trẻ trung nhưng không kém phần lịch lãm.', 33, 6, 74),

( N'Quần Tây Nam Dolce & Gabbana Wool Tailored Trousers', 65 , N'Wool 85%, Silk 15%', N'Đức','20220501 10:00:09 AM', 1, N'Quần Tây Nam Dolce & Gabbana Wool Tailored Trousers Màu Đen được thiết kế đẹp mắt với gam màu thanh lịch.', 126, 6, 75),
( N'Quần Tây Dsquared2 Tailored Wool Trousers', 450 , N'95% Lana Vergine, 5% Elastane', N'Ý','20220501 10:00:09 AM', 1, N'Quần Tây Dsquared2 Tailored Wool Trousers Màu Đen được làm từ chất vải 95% Lana Vergine, 5% Elastane thoáng mát, thấm hút mồ hôi tốt mang lại cảm giác thoải mái cho người mặc.', 119, 6, 75),
( N'Quần Tây Nam Calvin Klein Modern Stretch Chino Wrinkle Resistant Pants', 165 , N'58% Cotton, 22% Polyester, 16% Viscose, 4% Elastane', N'Mỹ','20220501 10:00:09 AM', 1, N'Quần Tây Nam Calvin Klein Modern Stretch Chino Wrinkle Resistant Pants Màu Đen được làm từ chất vải 58% Cotton, 22% Polyester, 16% Viscose, 4% Elastane thoáng mát, thấm hút mồ hôi tốt mang lại cảm giác thoải mái cho người mặc.', 114, 6, 75),
( N'Quần Tây Nam Calvin Klein Move 365 Slim Fit Tech Modern Stretch Chino Wrinkle Resistant Pants ', 165 , N'51% Cotton, 46% Polyester, 3% Elastane', N'Mỹ','20220501 10:00:09 AM', 1, N'Quần Tây Nam Calvin Klein Move 365 Slim Fit Tech Modern Stretch Chino Wrinkle Resistant Pants Màu Xám được làm từ chất vải 51% Cotton, 46% Polyester, 3% Elastane thoáng mát, thấm hút mồ hôi tốt mang lại cảm giác thoải mái cho người mặc.', 114, 6, 75),
( N'Quần Tây Nam Aristino ATR00308', 85 , N'Polyester', N'Việt Nam','20220501 10:00:09 AM', 1, N'Quần Tây Nam Aristino ATR00308 Màu Xanh Tím Than được làm từ chất vải Polyester thấm hút mồ hôi tốt mang lại cảm giác thoải mái cho người mặc.', 8, 6, 75),

( N'Quần Dài Nam Kaki Lacoste Men Lacoste Men Slim Fit 5-Pocket Stretch Cotton Pants', 85 , N'98% Cotton, 2% Elastane', N'Mỹ','20220501 10:00:09 AM', 1, N'Quần Dài Nam Kaki Lacoste Men Lacoste Men Slim Fit 5-Pocket Stretch Cotton Pants HH9561 02S Màu Nâu có thiết kế đẹp mắt, thời trang đến từ thương hiệu Lacoste nổi tiếng. Với chiếc quần này bạn có thể kết hợp với nhiều trang phục khác nhau.', 266, 6, 76),
( N'Quần Kaki Lacoste Men Regular Fit HH4602 00 VDW', 135 , N'Cotton', N'Mỹ','20220501 10:00:09 AM', 1, N'Quần Kaki Lacoste Men Regular Fit là mẫu quần khaki thời trang thiết kế kiểu dáng trẻ trung, hiện đại. Quần Lacoste được làm từ chất vải cotton thoáng mát mang lại cảm giác thoải mái nhất cho người mặc.', 266, 6, 76),
( N'Quần Kaki Lacoste Men Stretch Cotton Gabardine Slim Fit Short Chinos HH5941', 115 , N'Cotton', N'Mỹ','20220501 10:00:09 AM', 1, N'Quần Kaki Lacoste Men Stretch Cotton Gabardine Slim Fit Short Chinos HH5941 được làm từ chất liệu vải cao cấp, mang lại sử thoải mái, thoáng mát cho người mặc. Form quần chuẩn đẹp từng đường kim mũi chỉ đảm bảo hài lòng ngay cả với khách hàng khó tính nhất..', 266, 6, 76),

( N'Quần Shorts Lacoste Đùi Vân Diamond Màu Trắng', 85 , N'98% Cotton, 2% Elastane', N'Mỹ','20220501 10:00:09 AM', 1, N'Quần Shorts Lacoste Đùi Vân Diamond Màu Trắng được sản xuất bằng công nghệ hiện đại, chất liệu vải cao cấp với khả năng thấm hút mồ hôi tốt, tạo cảm giác thoải mái cho người mặc trong mọi hoạt động thường ngày.', 266, 6, 77),
( N'Quần Shorts Lacoste Đùi Vân Diamond Màu Đen', 85 , N'98% Cotton, 2% Elastane', N'Mỹ','20220501 10:00:09 AM', 1, N'Quần Shorts Lacoste Đùi Vân Diamond Màu Đen được sản xuất bằng công nghệ hiện đại, chất liệu vải cao cấp với khả năng thấm hút mồ hôi tốt, tạo cảm giác thoải mái cho người mặc trong mọi hoạt động thường ngày.', 266, 6, 77),

( N'Quần Jeans Levi Nam Dài Slim Tapered Jean 512 28833-0884', 185 , N'62% Cotton/36% Polyester/2% Elastane', N'Mỹ','20220501 10:00:09 AM', 1, N'Quần Jeans Levi Nam Dài Slim Tapered Jean 512 28833-0884 là chiếc quần jeans dành cho nam đến từ thương hiệu Levi của Mỹ. Quần dáng slim fit, có thiết kế hiện đại mang lại sự trẻ trung, năng động.', 274, 6, 78),
( N'Quần Jeans Levi Nam Dài Slim-Fit Jean 511 04511-5008-31R', 110 , N'62% Cotton/36% Polyester/2% Elastane', N'Mỹ','20220501 10:00:09 AM', 1, N'Quần Jeans Levi Nam Dài Slim-Fit Jean 511 04511-5008-31R được làm từ chất vải 62% Cotton/36% Polyester/2% Elastane thoáng mát mang lại cảm giác thoải mái nhất cho người mặc. Form quần chuẩn đẹp từng đường kim mũi chỉ đảm bảo hài lòng ngay cả với khách hàng khó tính nhất.', 274, 6, 78),
( N'Quần Bò Dsquared2 Skater Jean Jeans Màu Xanh Navy Size 46', 110 , N'Vải bò', N'Ý','20220501 10:00:09 AM', 1, N'Quần Bò Dsquared2 Skater Jean Jeans Màu Xanh Navy được làm từ chất liệu vải vải bò cao cấp, thầm hút mồ hôi tốt. Form quần được may với các đường nét vô cùng tỉ mỉ và chắc chắn.', 119, 6, 78),
( N'Quần Jeans Levi Nam Dài Straight Jean 505 00505-2192-32S', 115 , N'62% Cotton/36% Polyester/2% Elastane', N'Mỹ','20220501 10:00:09 AM', 1, N'Quần Jeans Levi Nam Dài Straight Jean 505 00505-2192-32S được làm từ chất vải 62% Cotton/36% Polyester/2% Elastane thoáng mát mang lại cảm giác thoải mái nhất cho người mặc. Form quần chuẩn đẹp từng đường kim mũi chỉ.', 274, 6, 78),
( N'Quần Jean Calvin Klein CKJ 027 Men Body Jean SS22-8266', 225, N'Cotton', N'Mỹ','20220501 10:00:09 AM', 1, N'Quần Jean Calvin Klein CKJ 027 Men Body Jean SS22-8266 Màu Xanh Tối với kiểu dáng thời trang - phong cách hiện đại, trẻ trung, năng động. Với thiết kế đẹp mắt cùng chất liệu cao cấp, ngay từ khi có mặt trên thị trường, Calvin Klein CKJ 027 Men Body Jean được nhiều tín đồ thời trang săn đón..', 114, 6, 78),

( N'Bộ Thể Thao Hè Polo Lacoste Roland Garros Breathable Piqué', 195 , N'Cotton, polyester, elastane', N'Mỹ','20220501 10:00:09 AM', 1, N'Bộ Thể Thao Hè Polo Lacoste Roland Garros Breathable Piqué được làm từ chất vải cotton kết hợp polyester rất mềm mịn, không nhăn xù và không phai màu. thoáng mát mang lại cảm giác thoải mái nhất cho người mặc.', 266, 6, 79),
( N'Bộ Thể Thao Puma Jersey Set CB Retro 588964-06', 75 , N'100% Polyester', N'Đức','20220501 10:00:09 AM', 1, N'Bộ Thể Thao Puma Jersey Set CB Retro 588964-06 Màu Xanh Navy Phối Viền Trắng Đỏ là một bộ thể thao đến từ thương hiệu Puma nổi tiếng của Đức. Mang vẻ đẹp trẻ trung nhưng không kém phần khoẻ khoắn, Puma Jersey Set CB Retro 588964-06  đã chiếm được cảm tình của phái mạnh.', 322, 6, 79),
( N'Bộ Thể Thao Hè Lacoste Roland Garros X Novak', 195 , N'	Cotton, polyester, elastane', N'Mỹ','20220501 10:00:09 AM', 1, N'Bộ Thể Thao Hè Lacoste Roland Garros X Novak Màu Trắng Đỏ Cho Nam được làm từ chất vải cotton kết hợp polyester thoáng mát mang lại cảm giác thoải mái nhất cho người mặc. Form quần áo được thiết kế chuẩn đẹp, đường kim mũi chỉ đều nét và sắc xảo, đảm bảo hài lòng ngay cả với khách hàng khó tính nhất.', 266, 6, 79),

( N'Váy Polo MLB Monogram Collar One Piece', 125 , N'Cotton', N'Hàn Quốc','20220501 10:00:09 AM', 0, N'Váy Polo MLB Monogram Collar One Piece 3FOPM0223-50CRS Màu Trắng Kem được làm từ chất vải cotton thoáng mát, mềm mịn. Form váy chuẩn đẹp từng đường kim mũi chỉ đảm bảo hài lòng ngay cả với khách hàng khó tính nhất.', 288, 6, 80),
( N'Váy Lacoste Women Sport Logo Tennis Sweatshirt Dress', 195 , N'Cotton', N'Mỹ','20220501 10:00:09 AM', 0, N'Váy Lacoste Women Sport Logo Tennis Sweatshirt Dress Màu Đen là chiếc váy dành cho nữ đến từ thương hiệu Lacoste nổi tiếng. Váy sở hữu gam màu thanh lịch cùng chất vải cao cấp mang lại cảm giác thoải mái cho người mặc.', 266, 6, 80),
( N'Váy Nữ Levi Marisole Floral Print Long Sleeves Dress', 105 , N'Cotton', N'Mỹ','20220501 10:00:09 AM', 0, N'Váy Nữ Levi Marisole Floral Print Long Sleeves Dress A0838-0000 Họa Tiết thiết kế kiểu dáng trẻ trung, hiện đại đến từ thương hiệu Levi nổi tiếng của Mỹ. Váy được làm từ chất liệu cao cấp, bền đẹp trong suốt quá trình sử dụng.', 274, 6, 80),
( N'Chân Váy MLB Xếp Ly New York Yankees', 125 , N'Cotton', N'Mỹ','20220501 10:00:09 AM', 0, N'Chân Váy MLB Xếp Ly New York Yankees 3FSKB0123-50BKS Màu Đen được làm từ chất liệu Polyester mặc thoải mái. Form và đường chỉ may vô cùng tỉ mỉ và chắc chắn hài lòng với cả khách hàng khó tính. ', 288, 6, 80),
( N'Váy Lacoste Women  Boat Neck Striped Interlock Nautical', 135 , N'Cotton', N'Mỹ','20220501 10:00:09 AM', 0, N'Váy Lacoste Women Boat Neck Striped Interlock Nautical Dress Màu Đỏ Cam Kẻ được làm từ chất vải cotton thoáng mát, mềm mịn. Form váy chuẩn đẹp từng đường kim mũi chỉ đảm bảo hài lòng ngay cả với khách hàng khó tính nhất. ', 266, 6, 80),


( N'Son MAC Chili - Son Đỏ Gạch Cực Lạ Nhất Định Phải Có', 25 , N'Son lì', N'Mỹ','20220501 10:00:09 AM', 0, N'Son MAC Chili màu đỏ gạch là dòng matte mang gam đỏ gạch cá tính, đầy sức lôi cuốn. MAC Chili màu đỏ gạch sở hữu chất son siêu lì cực mịn, bám nhẹ nhàng trên môi với công thức bền màu nhiều giờ đồng hồ để bạn thoải mái ăn uống mà không sợ sắc son phai đi.', 282, 9, 89),
( N'Son Lì Gucci Rouge À Lèvres Voile Mat', 105 , N'Son lì', N'Ý','20220501 10:00:09 AM', 0, N'Được mệnh danh là "Nàng Hậu" trong thế giới son môi, Son Gucci 208 They Met in Argentina Matte được thiết kế ấn tượng với tông vàng chủ đạo kết hợp hoa văn đầy tinh tế, sang trọng.', 189, 9, 89),
( N'Son Chanel Rouge Allure Luminous Intense Lip Colour 179', 105 , N'Son lì', N'Pháp','20220501 10:00:09 AM', 0, N'Son Chanel Rouge Allure Luminous Intense Lip Colour 179 Màu Hồng Cam3.5g là cây son đến đến từ thương hiệu Chanel nổi tiếng của Pháp. Chanel Rouge Coco Bloom 134 Sunlight mang đến cho bạn một bờ môi căng mọng, tràn đầy sức sống suốt ngày dài năng động mà đôi môi trở nên mịn màng, tự nhiên.', 105, 9, 89),

( N'Son Kem Dior Rouge Forever Liquid 720 Forever Icone', 45 , N'Son kem', N'Pháp','20220501 10:00:09 AM', 0, N'Dior Rouge Forever Liquid 720 Forever Icone sở hữu cho mình một sắc hồng đất thời thượng đến nóng bỏng. Với sắc độ trẻ trung, cực kỳ quyến rũ.', 131, 9, 90),
( N'Son MAC 984 Billion $ Smile Màu Hồng Neon – Powder Kiss Liquid', 30 , N'Son kem', N'Mỹ','20220501 10:00:09 AM', 0, N'Son MAC 984 Billion $ Smile Màu Hồng Neon – Powder Kiss Liquid là thỏi son kem cao cấp cho chị em tới từ thường hiệu MAC của Mỹ. Sở hữu gam màu hồng xinh xắn cho nàng thêm trẻ trung, rạng rỡ.', 282, 9, 90),
( N'Son Kem Chanel 66 Permanent Màu Hồng Đỏ Đậu', 45 , N'Son kem', N'Pháp','20220501 10:00:09 AM', 0, N'Son Kem Chanel 66 Permanent màu hồng đỏ đậu lên môi xinh xắn, son có chất son mềm mịn như nhung, khả năng lên màu chuẩn mà cho đôi môi cảm giác mỏng nhẹ, tự nhiên. Nhờ thành phần tự nhiên mà khả năng dưỡng ẩm và mềm môi của son Chanel 66 thực sự là niềm tự hào mà Chanel mang tới. ', 105, 9, 90),

( N'Son Dưỡng Dior Addict Lip Glow Màu 004 Coral', 37 , N'Son dưỡng', N'Pháp','20220501 10:00:09 AM', 0, N'Son Dưỡng Dior Addict Lip Glow Màu 004 Coral (Mới Nhất 2021) là cây son cao cấp thân thuộc của dòng Dior Addict Lip Glow. Dior Addict 004 là công thức dưỡng môi khác biệt và tối ưu cho đôi môi bạn trở nên tươi tắn, căng mọng đầy sức sống.', 131, 9, 91),
( N'Son Dưỡng Tom Ford 24k Gold Z09 Soleil Lip Blush 3g', 65 , N'Son dưỡng', N'Mỹ','20220501 10:00:09 AM', 0, N'Son Dưỡng Tom Ford 24k Gold Z09 Soleil Lip Blush 3g Màu Hồng Phấn làm ai yêu son đều phải "đắm đuối" ngay từ cái nhìn đầu tiên. Có thể nói đây chính là thỏi son dưỡng đẹp và đẳng cấp nhất trong làng son high-end hiện nay.', 377, 9, 91),
( N'Son Dưỡng Chanel Rouge Coco Bloom 120 Freshness', 43 , N'Son dưỡng', N'Pháp','20220501 10:00:09 AM', 0, N'Son Chanel Rouge Coco Bloom 120 Freshness màu đỏ hồng trầm là cây son cao cấp của thương hiệu Chanel được yêu thích nhất để diện trong những bữa tiệc cuối năm đầy ấn tượng và mùa lễ hội đầu năm đầy sắc màu', 105, 9, 91),


( N'Son Chanel Rouge Coco Gloss Moisturizing Glossimer 119 Bourgeoisie', 45 , N'Son nước', N'Pháp','20220501 10:00:09 AM', 0, N'Chanel Rouge Coco Gloss Moisturizing Glossimer 119 Bourgeoisie sở hữu sắc son màu mận hồng rất nhẹ nhàng hòa quyện với chất son dưỡng có độ bóng mượt, không những giúp làn môi của bạn trở nên căng mịn, tràn đầy sức sống mà còn giúp diện mạo của bạn trở nên tươi tắn hơn hẳn đấy nhé.', 105, 9, 92),
( N'Son Nước Dưỡng Môi Đông Y Whoo Mi Liquid Lip Rouge - Cung Cấp Ẩm & Giảm Nhăn ', 41 , N'Son dưỡng', N'Hàn Quốc','20220501 10:00:09 AM', 0, N'Son dưỡng môi Whoo Mi Glow Lip Balm SPF10 một sản phẩm dưỡng và kết hợp trang điểm. Với thành phần trong son lành tính phù hợp với nhiều đối tượng lứa tuổi khác nhau.', 404, 9, 92),




( N'Dây Chuyền Cartier Hình Con Báo Vàng Trắng', 3220 , N'Vàng trắng 18k, Đính 100 viên kim cương', N'Pháp','20220501 10:00:09 AM', 0, N'Dây Chuyền Cartier Hình Con Báo Vàng Trắng là phụ kiện trang sức cao cấp đến từ thương hiệu Cartier nổi tiếng của Pháp. Sở hữu tông màu nổi bật, cùng thiết kế tinh tế chiếc dây chuyền Cartier đem đến cho người mang vẻ đẹp sang trọng.', 112, 10, 95),


( N'Khuyên Tai Cartier Panthere Hình Con Báo Vàng Trắng', 2250 , N'Vàng trắng 18k và 76 viên kim cương', N'Pháp','20220501 10:00:09 AM', 0, N'Khuyên Tai Cartier Panthere Hình Con Báo Vàng Trắng là phụ kiện trang sức nữ cao cấp đến từ thương hiệu Cartier nổi tiếng của Pháp. Cartier Panthere Hình Con Báo sở hữu tông màu nổi bật, cùng thiết kế tinh tế đem đến cho người mang vẻ đẹp sang trọng.', 112, 10, 96),
( N'Khuyên Tai Cartier Love 1 Viên Kim Cương', 1250 , N'Vàng hồng 18k và viên kim cương', N'Pháp','20220501 10:00:09 AM', 0, N'Khuyên Tai Cartier Love 1 Viên Kim Cương là phụ kiện trang sức nữ cao cấp đến từ thương hiệu Cartier nổi tiếng của Pháp. Cartier Love 1 Viên Kim Cương sở hữu tông màu nổi bật, cùng thiết kế tinh tế đem đến cho người mang vẻ đẹp sang trọng.', 112, 10, 96),

( N'Vòng Đeo Tay Cartier Love Màu Vàng Hồng', 5250 , N'Vàng hồng 18k & kim cương', N'Pháp','20220501 10:00:09 AM', 0, N'Vòng Đeo Tay Cartier Love Màu Vàng Hồng là phụ kiện trang sức nữ cao cấp đến từ thương hiệu Cartier nổi tiếng của Pháp. Sở hữu thiết kế bắt mắt, cùng tông màu thanh lịch Vòng Đeo Tay Cartier Love cho người đeo thêm sang trọng.', 112, 10, 97),
( N'Vòng Đeo Tay Louis Vuitton M6170E Fasten Your LV Bracelet', 450 , N'Vàng hồng 18k & kim cương', N'Pháp','20220501 10:00:09 AM', 0, N'Vòng Đeo Tay Louis Vuitton M6170E Fasten Your LV Bracelet Màu Nâu Vàng là chiếc vòng đeo tay cao cấp đến từ thương hiệu Louis Vuitton nổi tiếng. Khi vừa mới ra mắt trên thị trường M6170E Fasten Your LV Bracelet được nhiều tín đồ thời trang săn đón.', 265, 10, 97),
( N'Vòng Đeo Tay Hermès Clic H Bracelet Blanc GhW Size PM Màu Vàng Trắng', 525 , N'Men,kim loại,mạ vàng', N'Pháp','20220501 10:00:09 AM', 0, N'Vòng Đeo Tay Hermès Clic H Bracelet Blanc GhW Size PM Màu Vàng Trắng là phụ kiện trang sức nữ cao cấp đến từ thương hiệu Hermès nổi tiếng của Pháp.', 218, 10, 97),


--======= ProductDiscount

--======= ProductPhotos

--======= ProductDetails

--======= ProductLikes

--======= ProductEvaluations

--======= ProductComments

--======= CommentPhotos

--======= ProductReplies

--======= Orders

--======= OrderDetails

--======= Histories

