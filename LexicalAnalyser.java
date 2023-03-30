package task2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class test {
    private static boolean isKeyword(String st){
        List<String> keywords = Arrays.asList("if","then","end","while","do","return","else","else if","print");
        for (String key : keywords) {
            if (st.equalsIgnoreCase(key))
                return true;
        }

        return false;
    }

    private static boolean isDatatype(String st){
        List<String> dataType = Arrays.asList("int","float","string","long","char");
        for (String data : dataType) {
            if (st.equalsIgnoreCase(data)) {
                return true;
            }
        }
        return false;
    }

    public static String isSymbol(String st){
        if (st.matches("[-+*/=<>!][=]|[-+*/(){}=;]")){
            switch (st){
                case "(":
                    return "Open_parenthesis";
                case ")":
                    return "Close_parenthesis";
                case "{":
                    return "Open_brace";
                case "}":
                    return "Close_brace";
                case "=":
                    return "Equal_sign";
                case"==":
                    return "Equal_Equal_sign";
                case "+":
                    return "Plus_op";

                case "-":
                    return "Minus_op";

                case "*":
                    return "Multi_op";

                case "/":
                    return "Divide_op";

                case "<":
                    return "less_than";

                case ">":
                    return "Greater_than";

                case "<=":
                    return "less_than or Equal";

                case ">=":
                    return "Greater_than or Equal";

                case "!=":
                    return "Not_Equal";

                case ";":
                    return "Semicolon";
            }

        }
        return "";
    }
    public static boolean isDigit(String st){
        if(st.matches("[\\s-+*/=(]?([0-9]*[.])?[0-9]+[\\s-+*/;]")) {
            return true;
        }
        return false;
    }

    private static boolean isId(String st){
        if (st.matches("[a-zA-Z]+[\\s-+*/=);]|[a-zA-Z]+[0-9]+[\\s-+*/=);]"))
            return true;
        return false;
    }


    private static boolean escapeChars(char ch) {
        char[] escapeChars = {'<', '(', '[', '{', '\\', '^', '$', '!', '|', ']', '}', ')', '>', ';', ' ','=','$'};
        for (char e : escapeChars) {
            if (ch == e) {
                return true;
            }
        }
        return false;
    }
    private static void readFromFile()throws Exception{
        Parse parser;
        File file = new File("task.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), "UTF-8"));

        int c;
        String temp = "";

        char ch;
        while((c = bufferedReader.read()) != -1){
            ch = (char)c;

            temp += ch;


            if(isKeyword(temp)){
                    parser=new Parse(temp);

                temp="";
            }

            if(isId(temp)  ){


                String s = Character.toString(temp.charAt(temp.length() - 1));
                String m = temp.substring(0,temp.length()-1);
                parser=new Parse(m);
                if(!isSymbol(s).isEmpty()) {
                    parser=new Parse(s);
                }
                temp="";
            }

            if(!isSymbol(temp).isEmpty()){

                parser=new Parse(temp);//i*i+(i+i)$


                temp="";
            }

            if(isDigit(temp)){
                String intORFloat = "";
                int x;
                try{
                    x = Integer.parseInt(temp.substring(0,temp.length()-1));
                    intORFloat += "Int_literal";

                }catch (NumberFormatException n){
                    intORFloat += "Float_literal";

                }
                parser=new Parse("num");

                String s = Character.toString(temp.charAt(temp.length() - 1));
                if(!isSymbol(s).isEmpty()) {
                    parser=new Parse(temp.substring(temp.length() - 1));//i*i+(i+i)$

                }

                temp="";
            }

            if(escapeChars(ch)){
                temp = "";
            }
            if(ch == '\n'){
                temp = "";
            }
        }


    }
    public static void main(String[] args) throws Exception {
        readFromFile();

    }

}
