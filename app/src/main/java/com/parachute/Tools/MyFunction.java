package com.parachute.Tools;




/**
 * @author benjaminwan
 *����ת������
 */
public class MyFunction {
	
	// ----------------smilekun---------------------------------����
	  //2�ֽ���װ��int �޷���λ   ��ǿ��װ�� 
	  //����һλ�����޷���λ
	static	 public  int twoByte2int (byte[] b  , int  index){
		 return   b[index+1] & 0xFF |  
	                (b[index] & 0xFF) << 8  
	                ;  
	 }

	/**
	 * @Description ：不带符号
	 */
	public static int twoBytesToInt (byte[] bytes , int index){
		byte[] by = new byte[4];
		by[0] = bytes[index+1];
		by[1] = bytes[index];

		int l;
		l = by[ 0];
		l &= 0xff;
		l |= ((long) by[ 1] << 8);
		l &= 0xffff;
		return l;
	}

	//�з���λ
	static	 public  int twoByte2int_ (byte[] b  , int  index){
		int result ;
		if ((b[index] & 0x80) == 0x80) {   
			 result  = - ((b[index+1] & 0xFF) |  ( (b[index] & 0x7F) << 8))  ;
		}else {
			 result  = (b[index+1] & 0xFF) |  ( (b[index] & 0xFF) << 8)  ;
		}
		 return result  ;
	 }
	/**
	 * ���ֽ�תint  �з���λ
	 *
	 * ������ P Q  ����
	 * @param a �� ��һ�ֽ�
	 * @param b �� �ڶ��ֽ�
	 * @return
	 */
	public static int twoBytesToIntHave (byte a , byte b){
		byte[] by = new byte[4];
		by[0] = b;
		by[1] = a;

		int l;
		l = by[ 0];
		l &= 0xff;
		l |= ((long) by[ 1] << 8);
		l &= 0xffff;
		l = l << 16;
		l = l/ (int) Math.pow(2,16);
		return l;
	}

	//-------------------------------------------------------
	// �ж�������ż����λ���㣬���һλ��1��Ϊ������Ϊ0��ż��
    static public int isOdd(int num)
	{
		return num & 0x1;
	}
    // ----------------smilekun---------------------------------����
    public static int byteArrayToInt(byte[] b , int index) {  
        return   b[index+3] & 0xFF |  
                (b[index+2] & 0xFF) << 8 |  
                (b[index+1] & 0xFF) << 16 |  
                (b[index] & 0xFF) << 24;  
    }  
    /** 
	 * �ֽ�ת��Ϊ���� 
	 *  
	 * @param b �ֽڣ�����4���ֽڣ� 
	 * @param index ��ʼλ�� 
	 * @return 
	 */  
	public static float byte2float(byte[] b, int index) {   
		byte[] by = new byte[4];
		by[0] = b[index+3];
		by[1] = b[index+2];
		by[2]= b[index+1];
		by[3] = b[index];
		
	    int l;                                             
	    l = by[ 0];                                  
	    l &= 0xff;                                         
	    l |= ((long) by[ 1] << 8);                   
	    l &= 0xffff;                                       
	    l |= ((long) by[2] << 16);                  
	    l &= 0xffffff;                                     
	    l |= ((long) by[3] << 24);                  
	    return Float.intBitsToFloat(l);                    
	}  
	 /** 
		 * �ֽ�ת��Ϊ���� 
		 *  
		 * @param b �ֽڣ�����4���ֽڣ� 
		 * @param index ��ʼλ�� 
		 * @return 
		 */  
		public static float byte2float2(byte[] b, int index) {   
			byte[] by = new byte[4];
			by[3] = b[index+3];
			by[2] = b[index+2];
			by[1]= b[index+1];
			by[0] = b[index];
			
		    int l;                                             
		    l = by[ 0];                                  
		    l &= 0xff;                                         
		    l |= ((long) by[ 1] << 8);                   
		    l &= 0xffff;                                       
		    l |= ((long) by[2] << 16);                  
		    l &= 0xffffff;                                     
		    l |= ((long) by[3] << 24);                  
		    return Float.intBitsToFloat(l);                    
		}  
    //-------------------------------------------------------
    static public int HexToInt(String inHex)//Hex�ַ���תint
    {
    	return Integer.parseInt(inHex, 16);
    }
    //-------------------------------------------------------
    static public byte HexToByte(String inHex)//Hex�ַ���תbyte
    {
    	return (byte)Integer.parseInt(inHex,16);
    }
    //-------------------------------------------------------
    static public String Byte2Hex(Byte inByte)//1�ֽ�ת2��Hex�ַ�
    {
    	return String.format("%02x", inByte).toUpperCase();
    }
    //-------------------------------------------------------
	static public String ByteArrToHex(byte[] inBytArr)//�ֽ�����תתhex�ַ���
	{
		StringBuilder strBuilder=new StringBuilder();
		int j=inBytArr.length;
		for (int i = 0; i < j; i++)
		{
			strBuilder.append(Byte2Hex(inBytArr[i]));
			strBuilder.append(" ");
		}
		return strBuilder.toString(); 
	}
  //-------------------------------------------------------
    static public String ByteArrToHex(byte[] inBytArr,int offset,int byteCount)//�ֽ�����תתhex�ַ�������ѡ����
	{																	       //byteCount��λ��  ���ǳ���
    	StringBuilder strBuilder=new StringBuilder();
		int j=byteCount;
		for (int i = offset; i < j; i++)
		{
			strBuilder.append(Byte2Hex(inBytArr[i]));
		}
		return strBuilder.toString();
	}
    //-------------------------------------------------------
    static public String ByteArrToHexBack(byte[] inBytArr,int offset,int byteCount)//�ֽ�����תתhex�ַ�������ѡ����
	{																	       //byteCount��λ��  ���ǳ���
    	StringBuilder strBuilder=new StringBuilder();
		int j=byteCount;
		for (int i = offset; i > j; i--)
		{
			strBuilder.append(Byte2Hex(inBytArr[i]));
		}
		return strBuilder.toString();
	}
	//-------------------------------------------------------
	//תhex�ַ���ת�ֽ�����
    static public byte[] HexToByteArr(String inHex)//hex�ַ���ת�ֽ�����
	{
		int hexlen = inHex.length();
		byte[] result;
		if (isOdd(hexlen)==1)
		{//����
			hexlen++;
			result = new byte[(hexlen/2)];
			inHex="0"+inHex;
		}else {//ż��
			result = new byte[(hexlen/2)];
		}
	    int j=0;
		for (int i = 0; i < hexlen; i+=2)
		{
			result[j]=HexToByte(inHex.substring(i,i+2));
			j++;
		}
	    return result; 
	}
}