package org.example.contest2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class J2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] size = reader.readLine().split(" ");
        int rows = Integer.parseInt(size[0]);
        int columns = Integer.parseInt(size[1]);
        List<String> squares = new ArrayList<>();

        List<SquareRow> a = new ArrayList<>();
        List<Integer> failA = new ArrayList<>();
        List<SquareRow> b = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            String row = reader.readLine();
            int start = row.indexOf("#");
            int end = row.lastIndexOf("#") + 1;
            StringBuilder sb = new StringBuilder(row);
            int length = end - start;

            SquareRow lastA = a.isEmpty() ? null : a.getLast();
            SquareRow lastB = b.isEmpty() ? null : b.getLast();
            if (start >= 0 && length > 0 && row.substring(start, end).equals("#".repeat(length))) {
                if (!squares.isEmpty() && squares.getLast().equals(".".repeat(columns))) {
                    if (!a.isEmpty() && !b.isEmpty()) {
                        System.out.print("NO");
                        return;
                    } else if (b.isEmpty() && a.size() > 1) {
                        squares.add(sb.replace(start, end, "b".repeat(length)).toString());
                        b.add(new SquareRow(length, start, end, i));
                        continue;
                    }
                }
                if (!a.isEmpty() && lastA != null && length != lastA.getLength()) {
                    if (i - 1 == lastA.getRow() && start != lastA.getStart() && length > lastA.getLength()) {
                        if (end != lastA.getEnd()) {
                            if (b.isEmpty()) {
                                squares.add(sb.replace(start, end, "b".repeat(length)).toString());
                                b.add(new SquareRow(length, start, end, i));
                                continue;
                            } else {
                                System.out.print("NO");
                                return;
                            }
                        }
                        if (!b.isEmpty() && lastB != null && lastB.getLength() + lastA.getLength() != length) {
                            System.out.print("NO");
                            return;
                        }
                        if (end - start < lastA.getLength()) {
                            squares.add(sb.replace(start, end, "b".repeat(length)).toString());
                            b.add(new SquareRow(length, start, end, i));
                            continue;
                        }

                        sb.replace(start, lastA.getStart(), "b".repeat(lastA.getStart() - start));
                        squares.add(sb.replace(lastA.getStart(), end, "a".repeat(end - lastA.getStart())).toString());
                        b.add(new SquareRow(lastA.getStart() - 1 - start, start, lastA.getStart() - 1, i));
                        a.add(new SquareRow(end - lastA.getStart(), lastA.getStart(), end, i));
                    } else if (i - 1 == lastA.getRow() && end != lastA.getEnd() && length > lastA.getLength()) {
                        if (start != lastA.getStart()) {
                            System.out.print("NO");
                            return;
                        }
                        if (!b.isEmpty() && lastB != null && lastB.getLength() + lastA.getLength() != length) {
                            System.out.print("NO");
                            return;
                        }
                        if (end - start < lastA.getLength()) {
                            squares.add(sb.replace(start, end, "b".repeat(length)).toString());
                            b.add(new SquareRow(length, start, end, i));
                            continue;
                        }
                        sb.replace(start, lastA.getEnd(), "a".repeat(lastA.getEnd() - start));
                        squares.add(sb.replace(lastA.getEnd(), end, "b".repeat(end - lastA.getEnd())).toString());
                        a.add(new SquareRow(lastA.getEnd() - start, start, lastA.getEnd(), i));
                        b.add(new SquareRow(end - lastA.getEnd(), lastA.getEnd(), end, i));
                    } else {
                        if (b.isEmpty() || (lastB != null && start == lastB.getStart() && end - start == lastB.getLength())) {
                            squares.add(sb.replace(start, end, "b".repeat(length)).toString());
                            b.add(new SquareRow(length, start, end, i));
                        } else {
                            System.out.print("NO");
                            return;
                        }
                    }
                } else {
                    if (!a.isEmpty() && ((b.isEmpty() && lastA != null && lastA.getStart() != start) || (lastB != null && !b.isEmpty() && lastB.getStart() == start))) {
                        squares.add(sb.replace(start, end, "b".repeat(length)).toString());
                        b.add(new SquareRow(length, start, end, i));
                    } else if (a.isEmpty() || (lastA != null && start == lastA.getStart())) {
                        squares.add(sb.replace(start, end, "a".repeat(length)).toString());
                        a.add(new SquareRow(length, start, end, i));
                    } else {
                        System.out.print("NO");
                        return;
                    }
                }
            } else if (start >= 0 && length > 0) {
                if (!a.isEmpty() && !b.isEmpty() && squares.getLast().equals(".".repeat(columns))) {
                    System.out.print("NO");
                    return;
                }
                int startA = start;
                while (row.charAt(startA) == '#') {
                    sb.setCharAt(startA, 'a');
                    startA++;
                }
                a.add(new SquareRow(startA - start, start, startA, i));
                int startB = row.substring(startA, end).indexOf("#") + startA;
                if (row.substring(startB, end).equals("#".repeat(end - startB))) {
                    squares.add(sb.replace(startB, end, "b".repeat(end - startB)).toString());
                    lastB = new SquareRow(end - startB, startB, end, i);
                    b.add(lastB);
                } else {
                    System.out.print("NO");
                    return;
                }
                if (!a.isEmpty() && lastA != null && start != lastA.getStart() && b.size() == 1) {
                    failA.add(i);
                } else if (!a.isEmpty() && !b.isEmpty() && lastA != null && (start != lastA.getStart() || startA - start != lastA.getLength()
                        || startB != lastB.getStart() || end - startB != lastB.getLength())) {
                    System.out.print("NO");
                    return;
                }
            } else {
                squares.add(row);
            }
        }

        if (!failA.isEmpty()) {
            for (int i = 0; i < failA.getFirst(); i++) {
                String row = squares.get(i);
                int start = row.indexOf("a");
                int end = row.lastIndexOf("a") + 1;
                StringBuilder sb = new StringBuilder(row);
                if (start != -1 && end - start > 0) {
                    squares.set(i, sb.replace(start, end, "b".repeat(end - start)).toString());
                }
            }
        }

        if (!a.isEmpty() && b.isEmpty()) {
            SquareRow last = a.getLast();
            SquareRow first = a.getFirst();
            for (int i = 0; i < squares.size(); i++) {
                if (squares.get(i).equals(".".repeat(columns))) {
                    continue;
                } else if (first.getLength() > 1) {
                    StringBuilder sb = new StringBuilder(squares.get(i));
                    sb.setCharAt(last.getEnd() - 1, 'b');
                    squares.set(i, sb.toString());
                    b.add(new SquareRow());
                } else if (last.getRow() - first.getRow() + 1 > 1) {
                    StringBuilder sb = new StringBuilder(squares.get(last.getRow()));
                    sb.setCharAt(last.getStart(), 'b');
                    squares.set(last.getRow(), sb.toString());
                    b.add(new SquareRow());
                    break;
                } else {
                    System.out.print("NO");
                    return;
                }
            }
        }

        if (a.isEmpty() || b.isEmpty()) {
            System.out.print("NO");
            return;
        }

        System.out.print("YES\n");
        for (String square : squares) {
            System.out.print(square + "\n");
        }
    }
}

class SquareRow {
    int length;
    int start;
    int end;
    int row;

    public SquareRow() {
    }

    public SquareRow(int length, int start, int end, int row) {
        this.length = length;
        this.start = start;
        this.end = end;
        this.row = row;
    }
    public int getLength() {
        return length;
    }
    public int getStart() {
        return start;
    }
    public int getEnd() {
        return end;
    }
    public int getRow() {
        return row;
    }
}
