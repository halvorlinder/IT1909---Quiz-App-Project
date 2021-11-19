package ui.controllers;

import ui.User;

public abstract class BaseController {
    private final User user;

    /**
     * @param user the current user
     */
    public BaseController(User user) {
        this.user = user;
    }

    /**
     * @return the current user
     */
    protected User getUser() {
        return new User(user.getUsername(), user.getAccessToken());
    }
}
