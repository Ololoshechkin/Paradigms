
class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (String arg : args) {
            for (int numberStartPos = 0; numberStartPos < arg.length(); numberStartPos++) {
                if (Character.isWhitespace(arg.charAt(numberStartPos))) {
                    continue;
                }
                int numberEndPos = numberStartPos + 1;
                while (numberEndPos < arg.length() && !Character.isWhitespace(arg.charAt(numberEndPos))) {
                    numberEndPos++;
                }
                String numberString = arg.substring(numberStartPos, numberEndPos);
                sum += Integer.parseInt(numberString);
                numberStartPos = numberEndPos;
            }
        }
        System.out.println(sum);
    }
}
