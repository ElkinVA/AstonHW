package org.example;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class PuttingIntoPractice {

    public static void main(String... args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );

        printHeader("Найти все транзакции за 2011 год и отсортировать их по сумме");
        transactions.stream()
            .filter(x -> x.getYear() == 2011)
            .sorted(Comparator.comparingInt(Transaction::getValue))
            .forEach(System.out::println);

        printHeader("Вывести список\033[93m неповторяющихся городов\033[92m" +
                    " в которых работают трейдеры.");
        transactions.stream()
            .map(Transaction::getTrader)
            .distinct()
            .map(Trader::getCity)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .filter(x -> x.getValue() == 1)
            .map(Map.Entry::getKey)
            .forEach(System.out::println);

        printHeader("Найти всех трейдеров из Кембриджа и отсортировать их по именам.");
        transactions.stream()
            .map(Transaction::getTrader)
            .distinct()
            .filter(x -> x.getCity().equals("Cambridge"))
            .sorted(Comparator.comparing(Trader::getName))
            .forEach(System.out::println);

        printHeader("Вернуть\033[93m строку\033[92m со всеми именами трейдеров, отсортированными в алфавитном " +
                    "порядке.");
        transactions.stream()
            .map(Transaction::getTrader)
            .sorted(Comparator.comparing(Trader::getName))
            .map(Trader::getName)
            .distinct()
            .forEach(x -> System.out.print(x + " "));
        System.out.println();

        printHeader("Выяснить, существует ли хоть один трейдер из Милана.");
        transactions.stream()
            .map(x -> x.getTrader().getCity())
            .distinct()
            .filter(x -> x.equals("Milan"))
            .map(x -> {
                x = (x.equals("Milan"))
                     ?("Есть как минимум один трейдер из Милана")
                     :("Трейдеров из Милана нет");
                return x;
            })
            .forEach(System.out::println);

        printHeader("Вывести суммы всех транзакций трейдеров из Кембриджа.");
        transactions.stream()
            .filter(x -> x.getTrader().getCity().equals("Cambridge"))
            .map(Transaction::getValue)
                .forEach(System.out::println);

        printHeader("Какова максимальная сумма среди всех транзакций?");
        Integer max = transactions.stream()
            .map(Transaction::getValue)
            .max(Comparator.naturalOrder())
            .get();
        System.out.println(max);

        printHeader("Найти транзакцию с минимальной суммой.");
        Transaction minVolTransact = transactions.stream()
            .min(Comparator.comparing(Transaction::getValue))
            .get();
        System.out.println(minVolTransact);
    }
    private static void printHeader(String header) {
        System.out.println("__________________________\n" +
                           "\033[92m" + header + " \033[39m");
    }
}