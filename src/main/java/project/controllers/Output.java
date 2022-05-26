package project.controllers;

public enum Output {
    REPEATED_USERNAME("user with this username already exists!"),
    REGISTERED("user registered successfully!"),
    INVALID_USERNAME_OR_PASSWORD("invalid username or password!"),
    CHANGE_DATA("data changed successfully!"),
    ;

    private String output;

    Output(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return this.output;
    }
}
