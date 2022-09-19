
package com.mycompany.nicrestvalidation1.Model;

public class UserTemp {
    private int id;
    private String fullName;
    private String address;
    private String nationality;
    private String nic;

    public UserTemp() {
    }

    public UserTemp(int id, String fullName, String address, String nationality, String nic) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.nationality = nationality;
        this.nic = nic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
    
    public static void saveTemp(UserTemp userTemp){
        System.out.println("Save Temp Working");
        String sql="INSERT INTO userdetails.user (fullName,address,nationality,nic,gender,age,dob) VALUES(?,?,?,?,?,?,?)";
    }
}
