import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String MULTIPLY = "*";
    private static final String DIV = "/";


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        System.out.println("\"" + input(text) + "\"");
    }

    public static String[] parseExpression(String input) {
        if (input.contains(" + ")) {
            String[] operators = input.split(Pattern.quote(" + "));
            return new String[]{operators[0], PLUS, operators[1]};
        }
        if (input.contains(" - ")) {
            String[] operators = input.split(Pattern.quote(" - "));
            return new String[]{operators[0], MINUS, operators[1]};
        }
        if (input.contains(" * ")) {
            String[] operators = input.split(Pattern.quote(" * "));
            return new String[]{operators[0], MULTIPLY, operators[1]};
        }
        if (input.contains(" / ")) {
            String[] operators = input.split(Pattern.quote(" / "));
            return new String[]{operators[0], DIV, operators[1]};
        }
        throw new IllegalArgumentException("Неверный оператор");
    }

    public static String input(String input) {
        String[] expression = parseExpression(input);

        validate(expression);

        expression[0] = deleteQuotes(expression[0]);
        expression[2] = deleteQuotes(expression[2]);


        boolean isDigit = Character.isDigit(expression[2].charAt(0));

        int num = 0;
        if (isDigit) {
            num = Integer.parseInt(expression[2]);
        }

        StringBuilder result = new StringBuilder();
        switch (expression[1]) {
            case "*":
                result.append(multi(expression[0], num));
                break;
            case "+":
                result.append(expression[0] + expression[2]);
                break;
            case "-":
                result.append(minus(expression[0], expression[2]));
                break;
            case "/":
                result.append(div(expression[0], num));
        }
        if (result.length() > 40) {
            result = shorting(result);
        }
        return result.toString();
    }

    static String multi(String a, int num) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < num; i++) {
            result.append(a);
        }
        return result.toString();
    }

    static String minus(String a, String b) {
        StringBuilder sc = new StringBuilder();
        if (a.contains(b)) {
            sc.append(a.replace(b, ""));
        } else {
            sc.append(a);
        }
        return sc.toString();
    }

    static String div(String a, int num) {
        StringBuilder sc = new StringBuilder();
        int div = a.length() / num;
        sc.append(a.substring(0, div));
        return sc.toString();
    }

    static StringBuilder shorting(StringBuilder result) {
        return new StringBuilder(result.substring(0, 40)).append("...");
    }

    static String deleteQuotes(String text) {
        return text.replaceAll("\"", "");
    }

    private static void validate(String[] expression) {
        if (expression[0].length() > 12 || expression[2].length() > 12) {
            throw new IllegalArgumentException("Длина строки не должна превышать 10");
        }

        if (Character.isDigit(expression[0].charAt(0))) {
            throw new IllegalArgumentException("Первом агрументом выражения не может быть число");
        }

        if (Character.isDigit(expression[2].charAt(0))) {
            int number = Integer.parseInt(expression[2]);
            if (number < 1 || number > 10) {
                throw new IllegalArgumentException("Число должно быть от 1 до 10");
            }
        }

        if (PLUS.equals(expression[1]) || MINUS.equals(expression[1])) {
            if (Character.isDigit(expression[2].charAt(0))) {
                throw new IllegalArgumentException("Вторым аргументом выражения не может быть число при сложении и вычитании");
            }
        }

        if (MULTIPLY.equals(expression[1]) || DIV.equals(expression[1])) {
            if (!Character.isDigit(expression[2].charAt(0))) {
                throw new IllegalArgumentException("Вторым аргументом выражения не может быть строка при умножении и делении");
            }
        }
    }
}