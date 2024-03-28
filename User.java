package com.tazerdev.spa;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class User implements Serializable {
    private int ID;
    private String FName;
    private String LName;
    private String Email;
    private String Password;

    /**
     * construct a user and throw exception when invalid
     * @param FName
     * @param LName
     * @param email
     * @param password
     * @param confpassword
     * @throws Exception
     */
    public User(String FName, String LName, String email, String password, String confpassword) throws Exception {
        if(!password.equals(confpassword)) throw new Exception("Conf and Pass Not Equal");
        if(!Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])").matcher(email).matches()) throw new Exception("Wrong Email format");
        this.FName = FName;
        this.LName = LName;
        Email = email;
        Password = get_SHA_512_SecurePassword(password, "SPA512SALTSTRING");
    }

    /**
     * hashes the password to SHA_512
     * @param passwordToHash
     * @param salt
     * @return
     */
    public String get_SHA_512_SecurePassword(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * @return user's ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets user's ID
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return user's first name
     */
    public String getFName() {
        return FName;
    }

    /**
     * Sets user's first name
     * @param FName
     */
    public void setFName(String FName) {
        this.FName = FName;
    }

    /**
     * @return user's last name
     */
    public String getLName() {
        return LName;
    }

    /**
     * Sets user's last name
     * @param LName
     */
    public void setLName(String LName) {
        this.LName = LName;
    }

    /**
     * @return user's email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Sets user's email
     * @param email
     */
    public void setEmail(String email) {
        Email = email;
    }

    /**
     * @return hashed user's password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * Set password and it will be hashed
     * @param password
     */
    public void setPassword(String password) {
        Password = get_SHA_512_SecurePassword(password, "SPA512SALTSTRING");
    }

    /**
     * compare 2 user objects
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getID() == user.getID() && getFName().equals(user.getFName()) && getLName().equals(user.getLName()) && getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword());
    }

    /**
     * @return a printable string value of User
     */
    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", FName='" + FName + '\'' +
                ", LName='" + LName + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }

    /**
     * prints user details
     */
    public void printDetails(){
        System.out.println(this);
    }
}
