using System;

namespace FormulaEvaluatorTester
{
    class Program
    {
        static int SimpleLookUp(String s)
        {
            return 2;
        }
        static void Main(string[] args)
        {
            // Addition
            String expressionAdd1 = "2+9";
            Console.WriteLine(expressionAdd1 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionAdd1, SimpleLookUp));
            String expressionAdd2 = "1+2+3";
            Console.WriteLine(expressionAdd2 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionAdd2, SimpleLookUp));
            String expressionAdd3 = "1+1+1+1+1";
            Console.WriteLine(expressionAdd3 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionAdd3, SimpleLookUp));

            // Subtraction
            String expressionSubt1 = "9-2";
            Console.WriteLine(expressionSubt1 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionSubt1, SimpleLookUp));
            String expressionSubt2 = "2-9";
            Console.WriteLine(expressionSubt2 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionSubt2, SimpleLookUp));
            String expressionSubt3 = "100-1-1-1-1-1";
            Console.WriteLine(expressionSubt3 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionSubt3, SimpleLookUp));

            // Multiplication
            String expressionMult1 = "2*3";
            Console.WriteLine(expressionMult1 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionMult1, SimpleLookUp));
            String expressionMult2 = "3*2";
            Console.WriteLine(expressionMult2 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionMult2, SimpleLookUp));
            String expressionMult3 = "2*2*2*2*2";
            Console.WriteLine(expressionMult3 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionMult3, SimpleLookUp));

            // Division
            String expressionDiv1 = "4/2";
            Console.WriteLine(expressionDiv1 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionDiv1, SimpleLookUp));
            String expressionDiv2 = "2/4";
            Console.WriteLine(expressionDiv2 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionDiv2, SimpleLookUp));
            String expressionDiv3 = "32/2/2/2/2/2";
            Console.WriteLine(expressionDiv3 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionDiv3, SimpleLookUp));
            String expressionDiv4 = "30/(5*2)";
            Console.WriteLine(expressionDiv4 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionDiv4, SimpleLookUp));
            String expressionDiv5 = "30/10";
            Console.WriteLine(expressionDiv5 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionDiv5, SimpleLookUp));
            // String expressionDiv4 = "2/0";
            // Console.WriteLine(expressionDiv4 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionDiv4, SimpleLookUp));

            // Parentheses
            String expressionPar1 = "(5+5)*7";
            Console.WriteLine(expressionPar1 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionPar1, SimpleLookUp));
            String expressionPar2 = "(10/5)-(10/10)";
            Console.WriteLine(expressionPar2 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionPar2, SimpleLookUp));
            String expressionPar3 = "(4+(30/(5*2)))";
            Console.WriteLine(expressionPar3 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionPar3, SimpleLookUp));

            // Whitespaces
            String expressionWS1 = "(2 + 35) * 2";
            Console.WriteLine(expressionWS1 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionWS1, SimpleLookUp));
            String expressionWS2 = "(2           + 35) *              2";
            Console.WriteLine(expressionWS2 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionWS2, SimpleLookUp));

            // SimpleLookup
            String expressionSLU1 = "(2+35)*A7";
            Console.WriteLine(expressionSLU1 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionSLU1, SimpleLookUp));

            // Argument Exception Error
            try
            {
                String expressionAE1 = "(2+35)*2@";
                Console.WriteLine(expressionAE1 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionAE1, SimpleLookUp));
            }
            catch (ArgumentException)
            {
                Console.WriteLine("ArgumentException successfully thrown.");
            }

            try
            {
                String expressionAE2 = "2+/2";
                Console.WriteLine(expressionAE2 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionAE2, SimpleLookUp));
            }
            catch (ArgumentException)
            {
                Console.WriteLine("ArgumentException successfully thrown.");
            }

            try
            {
                String expressionAE2 = "((2+2)*3";
                Console.WriteLine(expressionAE2 + "=" + FormulaEvaluator.Evaluator.Evaluate(expressionAE2, SimpleLookUp));
            }
            catch (ArgumentException)
            {
                Console.WriteLine("ArgumentException successfully thrown.");
            }
        }
    }
}
