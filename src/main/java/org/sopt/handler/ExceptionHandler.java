package org.sopt.handler;

import java.time.format.DateTimeParseException;

public class ExceptionHandler {

    public static void execute(Runnable task) {
        try {
            task.run();
        } catch (DateTimeParseException e) {
            System.out.println("❌ 유효하지 않은 날짜 형식입니다. YYYYMMDD 형식으로 입력해주세요.");
        } catch (NumberFormatException e) {
            System.out.println("❌ 유효하지 않은 형식입니다. 숫자를 입력해주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ 오류가 발생했습니다: " + e.getMessage());
        }
    }
}