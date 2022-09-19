/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nicrestvalidation1.Model;

import com.google.gson.Gson;
import com.mycompany.nicrestvalidation1.Controller.UserDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author Dilmi
 */
public class User {

    private int id;
    private String fullName;
    private String address;
    private String nationality;
    private String nic;
    private String gender;
    private int age;
    private String dob;

    public User(int id, String fullName, String address, String nationality, String nic, String gender, int age, String dob) {

        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.nationality = nationality;
        this.nic = nic;
        this.gender = gender;
        this.age = age;
        this.dob = dob;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getNationality() {
        return nationality;
    }

    public String getNic() {
        return nic;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getDob() {
        return dob;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * getUsers
     */
    public static List<User> getUsers() {

        User user;

        ResultSet resultset = null;
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM userdetails.usernew WHERE state='active'";

        try {
            System.out.println("Connection  strated");
            Connection con = UserDao.getDaoConnection();
            System.out.println("Connnected to db");

            Statement stmt = con.createStatement();
            System.out.println("Staement created");

            resultset = stmt.executeQuery(sql);
            System.out.println("Query  ececuted  ");

            while (resultset.next()) {
                user = new User();
                user.setId(resultset.getInt("id"));
                user.setFullName(resultset.getString("fullName"));
                user.setAddress(resultset.getString("address"));
                user.setNationality(resultset.getString("nationality"));
                user.setNic(resultset.getString("nic"));
                user.setGender(resultset.getString("gender"));
                user.setAge(resultset.getInt("age"));
                user.setDob(resultset.getString("dob"));

                users.add(user);

            }

        } catch (Exception e) {
            System.out.println("error  is   " + e.getMessage());
        }

        return users;
    }

    /**
     *
     * saveUsers
     */
    public static String save(User user) {
        RequestDispatcher rd;

        boolean validNIC = false;
        boolean newNIC = false;

        String fullName = user.getFullName();
        String address = user.getAddress();
        String nationality = user.getNationality();
        String nic = user.getNic();

        String gender = null;
        int age = 0;
        String dob = null;

        int daysperMonth[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int year = 0;
        int calculateyear;
        int monthdate = 0;
        int temp;
        int month = 0;
        int date;
        String errMsg = "";

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        try {
            if (nic.length() == 10 || nic.length() == 12) {
                validNIC = true;
            }
            {
                System.out.println("Invalid nic");
            }
            if (nic.length() == 12 && validNIC == true) {
                newNIC = true;
            } else if (nic.length() == 10 && validNIC == true) {
                newNIC = false;
            }

            if (newNIC == false && validNIC == true) {
                calculateyear = Integer.parseInt(nic.substring(0, 2));
                monthdate = Integer.parseInt(nic.substring(2, 5));

                year = 1900 + calculateyear;
            } else if (newNIC == true && validNIC == true) {
                calculateyear = Integer.parseInt(nic.substring(0, 4));
                monthdate = Integer.parseInt(nic.substring(4, 7));

                year = calculateyear;
            }

            if (monthdate > 500) {
                monthdate = monthdate - 500;
                gender = "Female";
            } else {
                gender = "Male";
            }

            temp = monthdate;

            for (int i = 0; i < 12; i++) {
                if (temp <= daysperMonth[i]) {
                    month = i + 1;
                    break;
                } else {
                    temp = temp - daysperMonth[i];
                }
            }

            for (int i = 0; i < (month - 1); i++) {
                monthdate = monthdate - daysperMonth[i];
            }

            date = monthdate;

            dob = year + "/" + date + "/" + month;
            currentYear = Calendar.getInstance().get(Calendar.YEAR);

            age = currentYear - year;

            user.setNic(nic);
            user.setFullName(fullName);
            user.setAddress(address);
            user.setNationality(nationality);
            user.setGender(gender);
            user.setAge(age);
            user.setDob(dob);

            System.out.println("Birthday from user.java" + user.getDob());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        UserDao ud = new UserDao();
        try {
            Connection con = null;
            con = ud.getDaoConnection();
            String sql = "INSERT INTO userdetails.usernew (fullName,address,nationality,nic,gender,age,dob,state) VALUES(?,?,?,?,?,?,?,'active')";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, fullName);
            ps.setString(2, address);
            ps.setString(3, nationality);
            ps.setString(4, nic);
            ps.setString(5, gender);
            ps.setInt(6, age);
            ps.setString(7, dob);

            ps.executeUpdate();
            return new Gson().toJson("{\"message\":\"success\"}");

        } catch (Exception ex) {
            ex.printStackTrace();
            return new Gson().toJson("{\"message\":\"" + ex.getMessage() + "\"}");
        }

    }

    public static User getUserData(User user) {
        User user1 = new User();
        int id = user.getId();
        ResultSet resultSet;
        UserDao ud = new UserDao();

        String sql = "SELECT * FROM userdetails.usernew WHERE id='" + id + "';";

        try {
            Connection con = ud.getDaoConnection();
            Statement stmt = con.createStatement();

            resultSet = stmt.executeQuery(sql);

            resultSet.next();

            user1.setId(resultSet.getInt("id"));
            user1.setFullName(resultSet.getString("fullName"));
            user1.setAddress(resultSet.getString("address"));
            user1.setNationality(resultSet.getString("nationality"));
            user1.setNic(resultSet.getString("nic"));
            user1.setGender(resultSet.getString("gender"));
            user1.setAge(resultSet.getInt("age"));
            user1.setDob(resultSet.getString("dob"));

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return user1;
    }
    
    //
    //Edit User
    //
    public static  void edit(User  user){
        String sql = "UPDATE userdetails.usernew SET fullName=?,address=?,nationality=?,nic=?,gender=?,age=?,dob=?,state='active',modifiedDate=?,modifiedBy='System' where  id=?";
        UserDao  ud = new UserDao();
        
        int  id  = user.getId();
        String fullName = user.getFullName();
        String address = user.getAddress();
        String nationality = user.getNationality();
        String nic = user.getNic();
        String gender = user.getGender();
        int age = user.getAge();
        String dob = user.getDob();
        
        int date = Calendar.getInstance().get(Calendar.DATE);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String today = date +"/"+ month+"/"+year;
        
        try {
            Connection con = ud.getDaoConnection();
            PreparedStatement ps=  con.prepareStatement(sql);
            
            ps.setString(1, fullName);
            ps.setString(2, address);
            ps.setString(3, nationality);
            ps.setString(4, nic);
            ps.setString(5, gender);
            ps.setInt(6, age);
            ps.setString(7, dob);
            ps.setString(8,today);
            ps.setInt(9, id);
            
            ps.executeUpdate();
           

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    
    
    
    //
    //Delete User
    //
    public static void userDelete(User user) {
        User deleteUser = new User();
        int id = user.getId();
        
        int date = Calendar.getInstance().get(Calendar.DATE);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String today = date +"/"+ month+"/"+year;
        
        try {
            UserDao ud = new UserDao();

            Connection con;
            con = ud.getDaoConnection();

            String sql = "UPDATE userdetails.usernew SET state='inactive',deleteDate=?,deleteBy='System' WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
           
            ps.setString(1,today);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("The Error is : " + e.getMessage());
        }

    }

}
