import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class LexicalAnalyser {
    // Need something for the states
    private enum State {
        Initial, Digit, Operator, StartWithZero, DP_Zero, DecimalNum, Num_WS, Decimal_WS
    };

    public static List<Token> analyse(String input) throws NumberException, ExpressionException {
        List<Token> result = new ArrayList<Token>(); //Stores token

        Token token = new Token();
        String buffer = "";
        String buffer_dec = "0.";
        State state = State.Initial;

        // To go through the input char by char
        for (int i = 0; i<input.length(); i++) {

            System.out.println("From: " + state + " with: " + input.charAt(i));

            if (input.charAt(i) == '0') {
                switch (state) {
                    case Initial:
                        state = State.StartWithZero;
                        //buffer.append(input.charAt(i));
                        buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Integer.parseInt(buffer)));
                            buffer = "";
                            break;
                        }
                        break;
                    case Digit:
                        state = State.Digit;
                        //buffer.append(input.charAt(i));
                        buffer = buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Integer.parseInt(buffer)));
                            buffer= "";
                            break;
                        }
                        break;

                    case Operator:
                        state = State.StartWithZero;
                        //buffer.append(input.charAt(i));
                        buffer = buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Integer.parseInt(buffer)));
                            buffer = "";
                            break;
                        }
                        break;

                    case StartWithZero:
                        throw new NumberException();
                    case DP_Zero:
                        state = State.DecimalNum;
                        //buffer.append(input.charAt(i));
                        System.out.println(buffer_dec);
                        buffer_dec += input.charAt(i);
                        System.out.println(buffer_dec);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Double.parseDouble(buffer_dec)));
                            buffer = "";
                            break;
                        }
                        break;

                    case DecimalNum:
                        state = State.DecimalNum;
                        buffer_dec += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Double.parseDouble(buffer_dec)));
                            buffer = "";
                            break;
                        }
                        break;

                    case Num_WS:
                        throw new ExpressionException();

                    case Decimal_WS:
                        throw new ExpressionException();
                }

            }

            else if (input.charAt(i) == '.') {
                switch(state) {
                    case Initial:
                        throw new NumberException();
                    case Digit:
                        throw new NumberException();
                    case Operator:
                        throw new ExpressionException();
                    case StartWithZero:
                        state = State.DP_Zero;
                        // buffer.append(input.charAt(i));
                        //buffer_dec += input.charAt(i);
                        if (i + 1 == input.length()) {
                            throw new NumberException();
                        }
                        break;
                    case DP_Zero:
                        throw new ExpressionException();
                    case DecimalNum:
                        throw new ExpressionException();
                    case Num_WS:
                        throw new NumberException();
                    case Decimal_WS:
                        throw new NumberException();
                }
            }

            else if (input.charAt(i) == ' ') {
                switch(state) {
                    case Initial:
                        state = State.Initial;
                        // buffer.append(input.charAt(i));
                        //buffer = buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            throw new ExpressionException();
                            //result.add(new Token(Double.parseDouble(buffer)));
                            //break;
                            //buffer= "";
                        }
                        break;

                    case Digit:
                        state = State.Num_WS;
                        //buffer.append(input.charAt(i));
                        //buffer = buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Integer.parseInt(buffer)));
                            buffer = "";
                            break;
                        }
                        break;

                    case Operator:
                        state = State.Operator;
                        //buffer = buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            throw new ExpressionException();
                            //result.add(new Token(Integer.parseInt(buffer)));
                            //buffer = "";
                            //break;
                        }
                        break;

                    case StartWithZero:
                        state = State.Num_WS;
                        //buffer.append(input.charAt(i));
                        //buffer = buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Integer.parseInt(buffer)));
                            break;
                            //buffer = "";
                        }
                        //break;

                    case DP_Zero:
                        //state = State.DP_Zero;
                        //buffer_dec += input.charAt(i);
                        //if (i + 1 == input.length()) {
                        throw new ExpressionException();
                        //}
                        //break;

                    case DecimalNum:
                        //throw new ExpressionException();
                        state = State.Decimal_WS;
                        //result.add(new Token(Double.parseDouble(buffer_dec)));
                        //buffer_dec = "";
                        //buffer_dec += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Double.parseDouble(buffer_dec)));
                            break;
                        }
                        break;
                    case Num_WS:
                        state = State.Num_WS;
                        // buffer.append(input.charAt(i));
                        //buffer = buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Integer.parseInt(buffer)));
                            break;
                        }
                        break;
                    case Decimal_WS:
                        state = State.Decimal_WS;
                        // buffer.append(input.charAt(i));
                        //buffer = buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Double.parseDouble(buffer)));
                            break;
                        }
                        break;
                }
            }

            else if (input.charAt(i) == '1' || input.charAt(i) == '2' || input.charAt(i) == '3' || input.charAt(i) == '4' || input.charAt(i) == '5' || input.charAt(i) == '6' || input.charAt(i) == '7' || input.charAt(i) == '8' || input.charAt(i) == '9') {
                switch(state) {
                    case Initial:
                        state = State.Digit;
                        //  buffer.append(input.charAt(i));
                        // check the next one to see if it is the end of string or not Number
                        // if it is the end, create token from the buffer and add it to result
                        // restart buffer
                        buffer = buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Integer.parseInt(buffer)));
                            buffer = "";
                            break;
                            //buffer.setLength(null);
                        }
                        break;

                    case Digit:
                        state = State.Digit;
                        //buffer.append(input.charAt(i));
                        buffer = buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Integer.parseInt(buffer)));
                            buffer = "";
                            break;
                        }
                        break;
                    case Operator:
                        state = State.Digit;
                        //buffer.append(input.charAt(i));
                        buffer = buffer += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Integer.parseInt(buffer)));
                            buffer = "";
                            break;
                        }
                        break;
                    case StartWithZero:
                        throw new NumberException();
                        //state = State.Digit;
                        //throw new ExpressionException();
                        //buffer = buffer += input.charAt(i);
                        //if (i + 1 == input.length()) {
                        //result.add(new Token(Integer.parseInt(buffer)));
                        //buffer = "";
                        // break;
                        //}
                        //break;
                    case DP_Zero:
                        state = State.DecimalNum;
                        //buffer.append(input.charAt(i));
                        buffer_dec += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Double.parseDouble(buffer_dec)));
                            buffer = "";
                            break;
                        }
                        break;

                    case DecimalNum:
                        state = State.DecimalNum;
                        buffer_dec += input.charAt(i);
                        if (i + 1 == input.length()) {
                            result.add(new Token(Double.parseDouble(buffer_dec)));
                            buffer = "";
                            break;
                        }
                        break;
                    case Num_WS:
                        //state = State.Num_WS;
                        throw new ExpressionException();
                    case Decimal_WS:
                        throw new ExpressionException();
                }
            }

            else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/') {
                switch(state) {
                    case Initial:
                        throw new ExpressionException();
                    case Digit:
                        state = State.Operator;
                        // Create token for the number from the buffer
                        result.add(new Token(Integer.parseInt(buffer)));
                        // reset the buffer
                        buffer = "";
                        if (i + 1 == input.length()) {
                            throw new ExpressionException();
                        }

                        result.add(new Token(Token.typeOf(input.charAt(i))));
                        break;

                    case Operator:
                        throw new ExpressionException();
                    case StartWithZero:
                        state = State.Operator;
                        // Create the token for the number
                        result.add(new Token(Integer.parseInt(buffer)));
                        // reset the buffer
                        buffer = "";
                        if (i + 1 == input.length()) {
                            throw new ExpressionException();
                        }
                        result.add(new Token(Token.typeOf(input.charAt(i))));
                        break;
                    case DP_Zero:
                        throw new ExpressionException();
                    case DecimalNum:
                        state = State.Operator;
                        result.add(new Token(Double.parseDouble(buffer_dec)));
                        // reset the buffer
                        buffer_dec = "";
                        if (i + 1 == input.length()) {
                            throw new ExpressionException();
                        }
                        result.add(new Token(Token.typeOf(input.charAt(i))));
                        break;
                    case Num_WS:
                        state = State.Operator;
                        result.add(new Token(Integer.parseInt(buffer)));
                        // reset the buffer
                        buffer = "";
                        if (i + 1 == input.length()) {
                            throw new ExpressionException();
                        }
                        result.add(new Token(Token.typeOf(input.charAt(i))));
                        break;
                    case Decimal_WS:
                        state = State.Operator;
                        result.add(new Token(Double.parseDouble(buffer_dec)));
                        buffer_dec = "";
                        if (i + 1 == input.length()) {
                            throw new ExpressionException();
                        }
                        result.add(new Token(Token.typeOf(input.charAt(i))));
                        break;
                }
            }

            System.out.println(" to: " + state);

        }

        return result;
    }
}
