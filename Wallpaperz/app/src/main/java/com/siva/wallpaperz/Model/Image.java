package com.siva.wallpaperz.Model;

/**
 * Created by sivakumar on 10-01-2017.
 */

public class Image {
    private String thumb;
    private String raw;
    private String full;
    private String small;
    private String regular;

    public Image() {

    }

    public Image(String thumb, String raw, String full) {
        this.thumb = thumb;
        this.raw = raw;
        this.full = full;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }
}
