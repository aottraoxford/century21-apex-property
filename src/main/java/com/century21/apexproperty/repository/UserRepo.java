package com.century21.apexproperty.repository;

import com.century21.apexproperty.util.Url;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public interface UserRepo {
    @SelectProvider(type = UserUtil.class,method = "findAllContactCount")
    int findAllContactCount(@Param("filter")ContactFilter filter, @Param("userID")int userID,@Param("roleType")String roleType);

    @SelectProvider(type = UserUtil.class,method = "findAllContact")
    @Results({
            @Result(property = "createAt",column = "created_at")
    })
    List<Contact> findAllContact(@Param("filter")ContactFilter filter, @Param("userID")int userID,@Param("roleType")String roleType,@Param("limit")int limit,@Param("offset")int offset);

    @Select("SELECT role " +
            "FROM authority " +
            "INNER JOIN authorizations on authorizations.authority_id=authority.id " +
            "INNER JOIN users on users.id=authorizations.users_id " +
            "WHERE users.id=#{userID}")
    String findUserRole(int userID);

    @Select("SELECT role " +
            "FROM authority " +
            "INNER JOIN authorizations on authorizations.authority_id=authority.id " +
            "INNER JOIN users on users.id=authorizations.users_id " +
            "WHERE users.email=#{email}")
    String findUserRoleByEmail(String email);

    @SelectProvider(type = UserUtil.class,method = "findUsers")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "firstName",column = "first_name"),
            @Result(property = "lastName",column = "last_name"),
            @Result(property = "phoneNumber",column = "phone_number"),
            @Result(property = "accountType",column = "account_type"),
            @Result(property = "role",column = "id",many = @Many(select = "roles"))
    })
    List<User> findUsers(String name,String role,int limit,int offset);

    @SelectProvider(type = UserUtil.class,method = "findUsersCount")
    int findUsersCount(String name,String role);

    @SelectProvider(type = UserUtil.class,method = "agentsCount")
    int agentsCount(@Param("name")String name,@Param("parentID") int parentID);

    @SelectProvider(type = UserUtil.class,method = "agents")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "firstName",column = "first_name"),
            @Result(property = "lastName",column = "last_name"),
            @Result(property = "phoneNumber",column = "phone_number"),
            @Result(property = "accountType",column = "account_type"),
            @Result(property = "role",column = "id",many = @Many(select = "roles"))
    })
    List<User> agents(@Param("name")String name,@Param("userID") int userID,int limit,int offset);

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
        public String findAllContactCount(@Param("filter")ContactFilter filter,@Param("userID")int userID,@Param("roleType")String roleType){
            String contactofProperty,contactofProject;
            if(roleType.equalsIgnoreCase("admin")){
                contactofProject="select count(contact.id) from contact " +
                        "inner join project on contact.project_id = project.id " +
                        "inner join users on users.id=project.user_id " +
                        "where contact.issue is null and users.id = #{userID} or users.parent_id=(select parent_id from users where id=#{userID})";
                contactofProperty="select count(contact.id) from contact " +
                        "inner join property on contact.property_id = property.id " +
                        "inner join users on users.id=property.user_id " +
                        "where contact.issue is null and users.id = #{userID} or users.parent_id=(select parent_id from users where id=#{userID})";
            }else if(roleType.equalsIgnoreCase("agent")){
                contactofProject="select count(contact.id) from contact " +
                        "inner join project on contact.project_id = project.id " +
                        "inner join users on users.id=project.user_id " +
                        "where contact.issue is null and users.id = #{userID}";
                contactofProperty="select count(contact.id) from contact " +
                        "inner join property on contact.property_id = property.id " +
                        "inner join users on users.id=property.user_id " +
                        "where contact.issue is null and users.id = #{userID}";
            }else return "select count(id) from contact limit 0";
            if(filter.getType().equalsIgnoreCase("project"))
                return contactofProject;
            else if(filter.getType().equalsIgnoreCase("property"))
                return contactofProperty;
            else {
                System.out.println("select sum(count) from (("+contactofProject+") union ("+contactofProperty+"))as foo");
                return "select sum(count) from (("+contactofProject+") union all ("+contactofProperty+"))as foo";
            }
        }

        public String findAllContact(@Param("filter")ContactFilter filter,@Param("userID")int userID,@Param("roleType")String roleType,@Param("limit")int limit,@Param("offset")int offset){
            String contactofProperty,contactofProject;
            if(roleType.equalsIgnoreCase("admin")){
                contactofProject="select contact.* from contact " +
                        "inner join project on contact.project_id = project.id " +
                        "inner join users on users.id=project.user_id " +
                        "where contact.issue is null and users.id = #{userID} or users.parent_id=(select parent_id from users where id=#{userID})";
                contactofProperty="select contact.* from contact " +
                        "inner join property on contact.property_id = property.id " +
                        "inner join users on users.id=property.user_id " +
                        "where contact.issue is null and users.id = #{userID} or users.parent_id=(select parent_id from users where id=#{userID})";
            }else if(roleType.equalsIgnoreCase("agent")){
                contactofProject="select contact.* from contact " +
                        "inner join project on contact.project_id = project.id " +
                        "inner join users on users.id=project.user_id " +
                        "where contact.issue is null and users.id = #{userID}";
                contactofProperty="select contact.* from contact " +
                        "inner join property on contact.property_id = property.id " +
                        "inner join users on users.id=property.user_id " +
                        "where contact.issue is null and users.id = #{userID}";
            }else return "select id from contact limit 0";
            if(filter.getType().equalsIgnoreCase("project"))
                return contactofProject+" order by id desc limit #{limit} offset #{offset}";
            else if(filter.getType().equalsIgnoreCase("property"))
                return contactofProperty+" order by id desc limit #{limit} offset #{offset}";
            else {
                return "("+contactofProject+") union all ("+contactofProperty+") order by id desc limit #{limit} offset #{offset}";
            }
        }

        public String findUsers(String name,String role,int limit,int offset){
            return new SQL(){
                {
                    SELECT("users.id,first_name,last_name,email,gender,phone_number,image,account_type");
                    FROM("users");
                    if(role!=null && role.trim().length()>0) {
                        INNER_JOIN("authorizations ON users.id=authorizations.users_id");
                        INNER_JOIN("authority ON authorizations.authority_id=authority.id");
                        WHERE("authority.role ilike #{role}");
                    }
                    if(name!=null && name.trim().length()>0)
                        WHERE("concat(first_name,' ',last_name) ilike '%'||#{name}||'%'");
                    ORDER_BY("id DESC limit #{limit} offset #{offset}");
                }
            }.toString();
        }

        public String findUsersCount(String name,String role){
            return new SQL(){
                {
                    SELECT("count(users.id)");
                    FROM("users");
                    if(role!=null && role.trim().length()>0) {
                        INNER_JOIN("authorizations ON users.id=authorizations.users_id");
                        INNER_JOIN("authority ON authorizations.authority_id=authority.id");
                        WHERE("authority.role ilike #{role}");
                    }
                    if(name!=null && name.trim().length()>0)
                        WHERE("concat(first_name,' ',last_name) ilike '%'||#{name}||'%'");

                }
            }.toString();
        }

        public String agents(@Param("name")String name,@Param("userID") int userID,int limit ,int offset){
           return new SQL(){
               {
                   SELECT("id,first_name,last_name,email,gender,phone_number,image,account_type");
                   FROM("users");
                   WHERE("parent_id=(select parent_id from users where id = #{userID})");
                   if(name!=null && name.length()>0)
                       WHERE("name ilike '%'||#{name}||'%'");
                   ORDER_BY("id DESC limit #{limit} offset #{offset}");
               }
           } .toString();
        }

        public String agentsCount(@Param("name")String name,@Param("parentID") int parentID){
            return new SQL(){
                {
                    SELECT("count(id)");
                    FROM("users");
                    WHERE("id=#{parentID} OR parent_id=#{parentID}");
                    if(name!=null && name.length()>0)
                        WHERE("name ilike '%'||#{name}||'%'");
                }
            } .toString();
        }
    }

    class Contact{
        private int id;
        private String name;
        private String phone;
        private String email;
        @JsonProperty("created_at")
        private Date createAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getCreateAt() {
            return createAt;
        }

        public void setCreateAt(Date createAt) {
            this.createAt = createAt;
        }


        class Property{
            private int id;
            private String title;
            private String description;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        class Project{
            private int id;
            private String title;
            private String description;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    class ContactFilter{
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
