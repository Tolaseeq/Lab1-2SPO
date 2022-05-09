public class Segment {
    public Token mainToken;

    public Segment prev;

    public Segment next;

    public Segment(Token token) {mainToken = token;}

    public static float computeTree(Segment segment) {
        float current;
        float next = 0;
        if (segment.prev != null) {
            current = computeTree(segment.prev);
        } else {
            return Float.parseFloat(segment.mainToken.tokenStr);
        }

        if (segment.next != null) {
            next = computeTree(segment.next);
        }

        if (segment.mainToken.tokenName.equals("operator")) {
            switch (segment.mainToken.tokenStr) {
                case "+":
                    return current + next;
                case "-":
                    if (segment.next != null) {
                        return current - next;
                    } else {
                        return -current;
                    }
                case "*":
                    return current * next;
                case "/":
                    return current / next;
                case "^":
                    return (float) Math.pow(current, next);
            }
        }
        return  0;
    }
}
