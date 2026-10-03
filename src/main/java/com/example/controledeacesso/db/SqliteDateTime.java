package com.example.controledeacesso.db;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/** Conversao entre LocalDateTime e o formato de data/hora usado pelo SQLite ("YYYY-MM-DD HH:MM:SS"). */
public final class SqliteDateTime {

    private static final DateTimeFormatter WRITE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter READ = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm")
            .optionalStart().appendPattern(":ss").optionalEnd()
            .optionalStart().appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).optionalEnd()
            .toFormatter();

    private SqliteDateTime() {
    }

    public static String format(LocalDateTime value) {
        return value == null ? null : value.format(WRITE);
    }

    public static LocalDateTime parse(Object value) {
        if (value == null) {
            return null;
        }
        String text = value.toString().trim().replace('T', ' ');
        if (text.endsWith("Z")) {
            text = text.substring(0, text.length() - 1);
        }
        return LocalDateTime.parse(text, READ);
    }
}
