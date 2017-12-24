package irakli.samniashvili.app.Fragments;

/**
 * Created by irakli on 12/21/2017.
 */

public class MyData {
    private String name;
    private String img;
    private String des;
    private String videohd;
    private String videosd;

    public MyData(String name,String img,String des,String videohd,String videosd) {
        this.name = name;
        this.img = img;
        this.des = des;
        this.videohd = videohd;
        this.videosd = videosd;
    }


    public String getName() {
        return name;
    }
    public String getImg() {
        return img;
    }
    public String getdes() {
        return des;
    }
    public String getVideohd() {
        return videohd;
    } public String getVideosd() {
        return videosd;
    }

    public void setName(String name,String img,String des,String videohd,String videosd) {
        this.name = name;
        this.name = name;
        this.img = img;
        this.des = des;
        this.videohd = videohd;
        this.videosd = videosd;
    }
}
