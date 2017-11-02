package calculator.src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MainScreen extends JFrame {

	// - Private Properties

	private JPanel contentPane;

	private JButton calcBtn;
	private JButton saveBtn;
	private JButton clearBtn;

	private JTextField amtTextField; 			// >0
	private JTextField monthTextField; 			// 12 month ~ 72 month
	private JTextField aprTextField; 			// 0% ~ 75%
	private JTextField paymentTextField; 		// >0
	
	private CalculatePayment payment;
	private CalculateNumOfMonth numOfMonth;
	private CalculateAPR apr;
	private CalculateCapitalAmt totalAmt;
	
	String firstMonthPayment;
	

	// - Main

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	// - GUI Method

	// Setup window
	public MainScreen() {
		init();
	}

	// Initialize window
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 700);
		setTitle("Finance Calculator");
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Capital Amount: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_1 = new JLabel("Number of Months: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_2 = new JLabel("APR (Up to 75%): ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_3 = new JLabel("Monthly Payment: ");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);

		amtTextField = new JTextField();
		amtTextField.setColumns(10);

		monthTextField = new JTextField();
		monthTextField.setColumns(10);

		aprTextField = new JTextField();
		aprTextField.setColumns(10);

		paymentTextField = new JTextField();
		paymentTextField.setColumns(10);

		calcBtn = new JButton("Calculate");
		calcBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					if (aprTextField.getText().isEmpty() & amtTextField.getText() != null
							& monthTextField.getText() != null & paymentTextField.getText() != null) {

						// APR
						int numOfMonth = Integer.parseInt(monthTextField.getText());
						double monthlyPayment = Double.parseDouble(paymentTextField.getText());
						double totalAmt = Double.parseDouble(amtTextField.getText());

						if (monthlyPayment <= 0 || totalAmt <= 0) {
							JOptionPane.showMessageDialog(MainScreen.this, "Invalid Entry");
						} else {
							
							apr = new CalculateAPR(totalAmt, monthlyPayment, numOfMonth);
							aprTextField.setText(apr.calculate());
							firstMonthPayment = apr.getFirstMonthPayment();
						}

					} else if (monthTextField.getText().isEmpty() & amtTextField.getText() != null
							& aprTextField.getText() != null & paymentTextField.getText() != null) {

						// Number Of Month
						double totalAmt = Double.parseDouble(amtTextField.getText());
						double interestRate = Double.parseDouble(aprTextField.getText());
						double monthlyPayment = Double.parseDouble(paymentTextField.getText());

						if (totalAmt <= 0 || interestRate < 0 || interestRate > 75 || monthlyPayment <= 0) {
							JOptionPane.showMessageDialog(MainScreen.this, "Invalid Entry");
						} else {
							
							numOfMonth = new CalculateNumOfMonth(totalAmt, interestRate, monthlyPayment);
							monthTextField.setText(numOfMonth.calculate());
							firstMonthPayment = numOfMonth.getFirstMonthPayment();
						}

					} else if (amtTextField.getText().isEmpty() & aprTextField.getText() != null
							& monthTextField.getText() != null & paymentTextField.getText() != null) {

						// Capital Amount
						double interestRate = Double.parseDouble(aprTextField.getText());
						int numOfMonth = Integer.parseInt(monthTextField.getText());
						double monthlyPayment = Double.parseDouble(paymentTextField.getText());

						if (interestRate < 0 || interestRate > 75 || monthlyPayment <= 0) {
							JOptionPane.showMessageDialog(MainScreen.this, "Invalid Entry");
						} else {
							
							
							totalAmt = new CalculateCapitalAmt(interestRate, numOfMonth, monthlyPayment);
							amtTextField.setText(totalAmt.calculate());	
							firstMonthPayment = totalAmt.getFirstMonthPayment();
						}

					} else {

						// Monthly Payment
						double totalAmt = Double.parseDouble(amtTextField.getText());
						int numOfMonth = Integer.parseInt(monthTextField.getText());
						double interestRate = Double.parseDouble(aprTextField.getText());

						if (totalAmt <= 0 || interestRate < 0 || interestRate > 75) {
							JOptionPane.showMessageDialog(MainScreen.this, "Invalid Entry");
						} else {
							
							payment = new CalculatePayment(totalAmt, numOfMonth, interestRate);
							paymentTextField.setText(payment.calculate());
							firstMonthPayment = payment.getFirstMonthPayment();
						}

					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(MainScreen.this, "Please enter 3 terms");
				}

			}
		});

		// setup JTable
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 400, 410, 200);
		this.getContentPane().add(scrollPane);

		JTable table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Capital Amount", "Month", "   APR  %", "First Payment", "Last Payment" }));

		// change column width
		TableColumn column = null;
		for (int i = 0; i < 4; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 1) {
				column.setPreferredWidth(70); // second column
			} else if (i == 2) {
				column.setPreferredWidth(70);
			} else if (i == 3) {
				column.setPreferredWidth(120);
			} else if (i == 4) {
				column.setPreferredWidth(120);
			} else {
				column.setPreferredWidth(90);
			}
		}

		// to center cell's text
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < 4; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		scrollPane.setViewportView(table);

		// setup Buttons
		calcBtn.setForeground(SystemColor.controlText);
		calcBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		calcBtn.setBackground(SystemColor.control);
		calcBtn.setBounds(200, 200, 100, 30);

		saveBtn = new JButton("Save To Grid");
		saveBtn.setForeground(SystemColor.controlText);
		saveBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		saveBtn.setBackground(SystemColor.control);
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// save to grid
				if (amtTextField.getText().isEmpty() || aprTextField.getText().isEmpty()
						|| monthTextField.getText().isEmpty() || paymentTextField.getText().isEmpty()) {

					JOptionPane.showMessageDialog(MainScreen.this, "Nothing to save, please finish a calculation");

				} else {

					int numbOfColumn = table.getModel().getColumnCount();
					Object[] columnPosition = new Object[numbOfColumn];
					columnPosition[0] = "$ " + amtTextField.getText();
					columnPosition[1] = monthTextField.getText();
					columnPosition[2] = aprTextField.getText() + " %";
					columnPosition[3] = "$ " + firstMonthPayment;
					columnPosition[4] = "$ " + paymentTextField.getText();
					((DefaultTableModel) table.getModel()).addRow(columnPosition);

					// clear textFields
					amtTextField.setText(null);
					monthTextField.setText(null);
					aprTextField.setText(null);
					paymentTextField.setText(null);
				}
			}
		});

		clearBtn = new JButton("Clear");
		clearBtn.setForeground(Color.BLACK);
		clearBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		clearBtn.setBackground(SystemColor.menu);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				amtTextField.setText(null);
				monthTextField.setText(null);
				aprTextField.setText(null);
				paymentTextField.setText(null);
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(calcBtn,
										GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane
										.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPane.createSequentialGroup().addGap(33)
														.addGroup(gl_contentPane
																.createParallelGroup(Alignment.LEADING, false)
																.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE,
																		124, Short.MAX_VALUE)
																.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addContainerGap().addComponent(lblNewLabel_1,
																GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addComponent(paymentTextField).addComponent(aprTextField)
												.addComponent(monthTextField).addComponent(amtTextField,
														GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))))
						.addContainerGap(76, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup().addContainerGap(161, Short.MAX_VALUE)
										.addComponent(saveBtn).addGap(158))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(175)
								.addComponent(clearBtn, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(178, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(45)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel).addComponent(
						amtTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(37)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1)
						.addComponent(monthTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(35)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2)
						.addComponent(aprTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(37)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_3)
						.addComponent(paymentTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(34).addComponent(calcBtn, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(clearBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
				.addComponent(saveBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addGap(20)));
		contentPane.setLayout(gl_contentPane);
	}
}
