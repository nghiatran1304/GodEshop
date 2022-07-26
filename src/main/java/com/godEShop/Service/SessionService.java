package com.godEShop.Service;

public interface SessionService {

    /**
     * Đọc giá trị của attribute trong session
     * 
     * @param name tên attribute
     * @return giá trị đọc được hoặc null nếu không tồn tại
     */
    <T> T get(String name);

    /**
     * Đọc giá trị của attribute trong session
     * 
     * @param name         tên attribute
     * @param defaultValue giá trị mặc định
     * @return giá trị đọc được hoặc defaultValue nếu không tồn tại
     */
    <T> T get(String name, T defaultValue);

    /**
     * Thay đổi hoặc tạo mới attribute trong session
     * 
     * @param name  tên attribute
     * @param value giá trị attribute
     */
    void set(String name, Object value);

    /**
     * Xóa attribute trong session
     * 
     * @param name tên attribute cần xóa
     */
    void remove(String name);
}
