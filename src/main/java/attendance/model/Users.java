package attendance.model;

import static attendance.model.UserReader.createRegisters;

import attendance.error.ErrorMessages;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Users {
    private final List<User> users;

    public Users(final List<User> users) {
        this.users = users;
    }

    public static Users getInstance() throws IOException {
        List<User> users = new ArrayList<>();
        final Map<String, List<String>> innerUsers = UserReader.readUsersFile();
        for (Entry<String, List<String>> user : innerUsers.entrySet()) {
            users.add(new User(user.getKey(), createRegisters(user.getValue())));
        }
        return new Users(users);
    }

    public User findUserByNickname(String nickname) {
        return users.stream()
                .filter(user -> user.getNickname().equals(nickname))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.UNREGISTERED_NAME.getMessage()));
    }

    public List<User> confirmationOfPersonsAtRiskOfExpulsion() {
        return users.stream()
                .filter(user -> user.getPunishment() != Punishment.NONE)
                .sorted(Comparator.comparingInt((User user) -> user.getPunishment().getAbsenceCount()).reversed())
                .sorted(Comparator.comparingInt(
                                (User user) -> user.getAttendanceRegisters().calculateAttendance().get(State.LATENESS))
                        .reversed())
                .toList();
    }

    public List<User> getUsers() {
        return users;
    }
}
