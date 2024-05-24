import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IllegalArgumentException {

        String val1;
        String val2;
        String resultArab;
        String resultRoman;
        char op = ' ';

        System.out.print ("Введите выражение римских или арабских чисел [1 + 10][I + X] от 1 до 10: ");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        input = input.toUpperCase().replaceAll(" ", "");

        String inp = input;
        if (isNegative(input)){
            inp = input.substring(1);
        }

        for (int i = 0; i < input.length(); i++){
            op = switch (input.charAt(i)) {
                case '+' -> '+';
                case '-' -> '-';
                case '*' -> '*';
                case '/' -> '/';
                default -> op;
            };
        }
        if (op == ' '){
            System.out.println("Введен неверный знак!");
        }else {

            String[] arrayVal = inp.split("[-+/*]");
            if (arrayVal.length > 2){
                System.out.println("Вы ввели больше двух чисел!");
            }else {

                val1 = arrayVal[0];
                val2 = arrayVal[1];

                if (isRoman(val1, val2) & !isNegative(input)) {

                    ToArab num1 = ToArab.valueOf(val1);
                    ToArab num2 = ToArab.valueOf(val2);
                    val1 = num1.getArab();
                    val2 = num2.getArab();

                    int number1 = 0;
                    int number2 = 0;

                    try {
                        number1 = Integer.parseInt(val1);
                        number2 = Integer.parseInt(val2);
                    } catch (NumberFormatException n) {
                        System.out.println("Неверное выражение!");
                        return;
                    }

                    resultArab = calc(number1, number2, op);
                    resultArab = "_" + resultArab;
                    resultRoman = ToRoman.valueOf(resultArab).getRoman();
                    System.out.println(resultRoman);

                } else if (!isRoman(val1, val2) & !isNegative(input)) {

                    int number1 = 0;
                    int number2 = 0;
                    try {
                        number1 = Integer.parseInt(val1);
                        number2 = Integer.parseInt(val2);
                    } catch (NumberFormatException n) {
                        System.out.println("Неверное выражение!");
                        return;
                    }
                    if (number1 > 10 | number2 > 10){
                        System.out.println("Неверное выражение!");
                    } else {

                        resultArab = calc(number1, number2, op);
                        System.out.println(resultArab);
                    }
                } else if (!isRoman(val1,val2) & isNegative(input)) {
                    int number1 = 0;
                    int number2 = 0;
                    try {
                        number1 = Integer.parseInt(val1);
                        number2 = Integer.parseInt(val2);
                    } catch (NumberFormatException n) {
                        System.out.println("Неверное выражение!");
                        return;
                    }
                    if (number1 > 10 | number2 > 10){
                        System.out.println("Неверное выражение!");
                    } else {

                        resultArab = calc(-number1, number2, op);
                        System.out.println(resultArab);
                    }

                } else {
                    System.out.println("В римских числах отрицательных не существует");
                }
            }
        }
    }
    static boolean isNegative (String input){
        if (input.substring(0,1).equalsIgnoreCase("-")){
            return true;
        } else {
            return false;
        }
    }

    static boolean isRoman(String value1, String value2){
        try {
            ToArab num1 = ToArab.valueOf(value1);
            ToArab num2 = ToArab.valueOf(value2);
            return true;
        } catch (IllegalArgumentException i){
            return false;
        }
    }

    static String calc (int value1, int value2, char operation) {
        String res = "";
        if (value2 != 0) {
            int res1 = 0;
            res = switch (operation) {
                case '+' -> {
                    res1 = value1 + value2;
                    yield Integer.toString(res1);
                }
                case '-' -> {
                    res1 = value1 - value2;
                    yield Integer.toString(res1);
                }
                case '*' -> {
                    res1 = value1 * value2;
                    yield Integer.toString(res1);
                }
                case '/' -> {
                    res1 = value1 / value2;
                    yield Integer.toString(res1);
                }
                default -> res;
            };
        } else {
            res = "На ноль днлить нельзя!";
        }
        return res;
    }
}

enum ToArab {
    I("1"), II("2"), III ("3"), IV ("4"), V("5"),
    VI("6"), VII("7"), VIII("8"), IX("9"), X("10");

    final String translate;

    ToArab (String translate){
        this.translate = translate;
    }

    String getArab (){
        return translate;
    }

}

enum ToRoman {
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

    ToRoman (String translate){
        this.translate = translate;
    }

    String getRoman (){
        return translate;
    }
}