/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nicrestvalidation1.Controller;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Dilmi
 */
public class UserDao {
    
    
    
    public static Connection getDaoConnection(){
       Connection con= null;      
       try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdetails","root","");
            System.out.println("Connnnected from User Dao");
        }catch (Exception e) {
            e.printStackTrace();
        } 
       
       return con;
    } 
    
    
}