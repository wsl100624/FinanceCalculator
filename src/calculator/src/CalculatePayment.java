package calculator.src;

import java.text.DecimalFormat;

public class CalculatePayment implements Calculator{
	
	private double totalAmt;
	private int month;
	private double interestRate;
	private double monthlyPayment; 
	private double lastPayment;
	
	
	public CalculatePayment(double totalAmt, int month, double interestRate) {
		
		this.totalAmt = totalAmt;
		this.month = month;
		this.interestRate = interestRate;
	}

	
	public String calculate() {

		if (interestRate == 0.0) {
			monthlyPayment = totalAmt / month;
			

		} else {

			double monthlyRate = (interestRate / 100) / 12;

			monthlyPayment = (totalAmt * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -month));
			
		}
				
		return String.format("%.2f", monthlyPayment);

	}


	public String getLastPayment() {
		
		double totalAmountDue = monthlyPayment * month;
		int pastMonths = month - 1; 
		DecimalFormat decimal = new DecimalFormat(".##");
		double pastPaid = Double.parseDouble(decimal.format(monthlyPayment)) * pastMonths;
				
		lastPayment = (totalAmountDue - pastPaid);
		
		return String.format("%.2f", lastPayment);
	}

}
