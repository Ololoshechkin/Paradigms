
class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (String arg : args) {
            int numberStartPos = 0, numberEndPos = 0;
            String numberString = "";
            while (numberEndPos <= arg.length()) {
                if (numberEndPos == arg.length() || Character.isWhitespace(arg.charAt(numberEndPos))) {
                    if (!Character.isWhitespace(arg.charAt(numberStartPos))) {
                        numberString = arg.substring(numberStartPos, numberEndPos);
                        sum += Integer.parseInt(numberString);
                    }
                    numberStartPos = numberEndPos;
                } else {
                    if (Character.isWhitespace(arg.charAt(numberStartPos))) {
                        numberStartPos = numberEndPos;
                    }
                }
                ++numberEndPos;
            }
        }
        System.out.println(sum);
    }
}
