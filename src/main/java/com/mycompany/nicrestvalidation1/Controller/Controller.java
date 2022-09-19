/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nicrestvalidation1.Controller;

import com.google.gson.Gson;
import com.mycompany.nicrestvalidation1.Model.User;
import static com.mycompany.nicrestvalidation1.Model.User.edit;
import static com.mycompany.nicrestvalidation1.Model.User.getUserData;
import static com.mycompany.nicrestvalidation1.Model.User.save;
import static com.mycompany.nicrestvalidation1.Model.User.userDelete;
import com.mycompany.nicrestvalidation1.Model.UserTemp;
import static com.mycompany.nicrestvalidation1.Model.UserTemp.saveTemp;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Dilmi
 */
@Path("user")
@RequestScoped
public class Controller {

    @Context
    private UriInfo context;

   
    public Controller() {
    }

    
    @GET
    @Path("view-user")
    @Produces(MediaType.APPLICATION_JSON)
    public String viewUser() {
        List<User> users = User.getUsers();
        return new  Gson().toJson(users);  
    }
    
    @POST
    @Path("retrieve-user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String retrieveUser(User user) {
        System.out.println("Retrieve user  "+ user.getId());
        return new  Gson().toJson(getUserData(user));  
    }
    
    @POST
    @Path("edit-user")
    @Produces(MediaType.APPLICATION_JSON)
    public void editUser(User user) { 
        System.out.println("Edit  user   from  Conntroler" + user.getAddress());
        edit(user);       
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String saveUser(User user) { 
        System.out.println("User from controller Calss");
        return save(user);       
    }

    @POST
    @Path("delete-User")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(User user) {
        System.out.println("Delete User   Called");
        userDelete(user);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
