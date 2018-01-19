package irakli.samniashvili.app.Fragments;

/**
 * Created by irakli on 12/21/2017.
 */

public class MyData {
    public String name;
    public String photo;
    public String des;
    public String sd;
    public String hd;






    public MyData() {

    }
    public MyData(String name, String photo, String des, String sd, String hd) {
        this.name = name;
        this.photo = photo;
        this.des = des;
        this.sd = sd;
        this.hd = hd;
    }




    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
