package gent.zeus.tappb;

public class User {
    private static final User instance = new User();

    private String username;
    private String tabToken;
    private String tapToken;
    private boolean loaded = false;

    public void load(String username, String tabToken, String tapToken) {
        this.username = username;
        this.tabToken = tabToken;
        this.tapToken = tapToken;
        this.loaded = true;
    }

    //private constructor to avoid client applications to use constructor
    private User(){}

    public static User getInstance(){
        return instance;
    }

    private void assertLoaded() {
        if (!this.loaded) {
            throw new RuntimeException("User is not loaded yet");
        }
    }

    public String getUsername() {
        assertLoaded();
        return username;
    }

    public String getTabToken() {
        assertLoaded();
        return tabToken;
    }

    public String getTapToken() {
        assertLoaded();
        return tapToken;
    }
}
