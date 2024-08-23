using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using SS;
using System;
using System.Collections.Generic;

namespace SpreadsheetTests
{
    [TestClass]
    public class SpreadsheetTests
    {
        [TestMethod]
        public void SpreadsheetDeclarationTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", 5);
            s.SetCellContents("A2", 5);
            s.SetCellContents("A3", 5);
            s.SetCellContents("A4", 5);
        }

        [TestMethod]
        public void GetCellContentsTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", 5);
            double doubleResult = (double)s.GetCellContents("A1");
            Assert.AreEqual(5, doubleResult);

            s.SetCellContents("A1", "I like pie.");
            string stringResult = (string)s.GetCellContents("A1");
            Assert.AreEqual("I like pie.", stringResult);

            s.SetCellContents("A1", new Formula("5+1"));
            Formula formulaResult = (Formula)s.GetCellContents("A1");
            Assert.AreEqual(new Formula("5+1"), formulaResult);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void GetCellContentsNullTest()
        {
            Spreadsheet s = new Spreadsheet();
            double doubleResult = (double)s.GetCellContents(null);
        }

        [TestMethod]
        public void NamesOfNonemptyCellsTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", 5);
            s.SetCellContents("A2", 5);
            s.SetCellContents("A3", 5);
            s.SetCellContents("A4", 5);
            string result = string.Join("", s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual("A1A2A3A4", result);
        }

        [TestMethod]
        public void EmptyCellsAddedTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", 5);
            s.SetCellContents("A2", 5);
            s.SetCellContents("A3", 5);
            s.SetCellContents("A4", 5);
            s.SetCellContents("A5", "");
            s.SetCellContents("A4", "");
            string result = string.Join("", s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual("A1A2A3", result);
        }

        [TestMethod]
        public void SetCellContentsDoubleTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", 5);
            s.SetCellContents("A2", 5.3);
            s.SetCellContents("A3", 7.2543);
            s.SetCellContents("A4", 10e+2);
            string result1 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("5", result1);
            string result2 = string.Join("", s.GetCellContents("A2"));
            Assert.AreEqual("5.3", result2);
            string result3 = string.Join("", s.GetCellContents("A3"));
            Assert.AreEqual("7.2543", result3);
            string result4 = string.Join("", s.GetCellContents("A4"));
            Assert.AreEqual("1000", result4);
        }

        [TestMethod]
        public void SetCellContentsDoubleReplaceTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", 5);
            string result1 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("5", result1);

            s.SetCellContents("A1", 5.3);
            string result2 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("5.3", result2);

            s.SetCellContents("A1", 7.2543);
            string result3 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("7.2543", result3);

            s.SetCellContents("A1", 10e+2);
            string result4 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("1000", result4);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetCellContentsDoubleNameNullTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents(null, 5);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetCellContentsDoubleInvalidNameTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("@", 5);
        }

        [TestMethod]
        public void SetCellContentsStringTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", "apple");
            s.SetCellContents("A2", "mom");
            s.SetCellContents("A3", "candy");
            s.SetCellContents("A4", "light");
            string result1 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("apple", result1);
            string result2 = string.Join("", s.GetCellContents("A2"));
            Assert.AreEqual("mom", result2);
            string result3 = string.Join("", s.GetCellContents("A3"));
            Assert.AreEqual("candy", result3);
            string result4 = string.Join("", s.GetCellContents("A4"));
            Assert.AreEqual("light", result4);
        }

        [TestMethod]
        public void SetCellContentsStringReplaceTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", "apple");
            string result1 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("apple", result1);

            s.SetCellContents("A1", "mom");
            string result2 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("mom", result2);

            s.SetCellContents("A1", "candy");
            string result3 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("candy", result3);

            s.SetCellContents("A1", "light");
            string result4 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("light", result4);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetCellContentsStringNameNullTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents(null, "apple");
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetCellContentsStringInvalidNameTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("@", "apple");
        }

        [TestMethod]
        public void SetCellContentsStringEmptyStringContentsTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", "");
            string result = string.Join("", s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual(result, "");
        }

        [TestMethod]
        public void SetCellContentsFormulaTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", new Formula("5+1"));
            s.SetCellContents("A2", new Formula("X1+1"));
            s.SetCellContents("A3", new Formula("7/14"));
            s.SetCellContents("A4", new Formula("((3+5)/9)"));
            Formula result1 = (Formula)s.GetCellContents("A1");
            Assert.AreEqual(new Formula("5+1"), result1);
            Formula result2 = (Formula)s.GetCellContents("A2");
            Assert.AreEqual(new Formula("X1+1"), result2);
            Formula result3 = (Formula)s.GetCellContents("A3");
            Assert.AreEqual(new Formula("7/14"), result3);
            Formula result4 = (Formula)s.GetCellContents("A4");
            Assert.AreEqual(new Formula("((3+5)/9)"), result4);
        }

        [TestMethod]
        public void SetCellContentsFormulaReplaceTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", new Formula("5+1"));
            Formula result1 = (Formula)s.GetCellContents("A1");
            Assert.AreEqual(new Formula("5+1"), result1);

            s.SetCellContents("A1", new Formula("X1+1"));
            Formula result2 = (Formula)s.GetCellContents("A1");
            Assert.AreEqual(new Formula("X1+1"), result2);

            s.SetCellContents("A1", new Formula("7/14"));
            Formula result3 = (Formula)s.GetCellContents("A1");
            Assert.AreEqual(new Formula("7/14"), result3);

            s.SetCellContents("A1", new Formula("((3+5)/9)"));
            Formula result4 = (Formula)s.GetCellContents("A1");
            Assert.AreEqual(new Formula("((3+5)/9)"), result4);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetCellContentsFormulaNameNullTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents(null, new Formula("5+1"));
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetCellContentsFormulaInvalidNameTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("@", new Formula("5+1"));
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void SetCellContentsFormulaNullTest()
        {
            Spreadsheet s = new Spreadsheet();
            Formula nullFormula = null;
            s.SetCellContents("A1", nullFormula);
        }

        [TestMethod]
        public void GetDirectDependentsTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", new Formula("5"));
            s.SetCellContents("A2", new Formula("A1+1"));
            s.SetCellContents("A3", new Formula("A1/3"));
            string result = string.Join("", s.SetCellContents("A1", new Formula("5")));
            Assert.AreEqual("A2A3", result);
        }

        [TestMethod]
        public void GetDirectDependentsTestFromGivenExampleTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", new Formula("3"));
            s.SetCellContents("B1", new Formula("A1 * A1"));
            s.SetCellContents("C1", new Formula("B1 + A1"));
            s.SetCellContents("D1", new Formula("B1 - C1"));
            string result = string.Join("", s.SetCellContents("A1", new Formula("3")));
            Assert.AreEqual("B1C1", result);
        }

        [TestMethod]
        public void CircularExceptionTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", new Formula("C1"));
            s.SetCellContents("C1", new Formula("3"));
            s.SetCellContents("C1", new Formula("B1 + A1"));
            Assert.AreEqual(s.GetCellContents("C1"), new Formula("3"));
        }

        [TestMethod]
        public void CircularExceptionKeepPreviousVariablesTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetCellContents("A1", new Formula("C1"));
            s.SetCellContents("C1", new Formula("D1"));
            s.SetCellContents("C1", new Formula("B1 + A1"));
            s.SetCellContents("C1", new Formula("3"));
            s.SetCellContents("C1", new Formula("F8"));
            s.SetCellContents("C1", "hello");
            s.SetCellContents("C1", new Formula("F8"));
            s.SetCellContents("C1", 3);
        }

        [TestMethod]
        public void CellEvaluateTest()
        {
            Cell c = new Cell();
            c.SetContents(new Formula("5+2+1+3*3"));
            Assert.AreEqual(c.Evaluate(s => 0), 17.0);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCastException))]
        public void CellEvaluateInvalidContentTest()
        {
            Cell c = new Cell();
            c.SetContents("apple");
            c.Evaluate(s => 0);
        }
    }
}
