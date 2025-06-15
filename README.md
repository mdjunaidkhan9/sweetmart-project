
# 🍬 SweetMart Full Stack Project

SweetMart is a full-stack e-commerce web application for a sweet mart. It allows users to register, login, browse products, add to cart, place orders, and generate bills. Admins can manage products and categories.

---

## 🛠️ Technologies Used

### Backend – Spring Boot (Microservices)
- Spring Boot
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- MySQL
- Feign Clients for inter-service communication
- Spring Cloud Gateway & Eureka Server
- AOP Logging & Global Exception Handling
- **All microservices are accessed through Spring Cloud Gateway**
- **Role-based authorization is enforced at the gateway**

### Frontend – React JS (Vite)
- React with Vite (Hooks, Routing)
- Axios
- React Router
- Toastify
- CSS (custom)

---

## 🧠 Microservices Breakdown

### 1. 🔐 Auth-Service
- User registration with OTP email verification
- Login with JWT token generation
- Role-based access (`USER`, `ADMIN`)

### 2. 📦 Product-Service
- Add/view/delete products (admin)
- View products (user)

### 3. 🛒 Cart-Service
- Add to cart by username
- View and delete cart items

### 4. 🧾 Order-Service
- Place orders based on cart
- View user orders
- Admin can view all orders

### 5. 🧾 Bill-Service
- Generate bill (once per order)
- View and delete bills

### 6. 🌐 API Gateway
- Role-based routing
- Token validation
- Acts as the single entry point for all services

### 7. 📡 Eureka Server
- Service discovery for microservices

---

## 🌐 Frontend (React + Vite)

- Login/Register forms
- OTP verification
- Product browsing
- Cart management
- Order & bill section
- Admin dashboard (add/delete products)

---

## ⚙️ How to Run Locally

### 📦 Backend Setup

> Prerequisites: Java 17+, Maven, MySQL, STS or IntelliJ

1. Import each service (e.g., `auth-service`, `product-service`, etc.) into STS or IntelliJ.
2. Configure MySQL in `application.yml` for each:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/sweetmart
       username: root
       password: yourpassword
   ```
3. Start **Eureka Server**, then **Gateway**, then other services.
4. Tables auto-generate via JPA.

### 🌐 Frontend Setup (React + Vite)

> Prerequisites: Node.js, npm

1. Go to the frontend folder:
   ```bash
   cd SWEETMART_FRONTEND
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start Vite dev server:
   ```bash
   npm run dev
   ```
4. Open: [http://localhost:5173](http://localhost:5173)

---

## 📸 Screenshots

> Upload all images to `screenshots/` folder and embed like below:

### 🔐 Registration with OTP
![Register]![Register](https://github.com/user-attachments/assets/fe2f2abd-76fa-482d-9565-15ecaa20dd91)


### 🛍️ Product Listing
![Products]![products](https://github.com/user-attachments/assets/a2c2f69d-b65c-4f18-a865-87cc13f03c55)


### 🛒 Cart View
![Cart]![Cart](https://github.com/user-attachments/assets/78c1eac3-508f-4f86-9426-f24f3f3db202)


### 🧾 Order + Bill
![Bill]![Orders](https://github.com/user-attachments/assets/fb135b77-9f1f-414b-9a6d-fd8419670fd4)

### 🧾 Payments
![Payments]![payment] ![Screenshot 2025-06-15 205955](https://github.com/user-attachments/assets/cc45517c-30c9-42a3-8a7b-129c13e436dd)

### 👨‍💼 Admin Dashboard
![Admin]![Screenshot 2025-06-15 211008](https://github.com/user-attachments/assets/9302a427-a77b-40dc-b3c6-690f0f2fb95b)


---



## 👤 Author

- **Name:** Junaid Khan
- **GitHub:** [@mdjunaidkhan9](https://github.com/mdjunaidkhan9)
- **Project:** Final Capgemini Full-Stack Internship Project

---

## 📢 Feedback

Feel free to fork, open issues, or suggest improvements! 💬
