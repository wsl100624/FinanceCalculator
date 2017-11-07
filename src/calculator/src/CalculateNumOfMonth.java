package calculator.src;

import java.text.DecimalFormat;

public class CalculateNumOfMonth implements Calculator {
	
	private double totalAmt;
	private double interestRate;
	private double monthlyRate;
	private double monthlyPayment;
	
	private int monthResult;
	
	private double lastPayment;
	

	public CalculateNumOfMonth(double totalAmt, double interestRate, double monthlyPayment) {
		
		this.totalAmt = totalAmt;
		this.interestRate = interestRate;
		this.monthlyPayment = monthlyPayment;
	}
	
	@Override
	public String calculate() {	
		
		if (interestRate == 0.0) {
			double result;
			result = totalAmt / monthlyPayment;
			
			monthResult = (int) (Math.round(result));
				
			return Integer.toString(monthResult);

		} else {

			monthlyRate = (interestRate / 100) / 12;

			double logValue = 1 - ((totalAmt * monthlyRate) / monthlyPayment);
			double total = -1 * (Math.log(logValue) / Math.log(1 + monthlyRate));
			
			monthResult = (int) (Math.ceil(total));
		
			return Integer.toString(monthResult);
			
		}
	}

	public String getLastPayment() {
		
		if (interestRate == 0.0) {
			int pastMonths = monthResult - 1 ; 
			DecimalFormat decimal = new DecimalFormat(".##");
			double pastPaid = Double.parseDouble(decimal.format(monthlyPayment)) * pastMonths;
			
			lastPayment = totalAmt - pastPaid;
		} else {
			double realMonthlyPayment = (totalAmt * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -monthResult));
			double totalAmountDue = realMonthlyPayment * monthResult;
			int pastMonths = monthResult - 1 ; 
			
			double pastPaid = monthlyPayment * pastMonths;
			
			lastPayment = totalAmountDue - pastPaid;
		}
		
		
		
		return String.format("%.2f", lastPayment);
	}
	

}
