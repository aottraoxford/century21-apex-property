package com.century21.repository;

import com.century21.repository.api_user_info.UserInfo;
import com.century21.service.authorize.Authority;
import com.century21.util.Url;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;
import sun.management.Agent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public interface UserRepo {

    @SelectProvider(type = UserUtil.class,method = "agents")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "firstName",column = "first_name"),
            @Result(property = "lastName",column = "last_name"),
            @Result(property = "phoneNumber",column = "phone_number"),
            @Result(property = "accountType",column = "account_type"),
            @Result(property = "role",column = "id",many = @Many(select = "roles"))
    })
    List<User> agents(@Param("name")String name,@Param("parentID") int parentID);

    @Select("SELECT authority.role " +
            "FROM authority " +
            "INNER JOIN authorizations ON authority.id=authorizations.authority_id " +
            "WHERE authorizations.users_id=#{id}")
    List<String> roles();

    @Update("UPDATE users SET parent_id=#{parentID} " +
            "WHERE id=#{childID}")
    int setChild(@Param("parentID")Integer parentID,@Param("childID")int childID);

    @Update("UPDATE authorizations SET authority_id=#{authID} " +
            "WHERE users_id=#{userID}")
    int updateRole(@Param("userID")int userID,@Param("authID")int authID);

    @Select("SELECT id " +
            "FROM authority " +
            "WHERE role ilike #{role}")
    Integer roleID(String role);

    @Select("SELECT count(id) " +
            "FROM verification WHERE name = #{email}")
    int checkAccount(String email);

    @Update("UPDATE verification SET enable = TRUE " +
            "WHERE code = #{code}")
    Integer updateEnable(int code);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email =#{email}")
    Integer findUserIDByEmail(String email);

    @Insert("INSERT INTO verification (name,code) " +
            "VALUES (#{email},#{code})")
    void insertVerification(@Param("email")String email,@Param("code")int code);

    @Delete("Delete " +
            "FROM verification " +
            "WHERE name = #{email} AND enable IS TRUE")
    void removeEmail(String email);

    @Update("UPDATE users SET password=crypt(#{change.password},gen_salt('bf')) " +
            "WHERE email = #{change.email}")
    Integer updatePassword(@Param("change")ChangePassword change);

    @Delete("DELETE FROM verification WHERE expired < now() - interval '10' minute AND enable IS false ")
    Integer removeCode();

    class UserUtil{
        public String agents(@Param("name")String name,@Param("parentID") int parentID){
           return new SQL(){
               {
                   SELECT("id,first_name,last_name,email,gender,phone_number,image,account_type");
                   FROM("users");
                   WHERE("id=#{parentID} OR parent_id=#{parentID}");
                   if(name!=null && name.length()>0)
                       WHERE("name ilike '%'||#{name}||'%'");
                   ORDER_BY("id DESC");
               }
           } .toString();
        }
    }

    class User{
        private int id;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        private String gender;
        private String email;
        @JsonProperty("phone_number")
        private String phoneNumber;
        @JsonProperty("photo")
        private String image;
        @JsonIgnore
        private String swapEmail;
        @JsonProperty("account_type")
        private String accountType;
        private String role;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            swapEmail=email;
            if(email.contains("|")) {
                String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
                Pattern pattern;
                Matcher matcher;
                pattern=Pattern.compile(EMAIL_REGEX,Pattern.CASE_INSENSITIVE);
                email=email.split("\\|")[0];
                matcher=pattern.matcher(email);
                if(!matcher.matches()) return null;
            }
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getImage() {
            if (image != null && !image.contains("/")) {
                return Url.userImageUrl + image;
            }
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSwapEmail() {
            return swapEmail;
        }

        public void setSwapEmail(String swapEmail) {
            this.swapEmail = swapEmail;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }
    }

    class ChangePassword{
        private String password;
        private String email;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    class AssignRoleRequest{
        @JsonProperty("user_id")
        private int userID;
        @JsonProperty("change_to")
        private String role;

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
