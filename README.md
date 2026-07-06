# Bài Tập Thực Hành: Xác Thực Bằng JWT (JSON Web Token)

**Sinh viên thực hiện:** Lại Xuân Biển - Nguyễn Thái Hà - Tạ Gia Bảo - Vũ Tùng Dương - 

## 🎥 Video Thuyết Minh
[**https://drive.google.com/file/d/1ZpzhZ6rnurx0UXYUXmLW7282lF2SSufs/view?usp=drive_link**]

---

## 🎯 Mục Tiêu Bài Tập
Xây dựng chức năng xác thực người dùng sử dụng JWT theo quy trình:
1. Client gửi yêu cầu đăng nhập với thông tin `username` và `password`.
2. Server xác thực thông tin. Nếu hợp lệ, Server tạo ra một mã JWT và trả về cho Client.
3. Client sử dụng JWT nhận được (gửi trong HTTP Header `Authorization: Bearer <JWT>`) cho các lần gọi API tiếp theo.
4. Server kiểm tra JWT để quyết định cấp quyền truy cập tài nguyên dựa trên Role (USER hoặc ADMIN). Trả về `authorized` nếu hợp lệ, `unauthorized` nếu không đủ quyền.

## ⚙️ Công Nghệ Sử Dụng
* **Ngôn ngữ:** Java
* **Framework:** Spring Boot, Spring Security
* **Thư viện xử lý Token:** `jjwt` (io.jsonwebtoken)
* **Công cụ test API:** Postman

---

## 📝 Tóm Tắt Các Bước Thực Hiện

1. **Cài đặt Dependency:** Thêm bộ thư viện `jjwt-api`, `jjwt-impl`, `jjwt-jackson` vào `pom.xml` để cung cấp các hàm băm và giải mã chuẩn JSON Web Token.
2. **Khởi tạo Model giao tiếp:** Xây dựng class `AuthRequest` để nhận thông tin đăng nhập và `AuthResponse` để đóng gói trả về chuỗi Token cho Client.
3. **Xây dựng Lõi xử lý JWT (`JwtUtils`):** 
   * Hàm `generateToken`: Đưa thông tin `username` và `role` vào Payload, dùng thuật toán băm `HS256` cùng Secret Key nội bộ để tạo JWT.
   * Hàm `validateToken`: Dùng Secret Key để mở khóa và kiểm tra tính hợp lệ, nguyên vẹn và hạn sử dụng của JWT client gửi lên.
4. **Chặn và Xác thực Request (`JwtFilter`):** Kế thừa `OncePerRequestFilter`. Lấy chuỗi Token từ HTTP Header `Authorization` (có tiền tố `Bearer `). Giải mã Token để lấy quyền và đẩy vào `SecurityContextHolder` nhằm báo cho hệ thống biết request này đã được xác thực hợp lệ.
5. **Cấu hình Phân quyền (`SecurityConfig`):** Tắt tính năng quản lý Session (do JWT hoạt động theo cơ chế Stateless). Cho phép gọi tự do API `/api/auth/login`. Ràng buộc kiểm tra phân quyền Role (`ADMIN`, `USER`) đối với các API chức năng còn lại.
6. **Tạo API Giao tiếp (`Controllers`):**
   * `AuthController`: Xử lý POST request đăng nhập. Nếu thông tin tài khoản đúng, cấp phát Token tương ứng.
   * `TestController`: Cung cấp các API GET `/api/test/user` và `/api/test/admin` để kiểm thử kết quả chặn và phân quyền truy cập.

---

## 🚀 Hướng Dẫn Chạy Và Kiểm Thử (Sử dụng Postman)

1. **Đăng nhập (Lấy Token):**
   * Tạo request `POST` đến `http://localhost:8080/api/auth/login`.
   * Chuyển sang tab **Body**, chọn **raw** (định dạng **JSON**):
     ```json
     {
         "username": "admin",
         "password": "123"
     }
     ```
   * Nhấn **Send** và copy chuỗi Token trả về.

2. **Gọi API chức năng:**
   * Tạo request `GET` đến `http://localhost:8080/api/test/admin` (hoặc `/test/user`).
   * Chuyển sang tab **Authorization**, chọn Type là **Bearer Token** và dán Token vừa copy vào ô bên cạnh.
   * Nhấn **Send** để xem kết quả trả về (`authorized` nếu đúng quyền hoặc lỗi `403 Forbidden` / `unauthorized` nếu sai quyền).
