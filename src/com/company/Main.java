package com.company;

import com.company.Utils.CalculatorUtils;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Введите уравнение :");
        Scanner scanner = new Scanner(System.in);

        String number_1 = scanner.next();
        String operator = scanner.next();
        String number_2 = scanner.next();

        CalculatorUtils.startCalculate(number_1,operator,number_2);
    }
}
