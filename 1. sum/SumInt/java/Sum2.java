import java.util.Arrays;

class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (String arg : args) {
            sum += Arrays.stream(
                                 arg.trim().split("\\p{javaWhitespace}")).
                                    mapToInt(
                                            (str) -> (str.compareTo("") == 0 ? 0 : Integer.parseInt(str))
                                            ).sum();
        }
        System.out.println(sum);
    }
}
