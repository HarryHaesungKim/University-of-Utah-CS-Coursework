// Skeleton written by Joe Zachary for CS 3500, September 2013
// Read the entire skeleton carefully and completely before you
// do anything else!

// Version 1.1 (9/22/13 11:45 a.m.)

// Change log:
//  (Version 1.1) Repaired mistake in GetTokens
//  (Version 1.1) Changed specification of second constructor to
//                clarify description of how validation works

// (Daniel Kopta) 
// Version 1.2 (9/10/17) 

// Change log:
//  (Version 1.2) Changed the definition of equality with regards
//                to numeric tokens

// (Harry Kim)
// Version 1.3 (2/11/21)

// Change log:
//  (Version 1.3) Added code to methods to make a funtioning class.

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace SpreadsheetUtilities
{
    // Author: Harry Kim, Spring 2021
    // University of Utah

    /// <summary>
    /// Represents formulas written in standard infix notation using standard precedence
    /// rules.  The allowed symbols are non-negative numbers written using double-precision 
    /// floating-point syntax (without unary preceeding '-' or '+'); 
    /// variables that consist of a letter or underscore followed by 
    /// zero or more letters, underscores, or digits; parentheses; and the four operator 
    /// symbols +, -, *, and /.  
    /// 
    /// Spaces are significant only insofar that they delimit tokens.  For example, "xy" is
    /// a single variable, "x y" consists of two variables "x" and y; "x23" is a single variable; 
    /// and "x 23" consists of a variable "x" and a number "23".
    /// 
    /// Associated with every formula are two delegates:  a normalizer and a validator.  The
    /// normalizer is used to convert variables into a canonical form, and the validator is used
    /// to add extra restrictions on the validity of a variable (beyond the standard requirement 
    /// that it consist of a letter or underscore followed by zero or more letters, underscores,
    /// or digits.)  Their use is described in detail in the constructor and method comments.
    /// </summary>
    public class Formula
    {
        private List<string> substrings;
        private string rawFormula;

        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically invalid,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer is the identity function, and the associated validator
        /// maps every string to true.
        /// </summary>
        public Formula(String formula) :
            this(formula, s => s, s => true)
        {
        }

        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically incorrect,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer and validator are the second and third parameters,
        /// respectively.  
        /// 
        /// If the formula contains a variable v such that normalize(v) is not a legal variable, 
        /// throws a FormulaFormatException with an explanatory message. 
        /// 
        /// If the formula contains a variable v such that isValid(normalize(v)) is false,
        /// throws a FormulaFormatException with an explanatory message.
        /// 
        /// Suppose that N is a method that converts all the letters in a string to upper case, and
        /// that V is a method that returns true only if a string consists of one letter followed
        /// by one digit.  Then:
        /// 
        /// new Formula("x2+y3", N, V) should succeed
        /// new Formula("x+y3", N, V) should throw an exception, since V(N("x")) is false
        /// new Formula("2x+y3", N, V) should throw an exception, since "2x+y3" is syntactically incorrect.
        /// </summary>
        public Formula(String formula, Func<string, string> normalize, Func<string, bool> isValid)
        {
            rawFormula = formula;

            // Initializing a double for bool isADouble.
            double i = 0;

            // Initializing the substrings list.
            substrings = new List<string>();

            // Getting rid of white spaces and adding each separate string into the substringArray
            List<string> potentialTokens = Regex.Split(String.Concat(formula.Where(c => !Char.IsWhiteSpace(c))), "(\\()|(\\))|(-)|(\\+)|(\\*)|(/)").ToList<string>();
            //List<string> potentialTokens = GetTokens(formula);


            bool prevIsOperator = true;

            int numOfOpenParenthesis = 0;

            bool partOfScientificNotation = false;

            string scientificNotationString = "";

            int index = -1;

            foreach (string s in GetTokens(formula))
            {
                index++;
                // Do nothing for random empty spaces
                if (s.Equals(""))
                {
                    if (potentialTokens.Count <= 1)
                    {
                        throw new FormulaFormatException("Formula contains an illegal variable.");
                    }
                    continue;
                }
                bool isADouble = double.TryParse(s, out i);

                if (isADouble)
                {
                    if (partOfScientificNotation)
                    {
                        scientificNotationString = scientificNotationString + s;
                        substrings.Add(Double.Parse(scientificNotationString, System.Globalization.NumberStyles.Float).ToString());
                        scientificNotationString = "";
                        partOfScientificNotation = false;
                        prevIsOperator = false;
                        continue;
                    }

                    // Catching no operator between numbers case.
                    if (!prevIsOperator)
                    {
                        throw new FormulaFormatException("Formula contains an illegal variable.");
                    }

                    substrings.Add(double.Parse(s).ToString());
                    prevIsOperator = false;
                    continue;
                }

                if (!isADouble)
                {
                    //Checks to see if input is in scientific notation.
                    if (Regex.IsMatch(s, @"^[0-9]+[e]{1}") || Regex.IsMatch(s, @"^[0-9]*[.]{1}[0-9]*[e]{1}"))
                    {
                        //substrings[index] = Double.Parse(s, System.Globalization.NumberStyles.Float).ToString();
                        scientificNotationString = scientificNotationString + s;
                        partOfScientificNotation = true;
                        continue;
                    }

                    // Checks to see if variable is valid.
                    if (Regex.IsMatch(s, @"^[a-zA-Z_]{1}[a-zA-Z0-9_]*"))
                    {
                        if (isValid(normalize(s)))
                        {
                            // Catching no operator between numbers case.
                            if (!prevIsOperator)
                            {
                                throw new FormulaFormatException("Formula contains an illegal variable.");
                            }
                            substrings.Add(normalize(s));
                            prevIsOperator = false;
                            continue;
                        }
                    }
                    // Checks to see if operators are not repeated.
                    else if (Regex.IsMatch(s, @"^[-+*\/]"))
                    {
                        if (Regex.IsMatch(s, @"^[-+]") && partOfScientificNotation)
                        {
                            scientificNotationString = scientificNotationString + s;
                            continue;
                        }

                        if (!prevIsOperator && index != 0)
                        {
                            substrings.Add(s);
                            prevIsOperator = true;
                            continue;
                        }
                    }
                    // Checks to see if parentheses are properly closed.
                    else if (s.Equals("("))
                    {
                        substrings.Add(s);
                        numOfOpenParenthesis++;
                        continue;
                    }
                    else if (s.Equals(")"))
                    {
                        if (!(numOfOpenParenthesis < 0))
                        {
                            substrings.Add(s);
                            numOfOpenParenthesis--;
                            continue;
                        }
                    }
                    throw new FormulaFormatException("Formula contains an illegal variable.");
                }
            }
            if(numOfOpenParenthesis != 0 || prevIsOperator)
            {
                throw new FormulaFormatException("Formula contains an illegal variable.");
            }
        }

        /// <summary>
        /// Evaluates this Formula, using the lookup delegate to determine the values of
        /// variables.  When a variable symbol v needs to be determined, it should be looked up
        /// via lookup(normalize(v)). (Here, normalize is the normalizer that was passed to 
        /// the constructor.)
        /// 
        /// For example, if L("x") is 2, L("X") is 4, and N is a method that converts all the letters 
        /// in a string to upper case:
        /// 
        /// new Formula("x+7", N, s => true).Evaluate(L) is 11
        /// new Formula("x+7").Evaluate(L) is 9
        /// 
        /// Given a variable symbol as its parameter, lookup returns the variable's value 
        /// (if it has one) or throws an ArgumentException (otherwise).
        /// 
        /// If no undefined variables or divisions by zero are encountered when evaluating 
        /// this Formula, the value is returned.  Otherwise, a FormulaError is returned.  
        /// The Reason property of the FormulaError should have a meaningful explanation.
        ///
        /// This method should never throw an exception.
        /// </summary>
        public object Evaluate(Func<string, double> lookup)
        {
            // Stacks for values and operators
            Stack<string> valueStack = new Stack<string>();
            Stack<string> operatorStack = new Stack<string>();

            // Initializing an int for bool isAnInteger
            double i = 0;

            foreach (String t in substrings)
            {
                // Checking to see if t is an integer
                bool isADouble = double.TryParse(t, out i);

                // If t is an integer
                if (isADouble)
                {
                    // Perform operation if stack is not empty and the next operator is * or /
                    if (operatorStack.Count != 0 && (operatorStack.Peek().Equals("*") || operatorStack.Peek().Equals("/")))
                    {
                        if (operatorStack.Peek().Equals("/") && t.Equals("0"))
                        {
                            return new FormulaError("Cannot divide by zero.");
                        }
                        double result = PerformOperation(operatorStack.Pop(), valueStack.Pop(), t); ;
                        valueStack.Push(result.ToString());
                    }

                    // Otherwise push t into valueStack 
                    else
                    {
                        valueStack.Push(t);
                    }
                }

                // If t is a variable
                else if (!isADouble && Regex.IsMatch(t, @"^[a-zA-Z_]{1}[a-zA-Z0-9_]*"))
                {
                    // Catching any invalid variables.
                    string variable = "";
                    try
                    {
                        variable = lookup(t).ToString();
                    }
                    catch
                    {
                        return new FormulaError("Invalid variable");
                    }

                    // Perform operation if stack is not empty and the next operator is * or /
                    if (operatorStack.Count != 0 && (operatorStack.Peek().Equals("*") || operatorStack.Peek().Equals("/")))
                    {
                        if (operatorStack.Peek().Equals("/") && variable.Equals("0"))
                        {
                            return new FormulaError("Cannot divide by zero."); ;
                        }
                        double result = PerformOperation(operatorStack.Pop(), valueStack.Pop(), variable);
                        valueStack.Push(result.ToString());
                    }

                    // Otherwise push t into valueStack 
                    else
                    {
                        valueStack.Push(variable);
                    }
                }

                // If t is either "+" or "-"
                else if (t.Equals("+") || t.Equals("-"))
                {
                    // Using extentions
                    if (operatorStack.IsOnTop("+") || operatorStack.IsOnTop("-"))
                    {
                        double result = PerformOperation(operatorStack.Pop(), valueStack.Pop(), valueStack.Pop()); ;
                        valueStack.Push(result.ToString());
                    }
                    operatorStack.Push(t);
                }

                // If t is either "*" or "/"
                else if (t.Equals("*") || t.Equals("/"))
                {
                    operatorStack.Push(t);
                }

                // If t is "("
                else if (t.Equals("("))
                {
                    operatorStack.Push(t);
                }

                // If t is ")"
                else if (t.Equals(")"))
                {
                    if (operatorStack.IsOnTop("+") || operatorStack.IsOnTop("-"))
                    {
                        double result = PerformOperation(operatorStack.Pop(), valueStack.Pop(), valueStack.Pop()); ;
                        valueStack.Push(result.ToString());
                    }

                    if (operatorStack.IsOnTop("("))
                    {
                        operatorStack.Pop();
                        if (!(operatorStack.IsOnTop("*") || operatorStack.IsOnTop("/")))
                            continue;
                    }

                    if (operatorStack.IsOnTop("*") || operatorStack.IsOnTop("/"))
                    {
                        string divisor = valueStack.Pop();
                        if(divisor.Equals("0"))
                        {
                            return new FormulaError("Cannot divide by zero."); ;
                        }
                        double result = PerformOperation(operatorStack.Pop(), valueStack.Pop(), divisor); ;
                        valueStack.Push(result.ToString());
                    }
                }

                // If t is an empty string, do nothing
                else if (t.Equals(""))
                {

                }
            }

            // Return final value if operator stack is empty
            if (operatorStack.Count == 0 && valueStack.Count == 1)
            {
                double a = double.Parse(valueStack.Pop());
                return a;
            }

            // Perform final operation of remaining two values, then return the final value
            else
            {
                return PerformOperation(operatorStack.Pop(), valueStack.Pop(), valueStack.Pop());
            }
        }

        /// <summary>
        /// Enumerates the normalized versions of all of the variables that occur in this 
        /// formula.  No normalization may appear more than once in the enumeration, even 
        /// if it appears more than once in this Formula.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x+y*z", N, s => true).GetVariables() should enumerate "X", "Y", and "Z"
        /// new Formula("x+X*z", N, s => true).GetVariables() should enumerate "X" and "Z".
        /// new Formula("x+X*z").GetVariables() should enumerate "x", "X", and "z".
        /// </summary>
        public IEnumerable<String> GetVariables()
        {
            // Returns a hashset of variables by checking which tokens are variables.
            HashSet<string> variables = new HashSet<string>();
            foreach(string s in substrings)
            {
                if(Regex.IsMatch(s, @"^[a-zA-Z_]{1}[a-zA-Z0-9_]*"))
                {
                    variables.Add(s);
                }
            }
            IReadOnlyCollection<string> variablesToReturn = variables;
            return variablesToReturn;
        }

        /// <summary>
        /// Returns a string containing no spaces which, if passed to the Formula
        /// constructor, will produce a Formula f such that this.Equals(f).  All of the
        /// variables in the string should be normalized.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x + y", N, s => true).ToString() should return "X+Y"
        /// new Formula("x + Y").ToString() should return "x+Y"
        /// </summary>
        public override string ToString()
        {
            string formulaString = "";
            foreach (string s in substrings)
            {
                formulaString = formulaString + s;
            }
            return formulaString;
        }

        /// <summary>
        /// If obj is null or obj is not a Formula, returns false.  Otherwise, reports
        /// whether or not this Formula and obj are equal.
        /// 
        /// Two Formulae are considered equal if they consist of the same tokens in the
        /// same order.  To determine token equality, all tokens are compared as strings 
        /// except for numeric tokens and variable tokens.
        /// Numeric tokens are considered equal if they are equal after being "normalized" 
        /// by C#'s standard conversion from string to double, then back to string. This 
        /// eliminates any inconsistencies due to limited floating point precision.
        /// Variable tokens are considered equal if their normalized forms are equal, as 
        /// defined by the provided normalizer.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        ///  
        /// new Formula("x1+y2", N, s => true).Equals(new Formula("X1  +  Y2")) is true
        /// new Formula("x1+y2").Equals(new Formula("X1+Y2")) is false
        /// new Formula("x1+y2").Equals(new Formula("y2+x1")) is false
        /// new Formula("2.0 + x7").Equals(new Formula("2.000 + x7")) is true
        /// </summary>
        public override bool Equals(object obj)
        {
            // Checking for null or not formula cases.
            if(obj is null || !(obj is Formula) || this is null || !(this is Formula))
            {
                return false;
            }
            IEnumerable<string> thisToken = GetTokens(this.ToString());
            IEnumerator<string> matchingToken = GetTokens(obj.ToString()).GetEnumerator();

            // Automatically return false if object and this are not the same size.
            if (thisToken.Count() != GetTokens(obj.ToString()).Count())
                return false;
            double i = 0;
            foreach (string s in thisToken)
            {
                matchingToken.MoveNext();

                // Checking to see if token in rawFormula is an integer
                bool thisIsADouble = double.TryParse(s, out i);
                bool matchingIsADouble = double.TryParse(matchingToken.Current, out i);
                if (thisIsADouble && thisIsADouble)
                {
                    // making sure that number tokens are the same
                    if (Double.Parse(s).ToString().Equals(Double.Parse(matchingToken.Current).ToString()))
                    {
                        continue;
                    }
                    else
                        return false;
                }
                else
                {
                    if (s.Equals(matchingToken.Current))
                    {
                        continue;
                    }
                    return false;
                }
            }
            return true;
        }

        /// <summary>
        /// Reports whether f1 == f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return true.  If one is
        /// null and one is not, this method should return false.
        /// </summary>
        public static bool operator ==(Formula f1, Formula f2)
        {
            if (f1 is null && f2 is null)
                return true;

            // Checking for null or not formula cases.
            if (f1 is null || !(f1 is Formula) || f2 is null || !(f2 is Formula))
            {
                return false;
            }

            return f1.Equals(f2);
        }

        /// <summary>
        /// Reports whether f1 != f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return false.  If one is
        /// null and one is not, this method should return true.
        /// </summary>
        public static bool operator !=(Formula f1, Formula f2)
        {
            if (f1 is null && f2 is null)
                return false;

            // Checking for null or not formula cases.
            if (f1 is null || !(f1 is Formula) || f2 is null || !(f2 is Formula))
            {
                return false;
            }

            return !f1.Equals(f2);
        }

        /// <summary>
        /// Returns a hash code for this Formula.  If f1.Equals(f2), then it must be the
        /// case that f1.GetHashCode() == f2.GetHashCode().  Ideally, the probability that two 
        /// randomly-generated unequal Formulae have the same hash code should be extremely small.
        /// </summary>
        public override int GetHashCode()
        {
            int hashCodeNumber = 0;
            // copy the string as UTF-8 bytes.
            byte[] tokenBytes = new byte[this.ToString().Length];
            // adds all utf-8 bytes into one number.
            for (int i = 0; i < this.ToString().Length; ++i)
            {
                hashCodeNumber = hashCodeNumber + (byte)this.ToString()[i];
            }

            return hashCodeNumber;
    }

        /// <summary>
        /// Given an expression, enumerates the tokens that compose it.  Tokens are left paren;
        /// right paren; one of the four operator symbols; a string consisting of a letter or underscore
        /// followed by zero or more letters, digits, or underscores; a double literal; and anything that doesn't
        /// match one of those patterns.  There are no empty tokens, and no token contains white space.
        /// </summary>
        private static IEnumerable<string> GetTokens(String formula)
        {
            // Patterns for individual tokens
            String lpPattern = @"\(";
            String rpPattern = @"\)";
            String opPattern = @"[\+\-*/]";
            String varPattern = @"[a-zA-Z_](?: [a-zA-Z_]|\d)*";
            String doublePattern = @"(?: \d+\.\d* | \d*\.\d+ | \d+ ) (?: [eE][\+-]?\d+)?";
            String spacePattern = @"\s+";

            // Overall pattern
            String pattern = String.Format("({0}) | ({1}) | ({2}) | ({3}) | ({4}) | ({5})",
                                            lpPattern, rpPattern, opPattern, varPattern, doublePattern, spacePattern);

            // Enumerate matching tokens that don't consist solely of white space.
            foreach (String s in Regex.Split(formula, pattern, RegexOptions.IgnorePatternWhitespace))
            {
                if (!Regex.IsMatch(s, @"^\s*$", RegexOptions.Singleline))
                {
                    yield return s;
                }
            }

        }

        /// <summary>
        /// A private helper method that performs an operation from doubles and operators in string form.
        /// </summary>
        /// <param name="op">
        /// The given operation in string form.
        /// </param>
        /// <param name="left">
        /// A given double in string form.
        /// </param>
        /// <param name="right">
        /// A given double in string form.
        /// </param>
        /// <returns>
        /// A double result from the given operation and the two integers.
        /// </returns>
        private static double PerformOperation(string op, string left, string right)
        {
            // Different cases for different operations.
            switch (op)
            {
                case "*": return Convert.ToDouble(left) * Convert.ToDouble(right);
                case "/": return Convert.ToDouble(left) / Convert.ToDouble(right);
                case "+": return Convert.ToDouble(left) + Convert.ToDouble(right);
                case "-": return Convert.ToDouble(right) - Convert.ToDouble(left);
                default: throw new ArgumentException();
            }
        }
    }

    /// <summary>
    /// Used to report syntactic errors in the argument to the Formula constructor.
    /// </summary>
    public class FormulaFormatException : Exception
    {
        /// <summary>
        /// Constructs a FormulaFormatException containing the explanatory message.
        /// </summary>
        public FormulaFormatException(String message)
            : base(message)
        {
        }
    }

    /// <summary>
    /// Used as a possible return value of the Formula.Evaluate method.
    /// </summary>
    public struct FormulaError
    {
        /// <summary>
        /// Constructs a FormulaError containing the explanatory reason.
        /// </summary>
        /// <param name="reason"></param>
        public FormulaError(String reason)
            : this()
        {
            Reason = reason;
        }

        /// <summary>
        ///  The reason why this FormulaError was created.
        /// </summary>
        public string Reason { get; private set; }
    }

    /// <summary>
    /// This class holds a method that serves as an extention for the C# Stack Class
    /// </summary>
    public static class StackExtentions
    {
        /// <summary>
        /// This method checks to make sure that a given stack is not empty and that a certain string is on top of the given stack.
        /// </summary>
        /// <param name="s">
        /// The given stackl
        /// </param>
        /// <param name="c">
        /// The string being looked for.
        /// </param>
        /// <returns>
        /// True if the string being looked for is on the top of the given stack, and false otherwise.
        /// </returns>
        public static bool IsOnTop(this Stack<string> s, string c)
        {
            if (s.Count < 1)
                return false;
            return s.Peek().Equals(c);
        }
    }
}

