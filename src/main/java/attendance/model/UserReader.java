package attendance.model;

import static java.util.stream.Collectors.toList;

import attendance.utils.MarkdownReader;
import attendance.utils.WeekdayChecker;
import camp.nextstep.edu.missionutils.DateTimes;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class UserReader {

    public static List<String[]> readFile() throws IOException {
        return MarkdownReader.readFile("src/main/resources/attendances.csv");
    }


    public static Map<String, List<String>> readUsers() throws IOException {
        Map<String, List<String>> users = new HashMap<>();
        final List<String[]> info = readFile();
        for (String[] userInfo : info) {
            final String nickname = userInfo[0];
            final String dateTime = userInfo[1];
            users.computeIfAbsent(nickname, k -> new ArrayList<>()).add(dateTime);
        }
        return users;
    }

    public static AttendanceRegisters createRegisters(List<String> times) {
        final List<AttendanceRegister> attendanceRegisters = times.stream().map(AttendanceRegister::from).collect(
                toList());
        checkAbsence(attendanceRegisters);
        return new AttendanceRegisters(attendanceRegisters);
    }

    public static void checkAbsence(List<AttendanceRegister> attendanceRegisters) {
        final int beforeDay = DateTimes.now().getDayOfMonth() - 1;
        final List<Integer> recordedAttendance = attendanceRegisters.stream()
                .map(attendanceRegister -> attendanceRegister.getDatetime().getDayOfMonth())
                .toList();
        IntStream.rangeClosed(2, beforeDay)
                .filter(number -> !recordedAttendance.contains(number))
                .filter(WeekdayChecker::isWeekday)
                .forEach(number -> attendanceRegisters.add(
                        new AttendanceRegister(createDateTime(number), State.ABSENCE)));
    }

    private static LocalDateTime createDateTime(final int number) {
        return LocalDateTime.of(2024, 12, number, 0, 0);
    }

}
