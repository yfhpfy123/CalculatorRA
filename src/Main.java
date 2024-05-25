import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws Exception {

        System.out.print("Введите выражение римских или арабских чисел [1 + 10][I + X] от 1 до 10: ");
        Scanner scan = new Scanner(System.in); // создаем объект класса Scanner
        char quit = 'q';

        String in = scan.nextLine();
        if (in.equalsIgnoreCase("")){ //если введена пустая строка, пробрасываем исключение
            throw new Exception();
        } else {
            while (in.charAt(0) != quit) { //цикл для бесконечной работы программы

                System.out.println(Calculator.calc(in));
                System.out.print("Введите выражение римских или арабских чисел [1 + 10][I + X] от 1 до 10: ");
                in = scan.nextLine();

            }
            System.out.println("Завершение работы");
            System.exit(0); //команда выхода из программы
        }
    }
}

class Calculator {

    public static String calc(String input) throws Exception { //создаем метод calc, в котором произведем все преобразования, вычисления и выполним отбор
        String result = "";
        char op = 0;
        input = input.toUpperCase().replaceAll(" ", ""); //убираем из строки лишнее


        String inp = input;
        if (isNegative(input)) { //проверяем на отрицательное число
            inp = input.substring(1); //если отрицательное, берем модуль числа, чтобы метод split нормально разделил строку

        }



        for (int i = 0; i < input.length(); i++) { //ловим оператор вычисления

            switch (input.charAt(i)) {
                case '+':
                    op = '+';
                    break;
                case '-':
                    op = '-';
                    break;
                case '*':
                    op = '*';
                    break;
                case '/':
                    op = '/';
                    break;
                default:
                    op = op;
                    break;
            }
        }

        if (op == ' ') { //если оператор не найден или другой оператор, пробрасываем исключение
            throw new Exception();
        } else {
            String[] arrayVal = inp.split("[-+/*]"); //делим строку по оператору

            if (arrayVal.length > 2) { //если в выражении больше 2-х чисел, пробрпсываем исключение
                throw new Exception();
            }
            String val1;
            String val2;
            try{
                val1 = arrayVal[0];
                val2 = arrayVal[1];
            } catch (ArrayIndexOutOfBoundsException a){ //если введен один символ, пробрасываем исключение
                throw new Exception();
            }

            if (isRoman(val1, val2) & !isNegative(input)) { //если число римское и не отрицательное

                ToArab num1 = ToArab.valueOf(val1); //переводим из римских в арабские
                ToArab num2 = ToArab.valueOf(val2);
                val1 = num1.getArab();
                val2 = num2.getArab();

                int number1 = 0;
                int number2 = 0;

                try {
                    number1 = Integer.parseInt(val1); //переводим из строки в число
                    number2 = Integer.parseInt(val2);
                } catch (NumberFormatException n) { //если число выходит за пределы от 1 до 10, выбрасываем исключение
                    throw new Exception();
                }

                String resultArab = calculate(number1, number2, op); //получаем результат арабскими
                resultArab = "_" + resultArab;
                try {
                    result = ToRoman.valueOf(resultArab).getRoman(); //преводим в римские, если число отрицательное, пробрасываем исключение
                }catch (IllegalArgumentException i){
                    throw new Exception();
                }
            } else if (!isRoman(val1, val2) & !isNegative(input)) { //если числа арабские и не отрицательные

                int number1 = 0;
                int number2 = 0;
                try {
                    number1 = Integer.parseInt(val1); //переводим из строки в число
                    number2 = Integer.parseInt(val2);
                } catch (NumberFormatException n) {
                    throw new Exception();
                }
                if (number1 > 10 | number2 > 10) { // если число выходит за пределы допустимых значений
                    throw new Exception();         // выбрасываем исключение
                } else {
                    result = calculate(number1, number2, op); //получаем результат для положительных арабских чисел
                }
            } else if (!isRoman(val1, val2) & isNegative(input)) { //если числа арабские и первое отрицательное
                int number1 = 0;
                int number2 = 0;
                try {
                    number1 = Integer.parseInt(val1); //переводим из строки в число
                    number2 = Integer.parseInt(val2);
                } catch (NumberFormatException n) {
                    throw new Exception();
                }
                if (number1 > 10 | number2 > 10) { // если числа выходят за пределы допустимых значений, пробрасываем исключение
                    throw new Exception();
                } else {

                    result = calculate(-number1, number2, op); //приводим первое число к отрицательному значению и приводим результат
                }
            } else { // если первое римское число отрицательное, пробрасываем исключение
                throw new Exception();
            }
        }

        return result; // метод возвращает строку с результатом
    }


    static String calculate (int value1, int value2, char operation) throws Exception { //метод для счета
        String res = "";
        if (value2 != 0) {
            int res1 = 0;
            switch (operation) {
                case '+':
                    res1 = value1 + value2;
                    res = Integer.toString(res1);
                    break;
                case '-':
                    res1 = value1 - value2;
                    res = Integer.toString(res1);
                    break;
                case '*':
                    res1 = value1 * value2;
                    res = Integer.toString(res1);
                    break;
                case '/':
                    res1 = value1 / value2;
                    res = Integer.toString(res1);
                    break;
                default:
                    res = res;
                    break;
            }
        } else {
//            throw new Exception("На ноль делить нельзя!");
            res = "На ноль делить нельзя!";
        }
        return res;
    }

    static boolean isNegative (String input){ //метод для определения отрицательного значения в первом числе
        if (input.substring(0,1).equalsIgnoreCase("-")){
            return true;
        } else {
            return false;
        }
    }

    static boolean isRoman(String value1, String value2){ // метод для определения римских чисел
        try {
            ToArab num1 = ToArab.valueOf(value1);
            ToArab num2 = ToArab.valueOf(value2);
            return true;
        } catch (IllegalArgumentException i){
            return false;
        }
    }
}

enum ToArab { //перечисление римских чисел для перевода в арабские
    I("1"), II("2"), III ("3"), IV ("4"), V("5"),
    VI("6"), VII("7"), VIII("8"), IX("9"), X("10");

    final String translate;

    ToArab (String translate) {    //конструктор перечисления
        this.translate = translate;
    }

    String getArab (){ //гетер перевода
        return translate;
    }

}

enum ToRoman { //перечисление арабских чисел для перевода в римские
    _1("I"), _2("II"), _3 ("III"), _4 ("IV"), _5("V"),
    _6("VI"), _7("VII"), _8("VIII"), _9("IX"), _10("X"),
    _11("XI"), _12("XII"), _13("XIII"), _14("XIV"), _15("XV"),
    _16("16"), _17("XVII"), _18("XVIII"), _19("XIX"), _20("XX"),
    _21("XXI"), _24("XXIV"), _25("XXV"), _27("XXVII"), _28("XXVIII"),
    _30("XXX"), _32("XXXII"), _36("XXXIV"), _40("XL"), _42("XLII"),
    _45("XLV"), _48("XLVIII"), _49("XLIX"), _50("L"), _54("LIV"),
    _56("LVI"), _60("LX"), _63("LXIII"), _64("LXIV"), _70("LXX"),
    _72("LXXII"), _80("LXXX"), _81("LXXXI"), _90("XC"), _100("C");

    final String translate;

    ToRoman (String translate){     // конструктор перечисления
        this.translate = translate;
    }

    String getRoman (){ //геттер для перевода из арабских в римские
        return translate;
    }
}