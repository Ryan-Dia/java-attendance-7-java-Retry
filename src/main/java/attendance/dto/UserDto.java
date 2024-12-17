package attendance.dto;

import attendance.model.Punishment;
import attendance.model.State;
import attendance.model.User;
import java.util.Map;

public record UserDto(Map<State, Integer> stateTotalCount, Punishment punishment, String userNickname) {
    public UserDto(User user) {
        this(user.getStateTotalCount(), user.getPunishment(), user.getNickname());
    }
}
