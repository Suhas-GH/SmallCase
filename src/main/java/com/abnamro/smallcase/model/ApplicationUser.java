package com.abnamro.smallcase.model;



import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(name = "FirstName")
    @NotBlank(message = "First Name cannot be Null")
    private String firstName;

    @Column(name = "LastName")
    @NotBlank(message = "Last Name cannot be Null")
    private String lastName;

    @Column(name = "UserName")
    @NotBlank(message = "Username cannot be Null")
    private String userName;

    @Column(name = "Password")
    @NotBlank(message = "Password cannot be Null")
    @Pattern(regexp = "/[a-zA-Z0-9!@#$%^&*]/g", message = "Password does not match the required format")
    private String password;

    public ApplicationUser() {
    }

    public ApplicationUser(Long userId, String firstName, String lastName, String userName, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

