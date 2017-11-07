package calculator.src;

import java.text.DecimalFormat;

public class CalculateCapitalAmt implements Calculator {

	private double interestRate;
	private int month;
	private double monthlyPayment;
	private double lastPayment;
	
	
	
	public CalculateCapitalAmt(double interestRate, int month, double monthlyPayment) {
		
		this.interestRate = interestRate;
		this.month = month;
		this.monthlyPayment = monthlyPayment;
	}
	
	@Override
	public String calculate() {
		
		double totalAmt;

		if (interestRate == 0.0) {
			totalAmt = Math.round(month * monthlyPayment);
			
			int pastMonths = month - 1 ; 
			double pastPaid = monthlyPayment * pastMonths;
			lastPayment = totalAmt - pastPaid;
		
		} else {
			double monthlyRate = (interestRate / 100) / 12;

			totalAmt = monthlyPayment * (1 - Math.pow(1 + monthlyRate, -month)) / monthlyRate;
			
			double totalAmountDue = Math.round(monthlyPayment*month);
			int pastMonths = month - 1 ; 
			double pastPaid = monthlyPayment * pastMonths;
			
			lastPayment = totalAmountDue - pastPaid;
		}

		return String.format("%.2f", totalAmt);
	}

	public String getLastPayment() {
		
		return String.format("%.2f", lastPayment);
	}

}
