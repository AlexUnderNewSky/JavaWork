import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MyFrame extends JFrame{
	
	Connection conn=null;
	PreparedStatement state=null;
	ResultSet result=null;
	int id=-1;
	
	JPanel upPanel=new JPanel();
	JPanel midPanel=new JPanel();
	JPanel downPanel=new JPanel();
	
	JLabel fnameL=new JLabel("Name:");
	JLabel lnameL=new JLabel("Last name:");
	JLabel sexL=new JLabel("Sex:");
	JLabel ageL=new JLabel("Age:");
	JLabel salaryL=new JLabel("Salary:");
	
	JTextField fnameTF=new JTextField();
	JTextField lnameTF=new JTextField();
	JTextField ageTF=new JTextField();
	JTextField salaryTF=new JTextField();
	
	String[] item= {"Man", "Woman"};
	JComboBox<String> sexCombo=new JComboBox<String>(item);
	JComboBox<String> employeeCombo=new JComboBox<String>();
	
	JButton addBt=new JButton("Add");
	JButton deleteBt=new JButton("Delete");
	JButton editBt=new JButton("Edit");
	JButton searchBt=new JButton("Search by age");
	JButton refreshBt=new JButton("Refresh");
	
	JTable table=new JTable();
	JScrollPane myScroll=new JScrollPane(table);
	
	
	public MyFrame() {
		this.setSize(400, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLayout(new GridLayout(3,1));
		
		//upPanel-----------------------------------------
		upPanel.setLayout(new GridLayout(5, 2));
		
		upPanel.add(fnameL);
		upPanel.add(fnameTF);
		upPanel.add(lnameL);
		upPanel.add(lnameTF);
		upPanel.add(sexL);
		upPanel.add(sexCombo);
		upPanel.add(ageL);
		upPanel.add(ageTF);
		upPanel.add(salaryL);
		upPanel.add(salaryTF);
		
		this.add(upPanel);
		
		//midPanel------------------------------------
		
		midPanel.add(addBt);
		midPanel.add(deleteBt);
		midPanel.add(editBt);
		midPanel.add(searchBt);
		midPanel.add(refreshBt);
		midPanel.add(employeeCombo);
		
		this.add(midPanel);
		
		//downPanel-----------------------------------
		myScroll.setPreferredSize(new Dimension(350, 150));
		downPanel.add(myScroll);
		
		this.add(downPanel);
		
		
		
		addBt.addActionListener(new AddAction());
		editBt.addActionListener(new EditAction());
		deleteBt.addActionListener(new DeleteAction());
		searchBt.addActionListener(new SearchAction());
		refreshBt.addActionListener(new RefreshAction());
		
		table.addMouseListener(new MouseAction());
				
		
		refreshTable();
		refreshComboEmployee();
		
		this.setVisible(false);
		
	}
	
	public void refreshTable() {
		conn=DBConnection.getConnection();
		
		try {
			state=conn.prepareStatement("select * from employee");
			result=state.executeQuery();
			table.setModel(new MyModel(result));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void refreshComboEmployee() {
		
		String sql="select id, fname, lname from employee";
		conn=DBConnection.getConnection();
		String item="";
		
		try {
			state=conn.prepareStatement(sql);
			result=state.executeQuery();
			employeeCombo.removeAllItems();
			while(result.next()) {
				item=result.getObject(1).toString()+". "+
						result.getObject(2).toString()+" "+
						result.getObject(3).toString();
				employeeCombo.addItem(item);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void clearForm() {
		fnameTF.setText("");
		lnameTF.setText("");
		ageTF.setText("");
		salaryTF.setText("");
	}
	
	class EditAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			conn=DBConnection.getConnection();
			String sql = "update employee set fname=?, lname=?, sex=?, age=?, salary=? where id=?";
			
			try {
				state=conn.prepareStatement(sql);
				state.setString(1, fnameTF.getText());
				state.setString(2, lnameTF.getText());
				state.setString(3, sexCombo.getSelectedItem().toString());
				state.setInt(4, Integer.parseInt(ageTF.getText()));
				state.setFloat(5, Float.parseFloat(salaryTF.getText()));
				state.setInt(6, id);
				
				state.execute();
				refreshTable();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		
	}
	
	
	
	class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			conn=DBConnection.getConnection();
			String sql="insert into employee(fname, lname, sex, age, salary) values(?,?,?,?,?)";
			
			try {
				state=conn.prepareStatement(sql);
				state.setString(1, fnameTF.getText());
				state.setString(2, lnameTF.getText());
				state.setString(3, sexCombo.getSelectedItem().toString());
				state.setInt(4, Integer.parseInt(ageTF.getText()));
				state.setFloat(5, Float.parseFloat(salaryTF.getText()));
				state.setInt(6, id);
				
				state.execute();
				refreshTable();
				refreshComboEmployee();
				clearForm();
				
			} catch (SQLException g) {
				// TODO Auto-generated catch block
				g.printStackTrace();
			}
			
		}
		
	
	}
	class MouseAction implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row=table.getSelectedRow();
			id=Integer.parseInt(table.getValueAt(row, 0).toString());
			fnameTF.setText(table.getValueAt(row, 1).toString());
			lnameTF.setText(table.getValueAt(row, 2).toString());
			ageTF.setText(table.getValueAt(row, 4).toString());
			salaryTF.setText(table.getValueAt(row, 5).toString());
			if(table.getValueAt(row, 3).toString().equals("Man")) {
				sexCombo.setSelectedIndex(0);
			}
			else {
				sexCombo.setSelectedIndex(1);
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class DeleteAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			conn=DBConnection.getConnection();
			String sql="delete from employee where id=?";
			
			try {
				state=conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				refreshTable();
				refreshComboEmployee();
				clearForm();
				id=-1;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
	
	class SearchAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn=DBConnection.getConnection();
			String sql="select * from employee where age=?";
			
			try {
				state=conn.prepareStatement(sql);
				state.setInt(1, Integer.parseInt(ageTF.getText()));
				result=state.executeQuery();
				table.setModel(new MyModel(result));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
	
	class RefreshAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			refreshTable();
			
		}
		
	}

}
