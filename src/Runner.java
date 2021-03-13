public class Runner {
    public static void main(String[] args) {
        try {
            System.out.println(LexicalAnalyser.analyse("0.23 3 + 2"));
        }
        catch (NumberException e) {
            System.out.println("NumberException");
        }
        catch (ExpressionException e) {
            System.out.println("ExpressionException");
        }
    }
}
