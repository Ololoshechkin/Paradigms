package expressions.parser;

/**
 * Created by Vadim on 12.04.17.
 */
public class ParserState {

    public String expression;
    public int position;

    public ParserState(String expression, int position) {
        this.expression = expression;
        this.position = position;
    }

    public Character getChar() {
        return expression.charAt(position);
    }

    public Character getCharMoved(int delta) {
        return expression.charAt(position + delta);
    }

    public boolean canMove(String token) {
        int lastPosition = position + token.length();
        return  lastPosition < expression.length() && expression.substring(position, lastPosition).equals(token);
    }

    public boolean canMove(int delta) {
        return position + delta < expression.length();
    }

    public void move(int delta) {
        position += delta;
    }

    public void move(String token) {
        position += token.length();
    }

    public boolean isLastState() {
        return position == expression.length();
    }

    public void skipWhitespaces() {
        while (position < expression.length() && Character.isWhitespace(expression.charAt(position))) {
            ++position;
        }
    }

    public Character skipWhitespacesAndGet() {
        while (position < expression.length() && Character.isWhitespace(expression.charAt(position))) {
            ++position;
        }
        return position < expression.length() ? expression.charAt(position) : null;
    }

    public boolean startsLikeConst() {
        if (Character.isDigit(expression.charAt(position))) {
            return true;
        }
        if (expression.charAt(position) == '-') {
            return position + 1 < expression.length() && Character.isDigit(expression.charAt(position + 1));
        }
        return false;
    }

    public void checkAfter(String operator, boolean inclusive, String... tokens) throws ExpressionParserException {
        boolean mach = false;
        for (String token : tokens) {
            if (operator.equals(token)) {
                mach = true;
                break;
            }
        }
        if (mach != inclusive) {
            return;
        }
        Character c = getChar();
        if (c != ' ' && c != '-' && c != '(') {
            throw new WrongOperationOrderException(
                    "\" \", \"(\" or \"-\" after " + operator,
                    getIdentifier(false),
                    position
            );
        }
    }

    public String prefix(int endPos) {
        return expression.substring(position, position + endPos);
    }

    public String revSufix(int startPos) {
        return expression.substring(startPos, position);
    }

    public String getIdentifier(boolean unceckedFirst) {
        return IdentifierFinder.getIdentifier(expression, position, unceckedFirst);
    }

}
