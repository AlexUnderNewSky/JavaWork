import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class NewFrame extends JFrame{
	
	JTabbedPane tab=new JTabbedPane();
	private final JPanel nnewP = new JPanel();
	private final JButton btnNewButton = new JButton("Employee");
	private final JButton btnNewButton_1 = new JButton("Students");
	private final JButton btnNewButton_2 = new JButton("Scholarship");
	
	public NewFrame() {
		this.setSize(400, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		nnewP.setForeground(Color.WHITE);
		nnewP.setBackground(Color.WHITE);
		tab.setBackground(Color.WHITE);
		tab.add(nnewP,"List");
		tab.setTitleAt(0, "");
		
		
		getContentPane().add(tab);
		
		tab.addTab("\u041E\u0442\u043A\u0440\u044B\u0442\u044C \u0444\u043E\u0440\u043C\u0443", null, nnewP, null);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MyFrame().setVisible(true);
			}
		});
		
		nnewP.add(btnNewButton);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MyStuFrame().setVisible(true);
			}
		});
		
		nnewP.add(btnNewButton_1);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new MyScholFrame().setVisible(true);
				
			}
		});
		
		nnewP.add(btnNewButton_2);
		
		
		this.setVisible(true);
	}

}
