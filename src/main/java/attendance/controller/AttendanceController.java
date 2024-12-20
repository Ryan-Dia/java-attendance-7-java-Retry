package attendance.controller;

import attendance.dto.AttendanceRegisterDto;
import attendance.dto.AttendanceRegisterWithPreviousDto;
import attendance.dto.UserDto;
import attendance.error.ErrorMessages;
import attendance.model.Command;
import attendance.model.user.AttendanceRegister;
import attendance.model.user.AttendanceRegisters;
import attendance.model.user.User;
import attendance.model.user.Users;
import attendance.utils.KoreanDateFormatter;
import attendance.utils.LoopTemplate;
import attendance.utils.OpenChecker;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class AttendanceController {

    private static final String QUIT = "Q";

    public void run() throws IOException {
        final Users users = Users.getInstance();
        runLoop(users);
    }

    private void runLoop(Users users) {
        while (true) {
            final String command = getCommand();
            if (command.equals(QUIT)) {
                break;
            }
            runCommand(command, users);
        }
    }

    private String getCommand() {
        return LoopTemplate.tryCatch(InputView::readCommand);
    }

    private void runCommand(String command, Users users) {
        if (Command.isCheckAttendance(command)) {
            runCheckAttendance(users);
        }
        if (Command.isEditAttendance(command)) {
            runAttendanceAdjustment(users);
        }
        if (Command.isCommandCheckAttendanceRecord(command)) {
            runCommandCheckAttendanceRecord(users);
        }
        if (Command.isConfirmationOfThoseAtRiskOfExpulsion(command)) {
            runConfirmationOfPersonsAtRiskOfExpulsion(users);
        }
    }

    private void runCommandCheckAttendanceRecord(Users users) {
        final User user = getUser(users);
        final AttendanceRegisters attendanceRegisters = user.getAttendanceRegisters();
        for (AttendanceRegister attendanceRegister : attendanceRegisters.getAttendanceRegisters()) {
            OutputView.printAttendanceRegister(new AttendanceRegisterDto(attendanceRegister));
        }
        OutputView.printStatTotalCount(user.getStateTotalCountWithoutConsidered());
        OutputView.printPunishment(user.getPunishment());
    }

    private User getUser(Users users) {
        return LoopTemplate.tryCatch(() -> users.findUserByNickname(InputView.readNickname()));
    }

    private void runCheckAttendance(Users users) {
        final LocalDateTime now = DateTimes.now();
        OpenChecker.checkCampusOpen(now);

        final User user = getNickNameWithoutRetry(users);
        final String schoolStartTime = readSchoolStartTime();
        user.attend(schoolStartTime, now);

        final AttendanceRegister attendanceRegister = user.findAttendanceByToday(now);
        final String time = KoreanDateFormatter.loadDateConverter(attendanceRegister.getDatetime());
        final String category = attendanceRegister.getState().getCategory();
        OutputView.printAttendanceCheck(time, category);
    }

    private String readSchoolStartTime() {
        return InputView.readSchoolStartTime();
    }

    private void runAttendanceAdjustment(Users users) {
        final User user = getNickNameWithoutRetry(users);
        final LocalDate date = getDateForAdjustment();
        final LocalTime time = getTimeForAdjustment();
        final LocalDateTime localDateTime = LocalDateTime.of(date, time);
        final AttendanceRegister attendanceRegister = user.adjustAttendanceTime(localDateTime);
        OutputView.printAdjustedAttendanceTime(new AttendanceRegisterWithPreviousDto(attendanceRegister));
    }

    private User getNickNameWithoutRetry(Users users) {
        return users.findUserByNickname(InputView.readNickname());
    }

    private LocalDate getDateForAdjustment() {
        try {
            final String date = InputView.readDateForAdjustment();
            return LocalDate.of(2024, 12, Integer.parseInt(date));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_FORMAT.getMessage());
        }
    }

    private LocalTime getTimeForAdjustment() {
        try {
            final String time = InputView.readTimeForAdjustment();
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(time, dateTimeFormatter);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_FORMAT.getMessage());
        }
    }

    private void runConfirmationOfPersonsAtRiskOfExpulsion(Users users) {
        final List<UserDto> riskyUsers = users.confirmationOfPersonsAtRiskOfExpulsion()
                .stream()
                .map(UserDto::new)
                .toList();
        OutputView.printConfirmationOfPersonsAtRiskOfExpulsion(riskyUsers);
    }
}
