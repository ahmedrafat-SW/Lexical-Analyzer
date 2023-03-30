package task2;
import java.util.Stack;


/**
 LL1

 S -> id = E;A              (1)
                |
                if D then S         (2)
 A -> id = E;A              (3)
                |
                empty           (4)
 D -> EBE       (5)
 B -> ==        (6)
 E -> ML        (7)
 L -> +ML       (8)
            |
            empty       (9)
 M -> FH        (10)
 H -> *FH       (11)
        /
            empty   (12)
 F -> (E)       (13)
            |
            id (14)
                |
                num     (15)
 =========================================================================================
                                                Terminal
 non-Terminal           id      num         +           *       (       )       ;       =       if      then        $
 S                      1                                                                        2
 A                      3                                                                                           4
 D                      5       5                               5
 B                                                                                      6
 E                      7       7                               7
 L                                          8                          9         9       9                  9
 M                      10      10                              10
 H                                          12          11
 F                      14      15                              13




 */

public class Parse {
    //input
    public String input;
    //Stack
    Stack<String> strack = new Stack<>();
    Stack<String> p = new Stack<>();


    //Table of rules
    String table[][] =
            {
                    {"id = E;A", null, null, null, null, null, null, null, "if D then S", null, null}
                    ,
                    {"id = E;A", null, null, null, null, null, null, null, null, null, null}
                    ,
                    {"EBE", "EBE", null, null, "EBE", null, null, null, null, null, null}
                    ,
                    {null, null, null, null, null, null, null, "==", null, null, null}
                    ,
                    {"ML", "ML", null, null, "ML", null, null, null, null, null, null}
                    ,
                    {null, null, "+ML", null, null, null, null, null, null, null, null}
                    ,
                    {"FH", "FH", null, null, "FH", null, null, null, null, null, null}
                    ,
                    {null, null, null, "*FH", null, null, null, null, null, null, null}
                    ,
                    {"id", "num", null, null, "(E)", null, null, null, null, null, null}

            };
    String nonTers[] = {"S", "A", "D", "B", "E", "L", "M", "H", "F"};
    String terminals[] = {"id", "num", "+", "*", "(", ")", ";", "=", "if", "then", "$"};


    public Parse(String in) {
        input = in;
        algorithm();
    }

    private void pushRule(String rule) {
        push(rule);
    }

    //algorithm
    public void algorithm() {


        push(input+"");
        //push("S");
        //Read one token from input

        String token = read();
        String top = null;


        do {
            top = this.pop();
            //if top is non-terminal
            if (isNonTerminal(top)) {
                String rule = this.getRule(top, token);
                this.pushRule(rule);
            } else if (isTerminal(top)) {
                if (!top.equals(token)) {
                    error("this token is not corrent , By Grammer rule . Token : (" + token + ")");
                } else {
                    System.out.println("Matching: Terminal :( " + token + " )");
                    token = read();
//top=pop();

                }
            } else {
                error("Never Happens , Because top : ( " + top + " )");
            }
            if (strack.isEmpty()) {
                System.out.println("Input is Accepted by LL1");
                break;
            } else {
                System.out.println("Input is not Accepted by LL1");
            }
            //if top is terminal

        } while (true);//out of the loop when the stack is empty

        //accept

    }

    private boolean isTerminal(String s) {
        for (int i = 0; i < this.terminals.length; i++) {
            if (s.equals(this.terminals[i])) {
                return true;
            }

        }
        return false;
    }

    private boolean isNonTerminal(String s) {
        for (int i = 0; i < this.nonTers.length; i++) {
            if (s.equals(this.nonTers[i])) {
                return true;
            }

        }
        return false;
    }

    private String read() {
        String str = input;

        return str;
    }

    private void push(String s) {
        this.strack.push(s);
    }

    private String pop() {
        return this.strack.pop();
    }

    private void error(String message) {
        System.out.println(message);
      //  throw new RuntimeException(message);
    }

    public String getRule(String non, String term) {

        int row = getnonTermIndex(non);
        int column = getTermIndex(term);
        String rule = this.table[row][column];
        if (rule == null) {
            error("There is no Rule by this , Non-Terminal(" + non + ") ,Terminal(" + term + ") ");
        }
        return rule;
    }

    private int getnonTermIndex(String non) {
        for (int i = 0; i < this.nonTers.length; i++) {
            if (non.equals(this.nonTers[i])) {
                return i;
            }
        }
        error(non + " is not NonTerminal");
        return -1;
    }

    private int getTermIndex(String term) {
        for (int i = 0; i < this.terminals.length; i++) {
            if (term.equals(this.terminals[i])) {
                return i;
            }
        }
        error(term + " is not Terminal");
        return -1;
    }


}
/*

 */