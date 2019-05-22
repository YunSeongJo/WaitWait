package com.waitwait;

public class LoginedUserInformation {

    public static String email = "null";
    public static String WaitingRestaurant = "none";
    public static int WaitingNumber = 0;

    public String getEmail(){
        return email;
    }

    public void setEmail(String s){
        email=s;
    }

    public String getWaitingRestaurant(){
        return WaitingRestaurant;
    }

    public void setWaitingRestaurant(String s){
        WaitingRestaurant=s;
    }

    public int getWaitingNumber(){
        return WaitingNumber;
    }

    public void setWaitingNumber(int s){
        WaitingNumber=s;
    }
}
