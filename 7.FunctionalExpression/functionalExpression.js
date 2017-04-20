var variables = ["x", "y", "z", "u", "v", "w"];
var varList = {};
variables.forEach(function (value) {
    varList[value] = [variable(value), 0];
    this[value] = variable(value);
});

function variable(name) {
    return function () {
        return arguments[variables.indexOf(name)];
    }
}

function cnst(value) {
    return function () {
        return value;
    }
}
var pi = cnst(Math.PI);
var e = cnst(Math.E);

function anyNary(operator) {
    return function () {
        var expressions = arguments;
        return function () {
            var ans = [], args = arguments;
            Object.keys(expressions).forEach(i => {ans.push(expressions[i].apply(undefined, args));});
            return operator.apply(undefined, ans);
        }
    }
}

var add = anyNary((l, r) => l + r);
var subtract = anyNary((l, r) => l - r);
var multiply = anyNary((l, r) => l * r);
var divide = anyNary((l, r) => l / r);
var negate = anyNary(x => -x);
var min3 = anyNary((x, y, z) => Math.min(x, y, z));
var max5 = anyNary((x, y, z, a, b) => Math.max(x, y, z, a, b));

function getOperationByList(list) {
    return function (expr) {
        for (var operation in list) {
            if (expr === operation) {
                return [list[operation][0], list[operation][1]];
            }
        }
        return undefined;
    }
}

var getOperation = getOperationByList({
    "+": [add, 2],
    "-": [subtract, 2],
    "*": [multiply, 2],
    "/": [divide, 2],
    "negate": [negate, 1],
    "min3": [min3, 3],
    "max5": [max5, 5]
});
var getLiteral = getOperationByList({"x": [x, 0], "y": [y, 0], "z": [z, 0]});
var getSpecialConst = getOperationByList({"pi": [pi, 0], "e": [e, 0]});
var getVariable = getOperationByList(varList);

function getConst(expr) {
    var special = getSpecialConst(expr);
    if (special !== undefined) {
        return special;
    }
    if (!/^-*\d+$/.test(expr)) {
        return undefined;
    }
    return [cnst(parseInt(expr)), 0];
}

function parse(exprPoland) {
    var tokens = exprPoland.split(/\s+/).filter(function (s) {
        return s !== "";
    });
    var stack = [];
    for (var i = 0; i < tokens.length; i++) {
        var token = tokens[i];
        var variants = [getConst(token), getOperation(token), getVariable(token), getLiteral(token)].filter(
            value => value !== undefined
        );
        for (var j = 0; j < variants.length; j++) {
            var currentVariant = variants[j];
            var expr = currentVariant[0];
            var args = [];
            for (var cnt = 0; cnt < currentVariant[1]; cnt++) {
                args[cnt] = stack.pop();
            }
            expr = currentVariant[1] ? expr.apply(undefined, args.reverse()) : expr;
            stack.push(expr);
            break;
        }
    }
    return stack[0];
}

console.log(parse("x y - pi e 5 min3 * negate")(3, -5));