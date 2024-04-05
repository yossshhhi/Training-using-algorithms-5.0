package org.example.contest1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Scanner;

public class H1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Locale.setDefault(Locale.US);

        int l = scanner.nextInt();
        int x1 = scanner.nextInt();
        int v1 = scanner.nextInt();
        int x2 = scanner.nextInt();
        int v2 = scanner.nextInt();
        scanner.close();
        BigDecimal t1;
        BigDecimal t2 = BigDecimal.ZERO;
        BigDecimal t3 = BigDecimal.ZERO;
        BigDecimal v;


        if ((v1 == 0 && v2 == 0) || x1 == x2) {
            if (x1 == x2 || (x1 > x2 ? l - x1 == x2 : l - x2 == x1)) {
                System.out.print("YES\n");
                System.out.print("0.0000000000");
                return;
            }
            System.out.println("NO");
            return;
        } else if (v1 == 0) {
            t1 = BigDecimal.valueOf((double) (l - x1 - x2) / v2);
        } else if (v2 == 0) {
            if (x2 > x1)
                t1 = BigDecimal.valueOf((double) (x2 - x1) / v1);
            else
                t1 = BigDecimal.valueOf((double) (l - x2 - x1) / v1);
        } else if (v1 == v2) {
            if (v1 > 0) {
                if (x1 > x2) {
                    t1 = BigDecimal.valueOf((double) (x1 - x2) / (v1 + v2));
                    t2 = BigDecimal.valueOf((double) (l - x1 - x2) / (v1 + v2));
                } else
                    t1 = BigDecimal.valueOf((double) (l - x1 - x2) / (v1 + v2));
            } else {
                t1 = BigDecimal.valueOf((double) x1).add(BigDecimal.valueOf((double) (x2 - x1) / 2)).divide(BigDecimal.valueOf(Math.abs(v1)), 10, RoundingMode.HALF_UP);
            }
        } else if (v1 == -v2) {
            if (v2 < 0) {
                t1 = BigDecimal.valueOf((double) l - x1 + x2).divide(BigDecimal.valueOf((double) v1 * 2), 10, RoundingMode.HALF_UP);
            } else {
                t1 = BigDecimal.valueOf((double) l - x2 + x1).divide(BigDecimal.valueOf((double) v2 * 2), 10, RoundingMode.HALF_UP);
            }
        } else {
            if (v1 > 0 && v2 > 0) {
                v = BigDecimal.valueOf(Math.max(v2, v1) - Math.min(v2, v1));
                if (v1 > v2 && x1 > x2)
                    t3 =  BigDecimal.valueOf(- x2 - x1 + 2L * l).divide( BigDecimal.valueOf(v1 + v2), 10, RoundingMode.HALF_UP);
            } else if (v1 < 0 && v2 < 0) {
                v = BigDecimal.valueOf(Math.max(Math.abs(v2), Math.abs(v1)) - Math.min(Math.abs(v2), Math.abs(v1)));
            } else {
                v = BigDecimal.valueOf(Math.abs(v2) + Math.abs(v1));
            }
            t1 = BigDecimal.valueOf(x2 - x1).divide(BigDecimal.valueOf(v1 - v2), 10, RoundingMode.HALF_UP);
            t2 = BigDecimal.valueOf(-x2 - x1).divide(BigDecimal.valueOf(v1 + v2), 10, RoundingMode.DOWN);
            BigDecimal divide = BigDecimal.valueOf(l).divide(v, 10, RoundingMode.HALF_UP);
            if (t1.compareTo(BigDecimal.ZERO) < 0) {
                t1 = t1.add(divide);
            }
            if (t2.compareTo(BigDecimal.ZERO) < 0) {
                t2 = t2.add(divide);
            }
        }

        if (t3.compareTo(BigDecimal.ZERO) > 0) {
            System.out.print("YES\n");
            System.out.printf("%.10f", t3.doubleValue());
        } else if (t1.compareTo(BigDecimal.ZERO) > 0 && t2.compareTo(BigDecimal.ZERO) > 0) {
            System.out.print("YES\n");
            System.out.printf("%.10f", Math.min(t1.doubleValue(), t2.doubleValue()));
        } else if (t1.compareTo(BigDecimal.ZERO) > 0) {
            System.out.print("YES\n");
            System.out.printf("%.10f", t1);
        } else {
            System.out.println("NO");
        }
    }
}
