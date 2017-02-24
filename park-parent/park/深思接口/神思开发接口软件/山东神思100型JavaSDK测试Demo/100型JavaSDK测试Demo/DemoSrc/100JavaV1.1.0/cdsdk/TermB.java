package cdsdk;

import java.io.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGCodec;

/**
 * <p>Title: ɽ����˼628A���߶�ȡ </p>
 * <p>Description: ����ɽ����˼628A����֤����</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:ɽ����˼ </p>
 * @author ɽ����˼
 * @version 1.0
 */
public class TermB {
  private native int start(int iPort);
  private native int finish();
  private native int AuthenticateCard();//
  private native int read(int Act);
  private native String GetSamID();
  private native int getTest();
  public static TermB getInstance() {
    if (termB == null)
      termB = new TermB();
    return termB;
  }

  public void dispose() {
    if (termB != null)
      termB.finish();
  }
  /**
   * ��ʼ���˿�(����:1~16,USB:1001~1016)
   * @param iPort int
   * @return int (1:success)
   */
  public int InitComm2(int iPort) {
    return (int)start(iPort);
  }
  /**
   * �رն˿�
   * @return int (1:success)
   */
  public int CloseComm() {
    return (int)finish();
  }
  /**
   * ����֤
   * @return int (1:success)
   */
  public int authenticate() {
    return (int)AuthenticateCard();
  }

  /**
   * ��������Ϣ
   * @param Act int (1:��������Ϣ;2:ֻ��������Ϣ;3:������סַ��Ϣ)
   * @return int (1:success)
   */
  public int Read_Content(int Act) {
    return (int)read(Act);
  }
  public String Get_SamID() {
    return (String)GetSamID();
  }

  public int getTestValue(){
    return (int)getTest();
  }

  /**
   * ���ص�ǰ��ȡ�Ķ���֤��������Ϣ
   * @return byte[]
   */
  public byte[] read_wz() {
    String filename = FILE_ROOT_DIR + "\\wz.txt";
    byte wzData[] = (byte[])null;
    try {
      FileInputStream hFile = new FileInputStream(filename);
      int i = hFile.available();
      wzData = new byte[i];
      hFile.read(wzData);
      hFile.close();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return wzData;
  }
  
  /**
   * ���ص�ǰ��ȡ�Ķ���֤�����µ�ַ
   * @return byte[]
   */
  public byte[] read_newAddr() {
    String filename = FILE_ROOT_DIR + "\\NewAdd.txt";
    byte wzData[] = (byte[])null;
    File f=new File(filename);
    if(f.exists()){    
    try {
      FileInputStream hFile = new FileInputStream(filename);
      int i = hFile.available();
      wzData = new byte[i];
      hFile.read(wzData);
      hFile.close();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    }
    return wzData;
  }


  /**
   * ȡ��ǰ������Ƭ����Ϣ
   * @return BufferedImage
   */
  public BufferedImage getPhotoBufferedImage() {
    Image img = new ParseBMPToJPG().load(System.getProperty("java.home")+"\\bin\\zp.bmp");
//
    BufferedImage bimg = new BufferedImage(img.getWidth(null),
                                           img.getHeight(null),
                                           BufferedImage.TYPE_INT_RGB);
    bimg.createGraphics().drawImage(img, 0, 0, null);

    return bimg;
  }




  /**
   * ȡ��ǰ���е���Ƭ��Ϣ
   * @return Image
   */
  public Image getPhotoImage() {
    Image img = new ParseBMPToJPG().load(System.getProperty("java.home")+"\\bin\\zp.bmp");

    return img;
  }

  /**
   * ��ȡ��ȡ���µ�ַ��Ϣ
   * @return byte[]
   */
  /*public byte[] read_newAdd() {
    String filename = FILE_ROOT_DIR + "\\wz.txt";
    byte newAddData[] = (byte[])null;
    try {
      FileInputStream hFile = new FileInputStream(filename);
      int i = hFile.available();
      newAddData = new byte[i];
      hFile.read(newAddData);
      hFile.close();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return newAddData;
  }
  */
  public static String Unicode2GBK(String dataStr) {
  /*  int index = 0;
    StringBuffer buffer = new StringBuffer();
    while(index<dataStr.length()-2) {
           if(!"\\u".equals(dataStr.substring(index,index+2))){
            buffer.append(dataStr.charAt(index));
            index++;
            continue;
     }
    String charStr = "";
    charStr = dataStr.substring(index+2,index+6);
    char letter = (char) Integer.parseInt(charStr, 16 );
     buffer.append(letter);
           index+=6;
    }
    return buffer.toString();*/
  	String str;
  	str = null;
  	try{     
  		str   =   new   String(dataStr.getBytes(),"GBK");     
      }catch(java.io.UnsupportedEncodingException   e){   
      }   	  	
    return str;  
}    
  /**
   * ���ؿ���������
   * @return CardAttribModel
   */
  public CardAttribModel getCardBaseData() {
    CardAttribModel model = new CardAttribModel();
    
    byte[] source = read_wz();
    if (source.length <= 0) {
      return null;
    }
    //ȡ��ǰ��ȡ��Ϣ
	String tmpstr1="",tmpstr2="",tmpstr3="",tmpstr4="",wltfile="",bmpfile="";
	String name="",sex="",nation="",birthdate="",address="",idnum="",organ="",startdate="",enddate="",newaddress=""; // ����,�Ա�,����,��������,סַ,���֤��,ǩ������,��ʼ����,��ֹ����,��סַ
	int year,month,day,hour,minute,second,tmpint1,tmpint2,tmpint3,tmpint4;
	byte[] tmpb=new byte[256];
	System.arraycopy(source,0,tmpb,0,256);
	
 	try{     
 		tmpstr1=new String(tmpb,"UTF-16LE"); 
      }catch(java.io.UnsupportedEncodingException   e){   
      }   	
	
	tmpb=new byte[4];
	System.arraycopy(source,0,tmpb,0,4);
	tmpint1=0;
	tmpint1 |=tmpb[0];
	tmpint1 &=0x000000FF;   // ȥ������
	tmpint2=0;
	tmpint2 |=tmpb[1];
	tmpint2 &=0x000000FF;
	tmpint3=0;
	tmpint3 |=tmpb[2];
	tmpint3 &=0x000000FF;
	tmpint4=0;
	tmpint4 |=tmpb[3];
	tmpint4 &=0x000000FF;
	
	int tmpp=tmpint4*0x1000000+tmpint3*0x10000+tmpint2*0x100+tmpint1;
	year=((tmpp>>25) & 0x7F)+2000;
	month=(tmpp>>21) & 0x0F;
	day=(tmpp>>16) & 0x1F;
	hour=(tmpp>>11) & 0x1F;
	minute=(tmpp>>5) & 0x3F;
	second=(tmpp & 0x1F)*2;
	
	tmpstr2="";

	tmpb=new byte[4];
	System.arraycopy(source,4,tmpb,0,4);
	try{     
		tmpstr4=new String(tmpb,"UTF-8"); 
      }catch(java.io.UnsupportedEncodingException   e){   
      }   	
	// ��unicode������Ϣ(256)����ȡ ����,�Ա�,����,��������,סַ,���֤��,ǩ������,��ʼ����,��ֹ����
	// ��������tmpstr1  �����п��ܴ�unicodeתstring
	int i,j=0;
	String charstr1="",charstr2="";
	char letter;
	
	tmpb=new byte[30];
	System.arraycopy(source,0,tmpb,0,30);	
	try{     
		name=new String(tmpb,"UTF-16LE");   // UTF-16BE  
		name=new String(name.getBytes("UTF-8"),"UTF-8");
      }catch(java.io.UnsupportedEncodingException   e){   
      }  
      model.setName(name); 
      
    tmpb=new byte[2];
  	System.arraycopy(source,30,tmpb,0,2);
  	if(tmpb[0]==50)		//0x32
  	{
  		model.setSex("Ů");
  	}else if(tmpb[0]==49)		//0x32
  	{
  		model.setSex("��");
  	}
	/*System.out.println("sex value is :"+tmpb[0]);
  	try{     
  		sex=new String(tmpb,"UTF-16LE");
  		sex=new String(nation.getBytes("UTF-8"),"UTF-8");
        }catch(java.io.UnsupportedEncodingException   e){   
        }  
        model.setSex(sex);*/
      
	tmpb=new byte[4];
	System.arraycopy(source,32,tmpb,0,4);
	System.out.println("nation value is :"+tmpb[0]+tmpb[1]+tmpb[2]+tmpb[3]);
	try{     
		nation=new String(tmpb,"UTF-16LE");
		nation=new String(nation.getBytes("UTF-8"),"UTF-8");
      }catch(java.io.UnsupportedEncodingException   e){   
      }  
      model.setNation(nation);

	tmpb=new byte[16];
	System.arraycopy(source,36,tmpb,0,16);
	try{     
		birthdate=new String(tmpb,"UTF-16LE");
		birthdate=new String(birthdate.getBytes("UTF-8"),"UTF-8");		
      }catch(java.io.UnsupportedEncodingException   e){   
      }  
      model.setBirthday(birthdate);
      
	tmpb=new byte[70];
	System.arraycopy(source,52,tmpb,0,70);
	try{     
		address=new String(tmpb,"UTF-16LE");
		address=new String(address.getBytes("UTF-8"),"UTF-8");	
      }catch(java.io.UnsupportedEncodingException   e){   
      }  
      model.setAddress(address);
	
	tmpb=new byte[36];
	System.arraycopy(source,122,tmpb,0,36);
	try{   
		idnum=new String(tmpb,"UTF-16LE");
		idnum=new String(idnum.getBytes("UTF-8"),"UTF-8");		
      }catch(java.io.UnsupportedEncodingException   e){   
      }  
      model.setIdcard(idnum);
	
	tmpb=new byte[30];
	System.arraycopy(source,158,tmpb,0,30);
	try{   
		organ=new String(tmpb,"UTF-16LE");
		organ=new String(organ.getBytes("UTF-8"),"UTF-8");				
      }catch(java.io.UnsupportedEncodingException   e){   
      }  
      model.setDepartment(organ);
      
	tmpb=new byte[16];
	System.arraycopy(source,188,tmpb,0,16);	
	try{   
		startdate=new String(tmpb,"UTF-16LE");
		startdate=new String(startdate.getBytes("UTF-8"),"UTF-8");				
      }catch(java.io.UnsupportedEncodingException   e){   
      }  
      
	tmpb=new byte[16];
	System.arraycopy(source,204,tmpb,0,16);
	try{  
		enddate=new String(tmpb,"UTF-16LE");
		enddate=new String(enddate.getBytes("UTF-8"),"UTF-8");				
      }catch(java.io.UnsupportedEncodingException   e){   
      }  
      model.setEnddate(enddate);	

    return model;
  }
  
  

  public CardAttribModel getNewAddrData() {
    CardAttribModel model = new CardAttribModel();
    
    byte[] source = read_newAddr();
    
    if (source == null) {
      return null;
    }
    
    if (source.length <= 0) {
      return null;
    }
    //ȡ��ǰ��ȡ��Ϣ
	String tmpstr1="";
	String newaddr=""; // ����,�Ա�,����,��������,סַ,���֤��,ǩ������,��ʼ����,��ֹ����,��סַ
	byte[] tmpb=new byte[70];
	
 	try{     
 		tmpstr1=new String(tmpb,"UTF-16LE"); 
      }catch(java.io.UnsupportedEncodingException   e){   
      }   		
	
	System.arraycopy(source,0,tmpb,0,70);
	try{     
		newaddr=new String(tmpb,"UTF-16LE");   // UTF-16BE  
		newaddr=new String(newaddr);
    }catch(java.io.UnsupportedEncodingException   e){   
    }  
    System.out.println("newaddr value is: "+newaddr);
	
    model.setNewAddr(newaddr); 
    return model;
  }

  private static TermB termB;
  private final String FILE_ROOT_DIR = System.getProperty("java.home")+"\\bin";
  //private String FILE_ROOT_DIR = "D:\\jre1.5.0\\bin";

  static {
    System.loadLibrary("jtermb");
  }

  public static void main(String[] args) {
    TermB test = TermB.getInstance();
    int returnCode=test.start(1);
    System.out.println(" " + returnCode);
  }
}
