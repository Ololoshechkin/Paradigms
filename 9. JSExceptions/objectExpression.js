var VARIABLES = ["x", "y", "z", "u", "v", "w"];

function myAssign() { // Object.assign in es6
    var targetObj = arguments[0], otherObjects = [].slice.call(arguments);
    otherObjects.shift();
    otherObjects.forEach(function (obj) {
        Object.keys(obj).forEach(function (key) {
            targetObj[key] = obj[key];
        });
    });
}

var literal = {
    simplify: function () {
        return this;
    },
    prefix: function () {
        return this.toString();
    },
    postfix: function () {
        return this.toString();
    },
    arguments: []
};

function Variable(name) {
    this.index = VARIABLES.indexOf(name);
}
myAssign(
    Variable.prototype,
    {
        evaluate: function () {
            return arguments[this.index];
        },
        toString: function () {
            return VARIABLES[this.index];
        },
        diff: function (varName) {
            return varName === this.toString() ? Const.ONE : Const.ZERO;
        },
        equals: function () {
            return false;
        }
    },
    literal
);

function Const(value) {
    this.value = value;
}
myAssign(
    Const.prototype,
    {
        evaluate: function () {
            return this.value;
        },
        toString: function () {
            return this.value.toString();
        },
        diff: function () {
            return Const.ZERO;
        },
        equals: function (constValue) {
            return this.evaluate() === constValue;
        }
    },
    literal
);

Const.ZERO = new Const(0);
Const.ONE = new Const(1);
Const.TWO = new Const(2);

function operationFactory(name, operator, diffOperator, simplification) {
    function AbstractOperation() {
        this.arguments = [].slice.call(arguments);
    }
    myAssign(AbstractOperation.prototype, {
        evaluate: function () {
            var extArgs = arguments;
            var evaluatedOperands = this.arguments.map(function (arg) {
                return arg.evaluate.apply(arg, extArgs);
            });
            return operator.apply(null, evaluatedOperands);
        },
        toString: function () {
            return this.arguments.join(" ") + " " + name;
        },
        diff: function (varName) {
            var argDiffs = this.arguments.map(function (expr) {
                return expr.diff(varName);
            });
            return diffOperator(this.arguments, argDiffs);
        },
        simplify: function () {
            var copy = new this.constructor();
            copy.arguments = this.arguments.map(function (arg) {
                return arg.simplify();
            });
            if (copy.arguments.every(function (obj) {
                    return obj instanceof Const
                })) {
                return new Const(copy.evaluate());
            } else {
                return simplification !== undefined ? simplification(copy) : copy;
            }
        },
        equals: function () {
            return false;
        },
        prefix: function () {
            return "(" + name + " " + this.arguments.map(function (arg) {
                    return arg.prefix();
                }).join(' ') + ")";
        },
        postfix: function () {
            return "(" + this.arguments.map(function (arg) {
                    return arg.postfix();
                }).join(' ') + " " + name + ")"
        }
    });
    return AbstractOperation;
}

var Add = operationFactory("+", function (a, b) {
    return a + b;
}, function (x, dx) {
    return new Add(dx[0], dx[1]);
}, function (expr) {
    for (var key in expr.arguments) {
        if (expr.arguments[key].equals(0)) {
            return expr.arguments[1 - parseInt(key)];
        }
    }
    return expr;
});

var Multiply = operationFactory("*", function (a, b) {
    return a * b;
}, function (x, dx) {
    return new Add(
        new Multiply(dx[0], x[1]),
        new Multiply(x[0], dx[1])
    );
}, function (expr) {
    for (var key in expr.arguments) {
        if (expr.arguments[key].equals(0)) {
            return expr.arguments[key];
        }
        if (expr.arguments[key].equals(1)) {
            return expr.arguments[1 - parseInt(key)];
        }
    }
    return expr;
});

var Subtract = operationFactory("-", function (a, b) {
    return a - b;
}, function (x, dx) {
    return new Subtract(dx[0], dx[1]);
}, function (expr) {
    if (expr.arguments[1].equals(0)) {
        return expr.arguments[0];
    }
    return expr;
});

var Divide = operationFactory("/", function (a, b) {
    return a / b;
}, function (x, dx) {
    return new Divide(
        new Subtract(
            new Multiply(dx[0], x[1]),
            new Multiply(x[0], dx[1])
        ),
        new Multiply(x[1], x[1])
    );
}, function (expr) {
    if (expr.arguments[0].equals(0) || expr.arguments[1].equals(1)) {
        return expr.arguments[0];
    }
    return expr;
});
var Negate = operationFactory("negate", function (x) {
    return -x
}, function (x, dx) {
    return new Negate(dx[0]);
}, function (expr) {
    if (expr.arguments[0].equals(0)) {
        return Const.ZERO;
    }
    return expr;
});

var Square = operationFactory("square", function (x) {
    return x * x;
}, function (x, dx) {
    return new Multiply(
        Const.TWO,
        new Multiply(x[0], dx[0])
    )
});

var Sqrt = operationFactory("sqrt", function (x) {
    return Math.sqrt(Math.abs(x));
}, function (x, dx) {
    return new Divide(
        new Multiply(x[0], dx[0]),
        new Multiply(
            Const.TWO,
            new Sqrt(
                new Multiply(new Multiply(x[0], x[0]), x[0])
            )
        )
    )
});

var Sin = operationFactory("sin", function (x) {
    return Math.sin(x);
}, function (x, dx) {
    return new Multiply(
        new Cos(x[0]),
        dx[0]
    );
});

var Cos = operationFactory("cos", function (x) {
    return Math.cos(x);
}, function (x, dx) {
    return new Multiply(
        new Negate(new Sin(x[0])),
        dx[0]
    );
});

var operations = {
    "+": [Add, 2],
    "-": [Subtract, 2],
    "*": [Multiply, 2],
    "/": [Divide, 2],
    "negate": [Negate, 1],
    "square": [Square, 1],
    "sqrt": [Sqrt, 1],
    "sin": [Sin, 1],
    "cos": [Cos, 1]
};

function ParsingException(message, position, expression) {
    this.message = ["(at position : ", position, ") ", message, '\n', expression, '\n'].join('');
    var arr = [];
    for (var i = 0; i < position; i++) {
        arr.push(' ');
    }
    this.message += arr.join('') + '^';
}
ParsingException.prototype = Object.create(Error.prototype);

function createExceptions(exceptionMap) {
    Object.keys(exceptionMap).forEach(function (exceptionName) {
        this[exceptionName] = function () {
            var state = arguments[0];
            var otherArguments = [].slice.call(arguments);
            otherArguments.shift();
            ParsingException.call(this, exceptionMap[exceptionName].apply(null, otherArguments), state.position, state.expression);
            this.name = exceptionName;
        };
        this[exceptionName].prototype = Object.create(Error.prototype);
    });
}

createExceptions({
    "EndOfLineException": function (expected) {
        return "expected " + expected + ", but found : end of line";
    },
    "UnknownIdentifierException": function (found, expected) {
        return "expected " + expected + ", but found : " + found;
    },
    "MissedParenthesisException": function (openParenthesisPos) {
        return "expected closing parenthesis for opening parenthesis at position : " + openParenthesisPos;
    },
    "BadIntegerFormatException": function (badInteger) {
        return badInteger + " is bad integer format";
    },
    "UnexpectedLineContinuationException": function (token) {
        return "expected end of line, but found : " + token;
    },
    "MissingParenthesisBeforeOperator": function (operator) {
        return "expected \"(\" before operator " + operator;
    },
    "MissingParenthesisBeOperatorBlock": function (operator) {
        return "expected \")\" after operator " + operator + " arguments";
    },
    "RedudantParenthesisBeforeLiteral": function (token) {
        return "parenthesis is unexpected before " + token + " since it is not an operator";
    },
    "RedudantParenthesis": function () {
        return "closing parenthesis is an excess here";
    },
    "FewArgumentsException": function (operator, expectedCnt, foundCnt) {
        return "expected " + expectedCnt + " operands for \"" + operator + "\", but found : " + foundCnt;
    }
});

function State(expression, tokens) {
    this._index = 0;
    this.position = 0;
    this.expression = expression;
    this.tokens = tokens;
}
myAssign(
    State.prototype,
    {
        getToken: function (delta) {
            return this.tokens[this._index + (delta ? delta : 0)];
        },
        move: function () {
            this.position += this.tokens[this._index++].length;
            if (!this.hasNext()) {
                return;
            }
            while (this.expression[this.position] != this.getToken()[0]) {
                this.position++;
            }
        },
        getTokenAndMove: function () {
            var answer = this.getToken();
            this.move();
            return answer;
        },
        hasNext: function() {
            return this._index !== this.tokens.length;
        }
    }
);

function parseAny(parseByTokens) {
    var tokenize = function (expr) {
        function tokenPasser(regex, returns, startCondition, action) {
            return function () {
                var startPos = pos;
                if (startCondition && startCondition()) {
                    return action();
                }
                while (pos < expr.length && regex.test(expr[pos])) {
                    pos++;
                }
                if (returns) {
                    var ans = expr.substr(startPos, pos - startPos);
                    return ans === "" ? undefined : ans;
                }
            }
        }

        var skipWhitespaces = tokenPasser(/\s/, false, false, null);
        var getIdentifier = tokenPasser(/[A-Za-z0-9]/, true, function () {
            return !/[A-Za-z]/.test(expr[pos]);
        }, function () {
            return expr[pos++];
        });
        var getConst = tokenPasser(/\d/, true, function () {
            return expr[pos] === '-' && !/\d/.test(expr[++pos]);
        }, function () {
            pos--;
            return undefined;
        });
        var pos = 0;
        var tokens = [];
        while (pos < expr.length) {
            skipWhitespaces();
            if (pos === expr.length) {
                break;
            }
            var token = getConst();
            if (token) {
                tokens.push(token);
            } else if (/[()]/.test(expr[pos])) {
                tokens.push(expr[pos++]);
            } else {
                tokens.push(getIdentifier());
            }
        }
        return tokens;
    };
    return function (expr) {
        return parseByTokens(
            new State(
                expr,
                tokenize(expr)
            )
        );
    };
}

var parsePrefix = parseAny(function (state) {
    function getExpr(open) {
        if (!state.hasNext()) {
            throw new EndOfLineException(
                state,
                "const, variable, opening parenthesis or operator"
            );
        }
        if (operations.hasOwnProperty(state.getToken())) {
            if (!open) {
                throw new MissingParenthesisBeforeOperator(state, state.getToken());
            }
            var currentExpr = new operations[state.getToken()][0]();
            var argumentsCnt = operations[state.getTokenAndMove()][1];
            for (var i = 0; i < argumentsCnt; i++) {
                currentExpr.arguments[i] = getExpr(false);
            }
            return currentExpr;
        }
        if (open) {
            throw new RedudantParenthesisBeforeLiteral(state, state.getToken());
        }
        if (state.getToken() === '(') {
            var openPos = state.position;
            state.move();
            var answer = getExpr(true);
            if (state.getToken() !== ')') {
                throw new MissedParenthesisException(state, openPos);
            }
            state.move();
            return answer;
        }
        if (VARIABLES.indexOf(state.getToken()) !== -1) {
            return new Variable(state.getTokenAndMove());
        }
        if (/[\d-]/.test(state.getToken()[0])) {
            try {
                var intValue = parseInt(state.getToken());
            } catch (e) {
                throw new BadIntegerFormatException(state, state.getToken());
            }
            state.move();
            return new Const(intValue);
        } else {
            throw new UnknownIdentifierException(
                state,
                state.getToken(),
                "const, variable, opening parenthesis or operator"
            );
        }
    }
    var answer = getExpr(false);
    if (state.hasNext()) {
        throw new UnexpectedLineContinuationException(state, state.getToken());
    }
    return answer;
});

var parsePostfix = parseAny(function (state) {
    if (!state.hasNext()) {
        throw new EndOfLineException(0, '', "const, variable, opening parenthesis or operator");
    }
    var stack = [], balance = 0, openPos = -1;
    for (; state.hasNext(); state.move()) {
        var token = state.getToken();
        if (token === '(') {
            balance++;
            stack.push('(');
            openPos = state.position;
            continue;
        }
        if (token === ')') {
            balance--;
            if (balance < 0) {
                throw new RedudantParenthesis(state);
            }
            token = state.getToken(-1);
            if (!operations.hasOwnProperty(token)) {
                throw new UnknownIdentifierException(
                    state,
                    state.getToken(),
                    "const, variable, operator or opening parenthesis"
                );
            }
        } else if (VARIABLES.indexOf(token) !== -1) {
            stack.push(new Variable(token));
        } else if (/[\d-]/.test(token[0]) && token !== '-') {
            try {
                stack.push(new Const(parseInt(token)));
            } catch (e) {
                throw new BadIntegerFormatException(state, token);
            }
        } else if (operations.hasOwnProperty(token)) {
            if (operations[token][1] > stack.length) {
                throw new FewArgumentsException(
                    state,
                    token,
                    operations[token][1],
                    stack.length
                );
            }
            var currentExpr = new operations[token][0]();
            for (var t = 0; t < operations[token][1]; t++) {
                var currentArg = stack.pop();
                if (currentArg === '(') {
                    throw new FewArgumentsException(state, token, operations[token][1], t);
                }
                currentExpr.arguments.push(currentArg);
            }
            if (stack[stack.length - 1] !== '(') {
                throw new RedudantParenthesis(state);
            }
            if (state.getToken(1) !== ')') {
                throw new MissedParenthesisException(state, openPos);
            }
            stack.pop();
            currentExpr.arguments.reverse();
            stack.push(currentExpr);
        } else {
            throw new UnknownIdentifierException(
                state,
                token,
                "variable, const, operator or opening parenthesis"
            );
        }
    }
    if (stack.length !== 1) {
        console.log(stack);
        throw new EndOfLineException(state, "operator");
    }
    return stack[0];
});