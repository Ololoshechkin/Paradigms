import java.util.Arrays;

class Sum {
    public static void main(String[] args) {
        final int[] sum = {0};
        Arrays.asList(args).forEach(
                                    (String arg) -> {
                                        sum[0] += Arrays.stream(arg.trim().split("\\p{javaWhitespace}")).
                                        mapToInt(
                                                (str) ->
                                                 (str.compareTo("") == 0 ? 0 : Integer.parseInt(str))
                                                ).sum();
                                    }
                                    );
        System.out.println(sum[0]);
    }
}
