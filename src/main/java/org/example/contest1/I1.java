package org.example.contest1;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class I1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberHolidays = scanner.nextInt();
        int year = scanner.nextInt();
        scanner.nextLine();
        String[] holidaysArray = new String[numberHolidays];

        for (int i = 0; i < numberHolidays; i++) {
            String s = scanner.nextLine();
            String[] day = s.split(" ");
            int number = Integer.parseInt(day[0]);
            String monthName = day[1];
            Month month = Month.valueOf(monthName.toUpperCase());
            LocalDate date = LocalDate.of(year, month, number);

            holidaysArray[i] = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        }

        String yearStartedWith = scanner.nextLine();
        Map<String, Integer> numberOfDaysOfWeeks = getStringIntegerMap(year, yearStartedWith, holidaysArray);

        Map.Entry<String, Integer> minEntry = null;
        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : numberOfDaysOfWeeks.entrySet()) {
            if (minEntry == null || entry.getValue() < minEntry.getValue()) {
                minEntry = entry;
            }
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }

        System.out.println(maxEntry.getKey() + " " + minEntry.getKey());
    }

    private static Map<String, Integer> getStringIntegerMap(int year, String yearStartedWith, String[] holidaysArray) {
        int numberOfDays = isLeapYear(year) ? 366 : 365;
        Map<String, Integer> numberOfDaysOfWeeks = new HashMap<>();

        String[] daysOfWeek = createDaysOfWeekStartingFrom(yearStartedWith);
        int count = 0;
        while (numberOfDays != count) {
            for (String day : daysOfWeek) {
                numberOfDaysOfWeeks.put(day, numberOfDaysOfWeeks.getOrDefault(day, 0) + 1);
                count++;
                if (count == numberOfDays)
                    break;
            }
        }

        for (int i = 0; i < holidaysArray.length; i++) {
            int finalI = i;
            numberOfDaysOfWeeks.forEach((key, value) -> {
                if (!holidaysArray[finalI].equals(key)) {
                    numberOfDaysOfWeeks.put(key, numberOfDaysOfWeeks.get(key) + 1);
                }
            });
        }
        return numberOfDaysOfWeeks;
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static String[] createDaysOfWeekStartingFrom(String startDay) {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        int startIndex = 0;

        for (int i = 0; i < daysOfWeek.length; i++) {
            if (daysOfWeek[i].equalsIgnoreCase(startDay)) {
                startIndex = i;
                break;
            }
        }

        String[] rotatedArray = new String[daysOfWeek.length];
        for (int i = 0; i < daysOfWeek.length; i++) {
            rotatedArray[i] = daysOfWeek[(startIndex + i) % daysOfWeek.length];
        }

        return rotatedArray;
    }

}
