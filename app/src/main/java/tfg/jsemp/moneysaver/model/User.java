package tfg.jsemp.moneysaver.model;

import androidx.annotation.NonNull;

public class User {

    private String userId;
    private String email;
    private String name;
    private String lastName;
    private String image;


    public User(){

    }


    public User(String email, String name, String lastName){
        this.email = email;
        this.name = name;
        this.lastName = lastName;
    }


    public String getUserId() {
        return userId;
    }


    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }


    public String getLastName() {
        return lastName;
    }


    public String getImage() {
        return image;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }


}
