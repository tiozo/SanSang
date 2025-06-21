CREATE DATABASE DBShopping;
USE DBShopping;

-- Bảng ROLE
CREATE TABLE ROLE (
    RoleID INT IDENTITY(1,1) PRIMARY KEY,
    RoleName VARCHAR(50) NOT NULL UNIQUE
);

-- Bảng ADDRESS
CREATE TABLE ADDRESS (
    AddressID INT IDENTITY(1,1) PRIMARY KEY,
    Street VARCHAR(100) NOT NULL,
    City VARCHAR(50) NOT NULL,
    State VARCHAR(50),
    PostalCode VARCHAR(20),
    Country VARCHAR(50) NOT NULL
);

-- Bảng USERS (Đã cập nhật)
CREATE TABLE USERS (
    UserID NVARCHAR(50) PRIMARY KEY, -- Thay đổi từ INT sang NVARCHAR
    Name NVARCHAR(100) NOT NULL,
    RoleID INT NOT NULL,
    Phone NVARCHAR(15),
    AddressID INT NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    FOREIGN KEY (RoleID) REFERENCES ROLE(RoleID),
    FOREIGN KEY (AddressID) REFERENCES ADDRESS(AddressID)
);

-- Bảng PRODUCT
CREATE TABLE PRODUCT (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    Name VARCHAR(100),
    Description TEXT,
    Price DECIMAL(10,2),
    Quantity INT
);

-- Bảng ORDERED (Cập nhật khóa ngoại)
CREATE TABLE ORDERED (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    UserID NVARCHAR(50) NOT NULL, -- Thay đổi kiểu dữ liệu
    Date DATETIME DEFAULT CURRENT_TIMESTAMP,
    Total DECIMAL(10,2),
    FOREIGN KEY (UserID) REFERENCES USERS(UserID) -- Tham chiếu đến NVARCHAR
);


-- Bảng ORDER_DETAIL
CREATE TABLE ORDER_DETAIL (
    OrderDetailID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT,
    ProductID INT,
    Quantity INT,
    Price DECIMAL(10,2),
    FOREIGN KEY (OrderID) REFERENCES ORDERED(OrderID),
    FOREIGN KEY (ProductID) REFERENCES PRODUCT(ProductID)
);

-- Bảng CHECKOUT (mới)
CREATE TABLE CHECKOUT (
    CheckoutID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT NOT NULL,
    PaymentMethod VARCHAR(50) NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    PaymentStatus VARCHAR(20) NOT NULL,
    TransactionDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (OrderID) REFERENCES ORDERED(OrderID)
);

-- 2.1 ROLE
INSERT INTO ROLE (RoleName) VALUES 
    ('Admin'),
    ('Customer'),
    ('Manager');

-- 2.2 ADDRESS
INSERT INTO ADDRESS (Street, City, State, PostalCode, Country) 
VALUES
    ('123 Main St', 'New York', 'NY', '10001', 'USA'),
    ('456 Oak Ave', 'Los Angeles', 'CA', '90001', 'USA'),
    ('789 Pine Rd', 'London', NULL, 'SW1A 1AA', 'UK');

-- 2.3 USERS (Với UserID là chuỗi)
INSERT INTO USERS (UserID, Name, RoleID, Phone, AddressID, Password) 
VALUES
    ('sa', N'Minh Thuận', 1, '123-456-7890', 1, '1'),
    ('anna_customer', N'Anna Smith', 2, '555-123-4567', 2, 'hashed_pwd_2'),
    ('mike_manager', N'Mike Johnson', 3, '999-888-7777', 3, 'hashed_pwd_3');

-- 2.4 PRODUCT
INSERT INTO PRODUCT (Name, Description, Price, Quantity) 
VALUES
    (N'iPhone 15', N'Điện thoại thông minh mới nhất', 999.00, 50),
    (N'Samsung Galaxy S23', N'Flagship Android', 799.00, 30);

-- 2.5 ORDERED (Cập nhật UserID)
INSERT INTO ORDERED (UserID, Total) 
VALUES
    ('anna_customer', 1798.00),
    ('mike_manager', 299.00);

-- 2.6 ORDER_DETAIL
INSERT INTO ORDER_DETAIL (OrderID, ProductID, Quantity, Price) 
VALUES
    (1, 1, 1, 999.00),
    (1, 2, 1, 799.00),
    (2, 1, 1, 999.00);

-- Chèn dữ liệu CHECKOUT
INSERT INTO CHECKOUT (OrderID, PaymentMethod, Amount, PaymentStatus) 
VALUES
    (1, N'Thẻ tín dụng', 1798.00, N'Hoàn thành'),
    (2, N'Ví điện tử', 299.00, N'Đang chờ');