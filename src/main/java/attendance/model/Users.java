package attendance.model;

import attendance.error.ErrorMessages;
import java.util.List;

public class Users {
    private final List<User> users;

    public Users(final List<User> users) {
        this.users = users;
    }


    public User findUserByNickname(String nickname) {
        return users.stream()
                .filter(user -> user.getNickname().equals(nickname))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.UNREGISTERED_NAME.getMessage()));
    }

    public List<User> getUsers() {
        return users;
    }
}
