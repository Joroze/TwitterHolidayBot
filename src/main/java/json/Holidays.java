package json;

/**
 * Created by jorda on 5/15/2016.
 */
public class Holidays {

    private String name;

    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ClassPojo [name = " + name + ", url = " + url + "]";
    }
}
