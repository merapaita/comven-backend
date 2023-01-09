//package com.rosist.comven.service.xx;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//public class Creatxt {
//	private static String cCampo;
//	private static String cAno;
//	private static String cMes;
//
//	private static final Logger logger = LoggerFactory.getLogger(Creatxt.class);
//	
//	public static void main(String[] args) {
//		cAno = args[0];
//		cMes = args[1];
//		pArcCompras = "LE20147082861" + cAno + cMes + "00080100001111.txt";
//		pArcCompras1 = "LE20147082861" + cAno + cMes + "00080200001011.txt";
//		pArcVentas = "LE20147082861" + cAno + cMes + "00140100001111.txt";
//
//		cargaXMLCompras();
//		cargaXMLVentas();
//	}
//
//	private static String pArcCompras;
//	private static String pArcCompras1;
//	private static String pArcVentas;
//	private static String pErrCompras;
//
//	private static void cargaXMLCompras() {
//		try {
//			pArcCompras = "..\\sunat\\" + pArcCompras;
//			pArcCompras1 = "..\\sunat\\" + pArcCompras1;
//			pErrCompras = "..\\sunat\\ErrCompras.txt";
//			File fCompras = new File(pArcCompras);
//			File fCompras1 = new File(pArcCompras1);
//			BufferedWriter bwCompras = new BufferedWriter(new FileWriter(fCompras));
//			BufferedWriter bwCompras1 = new BufferedWriter(new FileWriter(fCompras1));
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			Document doc = dBuilder.parse(new File("..\\sunat\\compras.xml"));
//
//			doc.getDocumentElement().normalize();
//			NodeList listaTabla = doc.getElementsByTagName("compras");
//			for (int i = 0; i < listaTabla.getLength(); i++) {
//				Node registro = listaTabla.item(i);
//				if (registro.getNodeType() == 1) {
//					Element elemento = (Element) registro;
//					cCampo = getTagValue("campo", elemento);
//
//					bwCompras.write(cCampo);
//					bwCompras.newLine();
//				}
//			}
//			bwCompras.close();
//			bwCompras1.close();
//		} catch (Exception e) {
//			File fErrCompras = new File(pErrCompras);
//			try {
//				BufferedWriter bwErrCompras = new BufferedWriter(new FileWriter(fErrCompras));
//				bwErrCompras.write(e.toString());
//				bwErrCompras.close();
//			} catch (IOException ex) {
//				logger.info(Creatxt.class.getName() + "-" +  ex);
//			}
//		}
//	}
//
//	private static void cargaXMLVentas() {
//		try {
//			pArcVentas = "..\\sunat\\" + pArcVentas;
//			File fVentas = new File(pArcVentas);
//
//			BufferedWriter bwVentas = new BufferedWriter(new FileWriter(fVentas));
//
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			Document doc = dBuilder.parse(new File("..\\sunat\\ventas.xml"));
//			doc.getDocumentElement().normalize();
//			NodeList listaTabla = doc.getElementsByTagName("ventas");
//			for (int i = 0; i < listaTabla.getLength(); i++) {
//				Node registro = listaTabla.item(i);
//				if (registro.getNodeType() == 1) {
//					Element elemento = (Element) registro;
//					cCampo = getTagValue("campo", elemento);
//
//					bwVentas.write(cCampo);
//					bwVentas.newLine();
//				}
//			}
//			bwVentas.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static String getTagValue(String sTag, Element eElement) {
//		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
//		Node nValue = nlList.item(0);
//		return nValue.getNodeValue();
//	}
//}
