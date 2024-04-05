package org.example.contest4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class E4 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
             FileWriter writer = new FileWriter("output.txt")) {

            BigDecimal n = BigDecimal.valueOf(Long.parseLong(reader.readLine()));
            BigDecimal[] fraction = findNthFraction(n);
            writer.write(fraction[0] + "/" + fraction[1]);
            writer.flush();
        }
    }

    private static BigDecimal[] findNthFraction(BigDecimal n) {
        BigDecimal low = BigDecimal.ONE, high = n;
        BigDecimal sum;
        while (low.compareTo(high) < 0) {
            BigDecimal mid = low.add(high.subtract(low).divide(BigDecimal.TWO, 0, RoundingMode.FLOOR));
            BigDecimal temp = mid.multiply(mid.add(BigDecimal.ONE)).divide(BigDecimal.TWO, 0, RoundingMode.FLOOR);
            if (temp.compareTo(n) < 0) {
                low = mid.add(BigDecimal.ONE);
            } else {
                high = mid;
            }
        }
        sum = high.multiply(high.add(BigDecimal.ONE)).divide(BigDecimal.TWO, 0, RoundingMode.FLOOR);
        return getFractionAt(high, sum, n);
    }

    private static BigDecimal[] getFractionAt(BigDecimal diagonal, BigDecimal sum, BigDecimal n) {
        BigDecimal l = n.subtract(sum.subtract(diagonal).add(BigDecimal.ONE));
        if (diagonal.remainder(BigDecimal.TWO).compareTo(BigDecimal.ZERO) == 0) {
            return new BigDecimal[] {diagonal.subtract(l), BigDecimal.ONE.add(l)};
        } else {
            return new BigDecimal[] {BigDecimal.ONE.add(l), diagonal.subtract(l)};
        }
    }
}
