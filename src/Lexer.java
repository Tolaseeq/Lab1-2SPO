public class Lexer {

    public Integer stateNum = 0;
    public String inString;

    public int symbolIndex = 0;

    public Lexer(String _inString) {
        this.inString = _inString;
    }

    private Integer transitionTable(int stateNum, char symbol) { //таблица транслитерации
        for (int i = 0; i < inString.length(); i++) {
            String numbers = "0123456789";
            String symbols = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            switch (stateNum) {
                case 0:
                    if (numbers.contains(String.valueOf(symbol)))
                        return 1;
                    else if (symbols.contains(String.valueOf(symbol)))
                        return 7;
                    else if (symbol == '(')
                        return 8;
                    else if (symbol == ')')
                        return 9;
                    else if (symbol == ',')
                        return 10;
                    else if (symbol == '+' || symbol == '*' || symbol == '/' || symbol == '^' || symbol == '-')
                        return 6;
                    break;
                case 1:
                    if (numbers.contains(String.valueOf(symbol)))
                        return 1;
                    else if (symbol == '.')
                        return 2;
                    else if (symbol == 'e' || symbol == 'E')
                        return 3;
                    break;
                case 2:
                    if (numbers.contains(String.valueOf(symbol)))
                        return 2;
                    else if (symbol == 'e' || symbol == 'E')
                        return 3;
                    break;
                case 3:
                    if (numbers.contains(String.valueOf(symbol)))
                        return 5;
                    else if (symbol == '+' || symbol == '-')
                        return 4;
                    break;
                case 4:
                case 5:
                    if (numbers.contains(String.valueOf(symbol)))
                        return 5;
                    break;
                case 7:
                    if (numbers.concat(symbols).contains(String.valueOf(symbol)))
                        return 7;
                    break;
            }
        }
        return 404;
    }

    public Token createToken ()
    {
        Token token;
        int state = 0;
        String tokenString = "";
        String tokenName = "";
        String currentSymbols = "";
        int bufferIndex = symbolIndex;

        if (inString.length() == bufferIndex) //сделано для проверки на конец ввода и на пробелы во входном коде, они игнорируются
            return new Token("INPUT_END", "");
        else if (inString.charAt(symbolIndex) == ' ')
            symbolIndex++;

        while (state != 404)
        {
            if (!stateToToken(state).equals("UNKNOWN_STATE"))
            {
                bufferIndex = symbolIndex;
                tokenName = stateToToken(state);
                tokenString = currentSymbols;
            }
            if (inString.length() <= symbolIndex)
                break;
            char ch = inString.charAt(symbolIndex);
            symbolIndex++;
            state = transitionTable(state, ch);

            currentSymbols = currentSymbols + ch;
        }
        symbolIndex = bufferIndex;

        return new Token(tokenName, tokenString);
    }

    private String stateToToken (int state) //получение типа токена из номера состояния
    {
        if (state == 1 || state == 2 || state == 5)
        {
            return "number";
        }
        else if (state == 6)
        {
            return "operator";
        }
        else if (state == 7)
        {
            return "id";
        }
        else if (state == 8)
        {
            return "lparen";
        }
        else if (state == 9)
        {
            return "rparen";
        }
        else if (state == 10)
        {
            return "comma";
        }
        return "UNKNOWN_STATE";
    }
}