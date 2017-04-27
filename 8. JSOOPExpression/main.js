var VARIABLES = ["x", "y", "z", "u", "v", "w"];

function Variable(name) {
    this.name = name;
    this.arguments = [];
}
Variable.prototype = {
    evaluate: function () {
        return arguments[VARIABLES.indexOf(this.name)];
    },
    toString: function () {
        return this.name;
    },
    diff: function(varName) {
        return new Const(varName === this.name ? 1 : 0);
    },
    simplify: function () {
        return this;
    }
};

function Const(value) {
    this.value = value;
    this.arguments = [];
}
Const.prototype = {
    evaluate: function () {
        return this.value;
    },
    toString: function () {
        return this.value.toString();
    },
    diff: function() {
        return new Const(0);
    },
    simplify: function () {
        return this;
    }
};

function objectToArray(obj) {
    var arr = [];
    Object.keys(obj).forEach(function (key) {
        arr[key] = obj[key];
    });
    return arr;
}

function operationFactory(length, name, operator, diffOperator) {
    function AbstractOperation() {
        this.arguments = objectToArray(arguments);
    }
    AbstractOperation.prototype = {
        length: length,
        operator: operator,
        evaluate: function () {
            var extArgs = arguments;
            var evaluatedOperands = this.arguments.map(function (arg) {
                return arg.evaluate.apply(arg, extArgs);
            });
            return operator.apply(null, evaluatedOperands);
        },
        toString: function () {
            var answer = "";
            this.arguments.forEach(function (expr) {
                answer += expr.toString() + " ";
            });
            return answer + name;
        },
        diff: function (varName) {
            return diffOperator(varName, this.arguments);
        },
        simplify: function () {
            if (this.arguments.every(function (obj) {
                    return obj instanceof Const
                })) {
                return new Const(this.evaluate());
            } else {
                var copy = Object.create(this);
                copy.arguments = this.arguments.map(function(arg) {
                    return arg.simplify();
                });
                return copy.toString() !== this.toString() ? copy.simplify() : copy;
            }
        }
    };
    return AbstractOperation;
}

var factory = operationFactory();

var Add = operationFactory(2, "+", function (a, b) {return a + b;}, function(varName, exprs) {
    return new Add(
        exprs[0].diff(varName),
        exprs[1].diff(varName)
    );
});

Add.prototype.simplify = function () {
    var answer = factory.prototype.simplify.call(this);
    for (var key in answer.arguments) {
        if (answer.arguments[key].toString() === "0") {
            return answer.arguments[1 - parseInt(key)];
        }
    }
    return answer;
};

var Multiply = operationFactory(2, "*", function (a, b) {return a * b;}, function(varName, exprs) {
    return new Add(
        new Multiply(
            exprs[0].diff(varName),
            exprs[1]
        ),
        new Multiply(
            exprs[0],
            exprs[1].diff(varName)
        )
    );
});

Multiply.prototype.simplify = function () {
    var answer = factory.prototype.simplify.call(this);
    for (var key in answer.arguments) {
        if (answer.arguments[key].toString() === "0") {
            return answer.arguments[key];
        }
        if (answer.arguments[key].toString() === "1") {
            return answer.arguments[1 - parseInt(key)];
        }
    }
    return answer;
};

var Subtract = operationFactory(2, "-", function (a, b) {return a - b;}, function(varName, exprs) {
    return new Subtract(
        exprs[0].diff(varName),
        exprs[1].diff(varName)
    );
});

Subtract.prototype.simplify = function () {
    var answer = factory.prototype.simplify.call(this);
    for (var key in answer.arguments) {
        if (answer.arguments[key].toString() === "0") {
            return (key === '0' ? new Negate(answer.arguments[1]) : answer.arguments[0]);
        }
    }
    return answer;
};

var Divide = operationFactory(2, "/", function (a, b) {return a / b;}, function(varName, exprs) {
    return new Divide(
        new Subtract(
            new Multiply(
                exprs[0].diff(varName),
                exprs[1]
            ),
            new Multiply(
                exprs[0],
                exprs[1].diff(varName)
            )
        ),
        new Multiply(
            exprs[1],
            exprs[1]
        )
    );
});

Divide.prototype.simplify = function () {
    var answer = factory.prototype.simplify.call(this);
    if (answer instanceof Const) {
        return answer;
    }
    if (answer.arguments[0].toString() === "0" || answer.arguments[1].toString() === "1") {
        return answer.arguments[0];
    }
    return answer;
};

var Sinh = operationFactory(1, "sinh", function (x) {return (Math.exp(x) - Math.exp(-x)) / 2.0}, function (varName, exprs) {
    return new Multiply(new Cosh(exprs[0]), exprs[0].diff(varName));
});

var Cosh = operationFactory(1, "cosh", function (x) {return (Math.exp(x) + Math.exp(-x)) / 2.0}, function (varName, exprs) {
    return new Multiply(new Sinh(exprs[0]), exprs[0].diff(varName));
});

var Negate = operationFactory(1, "negate", function (x) {return -x}, function (varName, exprs) {
    return new Negate(exprs[0].diff(varName));
});

var Power = operationFactory(2, "pow", function (a, n) {return Math.pow(a, n)}, function(varName, exprs) {
    return new Multiply(
        new Power(
            exprs[0],
            new Subtract(
                exprs[1],
                new Const(1)
            )
        ),
        new Add(
            new Multiply(
                exprs[1],
                exprs[0].diff(varName)
            ),
            new Multiply(
                exprs[0],
                new Multiply(
                    new Log(exprs[0]),
                    exprs[1].diff(varName)
                )
            )
        )
    );
});

var Log = operationFactory(2, "log", function (a, b) {return Math.log(Math.abs(b)) / Math.log(Math.abs(a))}, function(varName, exprs) {
    return new Divide(
        new Subtract(
            new Divide(
                new Multiply(
                    new Log(
                        exprs[0],
                        new Const(Math.E)
                    ),
                    exprs[1].diff(varName)
                ),
                exprs[1]
            ),
            new Divide(
                new Multiply(
                    new Log(
                        exprs[1],
                        new Const(Math.E)
                    ),
                    exprs[0].diff(varName)
                ),
                exprs[0]
            )
        ),
        new Multiply(
            new Log(
                exprs[0],
                new Const(Math.E)
            ),
            new Log(
                exprs[0],
                new Const(Math.E)
            )
        )
    )
});

var Square = operationFactory(1, "square", function(x) {return x * x;}, function(varName, exprs) {
    return new Multiply(
        new Const(2),
        new Multiply(
            exprs[0],
            exprs[0].diff(varName)
        )
    )
});

var Sqrt = operationFactory(1, "sqrt", function(x) {return Math.sqrt(Math.abs(x));}, function(varName, exprs) {
    return new Divide(
        new Multiply(
            exprs[0],
            exprs[0].diff(varName)
        ),
        new Multiply(
            new Const(2),
            new Sqrt(
                new Multiply(
                    new Multiply(
                        exprs[0],
                        exprs[0]
                    ),
                    exprs[0]
                )
            )
        )
    )
});

Negate.prototype.simplify = function () {
    var answer = factory.prototype.simplify.call(this);
    if (answer.toString() === "0 negate") {
        return new Const(0);
    }
    return answer;
};

var operations = {
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide,
    "sinh": Sinh,
    "cosh": Cosh,
    "negate": Negate,
    "pow": Power,
    "log": Log,
    "square": Square,
    "sqrt": Sqrt
};

function isVariable(token) {
    return VARIABLES.indexOf(token) !== -1;
}

function isConst(token) {
    return /^-*\d+$/.test(token);
}

function parse(exprPoland) {
    var tokens = exprPoland.split(/\s+/).filter(function (s) {
        return s.length;
    });
    var stack = [];
    for (var i = 0; i < tokens.length; i++) {
        var token = tokens[i];
        if (isVariable(token)) {
            stack.push(new Variable(token));
        } else if (isConst(token)) {
            stack.push(new Const(parseInt(token)));
        } else {
            var currentExpr = new operations[token]();
            for (var t = 0; t < currentExpr.length; t++) {
                currentExpr.arguments.push(stack.pop());
            }
            currentExpr.arguments.reverse();
            stack.push(currentExpr);
        }
    }
    return stack[0];
}

console.log(parse('x x * y y * + z z * +').toString());

