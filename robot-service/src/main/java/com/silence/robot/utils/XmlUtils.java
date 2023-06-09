package com.silence.robot.utils;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.*;

/**
 * XML工具类
 *
 * @author silence
 * @since 2021/8/22
 */
public class XmlUtils {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    /**
     * 将输入流转化为jsonObject
     *
     * @param inputStream
     * @return
     */
    public static JSONObject xmlToJsonByInputStream(InputStream inputStream) {
        String xmlStr = FileUtils.readAllContents(inputStream);
        Map<String, Object> map = xml2mapWithAttr(xmlStr, true);
        return new JSONObject(map);
    }

    /**
     * 将文件转化为jsonObject
     *
     * @param filePath
     * @param fileName
     * @return com.alibaba.fastjson.JSONObject
     * @author silence
     * @since 2021/8/23 9:58
     */
    public static JSONObject xmlToJsonByPath(String filePath, String fileName) {
        String xmlStr = FileUtils.readAllContents(filePath, fileName);
        Map<String, Object> map = xml2mapWithAttr(xmlStr, true);
        return new JSONObject(map);
    }

    /**
     * 将文件转化为jsonObject
     *
     * @param path
     * @return com.alibaba.fastjson.JSONObject
     * @author silence
     * @since 2021/8/23 9:58
     */
    public static JSONObject xmlToJsonByPath(Path path) {
        String xmlStr = FileUtils.readAllContents(path);
        Map<String, Object> map = xml2mapWithAttr(xmlStr, true);
        return new JSONObject(map);
    }


    /*** xml转map 不带属性
     *@param xmlStr
     *@param needRootKey 是否需要在返回的map里加根节点键
     * */

    public static Map<String, Object> xml2map(String xmlStr, boolean needRootKey) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            logger.error("xml解析失败", e);
        }

        assert doc != null;
        Element root = doc.getRootElement();

        Map<String, Object> map = xml2map(root);
        if (root.elements().size() == 0 && root.attributes().size() == 0) {
            return map;

        }
        if (needRootKey) {
            //在返回的map里加根节点键(如果需要)

            Map<String, Object> rootMap = new HashMap<>();

            rootMap.put(root.getName(), map);
            return rootMap;

        }
        return map;

    }

    /*** xml转map 带属性
     *@paramxmlStr
     *@paramneedRootKey 是否需要在返回的map里加根节点键
     *@return*@throwsDocumentException*/

    public static Map<String, Object> xml2mapWithAttr(String xmlStr, boolean needRootKey) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            logger.error("xml解析失败", e);
            throw new BusinessException(ExceptionCode.FILE_READ_ERROR);
        }

        Element root = doc.getRootElement();

        Map<String, Object> map = xml2mapWithAttr(root);
        if (root.elements().size() == 0 && root.attributes().size() == 0) {
            return map;
            //根节点只有一个文本内容

        }
        if (needRootKey) {
            //在返回的map里加根节点键(如果需要)

            Map<String, Object> rootMap = new HashMap<>();

            rootMap.put(root.getName(), map);
            return rootMap;

        }
        return map;

    }

    /*** xml转map 不带属性
     *@parame
     *@return

     */

    private static Map<String, Object> xml2map(Element e) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<Element> list = e.elements();
        if (list.size() > 0) {
            for (Element iter : list) {
                List mapList = new ArrayList();
                if (iter.elements().size() > 0) {
                    Map<String, Object> m = xml2map(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!(obj instanceof List)) {
                            mapList = new ArrayList();

                            mapList.add(obj);

                            mapList.add(m);

                        }
                        if (obj instanceof List) {
                            mapList = (List) obj;

                            mapList.add(m);

                        }

                        map.put(iter.getName(), mapList);

                    } else {
                        map.put(iter.getName(), m);
                    }

                } else {
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!(obj instanceof List)) {
                            mapList = new ArrayList();

                            mapList.add(obj);

                            mapList.add(iter.getText());

                        }
                        if (obj instanceof List) {
                            mapList = (List) obj;

                            mapList.add(iter.getText());

                        }

                        map.put(iter.getName(), mapList);

                    } else {
                        map.put(iter.getName(), iter.getText());
                    }

                }

            }

        } else {
            map.put(e.getName(), e.getText());
        }
        return map;

    }

    /*** xml转map 带属性
     *@parame
     *@return

     */

    private static Map<String, Object> xml2mapWithAttr(Element element) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<Element> list = element.elements();

        List<Attribute> listAttr0 = element.attributes();
        //当前节点的所有属性的list

        for (Attribute attr : listAttr0) {
            map.put("@" + attr.getName(), attr.getValue());

        }
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Element iter = list.get(i);

                List mapList = new ArrayList();
                if (iter.elements().size() > 0) {
                    Map<String, Object> m = xml2mapWithAttr(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!(obj instanceof List)) {
                            mapList = new ArrayList();

                            mapList.add(obj);

                            mapList.add(m);

                        }
                        if (obj instanceof List) {
                            mapList = (List) obj;

                            mapList.add(m);

                        }

                        map.put(iter.getName(), mapList);

                    } else {
                        map.put(iter.getName(), m);
                    }

                } else {
                    List<Attribute> listAttr = iter.attributes();
                    //当前节点的所有属性的list

                    Map attrMap = null;
                    boolean hasAttributes = false;
                    if (listAttr.size() > 0) {
                        hasAttributes = true;

                        attrMap = new LinkedHashMap();
                        for (Attribute attr : listAttr) {
                            attrMap.put("@" + attr.getName(), attr.getValue());

                        }

                    }
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!(obj instanceof List)) {
                            mapList = new ArrayList();

                            mapList.add(obj);

                            if (hasAttributes) {
                                attrMap.put("#text", iter.getText());

                                mapList.add(attrMap);

                            } else {
                                mapList.add(iter.getText());

                            }

                        }
                        if (obj instanceof List) {
                            mapList = (List) obj;

                            if (hasAttributes) {
                                attrMap.put("#text", iter.getText());

                                mapList.add(attrMap);

                            } else {
                                mapList.add(iter.getText());

                            }

                        }

                        map.put(iter.getName(), mapList);

                    } else {

                        if (hasAttributes) {
                            attrMap.put("#text", iter.getText());

                            map.put(iter.getName(), attrMap);

                        } else {
                            map.put(iter.getName(), iter.getText());

                        }

                    }

                }

            }

        } else {//根节点的

            if (listAttr0.size() > 0) {
                map.put("#text", element.getText());

            } else {
                map.put(element.getName(), element.getText());

            }

        }
        return map;

    }

    /*** map转xml map中没有根节点的键
     *@parammap
     *@paramrootName
     *@throwsDocumentException
     *@throwsIOException*/

    public static Document map2xml(Map<String, Object> map, String rootName) {
        Document doc = DocumentHelper.createDocument();

        Element root = DocumentHelper.createElement(rootName);

        doc.add(root);

        map2xml(map, root);

        return doc;

    }

    /*** map转xml map中含有根节点的键
     *@parammap
     *@throwsDocumentException
     *@throwsIOException*/

    public static Document map2xml(Map<String, Object> map) {
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        if (entries.hasNext()) { //获取第一个键创建根节点

            Map.Entry<String, Object> entry = entries.next();

            Document doc = DocumentHelper.createDocument();

            Element root = DocumentHelper.createElement(entry.getKey());

            doc.add(root);

            map2xml((Map) entry.getValue(), root);

            return doc;

        }
        return null;

    }

    /*** map转xml
     *@parammap
     *@parambody xml元素
     *@return

     */

    private static Element map2xml(Map<String, Object> map, Element body) {
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();

            String key = entry.getKey();

            Object value = entry.getValue();
            if (key.startsWith("@")) { //属性

                body.addAttribute(key.substring(1, key.length()), value.toString());

            } else if ("#text".equals(key)) { //有属性时的文本

                body.setText(value.toString());

            } else {
                if (value instanceof java.util.List) {
                    List list = (List) value;

                    Object obj;
                    for (Object o : list) {

                        //list里是map或String，不会存在list里直接是list的，
                        obj = o;

                        if (obj instanceof Map) {
                            Element subElement = body.addElement(key);

                            map2xml((Map) o, subElement);

                        } else {
                            body.addElement(key).setText((String) o);

                        }

                    }

                } else if (value instanceof java.util.Map) {
                    Element subElement = body.addElement(key);

                    map2xml((Map) value, subElement);

                } else {
                    body.addElement(key).setText(value.toString());

                }

            }

        }

        return body;

    }

    /*** 格式化输出xml
     *@paramdocument2
     *@return*@throwsDocumentException
     *@throwsIOException*/

    public static String formatXml(String xmlStr) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            logger.error("xml解析失败", e);
        }
        return formatXml1(document);

    }

    /*** 格式化输出xml
     *@paramdocument
     *@return*@throwsDocumentException
     *@throwsIOException*/

    public static String formatXml1(Document document) {
        //格式化输出格式

        OutputFormat format = OutputFormat.createPrettyPrint();

        StringWriter writer = new StringWriter();
        //格式化输出流

        XMLWriter xmlWriter = new XMLWriter(writer, format);
        //将document写入到输出流

        try {
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            logger.error("xml解析失败", e);
        }


        return writer.toString();

    }


}
