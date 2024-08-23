using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;

namespace FormulaEvaluator
{
    /// <summary>
    /// This class contains all methods for solving integer arithmetic expressions written using standard infix notation.
    /// </summary>
    public static class Evaluator
    {
        /// <summary>
        /// This declares a new type.
        /// The type represents a method signature.
        /// Variables of this type will hold methods as values.
        /// </summary>
        /// <param name="v"></param>
        /// <returns></returns>
        public delegate int Lookup(String v);

        /// <summary>
        /// Evaluates a given string expression.
        /// </summary>
        /// <param name="exp">
        /// The given string expression.
        /// </param>
        /// <param name="variableEvaluator">
        /// A delegate to be provided by the user.
        /// </param>
        /// <returns>
        /// An int result of the evaluated string expression.
        /// </returns>
        public static int Evaluate(String exp, Lookup variableEvaluator)
        {
            // Stacks for values and operators
            Stack<string> valueStack = new Stack<string>();
            Stack<string> operatorStack = new Stack<string>();

            // Getting rid of white spaces
            string expNoWhiteSpaces = String.Concat(exp.Where(c => !Char.IsWhiteSpace(c)));
            string[] substrings = Regex.Split(expNoWhiteSpaces, "(\\()|(\\))|(-)|(\\+)|(\\*)|(/)");

            // Initializing an int for bool isAnInteger
            int i = 0;

            foreach (String t in substrings)
            {
                // Checking to see if t is an integer
                bool isAnInteger = int.TryParse(t, out i);

                // If t is an integer
                if (isAnInteger)
                {
                    // Perform operation if stack is not empty and the next operator is * or /
                    if (operatorStack.Count != 0 && (operatorStack.Peek().Equals("*") || operatorStack.Peek().Equals("/")))
                    {
                        int result = PerformOperation(operatorStack.Pop(), valueStack.Pop(), t); ;
                        valueStack.Push(result.ToString());
                    }

                    // Otherwise push t into valueStack 
                    else
                    {
                        valueStack.Push(t);
                    }
                }

                // If t is a variable (not an integer and contains only letters and numbers)
                else if (!isAnInteger && Regex.IsMatch(t, @"^[a-zA-Z]+[0-9]+"))
                {
                    string variable = variableEvaluator(t).ToString();
                    // Perform operation if stack is not empty and the next operator is * or /
                    if (operatorStack.Count != 0 && (operatorStack.Peek().Equals("*") || operatorStack.Peek().Equals("/")))
                    {
                        int result = PerformOperation(operatorStack.Pop(), valueStack.Pop(), variable);
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
                        int result = PerformOperation(operatorStack.Pop(), valueStack.Pop(), valueStack.Pop()); ;
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
                        int result = PerformOperation(operatorStack.Pop(), valueStack.Pop(), valueStack.Pop()); ;
                        valueStack.Push(result.ToString());
                    }

                    if (operatorStack.IsOnTop("("))
                    {
                        operatorStack.Pop();
                        if(!(operatorStack.IsOnTop("*") || operatorStack.IsOnTop("/")))
                            continue; 
                    }

                    if (operatorStack.IsOnTop("*") || operatorStack.IsOnTop("/"))
                    {
                        string divisor = valueStack.Pop();
                        int result = PerformOperation(operatorStack.Pop(), valueStack.Pop(), divisor); ;
                        valueStack.Push(result.ToString());
                    }
                    else
                    {
                        throw new ArgumentException();
                    }
                }

                // If t is an empty string, do nothing
                else if (t.Equals("")) { }

                // Throws an exception for an invalid input
                else
                {
                    throw new ArgumentException();
                }
            }

            // Return final value if operator stack is empty
            if (operatorStack.Count == 0 && valueStack.Count == 1)
            {
                return Int32.Parse(valueStack.Pop());
            }

            // Perform final operation of remaining two values, then return the final value
            else if (operatorStack.Count == 1 && valueStack.Count == 2)
            {
                return PerformOperation(operatorStack.Pop(), valueStack.Pop(), valueStack.Pop());
            }

            // Else throw an exeption for invalid expression
            else
            {
                throw new ArgumentException();
            }
        }

        /// <summary>
        /// Performs an integer operation from integers and operators in string form.
        /// </summary>
        /// <param name="op">
        /// The given operation in string form.
        /// </param>
        /// <param name="left">
        /// A given integer in string form.
        /// </param>
        /// <param name="right">
        /// A given integer in string form.
        /// </param>
        /// <returns>
        /// An integer result from the given operation and the two integers.
        /// </returns>
        public static int PerformOperation(string op, string left, string right)
        {
            // Different cases for different operations.
            switch (op)
            {
                case "*": return Convert.ToInt32(left) * Convert.ToInt32(right);
                case "/":
                    if (right.Equals("0"))
                    {
                        throw new ArgumentException();
                    }
                    return Convert.ToInt32(left) / Convert.ToInt32(right);
                case "+": return Convert.ToInt32(left) + Convert.ToInt32(right);
                case "-": return Convert.ToInt32(right) - Convert.ToInt32(left);
                default: throw new ArgumentException();
            }
        }
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