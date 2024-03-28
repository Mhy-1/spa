package com.tazerdev.spa;

import ch.obermuhlner.math.big.BigDecimalMath;
import ch.obermuhlner.math.big.DefaultBigDecimalMath;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Evalulate {
    private static final ArrayList<String> parenthesesstr = new ArrayList<>();
    private static final HashMap<Integer, Integer> parenthesesblock = new HashMap<>();
    private static final ArrayList<String> paranthesesFormulas = new ArrayList<>();
    public static boolean radain = true;

    /**
     * Evaluates the math expression
     * @param expression
     * @return
     */
    public static String Eval(String expression) {
        try {
            expression = expression.replace(".e+", " * 10 ^ ");
            expression = evaltrig(expression);
            if(expression.contains("(")){
                ArrayList<Integer> p1 = new ArrayList<>();
                int p = 0;
                for (int i = 0; i < expression.length(); i++) {
                    if(expression.charAt(i) == '('){
                        parenthesesblock.put(i, -1);
                        p1.add(i);
                        p++;
                    }
                    if(expression.charAt(i)==')'){
                        parenthesesblock.put(p1.get(p-1), i);
                        p1.remove(p-1);
                        p--;
                    }
                }
                for (Integer x: parenthesesblock.keySet()) {
                    parenthesesstr.add(expression.substring(x, parenthesesblock.get(x)+1));
                }
                ArrayList<String> parenthesesstrbefore = (ArrayList<String>) parenthesesstr.clone();
                for (int i = 0; i < parenthesesstr.size(); i++) {
                    for (int j = 0; j < parenthesesstr.size(); j++) {
                        if (i != j) {
                            if (parenthesesstr.get(i).contains(parenthesesstr.get(j)) || parenthesesstr.get(i).equals(parenthesesstr.get(j))) {
                                int index;
                                if (paranthesesFormulas.contains(parenthesesstr.get(j))) {
                                    index = paranthesesFormulas.indexOf(parenthesesstr.get(j));
                                } else {
                                    paranthesesFormulas.add(parenthesesstr.get(j));
                                    index = (paranthesesFormulas.size() - 1);
                                }
                                parenthesesstr.set(i, parenthesesstr.get(i).replace(parenthesesstr.get(j), "$" + index));
                            }
                        } else {
                            if (paranthesesFormulas.contains(parenthesesstr.get(j))) {
                                int index = paranthesesFormulas.indexOf(parenthesesstr.get(j));
                                parenthesesstr.set(i, parenthesesstr.get(i).replace(parenthesesstr.get(j), "$" + index));
                            }

                        }
                    }
                    if(parenthesesstr.get(i).contains("(")){
                        paranthesesFormulas.add(parenthesesstr.get(i));
                        int index = (paranthesesFormulas.size() - 1);
                        parenthesesstr.set(i,"$" + index);
                    }
                }
                for (int i = 0; i < parenthesesstr.size(); i++) {
                    expression = expression.replace(parenthesesstrbefore.get(i),parenthesesstr.get(i));
                }
            }

            BigDecimal result = excute(expression);
            String re = String.valueOf(result.setScale(10, RoundingMode.HALF_EVEN).stripTrailingZeros());
            if (re.contains("E")) {
                if(re.contains("E+")){
                    re = re.replace("E+", " * 10 ^ ");

                }else{
                    re = re.replace("E", " * 10 ^ ");
                }
                if(Integer.parseInt(re.substring(re.indexOf("^")+2)) < 10){
                    ArrayList<String> arguments = new ArrayList<>();
                    final String regex = "(?:[0-9-+*/^()|!√x%πE$.]|abs|e\\^x|ln|log|a?(?:sin|cos|tan)h?)+";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(re);
                    while (matcher.find()) {
                        arguments.add(matcher.group(0));
                    }
                    arguments = power(arguments);
                    arguments = multiplication(arguments);
                    re = arguments.get(0);
                }
            }
            if(!re.contains("*")) {
                re = re.contains(".") ? re.replaceAll("0*$", "").replaceAll("\\.$", "") : re;
            }
            return re;
        }
        catch (Exception e){
            if(e.getMessage() == "OverFlow"){
                return "OverFlow";
            }
            return "MATH ERROR";
        }
    }

    /**
     * evaluates trigonometric expressions
     * @param x
     * @return
     */
    private static String evaltrig(String x){
        while(x.contains("sin(") || x.contains("cos(") || x.contains("tan(") || x.contains("cot(") || x.contains("sec(") || x.contains("csc(")){
            final String regex = "\\b\\w+\\(.*?\\)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(x);
            while (matcher.find()) {
                String value = matcher.group(0).substring(4, matcher.group(0).length() - 1);
                if(value.charAt(value.length()-1) == 'π'){
                    if(value.length() == 1){
                        value = String.valueOf(BigDecimalMath.pi(MathContext.DECIMAL128));
                    }else {
                        value = value.substring(0, value.length() - 1);
                        value += "*";
                        value += String.valueOf(BigDecimalMath.pi(MathContext.DECIMAL128));
                    }
                }
                else if(value.charAt(value.length()-1) == 'E'){
                    if(value.length() == 1){
                        value = String.valueOf(Math.E);
                    }else{
                        value = value.substring(0, value.length()-1);
                        value += "*";
                        value += String.valueOf(Math.E);
                    }
                }
                try {
                    BigDecimal number = excute(value);
                    number = number.multiply(BigDecimalMath.pi(MathContext.DECIMAL128).divide(BigDecimal.valueOf(180.0)));
                    if(radain){
                        number = number.multiply(BigDecimal.valueOf(180.0).divide(BigDecimalMath.pi(MathContext.DECIMAL128)));
                    }

                    if(matcher.group(0).startsWith("sin")) {
                        number = BigDecimalMath.sin(number,MathContext.DECIMAL128);
                    }
                    else if(matcher.group(0).startsWith("cos")) {
                        number = BigDecimalMath.cos(number,MathContext.DECIMAL128);
                    }
                    else if(matcher.group(0).startsWith("tan")) {
                        number = BigDecimalMath.tan(number,MathContext.DECIMAL128);
                    }
                    else if(matcher.group(0).startsWith("cot")) {
                        number = BigDecimalMath.cot(number,MathContext.DECIMAL128);
                    }
                    else if(matcher.group(0).startsWith("sec")) {
                        number =  BigDecimal.ONE.divide(BigDecimalMath.sin(number,MathContext.DECIMAL128));
                    }
                    else if(matcher.group(0).startsWith("csc")) {
                        number = BigDecimal.ONE.divide(BigDecimalMath.cos(number,MathContext.DECIMAL128));
                    }
                    BigDecimal num = number.setScale(10,RoundingMode.HALF_EVEN);
                    x = x.replace(matcher.group(0), num.toPlainString());
                } catch (Exception e) {
                    return "Math Error";
                }
            }
        }
        while(x.contains("log(") || x.contains("ln(")){
            final String regex = "\\b\\w+\\(.*?\\)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(x);
            while (matcher.find()) {
                String value = "";
                if(matcher.group(0).startsWith("log")) {
                    value = matcher.group(0).substring(4, matcher.group(0).length() - 1);
                }else{
                    value = matcher.group(0).substring(3, matcher.group(0).length() - 1);
                }
                if(value.charAt(value.length()-1) == 'π'){
                    if(value.length() == 1){
                        value = String.valueOf(BigDecimalMath.pi(MathContext.DECIMAL128));
                    }else {
                        value = value.substring(0, value.length() - 1);
                        value += "*";
                        value += String.valueOf(BigDecimalMath.pi(MathContext.DECIMAL128));
                    }
                }
                else if(value.charAt(value.length()-1) == 'E'){
                    if(value.length() == 1){
                        value = String.valueOf(Math.E);
                    }else{
                        value = value.substring(0, value.length()-1);
                        value += "*";
                        value += String.valueOf(Math.E);
                    }
                }
                try {
                    BigDecimal number = excute(value);

                    if(matcher.group(0).startsWith("log")) {
                        number = BigDecimalMath.log10(number, MathContext.DECIMAL64);
                    }
                    else if(matcher.group(0).startsWith("ln")) {
                        number = BigDecimalMath.log(number, MathContext.DECIMAL64);
                    }
                    x = x.replace(matcher.group(0), number.toPlainString());
                } catch (Exception e) {
                    return "Math Error";
                }
            }
        }

        return x;
    }

    /**
     * evaluates normal expressions
     * @param x
     * @return
     * @throws Exception
     */
    private static BigDecimal excute(String x) throws Exception {
        BigDecimal result;
        if(x.contains("Infinity")){
            x = x.replace("Infinity", "1 / 0");
        }
        if(x.contains("(")){
            x = x.replace("(","");
        }
        if(x.contains(")")){
            x = x.replace(")","");
        }
        ArrayList<String> arguments = new ArrayList<>();
        final String regex = "(?:[0-9-+*/^()|!√x%πE$.]|abs|e\\^x|ln|log|a?(?:sin|cos|tan)h?)+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(x);
        while (matcher.find()) {
            if(matcher.group(0).charAt(matcher.group(0).length()-1) == 'π'){
                if(matcher.group(0).length() == 1){
                    arguments.add(String.valueOf(BigDecimalMath.pi(MathContext.DECIMAL128)));
                }else {
                    arguments.add(matcher.group(0).substring(0, matcher.group(0).length() - 1));
                    arguments.add("*");
                    arguments.add(String.valueOf(BigDecimalMath.pi(MathContext.DECIMAL128)));
                }
            }
            else if(matcher.group(0).charAt(matcher.group(0).length()-1) == 'E'){
                if(matcher.group(0).length() == 1){
                    arguments.add(String.valueOf(BigDecimalMath.e(MathContext.DECIMAL128)));
                }else{
                    arguments.add(matcher.group(0).substring(0, matcher.group(0).length()-1));
                    arguments.add("*");
                    arguments.add(String.valueOf(BigDecimalMath.e(MathContext.DECIMAL128)));
                }
            }
            else if(matcher.group(0).startsWith("√")){
                arguments.add(String.valueOf(Math.sqrt(Double.parseDouble(matcher.group(0).substring(1)))));
            }
            else if(matcher.group(0).startsWith("|") && matcher.group(0).endsWith("|")){
                arguments.add(String.valueOf(Math.abs(Double.parseDouble(matcher.group(0).substring(1, matcher.group(0).length()-1)))));
            }
            else {
                if (matcher.group(0).startsWith("$")) {
                    String valueOfFunction = String.valueOf(excute(paranthesesFormulas.get(Integer.parseInt(matcher.group(0).substring(1)))));
                    arguments.add(valueOfFunction);
                }
                else if(matcher.group(0).contains("$")){
                    for (int i = 0; i < matcher.group(0).length(); i++) {
                        if(matcher.group(0).charAt(i) == '$'){
                            arguments.add(matcher.group(0).substring(0, i));
                            arguments.add("*");
                            String valueOfFunction = String.valueOf(excute(paranthesesFormulas.get(Integer.parseInt(matcher.group(0).substring(i+1)))));
                            arguments.add(valueOfFunction);
                        }
                    }
                }
                else {
                    arguments.add(matcher.group(0));
                }
            }
        }
        arguments = fact(arguments);
        arguments = power(arguments);
        arguments = division(arguments);
        arguments = modulo(arguments);
        arguments = multiplication(arguments);
        arguments = addition(arguments);
        arguments = subtraction(arguments);
        result = new BigDecimal(arguments.get(0));
        return result;
    }

    /**
     * evaluates factorials
     * @param x
     * @return
     * @throws Exception
     */
    private static ArrayList<String> fact(ArrayList<String> x) throws Exception {
        ArrayList<String> xs = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if(x.get(i).equals("!")){
                BigDecimal result;
                String val = xs.get(xs.size()-1);
                try {
                    int fact = Integer.parseInt(val);
                    result = new BigDecimal(factorial(fact));
                } catch (NumberFormatException e) {
                    double fact = Double.parseDouble(val);
                    if ((fact == Math.floor(fact)) && !Double.isInfinite(fact)) {
                        int factx = (int) Math.rint(fact);
                        result = new BigDecimal(factorial(factx));
                    }else{
                        result = new BigDecimal(factorial(fact));
                    }
                }
                xs.set(xs.size()-1, result.toString());
                i++;
            }else{
                xs.add(x.get(i));
            }
        }
        return xs;
    }

    /**
     * evaluates powers
     * @param x
     * @return
     */
    private static ArrayList<String> power(ArrayList<String> x) {
        ArrayList<String> xs = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if(x.get(i).equals("^")){
                BigDecimal result = new BigDecimal(xs.get(xs.size()-1)).pow(Integer.parseInt(x.get(i+1)));
                xs.set(xs.size()-1, result.toString());
                i++;
            }else{
                xs.add(x.get(i));
            }
        }
        return xs;
    }

    /**
     * evaluates divisions
     * @param x
     * @return
     */
    private static ArrayList<String> division(ArrayList<String> x) {
        ArrayList<String> xs = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if(x.get(i).equals("/")){
                BigDecimal result = new BigDecimal(xs.get(xs.size()-1)).divide(new BigDecimal(x.get(i+1)));
                xs.set(xs.size()-1, String.valueOf(result));
                i++;
            }else{
                xs.add(x.get(i));
            }
        }
        return xs;
    }

    /**
     * evaluates multiplications
     * @param x
     * @return
     */
    private static ArrayList<String> multiplication(ArrayList<String> x) {
        ArrayList<String> xs = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if(x.get(i).equals("*")){
                BigDecimal result = new BigDecimal(xs.get(xs.size()-1)).multiply(new BigDecimal(x.get(i+1)));
                xs.set(xs.size()-1, String.valueOf(result));
                i++;
            }else{
                xs.add(x.get(i));
            }
        }
        return xs;
    }

    /**
     * evaluates modulos
     * @param x
     * @return
     */
    private static ArrayList<String> modulo(ArrayList<String> x) {
        ArrayList<String> xs = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if(x.get(i).equals("%")){
                double result = Double.parseDouble(xs.get(xs.size()-1))%Double.parseDouble(x.get(i+1));
                xs.set(xs.size()-1, String.valueOf(result));
                i++;
            }else{
                xs.add(x.get(i));
            }
        }
        return xs;
    }

    /**
     * evaluates additions
     * @param x
     * @return
     */
    private static ArrayList<String> addition(ArrayList<String> x) {
        ArrayList<String> xs = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if(x.get(i).equals("+")){
                BigDecimal result = new BigDecimal(xs.get(xs.size()-1)).add(new BigDecimal(x.get(i+1)));
                xs.set(xs.size()-1, String.valueOf(result));
                i++;
            }else{
                xs.add(x.get(i));
            }
        }
        return xs;
    }

    /**
     * evaluates subtractions
     * @param x
     * @return
     */
    private static ArrayList<String> subtraction(ArrayList<String> x) {
        ArrayList<String> xs = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if(x.get(i).equals("-")){
                BigDecimal result =new BigDecimal(xs.get(xs.size()-1)).subtract(new BigDecimal(x.get(i+1)));
                xs.set(xs.size()-1, String.valueOf(result));
                i++;
            }else{
                xs.add(x.get(i));
            }
        }
        return xs;
    }

    /**
     * a Sine function but with bigdecimals
     * @param x
     * @return
     */
    private static BigDecimal sine(BigDecimal x) {
        BigDecimal lastVal = x.add(BigDecimal.ONE);
        BigDecimal currentValue = x;
        BigDecimal xSquared = x.multiply(x);
        BigDecimal numerator = x;
        BigDecimal denominator = BigDecimal.ONE;
        int i = 0;

        while (lastVal.compareTo(currentValue) != 0) {
            lastVal = currentValue;

            int z = 2 * i + 3;

            denominator = denominator.multiply(BigDecimal.valueOf(z));
            denominator = denominator.multiply(BigDecimal.valueOf(z - 1));
            numerator = numerator.multiply(xSquared);

            BigDecimal term = numerator.divide(denominator, RoundingMode.HALF_EVEN);

            if (i % 2 == 0) {
                currentValue = currentValue.subtract(term);
            } else {
                currentValue = currentValue.add(term);
            }

            i++;
        }
        return currentValue;
    }

    /**
     * a gamma function but with bigdecimals
     * @param x
     * @return
     */
    public static BigDecimal gamma(BigDecimal x){
        double[] pd = {0.99999999999980993, 676.5203681218851, -1259.1392167224028,
                771.32342877765313, -176.61502916214059, 12.507343278686905,
                -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7};
        ArrayList<BigDecimal> p = new ArrayList<>();
        for (double z: pd) {
            p.add(BigDecimal.valueOf(z));
        }
        BigDecimal g = BigDecimal.valueOf(7);
        if(x.compareTo(BigDecimal.valueOf(0.5)) < 0) return BigDecimalMath.pi(MathContext.DECIMAL128).divide((sine(x.multiply(BigDecimalMath.pi(MathContext.DECIMAL128))).multiply(gamma(BigDecimal.ONE.subtract(x)))), RoundingMode.HALF_EVEN);

        x = x.subtract(BigDecimal.ONE);
        BigDecimal a = p.get(0);
        BigDecimal t = x.add(g).add(BigDecimal.valueOf(0.5));
        for(int i = 1; i < p.size(); i++){
            a = a.add(p.get(i).divide((x.add(BigDecimal.valueOf(i))), RoundingMode.HALF_EVEN));
        }
        BigDecimal x1 = BigDecimalMath.sqrt(BigDecimalMath.pi(MathContext.DECIMAL128).multiply(BigDecimal.valueOf(2.0)), MathContext.DECIMAL128);
        BigDecimal x2 = t.pow(x.add(BigDecimal.valueOf(0.5)).toBigInteger().intValue());
        int x4 = BigDecimal.ZERO.subtract(t).toBigInteger().intValue();
        BigDecimal x3 = BigDecimal.valueOf(Math.E).pow(x4, MathContext.DECIMAL128).multiply(a);
        return x1.multiply(x2).multiply(x3);
    }

    /**
     * factorial of doubles (Decimals)
     * @param number
     * @return
     * @throws Exception
     */
    public static String factorial(double number) throws Exception {
        if(number > 9000){
            throw new Exception("OverFlow");
        }
        return gamma(BigDecimal.valueOf(number+1)).toString();
    }

    /**
     * normal factorial
     * @param number
     * @return
     * @throws Exception
     */
    public static String factorial(int number) throws Exception {
        BigInteger factorial = BigInteger.ONE;
        if(number > 9000){
            throw new Exception("OverFlow");
        }
        for (int i = number; i > 0; i--) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));
        return formatter.format(factorial);
    }

    /**
     * interchange whether radains or not
     * @param radain
     */
    public static void setRadain(boolean radain) {
        Evalulate.radain = radain;
    }

    /**
     * check if radains
     * @return
     */
    public static boolean isRadain() {
        return radain;
    }
}
