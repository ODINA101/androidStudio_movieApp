package irakli.samniashvili.app.SruladFragments;

/**
 * Created by irakli on 12/26/2017.
 */

public class movies {
    public String name;
    public String photo;
    public String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
public movies() {

}
    public movies(String name, String photo, String comment) {
        this.name = name;
        this.photo = photo;
        this.comment = comment;
    }
}
