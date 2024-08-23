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
        // PS4 old tests
        [TestMethod]
        public void SpreadsheetDeclarationTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "5");
            s.SetContentsOfCell("A2", "5");
            s.SetContentsOfCell("A3", "5");
            s.SetContentsOfCell("A4", "5");
        }

        [TestMethod]
        public void GetCellContentsTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "5");
            double doubleResult = (double)s.GetCellContents("A1");
            Assert.AreEqual(5, doubleResult);

            s.SetContentsOfCell("A1", "I like pie.");
            string stringResult = (string)s.GetCellContents("A1");
            Assert.AreEqual("I like pie.", stringResult);

            s.SetContentsOfCell("A1", "=5+1");
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
        public void GetCellContentsNoCellTest()
        {
            Spreadsheet s = new Spreadsheet();
            Assert.AreEqual("", s.GetCellContents("A1"));
        }

        [TestMethod]
        public void NamesOfNonemptyCellsTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "5");
            s.SetContentsOfCell("A2", "5");
            s.SetContentsOfCell("A3", "5");
            s.SetContentsOfCell("A4", "5");
            string result = string.Join("", s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual("A1A2A3A4", result);
        }

        [TestMethod]
        public void EmptyCellsAddedTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "5");
            s.SetContentsOfCell("A2", "5");
            s.SetContentsOfCell("A3", "5");
            s.SetContentsOfCell("A4", "5");
            s.SetContentsOfCell("A5", "");
            s.SetContentsOfCell("A4", "");
            string result = string.Join("", s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual("A1A2A3", result);
        }

        [TestMethod]
        public void SetContentsOfCellDoubleTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "5");
            s.SetContentsOfCell("A2", "5.3");
            s.SetContentsOfCell("A3", "7.2543");
            s.SetContentsOfCell("A4", "10e+2");
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
        public void SetContentsOfCellDoubleReplaceTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "5");
            string result1 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("5", result1);

            s.SetContentsOfCell("A1", "5.3");
            string result2 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("5.3", result2);

            s.SetContentsOfCell("A1", "7.2543");
            string result3 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("7.2543", result3);

            s.SetContentsOfCell("A1", "10e+2");
            string result4 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("1000", result4);
        }

        [TestMethod]
        public void SetContentsOfCellAllTypesReplaceTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=X3+5");
            string result1 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("X3+5", result1);

            s.SetContentsOfCell("A1", "5.3");
            string result2 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("5.3", result2);

            s.SetContentsOfCell("A1", "=X3+5");
            string result3 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("X3+5", result3);

            s.SetContentsOfCell("A1", "honey");
            string result4 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("honey", result4);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetContentsOfCellDoubleNameNullTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell(null, "5");
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetContentsOfCellDoubleInvalidNameTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("@", "5");
        }

        [TestMethod]
        public void SetContentsOfCellStringTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "apple");
            s.SetContentsOfCell("A2", "mom");
            s.SetContentsOfCell("A3", "candy");
            s.SetContentsOfCell("A4", "light");
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
        public void SetContentsOfCellStringReplaceTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "apple");
            string result1 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("apple", result1);

            s.SetContentsOfCell("A1", "mom");
            string result2 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("mom", result2);

            s.SetContentsOfCell("A1", "candy");
            string result3 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("candy", result3);

            s.SetContentsOfCell("A1", "light");
            string result4 = string.Join("", s.GetCellContents("A1"));
            Assert.AreEqual("light", result4);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetContentsOfCellStringNameNullTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell(null, "apple");
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetContentsOfCellStringInvalidNameTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("@", "apple");
        }

        [TestMethod]
        public void SetContentsOfCellStringEmptyStringContentsTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "");
            string result = string.Join("", s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual(result, "");
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void SetContentsOfCellFormulaNullTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", (string)null);
        }

        [TestMethod]
        public void SetContentsOfCellFormulaTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=5+1");
            s.SetContentsOfCell("A2", "=X1+1");
            s.SetContentsOfCell("A3", "=7/14");
            s.SetContentsOfCell("A4", "=((3+5)/9)");
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
        public void SetContentsOfCellFormulaReplaceTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=5+1");
            Formula result1 = (Formula)s.GetCellContents("A1");
            Assert.AreEqual(new Formula("5+1"), result1);

            s.SetContentsOfCell("A1", "=X1+1");
            Formula result2 = (Formula)s.GetCellContents("A1");
            Assert.AreEqual(new Formula("X1+1"), result2);

            s.SetContentsOfCell("A1", "=7/14");
            Formula result3 = (Formula)s.GetCellContents("A1");
            Assert.AreEqual(new Formula("7/14"), result3);

            s.SetContentsOfCell("A1", "=((3+5)/9)");
            Formula result4 = (Formula)s.GetCellContents("A1");
            Assert.AreEqual(new Formula("((3+5)/9)"), result4);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetContentsOfCellFormulaNameNullTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell(null, "=5+1");
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void SetContentsOfCellFormulaInvalidNameTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("@", "=5+1");
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidCastException))]
        public void CellEvaluateInvalidContentTest()
        {
            Cell c = new Cell();
            c.SetContents("apple");
            c.Evaluate(s => 0);
        }

        [TestMethod()]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void CircularTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=A2");
            s.SetContentsOfCell("A2", "=A3");
            s.SetContentsOfCell("A3", "=A1");
        }

        [TestMethod()]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void CircularReplaceFormulaTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=A2");
            s.SetContentsOfCell("A2", "=A3");
            s.SetContentsOfCell("A3", "=A4+5");
            s.SetContentsOfCell("A3", "=A1");
        }

        [TestMethod()]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void CircularReplaceStringTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "=A2");
            s.SetContentsOfCell("A2", "=A3");
            s.SetContentsOfCell("A3", "poppy");
            s.SetContentsOfCell("A3", "=A1");
        }

        // PS5 tests

        // isValid
        public bool IsAllUpper(string input)
        {
            for (int i = 0; i < input.Length; i++)
            {
                if (!Char.IsNumber(input[i]) && !Char.IsUpper(input[i]))
                    return false;
            }
            return true;
        }

        // normalizer
        public string ToUpperCase(string input)
        {
            return input.ToUpper();
        }

        [TestMethod]
        public void GetCellValueTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "5");
            double doubleResult = (double)s.GetCellValue("A1");
            Assert.AreEqual(5.0, doubleResult);

            s.SetContentsOfCell("A1", "I like pie.");
            string stringResult = (string)s.GetCellValue("A1");
            Assert.AreEqual("I like pie.", stringResult);

            s.SetContentsOfCell("A1", "=5+1");
            object formulaResult = s.GetCellValue("A1");
            Assert.AreEqual(6.0, formulaResult);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void GetCellValueInvalidNameTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.GetCellValue("@");
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void GetCellValueNullNameTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.GetCellValue((string)null);
        }

        [TestMethod]
        public void GetCellValueNameDoesNotExistTest()
        {
            Spreadsheet s = new Spreadsheet();
            Assert.AreEqual("", s.GetCellValue("A1"));
        }

        [TestMethod]
        public void GetCellValueFormulaTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A3", "=7/14");
            s.SetContentsOfCell("A4", "=A3");
            Assert.AreEqual(0.5, s.GetCellValue("A4"));
        }

        [TestMethod]
        public void WriteXmlFileTest()
        {
            Spreadsheet s = new Spreadsheet();
            // Type double.
            s.SetContentsOfCell("A1", "5");
            s.SetContentsOfCell("A2", "5");
            s.SetContentsOfCell("A3", "5");
            s.SetContentsOfCell("A4", "5");
            // Type string.
            s.SetContentsOfCell("A1", "honey");
            s.SetContentsOfCell("A2", "cherry");
            s.SetContentsOfCell("A3", "apple");
            s.SetContentsOfCell("A4", "hamburger");
            // Type formula.
            s.SetContentsOfCell("A1", "=5+1");
            s.SetContentsOfCell("A2", "=X1+1");
            s.SetContentsOfCell("A3", "=7/14");
            s.SetContentsOfCell("A4", "=((3+5)/9)");
            s.Save("save.txt");
            string version = s.GetSavedVersion("save.txt");
            Assert.AreEqual(version, "default");
        }

        [TestMethod]
        public void WriteXmlFileChangedTest()
        {
            Spreadsheet s = new Spreadsheet();
            Assert.IsFalse(s.Changed);
            s.SetContentsOfCell("A1", "5");
            Assert.IsTrue(s.Changed);
            s.Save("save.txt");
            Assert.IsFalse(s.Changed);
        }

        [TestMethod]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void WriteXmlFileBadSaveTest()
        {
            AbstractSpreadsheet s = new Spreadsheet();
            s.Save("/some/nonsense/path.xml");   // you would expect this line to throw
        }

        [TestMethod]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void GetSavedVersionNameDoesNotExistTest()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "5");
            s.Save("save.txt");
            string version = s.GetSavedVersion("IDoNotExist.txt");
        }

        //public Spreadsheet(Func<string, bool> isValid, Func<string, string> normalize, string version) : base(isValid, normalize, version)
        [TestMethod]
        public void IsValidTest()
        {
            Spreadsheet s = new Spreadsheet(IsAllUpper, ToUpperCase, "uppercase");
            // Type double.
            s.SetContentsOfCell("a1", "poppy");
            string result = string.Join("", s.GetNamesOfAllNonemptyCells());
            Assert.AreEqual("A1", result);
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void BadNormalizerStringTest()
        {
            Spreadsheet s = new Spreadsheet("uppercase.txt", IsAllUpper, s => s, "uppercase");
            // Type double.
            s.SetContentsOfCell("a1", "poppy");
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void BadNormalizerDoubleTest()
        {
            Spreadsheet s = new Spreadsheet("uppercase.txt", IsAllUpper, s => s, "uppercase");
            // Type double.
            s.SetContentsOfCell("a1", "855");
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void BadNormalizerFormulaTest()
        {
            Spreadsheet s = new Spreadsheet("uppercase.txt", IsAllUpper, s => s, "uppercase");
            // Type double.
            s.SetContentsOfCell("a1", "=8+8");
        }

        [TestMethod]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void BadNormalizerVariableTest()
        {
            Spreadsheet s = new Spreadsheet("uppercase.txt", IsAllUpper, s => s, "uppercase");
            // Type double.
            s.SetContentsOfCell("A1", "=x5+8");
        }

        [TestMethod(), Timeout(2000)]
        [TestCategory("17")]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void UndoCircularContentsTest()
        {
            Spreadsheet s = new Spreadsheet();
            try
            {
                s.SetContentsOfCell("A1", "=A2+A3");
                s.SetContentsOfCell("A2", "15");
                s.SetContentsOfCell("A3", "30");
                s.SetContentsOfCell("A2", "=A3*A1");
            }
            catch (SpreadsheetReadWriteException e)
            {
                Assert.AreEqual(15, (double)s.GetCellContents("A2"), 1e-9);
                throw e;
            }
        }

        [TestMethod(), Timeout(2000)]
        [TestCategory("17")]
        [ExpectedException(typeof(SpreadsheetReadWriteException))]
        public void UndoValuesCircularTest()
        {
            Spreadsheet s = new Spreadsheet();
            try
            {
                s.SetContentsOfCell("A1", "=A2+A3");
                s.SetContentsOfCell("A2", "=5+10");
                s.SetContentsOfCell("A3", "30");
                s.SetContentsOfCell("A2", "=A3*A1");
            }
            catch (SpreadsheetReadWriteException e)
            {
                Assert.AreEqual(15, (double)s.GetCellValue("A2"), 1e-9);
                throw e;
            }
        }
    }
}
