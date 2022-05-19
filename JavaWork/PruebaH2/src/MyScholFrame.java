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

public class MyScholFrame extends JFrame{
	
	Connection conn=null;
	PreparedStatement state=null;
	ResultSet result=null;
	int id=-1;
	
	JPanel upPanel=new JPanel();
	JPanel midPanel=new JPanel();
	JPanel downPanel=new JPanel();
	
	JLabel fnameL=new JLabel("Name:");
	JLabel lnameL=new JLabel("Last name:");
	JLabel scoreL=new JLabel("Score:");
	//JLabel gradesL=new JLabel("Grades:");
	
	JTextField fnameTF=new JTextField();
	JTextField lnameTF=new JTextField();
	JTextField scoreTF=new JTextField();
	//JTextField gradesTF=new JTextField();
	
	JComboBox<String> studentsCombo=new JComboBox<String>();
	
	JButton addBt=new JButton("Add");
	JButton deleteBt=new JButton("Delete");
	JButton editBt=new JButton("Edit");
	JButton refreshBt=new JButton("Refresh");
	
	JTable table=new JTable();
	JScrollPane myScroll=new JScrollPane(table);
	private final JButton btnNewButton = new JButton("Check scholarship");
	private final JButton btnNewButton_1 = new JButton("Update info");
	private final JButton btnNewButton_2 = new JButton("Search by score");
	
	
	public MyScholFrame() {
		this.setSize(400, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(3,1));
		
		//upPanel-----------------------------------------
		upPanel.setLayout(new GridLayout(5, 2));
		
		upPanel.add(fnameL);
		upPanel.add(fnameTF);
		upPanel.add(lnameL);
		upPanel.add(lnameTF);
		upPanel.add(scoreL);
		upPanel.add(scoreTF);
		//upPanel.add(gradesL);
		//upPanel.add(gradesTF);
		
		getContentPane().add(upPanel);
		
		//midPanel------------------------------------
		
		midPanel.add(addBt);
		midPanel.add(deleteBt);
		midPanel.add(editBt);
		//midPanel.add(searchBt);
		midPanel.add(refreshBt);
		midPanel.add(studentsCombo);
		
		getContentPane().add(midPanel);
		midPanel.add(btnNewButton_2);
	
		midPanel.add(btnNewButton_1);
		midPanel.add(btnNewButton);
		
		
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conn=DBConnection.getConnection();
				String sql="select * from scholarship where score>=? AND score <=?+0.99";
				
				try {
					state=conn.prepareStatement(sql);
					state.setFloat(1, Float.parseFloat(scoreTF.getText()));
					state.setFloat(2, Float.parseFloat(scoreTF.getText()));
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
		});
	
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				try {
					state=conn.prepareStatement("SELECT * FROM scholarship WHERE score > 4.00");
					result=state.executeQuery();
					table.setModel(new MyModel(result));
					
				} catch (SQLException g) {
					// TODO Auto-generated catch block
					g.printStackTrace();
				} catch (Exception g) {
					// TODO Auto-generated catch block
					g.printStackTrace();
				}
				
				/*String sql="SELECT * FROM scholarship WHERE score > 4.00";
				conn=DBConnection.getConnection();
				String item="";
				
				refreshTable();
				refreshComboStudents();
				clearForm();*/
				
			}
		});
		
		
		editBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				conn=DBConnection.getConnection();
				String sql="UPDATE SCHOLARSHIP SET fname = ?, lname = ?, score = ? where id = ?"; 

				
				try {
					state=conn.prepareStatement(sql);

					state.setString(1, fnameTF.getText());
					state.setString(2, lnameTF.getText());
					state.setFloat(3, Float.parseFloat(scoreTF.getText()));
					state.setInt(4, id);
					
					state.execute();
					refreshTable();
					refreshComboStudents();
					clearForm();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String sql=("DELETE FROM SCHOLARSHIP");
					String sql2=("INSERT INTO SCHOLARSHIP (FNAME , LNAME , SCORE) SELECT FNAME, LNAME, SCORE FROM STUDENTS ");
								
					do {
						state=conn.prepareStatement(sql);
					} while (state.execute());
					state=conn.prepareStatement(sql2);
								
					state.execute();
					refreshTable();
					//refreshComboStudents();
					//clearForm();
								
					result=state.executeQuery();
					table.setModel(new MyModel(result));
					
				} catch (SQLException g) {
					// TODO Auto-generated catch block
					g.printStackTrace();
				} catch (Exception g) {
					// TODO Auto-generated catch block
					g.printStackTrace();
				}
				
			}
		});
		
		
		
		
		//downPanel-----------------------------------
		myScroll.setPreferredSize(new Dimension(350, 150));
		downPanel.add(myScroll);
		
		getContentPane().add(downPanel);
		
		
		
		addBt.addActionListener(new AddAction());
		deleteBt.addActionListener(new DeleteAction());
		//searchBt.addActionListener(new SearchAction());
		refreshBt.addActionListener(new RefreshAction());
		
		table.addMouseListener(new MouseAction());
				
		
		refreshTable();
		refreshComboStudents();
		
		this.setVisible(false);
		
	}
	
	public void refreshTable() {
		conn=DBConnection.getConnection();
		
		try {
			//state=conn.prepareStatement("DELETE FROM SCHOLARSHIP");
			//state=conn.prepareStatement("INSERT INTO SCHOLARSHIP (FNAME , LNAME , SCORE) SELECT FNAME, LNAME, SCORE FROM STUDENTS ");
			state=conn.prepareStatement("SELECT * FROM SCHOLARSHIP");
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
	
	public void refreshComboStudents() {
		
		String sql="select id, fname, lname from scholarship";
		conn=DBConnection.getConnection();
		String item="";
		
		try {
			state=conn.prepareStatement(sql);
			result=state.executeQuery();
			studentsCombo.removeAllItems();
			while(result.next()) {
				item=result.getObject(1).toString()+". "+
						result.getObject(2).toString()+" "+
						result.getObject(3).toString();
				studentsCombo.addItem(item);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void clearForm() {
		fnameTF.setText("");
		lnameTF.setText("");
		scoreTF.setText("");
		//gradesTF.setText("");
	}
	
	class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			conn=DBConnection.getConnection();
			String sql="insert into scholarship(fname, lname, score) values(?,?,?)";
			
			try {
				state=conn.prepareStatement(sql);
				state.setString(1, fnameTF.getText());
				state.setString(2, lnameTF.getText());
				//state.setString(3, sexCombo.getSelectedItem().toString());
				//state.setInt(4, Integer.parseInt(ageTF.getText()));
				state.setFloat(3, Float.parseFloat(scoreTF.getText()));
				
				state.execute();
				refreshTable();
				refreshComboStudents();
				clearForm();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
			scoreTF.setText(table.getValueAt(row, 3).toString());
			//gradesTF.setText(table.getValueAt(row, 4).toString());
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
			String sql="delete from scholarship where id=?";
			
			try {
				state=conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				refreshTable();
				refreshComboStudents();
				clearForm();
				id=-1;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
	
	/*class SearchAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn=DBConnection.getConnection();
			String sql="select * from scholarship where age=?";
			
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
		
	}*/
	
	class RefreshAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			refreshTable();
			
		}
		
	}

}
