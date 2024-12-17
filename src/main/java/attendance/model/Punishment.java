package attendance.model;

import java.util.Arrays;

public enum Punishment {
    WEEDING("제적", 5),
    INTERVIEW("면담", 2),
    WARNING("경고", 1),
    NONE("없음", -1);


    private final String punishmentName;
    private final int absenceCount;

    Punishment(final String punishmentName, final int absenceCount) {
        this.punishmentName = punishmentName;
        this.absenceCount = absenceCount;
    }

    public static Punishment findByPunishMent(int absenceCount) {
        return Arrays.stream(Punishment.values())
                .filter(value -> value.getAbsenceCount() < absenceCount)
                .findFirst()
                .orElse(null);
    }

    public String getPunishmentName() {
        return punishmentName;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}

