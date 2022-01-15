package com.company.Utils;

import com.company.Exceptions.IncorrectInputExceptions;

import java.util.Arrays;

public class CalculatorUtilsImpl implements CalculatorUtils {

    private static final String[] ARABIC_NUM_RANGE = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private static final String[] ROME_NUM_RANGE = new String[]{" ","I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    private static final String[] OPERATOR_RANGE = new String[]{"-", "+", "*", "/"};
    private static final String[][] RomanDischarge = {
            {"I", "X", "C"},
            {"II", "XX", "CC"},
            {"III", "XXX", "CCC"},
            {"IV", "XL", "CD"},
            {"V", "L", "D"},
            {"VI", "LX", "DC"},
            {"VII", "LXX", "DCC"},
            {"VIII", "LXXX", "DCCC"},
            {"IX", "XC", "CM"},
    };

    private static boolean numb_1IsRome = false;
    private static boolean numb_2IsRome = false;
    private static int romeIndex = 0;

    //метод, который рулит все логикой
    public void startCalculate(String number_1, String operator, String number_2) {

        int arabicNumber_1;
        int arabicNumber_2;
        String result;

        validationInput(number_1, operator, number_2);

        if (numb_1IsRome) {
            linearSearch(ROME_NUM_RANGE, number_1, true);
            arabicNumber_1 = Integer.parseInt(ARABIC_NUM_RANGE[romeIndex]);
            linearSearch(ROME_NUM_RANGE, number_2, true);
            arabicNumber_2 = Integer.parseInt(ARABIC_NUM_RANGE[romeIndex]);
        } else {
            arabicNumber_1 = Integer.parseInt(number_1);
            arabicNumber_2 = Integer.parseInt(number_2);
        }

        result = String.valueOf(calculate(arabicNumber_1, operator, arabicNumber_2));

        if (numb_1IsRome) {
            result = transferToArabic(result);
        }
        System.out.println(result);

    }

    // метод поиска елемента в массиве
    private static boolean linearSearch(String[] array, String elementToSearch, boolean calculateRomeIndex) {

        if (calculateRomeIndex) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(elementToSearch)) {
                    romeIndex = i;
                }
            }
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(elementToSearch)) {
                return true;
            }
        }
        return false;
    }

    // метод поиска валидации элемента
    private static boolean validationInput(String number_1, String operator, String number_2) {

        boolean numb_1IsCorrect = false;
        boolean numb_2IsCorrect = false;
        boolean operatorIsCorrect = false;


        if (linearSearch(ARABIC_NUM_RANGE, number_1, false) || linearSearch(ROME_NUM_RANGE, number_1, false)) {
            numb_1IsCorrect = true;
            if (linearSearch(ROME_NUM_RANGE, number_1, false)) {
                numb_1IsRome = true;
            }
        } else {
            System.out.println("Неккоретно введен первый оператор");
            throwExceptions(number_1, operator, number_2);
        }

        if (linearSearch(ARABIC_NUM_RANGE, number_2, false) || linearSearch(ROME_NUM_RANGE, number_2, false)) {
            numb_2IsCorrect = true;

            if (linearSearch(ROME_NUM_RANGE, number_2, false)) {
                numb_2IsRome = true;
            }
        } else {
            System.out.println("Неккоретно введен второй оператор");
            throwExceptions(number_1, operator, number_2);
        }

        if (linearSearch(OPERATOR_RANGE, operator, false)) {
            operatorIsCorrect = true;
        } else {
            System.out.println("Неккоретно введен арифметический знак");
            throwExceptions(number_1, operator, number_2);
        }

        if (numb_1IsCorrect && numb_2IsCorrect && operatorIsCorrect) {

            if ((numb_1IsRome && numb_2IsRome) || (!numb_1IsRome && !numb_2IsRome)) {
                return true;
            } else {
                throwExceptions(number_1, operator, number_2);
            }
        }
        return false;
    }

    // метод выброса исключений
    private static void throwExceptions(String number_1, String operator, String number_2) {
        throw new IncorrectInputExceptions("Некорректные данные, было введено : "
                + number_1 + " " + operator + " " + number_2 + " Допустимый диапазон значений "
                + Arrays.toString(ARABIC_NUM_RANGE) + Arrays.toString(ROME_NUM_RANGE) + Arrays.toString(OPERATOR_RANGE));
    }

    //вычисление
    private static int calculate(int number_1, String operator, int number_2) {


        switch (operator) {
            case ("+"):
                return number_1 + number_2;
            case ("-"):
                return number_1 - number_2;
            case ("*"):
                return number_1 * number_2;
            case ("/"):
                return number_1 / number_2;
        }

        return 0;
    }

    //преобразование из римских в арабские
    private static String transferToArabic(String result) {
        String CharSearch_2;
        int indexCalcDozens_2;
        String part_1_2;
        String CharSearch;
        int indexCalcDozens;
        int indexCalcUnits;

        switch (Integer.parseInt(result)) {
            case 100 -> {
                return RomanDischarge[0][2];
            }
            case 90, 80, 70, 60, 50, 40, 30, 20, 10 -> {
                CharSearch_2 = Integer.toString(Integer.parseInt(result));
                indexCalcDozens_2 = Integer.parseInt(String.valueOf(CharSearch_2.charAt(0)));
                return part_1_2 = RomanDischarge[indexCalcDozens_2 - 1][1];
            }
            case 9, 8, 7, 6, 5, 4, 3, 2, 1 -> {
                CharSearch_2 = Integer.toString(Integer.parseInt(result));
                indexCalcDozens_2 = Integer.parseInt(String.valueOf(CharSearch_2.charAt(0)));
                return part_1_2 = RomanDischarge[indexCalcDozens_2 - 1][0];
            }
            case 0 -> {
                System.out.println("Пишу арабский 0, но у Римлян его существовало");

            }
            default -> {
                CharSearch = Integer.toString(Integer.parseInt(result));
                indexCalcDozens = Integer.parseInt(String.valueOf(CharSearch.charAt(0)));
                indexCalcUnits = Integer.parseInt(String.valueOf(CharSearch.charAt(1)));
                String part_1 = RomanDischarge[indexCalcDozens - 1][1];
                String part_2 = RomanDischarge[indexCalcUnits - 1][0];
                return part_1 + part_2;
            }
        }
        return "";
    }
}