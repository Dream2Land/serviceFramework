package cn.xdaoy.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtils {
	
	private static final String cs = "UTF-8";
	
	
	/**
	 * parse xml String to xml element
	 * 
	 * @param xml String
	 * 
	 * @return xml element
	 * 
	 * @throws Exception
	 */
	public static Element parseXML(final String msg) throws Exception{
		return parseXML(new ByteArrayInputStream(msg.getBytes()),cs);
	}
	
	/**
	 * parse xml String to xml element
	 * 
	 * @param xml String ,charset
	 * @return
	 * @throws Exception
	 */
	public static Element parseXML(final String msg,final String cs) throws Exception{
		return parseXML(new ByteArrayInputStream(msg.getBytes()),cs);
	}
	
	/**
	 * parse xml file to xml element
	 * 
	 * @param xml file
	 * @return
	 * @throws Exception
	 */
	public static Element parseXMLFile(final String file) throws Exception{
		return parseXML(new FileInputStream(file),cs);
	}
	
	/**
	 * parse xml file to xml element
	 * 
	 * @param xml file,file charset
	 * @return
	 * @throws Exception
	 */
	public static Element parseXMLFile(final String file,final String cs) throws Exception{
		return parseXML(new FileInputStream(file),cs);
	}
	
	
	/**
	 * parse xml inputstream to xml element
	 * @param inputstream ,charset
	 * @return
	 * @throws Exception
	 */
	public static Element parseXML(final InputStream inputStream,final String cs) throws Exception{
		try{
	        SAXReader reader = new SAXReader();
	        //reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
	        //reader.setFeature("http://xml.org/sax/features/external-general-entites", false);
	        //reader.setFeature("http://xml.org/sax/features/external-parameter-entites", false);
	        reader.setEncoding(cs);
	        Document document = reader.read(inputStream);
	        Element root = document.getRootElement();
	        return root;
			
		}finally {
			if(null != inputStream) {
				inputStream.close();
			}
		}
	}
    
}
