package irakli.samniashvili.app.Fragments;

/**
 * Created by root on 3/16/18.
 */

public class seriesModel  {

    String name;
    String des;
    String photo;
    public seriesModel() {}

    public seriesModel(String name, String des, String photo) {
        this.name = name;
        this.des = des;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
