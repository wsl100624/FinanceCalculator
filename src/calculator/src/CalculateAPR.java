package calculator.src;


public class CalculateAPR implements Calculator {

	private double totalAmt;
	private double monthlyPayment;
	private int month;
	private double apr;
	private double lastPayment;

	public CalculateAPR(double totalAmt, double monthlyPayment, int month) {

		this.totalAmt = totalAmt;
		this.monthlyPayment = monthlyPayment;
		this.month = month;
	}

	@Override
	public String calculate() {

		double low = 0.0;
		double mid = 0.0;
		double high = 75.0;

		while (low <= high) {

			// set the "mid" which is the temporary APR
			mid = (low + high) / 2;

			// calculate the Principal with the temporary APR
			double tempMonthlyRate = (mid / 100) / 12;

			double testTotalAmt = monthlyPayment * (1 - Math.pow(1 + tempMonthlyRate, -month)) / tempMonthlyRate;

			// modify the temporary APR
			if (testTotalAmt < totalAmt) {

				high = mid - 0.001;

			} else if (testTotalAmt > totalAmt) {

				low = mid + 0.001;

			} else {
				apr = Math.round(mid * 100) / 100.0d;
				return Double.toString(apr);

			}
		}
		
		apr = Math.round(mid * 100) / 100.0d;
		return Double.toString(apr);
	}

	public String getlastPayment() {
		
		if (apr == 0.0) {
			int pastMonths = month - 1 ; 
			double pastPaid = monthlyPayment* pastMonths;
			
			lastPayment = totalAmt - pastPaid;
			System.out.println(pastPaid);
			
		} else {
			double monthlyRate = (apr/100)/12;
			double realMonthlyPayment = (totalAmt * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -month));
			double totalAmountDue = Math.round(realMonthlyPayment * month);
			int pastMonths = month - 1 ; 
			double pastPaid = realMonthlyPayment * pastMonths;
			
			lastPayment = totalAmountDue - pastPaid;
		}
		
		return String.format("%.2f", lastPayment);
	}

}
