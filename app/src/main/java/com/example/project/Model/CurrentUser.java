package com.example.project.Model;

public class CurrentUser {
    String Name="";
    String UID="";

    String PhoneNumber="";
    String Nickname="";
    String ImageURL="";
    String Email="";


    public CurrentUser() {
    }

    public CurrentUser(String name, String UID) {
        this.Name = name;
        this.UID = UID;

    }
    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }


    @Override
    public String toString() {
        return "Name "+getName()+" Email" + getEmail() +" " + "Phone " +getPhoneNumber() + " UID " + getUID() ;
    }
}
