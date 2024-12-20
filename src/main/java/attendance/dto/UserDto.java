package attendance.dto;

import attendance.model.Punishment;
import attendance.model.State;
import attendance.model.user.User;
import java.util.Map;

public record UserDto(Map<State, Integer> stateTotalCountWithoutConsidered, Punishment punishment,
                      String userNickname) {
    public UserDto(User user) {
        this(user.getStateTotalCountWithoutConsidered(), user.getPunishment(), user.getNickname());
    }
}
