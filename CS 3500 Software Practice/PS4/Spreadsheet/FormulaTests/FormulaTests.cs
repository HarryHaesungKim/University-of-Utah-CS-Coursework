using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace FormulaTests
{
    [TestClass]
    public class FormulaTests
    {
        public string toUpper(string s)
        {
            return s.ToUpper();
        }

        public bool IsAllUpper(string input)
        {
            for (int i = 0; i < input.Length; i++)
            {
                if (Regex.IsMatch(input[i].ToString(), @"^[a-zA-Z]")){
                    if (!Char.IsUpper(input[i]))
                        return false;
                }
            }
            return true;
        }

        [TestMethod]
        public void ClassScientificNotationTest()
        {
            Formula f = new Formula("7e-5");
            object result = f.Evaluate(s => 0);
            Assert.AreEqual(result, 0.00007);
        }

        [TestMethod]
        public void SingleNumberTest()
        {
            Formula f = new Formula("1");
            object result = f.Evaluate(s => 0);
            Assert.AreEqual(result, 1.0);
        }

        [TestMethod]
        public void ScientificNotationTest()
        {
            Formula f = new Formula("10e2");
            object result = f.Evaluate(s => 0);
            Assert.AreEqual(result, 1000.0);
        }

        [TestMethod]
        public void SimpleAddingTest()
        {
            Formula f = new Formula("1 + 2");
            object result = f.Evaluate(s => 0);
            Assert.AreEqual(result, 3.0);
        }

        [TestMethod]
        public void SimpleAddingDoublesTest()
        {
            Formula f = new Formula("1.5 + 2.4");
            object result = f.Evaluate(s => 0);
            Assert.AreEqual(result, 3.9);
        }

        [TestMethod]
        public void VariableTest()
        {
            Formula f = new Formula("1.5 + 2.4 + A23");
            object result = f.Evaluate(s => 4.0);
            Assert.AreEqual(result, 7.9);
        }

        [TestMethod]
        public void VariableNormalizerTest()
        {
            Formula f = new Formula("1.5 + 2.4 + a23", s => toUpper(s), s => IsAllUpper(s));
            HashSet<string> expected = new HashSet<string>() { "A23" };
            Assert.IsTrue(expected.SetEquals(f.GetVariables()));
        }

        [TestMethod]
        public void EvaluateTest()
        {
            Formula f = new Formula("(1.5 + 2.4 * 3.6 + (6 * A23 * (2.9)) / 2 + (3+(3-(9*2))) + _12sAAS9996969) * 1e2");
            object result = f.Evaluate(s => 4.0);
            Assert.AreEqual(result, 3694.0);
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void InvalidVariableTest()
        {
            Formula f = new Formula("1 + 2a");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void singleOperatorInvalidTest()
        {
            Formula f = new Formula("+");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void InvalidSymbolTest()
        {
            Formula f = new Formula("@#");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void DoubleOperatorInvalidTest()
        {
            Formula f = new Formula("1 ++ 2");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void OpenParenthesisTest()
        {
            Formula f = new Formula("(1 + 2");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void ExtraClosingparenthesisTest()
        {
            Formula f = new Formula("(1 + 2))");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void ParenthesisOverkillFailTest()
        {
            Formula f = new Formula("((((((1 + (2))))))");
        }

        [TestMethod]
        public void GetVariablesTest()
        {
            Formula f = new Formula("1.5 + 2.4 + A23 + _44 + aRefW");
            HashSet<string> expected = new HashSet<string>() {"A23", "_44", "aRefW"};
            Assert.IsTrue(expected.SetEquals(f.GetVariables()));
        }

        [TestMethod]
        public void ToStringTest()
        {
            Formula f = new Formula("1.5 + 2.4 + A23 + _44 + aRefW");
            string expected = "1.5+2.4+A23+_44+aRefW";
            Assert.AreEqual(expected, f.ToString());
        }

        [TestMethod]
        public void SimpleEqualsTest()
        {
            Formula f = new Formula("1.5");
            Formula g = new Formula("1.5");
            Assert.IsTrue(f.Equals(g));
        }

        [TestMethod]
        public void SimpleEquationEqualsTest()
        {
            Formula f = new Formula("1+5*3/a7");
            Formula g = new Formula("1 + 5 * 3 / a7");
            Assert.IsTrue(f.Equals(g));
        }

        [TestMethod]
        public void DecimalEqualsTest()
        {
            Formula f = new Formula("1.500");
            Formula g = new Formula("1.5");
            Assert.IsTrue(f.Equals(g));
        }

        [TestMethod]
        public void ScientificNotationEqualsTest()
        {
            Formula f = new Formula("1e2");
            Formula g = new Formula("100");
            Assert.IsTrue(f.Equals(g));
        }


        [TestMethod]
        public void SimpleEquationEqualsFailTest()
        {
            Formula f = new Formula("1+5*3/a7");
            Formula g = new Formula("1 + 5 * 3 / a6");
            Assert.IsFalse(f.Equals(g));
        }

        [TestMethod]
        public void UnevenLengthStringEqualsTest()
        {
            Formula f = new Formula("1+5*3/a7");
            Formula g = new Formula("1 + 5 * 3 / a7 + 99");
            Assert.IsFalse(f.Equals(g));
        }

        [TestMethod]
        public void ObjectIsNullEqualsTest()
        {
            Formula f = new Formula("1+5*3/a7");
            Formula g = null;
            Assert.IsFalse(f.Equals(g));
        }

        [TestMethod]
        public void ObjectIsNotFormulaEqualsTest()
        {
            Formula f = new Formula("1+5*3/a7");
            string g = "1+5*3/a7";
            Assert.IsFalse(f.Equals(g));
        }

        [TestMethod]
        public void EqualsOperatorTest()
        {
            Formula f = new Formula("1+5*3/a7");
            Formula g = new Formula("1 + 5 * 3 / a7");
            Assert.IsTrue(f==g);
        }

        [TestMethod]
        public void EqualsOperatorBothNullsTest()
        {
            Formula f = null;
            Formula g = null;
            Assert.IsTrue(f == g);
        }

        [TestMethod]
        public void NotEqualsOperatorTest()
        {
            Formula f = new Formula("1+5*3/a7");
            Formula g = new Formula("1 + 5 * 3 / a7 +99");
            bool result = f != g;
            Assert.IsTrue(result);
        }
        [TestMethod]
        public void NotEqualsOperatorBothNullsTest()
        {
            Formula f = null;
            Formula g = null;
            Assert.IsFalse(f != g);
        }

        [TestMethod]
        public void HashCodeAreEqualTest()
        {
            Formula f = new Formula("1+5*3/a7");
            Formula g = new Formula("1+5*3/a7");
            int fhc = f.GetHashCode();
            int ghc = g.GetHashCode();
            Assert.AreEqual(fhc, ghc);
        }

        [TestMethod]
        public void HashCodeAreNotEqualTest()
        {
            Formula f = new Formula("1+5*3/a7");
            Formula g = new Formula("1+5*3/a7+33");
            int fhc = f.GetHashCode();
            int ghc = g.GetHashCode();
            Assert.AreNotEqual(fhc, ghc);
        }

        [TestMethod]
        public void FormulaErrorTest()
        {
            Formula f = new Formula("1/0");
            Assert.IsInstanceOfType(f.Evaluate(s => 0), typeof(FormulaError));
        }


        [TestMethod]
        public void ParenthesesFormulaErrorTest()
        {
            Formula f = new Formula("1/(1-1)");
            Assert.IsInstanceOfType(f.Evaluate(s => 0), typeof(FormulaError));
        }

        [TestMethod]
        public void FormulaErrorWithVariableTest()
        {
            Formula f = new Formula("1/a23", s => "0", s => s.Equals("0"));
            Formula g = new Formula("a12/a23", s => "0", s => s.Equals("0"));
            Assert.IsInstanceOfType(f.Evaluate(s => 0), typeof(FormulaError));
            Assert.IsInstanceOfType(g.Evaluate(s => 0), typeof(FormulaError));
        }
    }
}
