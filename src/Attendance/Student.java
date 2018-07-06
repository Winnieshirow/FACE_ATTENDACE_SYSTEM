/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Attendance;

import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.opencv.core.Core;

/**
 *
 * @author shirow
 */
public class Student extends javax.swing.JFrame{
    Connection conn; // connection to the database
    ResultSet rs;
    ResultSet rst6;
    ResultSet rs5;
    ResultSet rst;
    ResultSet rs2;
    ResultSet rs3;
    ResultSet rs4;
    Statement st2;
    Statement st;
    PreparedStatement ps;
    PreparedStatement pst;

    /**
     * Creates new form Student
     */
    public Student() {
        initComponents();
        fillcombo();
        fillcombotwo();
        currentdate();
        filltextfield();
        filljPanel2();
       
    }
        private void filljPanel2(){
             
        }
        public void filltextfield(){
            try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String dbURL = "jdbc:mysql://localhost:3306/user_info";
            String dbUser = "root";
            String dbPass = "shirow12";
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass); 
        
        System.out.println("connected to db");
         reg_number.getDocument().addDocumentListener(new DocumentListener()  {
             @Override
    public void insertUpdate(DocumentEvent e) {
        try {    
        String z = (String) reg_number.getText();
        System.out.println("registration number: " + reg_number.getText());
        
        String sql2 = "SELECT firstname,student_id FROM student WHERE registration_number ='"+z+"'";
         
        ps = conn.prepareStatement(sql2);
            rst =ps.executeQuery();
            while(rst.next()){
                
                firstname.setText(rst.getString("firstname"));
                sid.setText(rst.getString("student_id"));
               
            }
            try{
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/user_info","root","shirow12");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select snapshot from student where firstname = '"+firstname.getText()+"'");
                if(rs.next()){
                    byte[] img = rs.getBytes("snapshot");

                    //Resize The ImageIcon
                    ImageIcon image = new ImageIcon(img);
                    Image im = image.getImage();
                    Image myImg = im.getScaledInstance(photo.getWidth(), photo.getHeight(),Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(myImg);
                    photo.setIcon(newImage);
                }
                
                else{
                    JOptionPane.showMessageDialog(null, "No Data");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        
                }
                catch(Exception evt){JOptionPane.showMessageDialog(null, "ERROR");}    
    
         }

                @Override
                public void changedUpdate(DocumentEvent de) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void removeUpdate(DocumentEvent de) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
     });
            }
        catch(Exception e){JOptionPane.showMessageDialog(null, "ERROR");} 
        }
        
    
    
    public void currentdate(){
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        
        date.setText(year+"/"+(month+1)+"/"+day);
        
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hour = cal.get(Calendar.HOUR);
        
        time.setText(hour+":"+(minute)+":"+second);
        
        Calendar now = Calendar.getInstance();
        
       String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thusday","Friday", "Saturday" };
       System.out.println("Current day is : " + strDays[now.get(Calendar.DAY_OF_WEEK) - 1]);
       days.setText(strDays[now.get(Calendar.DAY_OF_WEEK) - 1]);
       
    }
    
    public void fillcombo(){
        try{
            // connects to the database
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String dbURL = "jdbc:mysql://localhost:3306/user_info";
            String dbUser = "root";
            String dbPass = "shirow12";
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            
            st = conn.createStatement();
            String sql="SELECT degree_name FROM degrees";
             rs = st.executeQuery(sql);

             while(rs.next()){
                 
             degree.addItem(rs.getString("degree_name"));

             }

          }catch(Exception e){

            JOptionPane.showMessageDialog(null, "ERROR");

            } finally {
            try{
              rs.close();
                  st.close();
                 conn.close();
              }
              catch(Exception e) {
                JOptionPane.showMessageDialog(null, "ERROR closed");
                               }
                  }
    }
    public void fillcombotwo(){
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String dbURL = "jdbc:mysql://localhost:3306/user_info";
            String dbUser = "root";
            String dbPass = "shirow12";
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);   
        
        
        degree.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent event) {
       try {    
        JComboBox<String> combo = (JComboBox<String>) event.getSource();
        String s = (String) degree.getSelectedItem();
        System.out.println("Degree: " + degree.getSelectedItem());
        String sql2 = "SELECT t1.course_name FROM courses t1 INNER JOIN degrees t2 ON t1.degree_id = t2.degree_id WHERE t2.degree_name = '"+s+"'";
         course.removeAllItems();
        ps = conn.prepareStatement(sql2);
            rs2 =ps.executeQuery();
            while(rs2.next()){
                
                course.addItem(rs2.getString("course_name"));
            }
        
                }
                catch(Exception e){JOptionPane.showMessageDialog(null, e);}
            }    
            }); 
        year.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent event) {
       try {    
        JComboBox<String> combo = (JComboBox<String>) event.getSource();
        String s = (String) degree.getSelectedItem();
        String yr = (String) year.getSelectedItem();
        System.out.println("Selected year: " + year.getSelectedItem());
        String sql3 = "SELECT t1.course_name FROM courses t1 INNER JOIN degrees t2 ON t1.degree_id = t2.degree_id WHERE (t2.degree_name = '"+s+"' AND t1.year = '"+yr+"')";
         course.removeAllItems();
        ps = conn.prepareStatement(sql3);
            rs3 =ps.executeQuery();
            while(rs3.next()){
                
                course.addItem(rs3.getString("course_name"));
            }
        
                }
                catch(Exception e){JOptionPane.showMessageDialog(null, e);}
            }    
            });
        semester.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent event) {
       try {    
        JComboBox<String> combo = (JComboBox<String>) event.getSource();
        String s = (String) degree.getSelectedItem();
        String yr = (String) year.getSelectedItem();
        String sem = (String) semester.getSelectedItem();
        
        System.out.println("Selected semester: " + semester.getSelectedItem());
        String sql3 = "SELECT t1.course_name FROM courses t1 INNER JOIN degrees t2 ON t1.degree_id = t2.degree_id WHERE (t2.degree_name = '"+s+"' AND t1.year = '"+yr+"' AND t1.semester = '"+sem+"')";
         course.removeAllItems();
        ps = conn.prepareStatement(sql3);
            rs4 =ps.executeQuery();
            while(rs4.next()){
                
                course.addItem(rs4.getString("course_name"));
            }
        
                }
                catch(Exception e){JOptionPane.showMessageDialog(null, e);}
            }    
            });
        days.getDocument().addDocumentListener(new DocumentListener()  {
             @Override
    public void insertUpdate(DocumentEvent e) {
        course.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent event) {
       try {    
        JComboBox<String> combo = (JComboBox<String>) event.getSource();
        
        String c = (String) course.getSelectedItem();
        String d = (String) days.getText();
        System.out.println("COURSE: " + course.getSelectedItem());
        
        System.out.println("DAY: " + days.getText());
        class_id.setText("");
        start_time.setText("00:00:00");
        end_time.setText("00:00:00");
          
        System.out.println("Selected course: " + course.getSelectedItem());
        String sql4 ="SELECT t1.class_id,start_time,end_time FROM class t1 INNER JOIN courses t2 ON t1.course_id = t2.course_id WHERE (t2.course_name ='"+c+"' AND t1.class_day = '"+d+"');\n" +
"";
         
        ps = conn.prepareStatement(sql4);
            rs5 =ps.executeQuery();
            while(rs5.next()){
                
                class_id.setText(rs5.getString("class_id"));
                start_time.setText(rs5.getString("start_time"));
                end_time.setText(rs5.getString("end_time"));
                
            }
                }
                catch(Exception e){JOptionPane.showMessageDialog(null, e);}
            }    
            });
         }

                @Override
                public void changedUpdate(DocumentEvent de) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void removeUpdate(DocumentEvent de) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
     });
          
         
            }
        catch(Exception e){JOptionPane.showMessageDialog(null, e);} 
        }      
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        photo = new javax.swing.JLabel();
        TITLE1 = new javax.swing.JLabel();
        TITLE2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        classbtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        course = new javax.swing.JComboBox<>();
        degree = new javax.swing.JComboBox<>();
        year = new javax.swing.JComboBox<>();
        semester = new javax.swing.JComboBox<>();
        date = new javax.swing.JTextField();
        time = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        days = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        reg_number = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        firstname = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        class_id = new javax.swing.JTextField();
        sid = new javax.swing.JTextField();
        end_time = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        reportsbtn = new javax.swing.JButton();
        start_time = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TITLE1.setFont(new java.awt.Font("SansSerif", 3, 15)); // NOI18N
        TITLE1.setText("BIOMETRIC FACE RECOGNITION ATTENDANCE SYSTEM");

        TITLE2.setFont(new java.awt.Font("SansSerif", 2, 15)); // NOI18N
        TITLE2.setText("CLASS ATTENDANCE VERIFICATION");

        jLabel4.setText("Degree");

        jLabel5.setText("Course");

        classbtn.setFont(new java.awt.Font("SansSerif", 2, 15)); // NOI18N
        classbtn.setText("CLICK TO ATTEND CLASS");
        classbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classbtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Year");

        jLabel3.setText("Semester");

        year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6" }));

        semester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));

        date.setEnabled(false);

        time.setEnabled(false);

        jLabel7.setText("DATE:");

        jLabel8.setText("TIME:");

        jLabel9.setText("DAY:");

        days.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("SansSerif", 2, 15)); // NOI18N
        jLabel6.setText("WELCOME:");

        reg_number.setEnabled(false);

        jLabel10.setText("Registration number");

        jLabel11.setText("Firstname");

        firstname.setEnabled(false);

        jLabel12.setText("Current class identification:");

        class_id.setEnabled(false);

        sid.setEnabled(false);

        end_time.setEnabled(false);

        jLabel13.setText("To");

        jLabel14.setText("From");

        reportsbtn.setFont(new java.awt.Font("SansSerif", 2, 15)); // NOI18N
        reportsbtn.setText("REPORTS");
        reportsbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportsbtnActionPerformed(evt);
            }
        });

        start_time.setEnabled(false);

        jButton1.setFont(new java.awt.Font("SansSerif", 2, 15)); // NOI18N
        jButton1.setText("Signout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("SansSerif", 2, 15)); // NOI18N
        jButton2.setText("Profile");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(reg_number, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                    .addComponent(firstname))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(sid, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(40, 40, 40)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(days, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(73, 73, 73))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(119, 119, 119))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(class_id, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(20, 20, 20)
                        .addComponent(jLabel14)
                        .addGap(14, 14, 14)
                        .addComponent(start_time, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(end_time, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(85, 85, 85)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(TITLE2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(84, 84, 84)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(course, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(degree, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(year, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(semester, 0, 195, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(64, 64, 64))))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(204, 204, 204)
                            .addComponent(classbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(122, 122, 122)
                            .addComponent(reportsbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(TITLE1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TITLE1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jButton2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(reg_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(firstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(days, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(class_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(end_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(start_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TITLE2)
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(degree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(semester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(course, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(classbtn)
                    .addComponent(reportsbtn))
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void classbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classbtnActionPerformed
        String s1 = start_time.getText();
        String s2 = end_time.getText();
        if ("".equals(s1))
            {
                JOptionPane.showMessageDialog(classbtn, "PLEASE SELECT A COURSE TO ATTEND CLASS");
            }
        else if("".equals(s2)){
                JOptionPane.showMessageDialog(classbtn, "PLEASE SELECT A COURSE TO ATTEND CLASS");
                }
        else{
        try
               {
                   Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_info", "root", "shirow12");
                    
                    String d = (String) class_id.getText();
                     System.out.println("Selected class: " + class_id.getText());
                      
                     String id = (String)sid.getText();
                    System.out.println("Selected student: " + sid.getText());
                    
                     Calendar calendar = Calendar.getInstance();
                     java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
                
                String expectedPattern = "hh:mm:ss";
                SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
               
                String lessontime = start_time.getText();
                java.util.Date date1 = formatter.parse(lessontime);
                System.out.println("Start time: " + date1);
              
                String lessonends = end_time.getText();
                java.util.Date date2 = formatter.parse(lessonends);
                System.out.println("End time: " + date2);
               
                String currenttime = (time.getText());
                java.util.Date date5 = formatter.parse(currenttime);
                
                 if(class_id.getText().equals("")){
                        JOptionPane.showMessageDialog(null,"NO CLASS TODAY ON THAT COURSE"); 
                 } 
                 else if(date5. before(date1)) 
                 {JOptionPane.showMessageDialog(null,"SORRY,YOU ARE TOO EARLY.WAIT FOR CLASS!");} 
                  
                  else if(date5. after(date2)) 
                 {JOptionPane.showMessageDialog(null,"SORRY,YOU ARE TOO LATE FOR CLASS");} 
                
                 else {
                      try {
                          
            conn = DriverManager.getConnection("jdbc:mysql://localhost/user_info", "root", "shirow12");
            String sqq = "SELECT attendance_date,student_id,class_id FROM attendance WHERE attendance_date = ? AND student_id = ? AND class_id = ?";
            pst = conn.prepareStatement(sqq);
            
            pst.setString(1, date.getText());
            pst.setString(2, sid.getText());
            pst.setString(3, d);
            
            System.out.println("date:" + date.getText() );
            System.out.println("reg:" + reg_number.getText());
            System.out.println("class:" + d );
            
            ResultSet result = pst.executeQuery();
            if(result.next()){
                JOptionPane.showMessageDialog(null, "YOU CANNOT ATTEND THE SAME CLASS TWICE!");
                System.out.println("date con1" );
            }
            else{
                System.out.println("date con2" );
                try
                    {
                    Class.forName("com.mysql.jdbc.Driver");
                     conn = DriverManager.getConnection("jdbc:mysql://localhost/user_info", "root", "shirow12");
                    String query="INSERT INTO attendance (attendance_date,student_id,class_id,verification) values(?,?,?,?)";
                    
                    ps = conn.prepareStatement(query);
                     System.out.println("date con" );
                    ps.setDate(1, ourJavaDateObject);
                    ps.setString(2,id);
                    ps.setString(3,d);
                    ps.setString(4,"1");
 
    
                    ps.executeUpdate();
                    
                   int row = ps.executeUpdate();
                if (row > 0) {
                   JOptionPane.showMessageDialog(classbtn, "welcome to class");
                   dispose();
        new facerecognition().setVisible(true);
                        }
                     else {
                         JOptionPane.showMessageDialog(classbtn, "CLASS ATTENDANCE FAILED");
                            }
                 }
                 catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
            }
            
                      }
                      catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                  }
               }
                      catch (ClassNotFoundException | SQLException | HeadlessException ex) {
                                    System.out.println("Found some error : " + ex);
                    }
     
         catch (ParseException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        }
    }//GEN-LAST:event_classbtnActionPerformed

    private void reportsbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportsbtnActionPerformed
                studentreports regFace =new studentreports();
                studentreports.reg_num.setText(this.reg_number.getText());
                regFace.setVisible(true);
                dispose();
    }//GEN-LAST:event_reportsbtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        new Homemain().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       profile regFace =new profile();
       profile.registration_number.setText(this.reg_number.getText());
       regFace.setVisible(true);
                dispose(); 
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Student().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TITLE1;
    private javax.swing.JLabel TITLE2;
    private javax.swing.JTextField class_id;
    private javax.swing.JButton classbtn;
    private javax.swing.JComboBox<String> course;
    private javax.swing.JTextField date;
    private javax.swing.JTextField days;
    private javax.swing.JComboBox<String> degree;
    private javax.swing.JTextField end_time;
    private javax.swing.JTextField firstname;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel photo;
    public static javax.swing.JTextField reg_number;
    private javax.swing.JButton reportsbtn;
    private javax.swing.JComboBox<String> semester;
    private javax.swing.JTextField sid;
    private javax.swing.JTextField start_time;
    private javax.swing.JTextField time;
    private javax.swing.JComboBox<String> year;
    // End of variables declaration//GEN-END:variables

    private String getTime(Calendar calendar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
