package cn.com.cdboost.collect.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlUtil {

	public static Document getDocument(String xml) {
        Document document = null;  
        try {  
        	document = DocumentHelper.parseText(xml);
        } catch (Exception e) {  
        	e.printStackTrace(); 
        }  
        return document;  
    }  
	
	public static void getMapByDocument(Element node, Map<String,String> map) {
        List<Attribute> list = node.attributes();
        // 遍历属性节点
        for (Attribute attr : list) {
            map.put(attr.getText(), attr.getValue());
        }

        if (!(node.getTextTrim().equals(""))) {
            map.put(node.getName(),node.getText());
        }

        // 当前节点下面子节点迭代器
        Iterator<Element> it = node.elementIterator();
        // 遍历
        while (it.hasNext()) {
            // 获取某个子节点对象
            Element e = it.next();
            // 对子节点进行遍历
            getMapByDocument(e, map);
        }
    }  
	
	/** 
     * 遍历当前节点元素下面的所有(元素的)子节点
     *  
     * @param node 
     */  
    public static void listNodes(Element node) {  
        System.out.println("当前节点的名称：：" + node.getName());
        // 获取当前节点的所有属性节点
        List<Attribute> list = node.attributes();
        // 遍历属性节点
        for (Attribute attr : list) {  
            System.out.println(attr.getText() + "-----" + attr.getName()  
                    + "---" + attr.getValue());  
        }  
  
        if (!(node.getTextTrim().equals(""))) {  
            System.out.println("文本内容：：：：" + node.getText());
        }  
  
        // 当前节点下面子节点迭代器
        Iterator<Element> it = node.elementIterator();  
        // 遍历
        while (it.hasNext()) {  
            // 获取某个子节点对象
            Element e = it.next();  
            // 对子节点进行遍历
            listNodes(e);  
        }  
    }  
	
    
    
	public static void main(String[] args) {
		Document doc = XmlUtil.getDocument("");
		listNodes(doc.getRootElement());
	}
}