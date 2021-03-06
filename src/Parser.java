import java.util.Objects;

public class Parser {
    private Token token;
    private Lexer lexer;

    public Segment Parse(String inString) {
        lexer = new Lexer(inString);
        token = lexer.createToken();
        return computeETF('E');
    }

    private Segment computeETF(char type) { //для E, T, F
        Segment sOne;
        Segment sTwo;
        if (type == 'E') {
            sOne = computeETF('T');
        } else if (type == 'T') {
            sOne = computeETF('F');
        } else {
            sOne = V();
        }
        sTwo = computeETFop(type);
        if (sTwo != null && sTwo.mainToken.tokenName.equals("operator")) {
            sTwo.prev = sOne;
            sOne = sTwo;
        }
        return sOne;
    }

    private Segment computeETFop(char type) { //для E', T', F'
        Segment segment;
        if (type == 'E' && !(Objects.equals(token.tokenStr, "+") || Objects.equals(token.tokenStr, "-")))
            return null;
        if (type == 'T' && !(Objects.equals(token.tokenStr, "*") || Objects.equals(token.tokenStr, "/")))
            return null;
        if (type == 'F' && !Objects.equals(token.tokenStr, "^"))
            return null;
        segment = new Segment(token);
        token = lexer.createToken();
        segment.next = computeETF(type);
        return segment;
    }

    private Segment V() {
        Segment segment = null;
        if (Objects.equals(token.tokenName, "number")) {
            segment = new Segment(token);
            token = lexer.createToken();
        } else if (Objects.equals(token.tokenName, "operator")) {
            segment = new Segment(token);
            token = lexer.createToken();
            segment.prev = V();
        } else if (Objects.equals(token.tokenName, "id")) {
            segment = new Segment(token);
            token = lexer.createToken();
        } else if (Objects.equals(token.tokenName, "lparen")) {
            token = lexer.createToken();
            segment = computeETF('E');
            token = lexer.createToken();
        } else
            System.out.println("PARSING ERROR! CHECK SYNTAX");
        return segment;
    }
}
