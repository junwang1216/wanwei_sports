package cdsdk;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import com.borland.jbcl.layout.*;
import cdsdk.TermB;
import java.io.*;
import cdsdk.CardAttribModel;
import java.awt.image.BufferedImage;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class FrmTerm extends JFrame {
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  
  GridBagConstraints gridBag = new GridBagConstraints();

  FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
  
  JPanel infoDetail = new JPanel(new GridLayout(8, 2));
  JPanel jPanel2 = new JPanel();

  JPanel Line0P = new JPanel();
  JPanel Line1P = new JPanel();
  JPanel Line2P = new JPanel();
  JPanel Line3P = new JPanel();
  JPanel Line4P = new JPanel();
  
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  //0316 PaneLayout paneLayout1 = new PaneLayout();
  

  JLabel Ldkh = new JLabel();
  JTextField fDkh = new JTextField();
	
  JLabel Lxm = new JLabel();
  JLabel jLabel4 = new JLabel();
  JTextField fXm = new JTextField();
  JLabel Lmz = new JLabel();
  JLabel Lxb = new JLabel();
  JLabel Lcsrq = new JLabel();
  JLabel Lsfzh = new JLabel();
  JLabel Ljtzz = new JLabel();
  JLabel Lnaddr = new JLabel();
  JLabel Lsamid = new JLabel();
  JLabel Lfzjg = new JLabel();
  JLabel Ldqzt = new JLabel();
  JLabel Lyxqz = new JLabel();

  JLabel Lbbh = new JLabel();
  
  JTextField fXb = new JTextField();
  JTextField fMz = new JTextField();
  JTextField fCsrq = new JTextField();
  JTextField fSfzh = new JTextField();
  
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea aJtzz = new JTextArea();
  JTextArea fNaddr = new JTextArea();
  JTextArea fSamid = new JTextArea();
  JTextField fFzjg = new JTextField();
  JTextField fYxqz = new JTextField();
  JButton jButton3 = new JButton();

  //Construct the frame
  public FrmTerm() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception  {
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(flowLayout);
    
    this.setForeground(new Color(235, 245, 255));
    this.setResizable(false);
    this.setSize(new Dimension(600, 480));
    this.setTitle("ɽ����˼628A����֤�������߲���");
    jButton1.setOpaque(false);
    jButton1.setText("��  ��");
    jButton1.addActionListener(new FrmTerm_jButton1_actionAdapter(this));
   
    jButton2.setOpaque(false);
    jButton2.setText("��  ��");
    jButton2.addActionListener(new FrmTerm_jButton2_actionAdapter(this));
    //0316 infoDetail.setLayout(flowLayout);
    
    jButton4.setOpaque(false);
    jButton4.setText("��������");
    jButton4.addActionListener(new FrmTerm_jButton4_actionAdapter(this));
    
    jButton5.setOpaque(false);
    jButton5.setText("��SamID");
    jButton5.addActionListener(new FrmTerm_jButton5_actionAdapter(this));
   

    GridLayout glout0 = new GridLayout(1,2);
    GridLayout glout1 = new GridLayout(3,2);
    GridLayout glout2 = new GridLayout(3,2);
    GridLayout glout3 = new GridLayout(4,2);
    GridLayout glout4 = new GridLayout(1,1);
    //lout.setHgap(8);
    //lout.setVgap(8);
    infoDetail.setLayout(glout4);
    Line0P.setLayout(glout0);
    Line1P.setLayout(glout1);
    Line2P.setLayout(glout2);
    Line3P.setLayout(glout3);
    Line4P.setLayout(glout4);
    
    
    Lxm.setMaximumSize(new Dimension(34, 16));
    Ldkh.setText("�˿ں�:");
    fDkh.setText("1001");
    
    Lxm.setText("����:");
    Lmz.setText("����:");
    Lxb.setText("�Ա�:");
    Lcsrq.setText("��������:");
    Lsfzh.setText("���֤��:");
   // jLabel4.setBackground(Color.white);
   // jLabel4.setOpaque(true);
   // jLabel4.setHorizontalTextPosition(SwingConstants.LEFT);
   // jLabel4.setText("");
    fXm.setText("    ");
    fXb.setText("    ");
    fMz.setText("");
    fCsrq.setText("        ");
    fSfzh.setText("                  ");
    Ljtzz.setText("��ͥסַ:");
    Lfzjg.setText("��֤����:");
    aJtzz.setText("                                         ");
    fFzjg.setText("                ");
    Ldqzt.setText("");//��ǰ״̬:
    Lyxqz.setText("��Ч��ֹ:");
    fYxqz.setText("         ");
    
    Lnaddr.setText("���µ�ַ:");
    fNaddr.setText("                                         ");
    
    Lsamid.setText("SamID:");
    fSamid.setText("                                         ");
    
    Lbbh.setText("V1.1.0");

    Line0P.add(Ldkh                  );
    Line0P.add(fDkh                  );
    
    Line1P.add(Lxm                  );
    Line1P.add(fXm                  );
    Line1P.add(Lxb                     );
    Line1P.add(fXb                     );    
    Line1P.add(Lcsrq                     );
    Line1P.add(fCsrq                     );
    
   // infoDetail.add(Lmz                    );
   // infoDetail.add(fMz                    );
    
    Line2P.add(Lsfzh                     );
    Line2P.add(fSfzh                     );
    Line2P.add(Lfzjg                     );
    Line2P.add(fFzjg                     );
    Line2P.add(Lyxqz                     );
    Line2P.add(fYxqz                    );
    
    Line3P.add(Ljtzz                     );
    Line3P.add(aJtzz                  );//jScrollPane1
    Line3P.add(Lnaddr                     );
    Line3P.add(fNaddr                    );
    Line3P.add(Lsamid                     );
    Line3P.add(fSamid                    );
    
    Line4P.add(Lbbh                    );
    
    //infoDetail.add(Ldqzt                     );
    infoDetail.add(jLabel4                     );
    
    
    
    contentPane.setOpaque(false);
    //jPanel2.setBackground(new Color(235, 245, 255));
    //jPanel2.setOpaque(true);
    // infoDetail.setBackground(new Color(235, 245, 255));
    infoDetail.setFont(new java.awt.Font("Dialog", 1, 11));
    

    
    jButton3.setText("jButton3");
    jButton3.addActionListener(new FrmTerm_jButton3_actionAdapter(this));
    jPanel2.add(jButton4, null);

    jPanel2.add(jButton5, null);
    
    jPanel2.add(jButton2, null);
   // jPanel2.add(jButton5, null);
    jPanel2.add(jButton1, null);
    jPanel2.add(Lbbh, null);
   // jPanel2.add(jButton3, null);
    

    contentPane.add(Line0P);
    contentPane.add(Line1P);
    contentPane.add(Line2P);
    contentPane.add(Line3P);
   // contentPane.add(Line4P);
    contentPane.add(infoDetail);
    contentPane.add(jPanel2);//Ldqzt
    contentPane.add(Ldqzt,BorderLayout.SOUTH);//Ldqzt
    //jScrollPane1.getViewport().add(aJtzz, null);

    aJtzz.setLineWrap(true);
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  //����
  void jButton2_actionPerformed(ActionEvent e) {
    String FILE_ROOT_DIR = System.getProperty("java.home")+"\\bin"; //java.home  java.class.path
    //String FILE_ROOT_DIR = "D:\\jre1.5.0\\bin";
    //��֮ǰ��ɾ����ǰ�ļ�
    File theFile = new File(FILE_ROOT_DIR+"\\zp.bmp");
    if (theFile.exists())
      theFile.delete();
    theFile = new File(FILE_ROOT_DIR+"\\wz.txt");
    if (theFile.exists())
      theFile.delete();


    int m_nReturnCode=-1;
	int Act = 1;
	int Act2 = 3;
    TermB readCard = TermB.getInstance();
    	
    //��֤
	m_nReturnCode = readCard.authenticate();
   /* if (m_nReturnCode != 1) {
      Ldqzt.setText("����֤����,������!!");
  		System.out.println("authenticate value is : "+m_nReturnCode);
      return;
    }*/
	System.out.println("authenticate value is : "+m_nReturnCode);
	
    //����
    m_nReturnCode = readCard.Read_Content(Act);
    if (m_nReturnCode != 1) {
      Ldqzt.setText("��������,������!!");
  	  System.out.println("Read_Content1 error ");
    }
    System.out.println("Read_Content1 value is: "+m_nReturnCode);
    
    m_nReturnCode = readCard.Read_Content(Act2);
    System.out.println("Read_Content2 value is: "+m_nReturnCode);
    
	
    //ȡ������Ϣ
    CardAttribModel model=readCard.getCardBaseData();
    CardAttribModel model2=readCard.getNewAddrData();
    
    //ȡͼ����Ϣ
    BufferedImage img =readCard.getPhotoBufferedImage();
    if (img != null) {
      jLabel4.setIcon(new ImageIcon(img));
      jLabel4.repaint();
      this.repaint();
    }

    //����
    fXm.setText(model.getName());
    //�Ա�
    fXb.setText(model.getSex());
    //����
    fMz.setText(model.getNation());
    //��������
    fCsrq.setText(model.getBirthday());
    //��ͥסַ
    aJtzz.setText(model.getAddress());
    //���֤��
    fSfzh.setText(model.getIdcard());
    //��֤����
    fFzjg.setText(model.getDepartment());
    //��Ч��ֹ
    fYxqz.setText(model.getEnddate());
    

 //0316   fNaddr.setText(model2.getNewAddr());
	Ldqzt.setText("    �������!");

  }

  void jButton1_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void jButton3_actionPerformed(ActionEvent e) {
    TermB readCard = TermB.getInstance();
    long value=readCard.getTestValue();
    Ldqzt.setText(String.valueOf(value));
  }
  
  void jButton4_actionPerformed(ActionEvent e) {
  	 //���ö���
    TermB readCard =  new TermB();
	 System.out.println("load dll OK");
	 int iport ;
	 String str;
	 str = fDkh.getText().toString();
	//str = "1001";
	 //System.out.println("str is:"+str);
	/*if(str.compareTo("")>0)
	{
		Ldqzt.setText("    ����ʧ��!");
		return;
	}*/
	iport =Integer.parseInt(fDkh.getText().toString());//140,83
	
	System.out.println("iport is:"+iport);
	if((iport<0)||(iport==0))
	{
		Ldqzt.setText("    ����ʧ��!");
		return;
	}

   //����ֵ
   Ldqzt.setText("    �������!");
   //���Ӷ˿�
   int m_nReturnCode;
   m_nReturnCode = readCard.InitComm2(iport);
   if (m_nReturnCode != 1) {
     Ldqzt.setText("���Ӷ˿ڴ���,������!!");
     return;
   }
  }
  
  void jButton5_actionPerformed(ActionEvent e) {
  /*	 fXm.setText("    ");
     fXb.setText("    ");
     fMz.setText("");
     fCsrq.setText("        ");
     fSfzh.setText("                  ");
     fYxqz.setText("         ");    
     aJtzz.setText("                                         ");
     fFzjg.setText("                ");
     jLabel4.setIcon(null);
     jLabel4.repaint();
     this.repaint();
     jLabel4.setText("");*/
  	
    int m_nReturnCode=-1;
    TermB readCard = TermB.getInstance();
    
    //��֤
	m_nReturnCode = readCard.authenticate();
   /* if (m_nReturnCode != 1) {
      Ldqzt.setText("����֤����,������!!");
  		System.out.println("authenticate value is : "+m_nReturnCode);
      return;
    }*/
	System.out.println("authenticate value is : "+m_nReturnCode);
	
    String SamID;
    SamID="";
    //����
    SamID = readCard.Get_SamID();
    if (SamID != null) {
      Ldqzt.setText("��SamID����,������!!");
  	  System.out.println("Get_SamID error ");
    }
	System.out.println("Get_SamID value is: "+m_nReturnCode);
    fSamid.setText(SamID);
	System.out.println("SamID value is: "+SamID);
    //fNaddr.setText(model2.getNewAddr());
	Ldqzt.setText("    ��SamID���!");
  }
}


class FrmTerm_jButton5_actionAdapter implements java.awt.event.ActionListener {
  FrmTerm adaptee;

  FrmTerm_jButton5_actionAdapter(FrmTerm adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton5_actionPerformed(e);
  }
}

class FrmTerm_jButton4_actionAdapter implements java.awt.event.ActionListener {
  FrmTerm adaptee;

  FrmTerm_jButton4_actionAdapter(FrmTerm adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton4_actionPerformed(e);
  }
}

class FrmTerm_jButton2_actionAdapter implements java.awt.event.ActionListener {
  FrmTerm adaptee;

  FrmTerm_jButton2_actionAdapter(FrmTerm adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton2_actionPerformed(e);
  }
}

class FrmTerm_jButton1_actionAdapter implements java.awt.event.ActionListener {
  FrmTerm adaptee;

  FrmTerm_jButton1_actionAdapter(FrmTerm adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class FrmTerm_jButton3_actionAdapter implements java.awt.event.ActionListener {
  FrmTerm adaptee;

  FrmTerm_jButton3_actionAdapter(FrmTerm adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton3_actionPerformed(e);
  }
}
