package attendance.utils;

import java.util.function.Supplier;

public final class LoopTemplate {

    private LoopTemplate() {
    }

    public static <T> T tryCatch(final Supplier<T> callBack) {
        while (true) {
            try {
                return callBack.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
