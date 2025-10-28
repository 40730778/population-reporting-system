package com.population.util;

import java.util.List;

public class ReportPrinter {
    public static void printRow(String... cols) {
        StringBuilder sb = new StringBuilder();
        for (String c : cols) sb.append(String.format("%-25s", c == null ? "" : c));
        System.out.println(sb);
    }

    public static void printSeparator(int cols) {
        System.out.println("-".repeat(cols * 25));
    }

    public static void printTable(List<String[]> rows, String... header) {
        printSeparator(header.length);
        printRow(header);
        printSeparator(header.length);
        for (String[] r : rows) printRow(r);
        printSeparator(header.length);
    }
}
