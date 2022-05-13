package tfg.jsemp.moneysaver.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable {

    private String userId;
    private String email;
    private String name;
    private String image;


    public User(){

    }


    public User(String userId, String email){
        this.userId = userId;
        this.email = email;
        this.name = splitEmail(email);
    }

    private String splitEmail(String email) {
        return email.split("@")[0];
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


    public String getImage() {
        return image;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setImage(String image) {
        this.image = image;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}
