using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TipCalculator
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void computeTipButton_Click(object sender, EventArgs e)
        {
            string totalBill = enterBillTextBox.Text;
            Double.TryParse(totalBill, out double totalBillDouble);

            string tipPercent = topPercentTextBox.Text;

            Double.TryParse(tipPercent, out double tipPercentageDouble);

            double tip = totalBillDouble * (tipPercentageDouble/100);

            tipAmountTextBox.Text = tip.ToString();

            totalTextBox.Text = (totalBillDouble + tip).ToString();
        }

        private void label1_Click_1(object sender, EventArgs e)
        {

        }

        private void enterBillTextBox_TextChanged(object sender, EventArgs e)
        {
            bool valid = Double.TryParse(enterBillTextBox.Text, out double totalBillDouble);
            bool valid2 = Double.TryParse(tipAmountTextBox.Text, out double tipPercent);
            computeTipButton.Enabled = valid && valid2;
        }
    }
}
