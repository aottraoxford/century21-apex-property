package com.century21.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo {

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email =#{email}")
    Integer findUserIDByEmail(String email);

    @Insert("INSERT INTO verification (name,code) " +
            "VALUES (#{email},#{code})")
    void insertVerification(@Param("email")String email,@Param("code")int code);

    @Update("UPDATE verification SET enable=true " +
            "WHERE name = #{email} AND code = #{code}")
    Integer checkCode(@Param("email")String email,@Param("code")int code);

    @Delete("Delete " +
            "FROM verification " +
            "WHERE name = #{email}")
    void removeEmail(String email);

    @Update("UPDATE users SET password=crypt(#{change.password},gen_salt('bf')) " +
            "WHERE email = #{change.email}")
    Integer updatePassword(@Param("change")ChangePassword change);

    @Delete("DELETE FROM verification WHERE expired < now() - interval '10' minute")
    Integer removeCode();

    class ChangePassword{
        private String password;
        private int code;
        private String email;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
