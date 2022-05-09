public class Main {
    public static void main(String[] args)
    {
        String inString = "(-12+3)*2+20/2^2";
        Parser parser = new Parser();
        Segment tree = parser.Parse(inString);
        double result = Segment.computeTree(tree);
        System.out.println(inString + " = " + result);
    }
}