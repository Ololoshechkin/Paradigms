
class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (String arg : args) {
            String[] innerNumberStrings = arg.trim().split("\\p{javaWhitespace}");
            for (String innerNumberString : innerNumberStrings) {
                sum += (innerNumberString.compareTo("") == 0 ? 0 : Integer.parseInt(innerNumberString));
            }
        }
        System.out.println(sum);
    }
}
