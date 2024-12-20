package attendance.model.user;

import static attendance.model.user.UserReader.createRegisters;

import attendance.error.ErrorMessages;
import attendance.model.Punishment;
import attendance.model.State;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Users {
    private static final int ABSENCE_STANDARD_COUNT = 3;
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
                .sorted(
                        punishmentOrder
                                .thenComparing(absenceCountWithLateness)
                                .thenComparing(User::getNickname)
                )
                .toList();
    }

    private final Comparator<User> punishmentOrder = Comparator
            .comparingInt((User user) -> user.getPunishment().getAbsenceCount())
            .reversed();

    private final Comparator<User> absenceCountWithLateness = Comparator
            .comparingInt((User user) -> {
                final Integer latenessCount = user.getAttendanceRegisters()
                        .calculateAttendanceWithoutConsidered()
                        .getOrDefault(State.LATENESS, 0);
                final Integer absenceCount = user.getAttendanceRegisters()
                        .calculateAttendanceWithoutConsidered()
                        .getOrDefault(State.ABSENCE, 0);
                return absenceCount + latenessCount / ABSENCE_STANDARD_COUNT;
            })
            .reversed();

    public List<User> getUsers() {
        return users;
    }
}
