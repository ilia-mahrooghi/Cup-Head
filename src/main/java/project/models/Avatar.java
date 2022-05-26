package project.models;

import project.App;

import java.net.URL;

public enum Avatar {
    AVATAR1(App.class.getResource("/project/png/frames/BossFly/1.png")),
    AVATAR2(App.class.getResource("/project/png/Airplane/blue.png")),
    AVATAR3(App.class.getResource("/project/png/frames/MiniBossFly/purple/1.png")),
    AVATAR4(App.class.getResource("/project/png/frames/MiniBossFly/yellow/2.png"));

    private URL url;

    Avatar(URL url) {
        setUrl(url);
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }
}