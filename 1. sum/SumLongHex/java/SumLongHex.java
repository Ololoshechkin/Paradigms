class SumLongHex {
    
    private static long parseLong(String numberString) {
        if (numberString.toLowerCase().startsWith("0x")) {
            return Long.parseUnsignedLong(numberString.substring(2, numberString.length()), 16);
        } else  {
            return Long.parseLong(numberString);
        }
    }
    
    public static void main(String[] args) {
        long sum = 0;
        for (String arg : args) {
            for (int begin = 0; begin < arg.length(); begin++) {
                if (Character.isWhitespace(arg.charAt(begin))) {
                    continue;
                }
                int end = begin + 1;
                while (end < arg.length() && !Character.isWhitespace(arg.charAt(end))) {
                    end++;
                }
                String numberString = arg.substring(begin, end);
                sum += parseLong(numberString);
                begin = end;
            }
        }
        System.out.println(sum);
    }
    
}
