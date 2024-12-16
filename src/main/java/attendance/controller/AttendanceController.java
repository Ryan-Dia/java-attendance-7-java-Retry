package attendance.controller;

import static attendance.model.UserReader.createRegisters;
import static attendance.model.UserReader.readUsers;

import attendance.dto.AttendanceRegisterDto;
import attendance.error.ErrorMessages;
import attendance.model.AttendanceRegister;
import attendance.model.AttendanceRegisters;
import attendance.model.Command;
import attendance.model.User;
import attendance.model.Users;
import attendance.utils.LoopTemplate;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AttendanceController {

    public void run() throws IOException {
        final Users users = getUsers();
        final String command = getCommand();
        runCommand(command, users);
    }

    private void runCommand(String command, Users users) {
        if (Command.isCommandCheckAttendanceRecord(command)) {
            runCommandCheckAttendanceRecord(users);
        }
        if (Command.CHECK_ATTENDANCE.getCommandOrder().equals(command)) {
            runCheckAttendance(users);
        }
    }

    private void runCheckAttendance(Users users) {
        final User nickname = getNickname(users);
        check(InputView.readSchoolStartTime());
    }

    private String check(String input) {
        final String[] split = input.split(":");
        if (Integer.parseInt(split[0]) > 12) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_FORMAT.getMessage());
        }
        if (Integer.parseInt(split[1]) > 60) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_FORMAT.getMessage());
        }
        return input;
    }


    private User getNickname(Users users) {
        return LoopTemplate.tryCatch(() -> users.findUserByNickname(InputView.readNickname()));
    }


    private void runCommandCheckAttendanceRecord(Users users) {
        final User user = getUser(users);
        final AttendanceRegisters attendanceRegisters = user.getAttendanceRegisters();
        for (AttendanceRegister attendanceRegister : attendanceRegisters.getAttendanceRegisters()) {
            OutputView.printAttendanceRegister(new AttendanceRegisterDto(attendanceRegister));
        }
        OutputView.printStatTotalCount(user.getStateTotalCount());
        OutputView.printPunishment(user.getPunishment());
    }

    private User getUser(Users users) {
        return LoopTemplate.tryCatch(() -> users.findUserByNickname(InputView.readNickname()));
    }


    private String getCommand() {
        return LoopTemplate.tryCatch(InputView::readCommand);
    }

    private Users getUsers() throws IOException {
        List<User> users = new ArrayList<>();
        final Map<String, List<String>> innerUsers = readUsers();
        for (Entry<String, List<String>> user : innerUsers.entrySet()) {
            users.add(new User(user.getKey(), createRegisters(user.getValue())));
        }
        return new Users(users);
    }
}
