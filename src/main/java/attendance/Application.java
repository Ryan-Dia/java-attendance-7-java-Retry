package attendance;

import attendance.controller.AttendanceController;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            AttendanceController attendanceController = new AttendanceController();
            attendanceController.run();
        } catch (IOException e) {
            System.out.println("[ERROR] 마크다운 파일에 문제가 있습니다.");
        }
    }
}
