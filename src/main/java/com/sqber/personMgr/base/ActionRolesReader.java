package com.sqber.personMgr.base;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;


@Component
public class ActionRolesReader {

	private Map<String, String> actionRolesConfig;

	public ActionRolesReader() {
		actionRolesConfig = new HashMap<String, String>();
		
		SAXReader saxReader = new SAXReader();
		try {
			String path = ResourceUtils.getFile("classpath:actionroles.xml").getPath(); 
			File file = new File(path);
			Document doc = saxReader.read(file);
			Element rootEle = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> list = rootEle.elements("Role");
			String url;
			String roleName;
			for (Element e : list) {
				url = e.attributeValue("url");
				roleName = e.getText();
				actionRolesConfig.put(url, roleName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
//		
//		// 创建一个DocumentBuilderFactory的对象
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		// 创建一个DocumentBuilder的对象
//		try {
//			// 创建DocumentBuilder对象
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			// 通过DocumentBuilder对象的parser方法加载books.xml文件到当前项目下
//			//String filePath = ResourceUtils.getFile("classpath:actionroles.xml").getPath();
//			Document document = db.parse(Application.class.getResourceAsStream("../../actionroles.xml"));
//			// 获取所有book节点的集合
//			NodeList roleList = document.getElementsByTagName("Role");
//			String url;
//			String roleName;
//			// 通过nodelist的getLength()方法可以获取bookList的长度
//			for (int i = 0; i < roleList.getLength(); i++) {
//				url = roleList.item(i).getAttributes().getNamedItem("url").getTextContent();
//				roleName = roleList.item(i).getFirstChild().getTextContent();
//				actionRolesConfig.put(url, roleName);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

	public Map<String, String> GetActionRolesConfig() {
		return actionRolesConfig;
	}
	
//	public static void main(String[] args) {
//		ActionRolesReader r = new ActionRolesReader();
//		r.GetActionRolesConfig();
//	}
	
}
