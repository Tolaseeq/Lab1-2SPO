import java.util.Objects;

public class Segment {
    public Token mainToken;

    public Segment prev;

    public Segment next;

    public Segment(Token token) {mainToken = token;}

    public static float computeTree(Segment segment) {
        float current;
        float next = 0;
        if (segment.prev != null)
            current = computeTree(segment.prev);
        else
            return Float.parseFloat(segment.mainToken.tokenStr);
        if (segment.next != null)
            next = computeTree(segment.next);
        if (segment.mainToken.tokenName.equals("operator") && Objects.equals(segment.mainToken.tokenStr, "+"))
            return current + next;
        else if (segment.mainToken.tokenName.equals("operator") && Objects.equals(segment.mainToken.tokenStr, "-") && segment.next != null)
            return current - next;
        else if (segment.mainToken.tokenName.equals("operator") && Objects.equals(segment.mainToken.tokenStr, "-"))
            return -current;
        else if (segment.mainToken.tokenName.equals("operator") && Objects.equals(segment.mainToken.tokenStr, "*"))
            return current * next;
        else if (segment.mainToken.tokenName.equals("operator") && Objects.equals(segment.mainToken.tokenStr, "/"))
            return current / next;
        else if (segment.mainToken.tokenName.equals("operator") && Objects.equals(segment.mainToken.tokenStr, "^"))
            return (float) Math.pow(current, next);
        return  0;
    }
}
