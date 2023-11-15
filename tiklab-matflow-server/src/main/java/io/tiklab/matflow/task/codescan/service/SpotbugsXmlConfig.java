package io.tiklab.matflow.task.codescan.service;

import io.tiklab.core.exception.SystemException;
import io.tiklab.matflow.task.codescan.model.*;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * SpotbugsXml文件解析
 * @author zcamy
 */
public class SpotbugsXmlConfig {

    /**
     * 解析代码扫描统计信息
     * @param xmlPath 扫描结果文件
     * @return 解析结果
     */
    public SpotbugsBugSummary findScanSummary(String xmlPath){
        try {
            File xmlFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // 可选：根据需要对Document进行额外的配置
            doc.getDocumentElement().normalize();

            // 获取根元素
            Element rootElement = doc.getDocumentElement();

            NodeList findBugsSummaryList = rootElement.getElementsByTagName("FindBugsSummary");
            for (int i = 0; i < findBugsSummaryList.getLength(); i++) {
                Node findBugsSummaryNode = findBugsSummaryList.item(i);
                if (findBugsSummaryNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element findBugsSummaryElement = (Element) findBugsSummaryNode;
                    String totalClasses = findBugsSummaryElement.getAttribute("total_classes"); //总共扫描的类的数量。
                    String referencedClasses = findBugsSummaryElement.getAttribute("referenced_classes");//引用的类的数量。
                    String totalBugs = findBugsSummaryElement.getAttribute("total_bugs");//总共检测到的问题（Bugs）的数量。
                    String numPackages = findBugsSummaryElement.getAttribute("num_packages");//项目中的包（package）数量。
                    String vmVersion = findBugsSummaryElement.getAttribute("vm_version");//vm_version Java 版本信息。
                    String priority2 = findBugsSummaryElement.getAttribute("priority_2");//优先级为 2 的问题数量。
                    String priority1 = findBugsSummaryElement.getAttribute("priority_1");//优先级为 1 的问题数量。
                    String priority3 = findBugsSummaryElement.getAttribute("priority_3");//优先级为 1 的问题数量。

                    // 转换时间
                    String format;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                    try {
                        Date date = dateFormat.parse(findBugsSummaryElement.getAttribute("timestamp"));
                        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                    } catch (ParseException e) {
                        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    }

                    return new SpotbugsBugSummary()
                            .setScanTime(format).setTotalClasses(totalClasses).setReferencedClasses(referencedClasses)
                            .setTotalBugs(totalBugs).setNumPackages(numPackages).setVmVersion(vmVersion)
                            .setPriorityOne(priority1).setPriorityTwo(priority2).setPriorityThree(priority3);
                }
            }
        }catch (Exception e){
            throw new SystemException("解析Xml文件失败，path:"+xmlPath+" ，message："+e.getMessage());
        }
        return null;
    }

    /**
     * 解析所有Bug
     * @param xmlPath 文件地址
     * @return Bug集合
     */

    public List<SpotbugsBugFileStats> findBugFileStats(String xmlPath){
        try {
            File xmlFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // 可选：根据需要对Document进行额外的配置
            doc.getDocumentElement().normalize();

            List<SpotbugsBugFileStats> list = new ArrayList<>();

            // 获取根元素
            Element rootElement = doc.getDocumentElement();

            NodeList findBugsSummaryList = rootElement.getElementsByTagName("FindBugsSummary");
            for (int i = 0; i < findBugsSummaryList.getLength(); i++) {
                Node findBugsSummaryNode = findBugsSummaryList.item(i);
                if (findBugsSummaryNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element findBugsSummaryElement = (Element) findBugsSummaryNode;

                    NodeList fileStatsList = findBugsSummaryElement.getElementsByTagName("FileStats");
                    for (int i1 = 0; i1 < fileStatsList.getLength(); i1++) {
                        Node fileStatsNide = fileStatsList.item(i);
                        if (fileStatsNide.getNodeType() == Node.ELEMENT_NODE) {
                            Element fileStatsElement = (Element) fileStatsNide;
                            String path = fileStatsElement.getAttribute("path");
                            String bugCount = fileStatsElement.getAttribute("bugCount");

                            String replace = path.replaceAll("/", ".").replace(".java","");

                            List<SpotbugsBugInstance> scanBugsList = findScanBugs(xmlPath);
                            List<SpotbugsBugInstance> bugInstanceList = scanBugsList.stream()
                                    .filter(a -> !Objects.equals(a.getBugClass().getClassname(), replace)).toList();

                            SpotbugsBugFileStats spotbugsBugFileStats = new SpotbugsBugFileStats()
                                    .setPath(path)
                                    .setBugNumber(String.valueOf(scanBugsList.size()))
                                    .setBugInstanceList(bugInstanceList);

                            list.add(spotbugsBugFileStats);

                        }
                    }
                }
            }

            return list;
        }catch (Exception e){
            throw new SystemException("解析Xml文件失败，path:"+xmlPath+" ，message："+e.getMessage());
        }
    }

    /**
     * 根据包解析Bug
     * @param xmlPath xml文件地址
     * @return bug集合
     */

    public List<SpotbugsBugPackageStats> findBugPackageStats(String xmlPath){
        try {
            File xmlFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // 可选：根据需要对Document进行额外的配置
            doc.getDocumentElement().normalize();

            List<SpotbugsBugPackageStats> list = new ArrayList<>();

            // 获取根元素
            Element rootElement = doc.getDocumentElement();

            NodeList findBugsSummaryList = rootElement.getElementsByTagName("FindBugsSummary");
            for (int i = 0; i < findBugsSummaryList.getLength(); i++) {
                Node findBugsSummaryNode = findBugsSummaryList.item(i);
                if (findBugsSummaryNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element findBugsSummaryElement = (Element) findBugsSummaryNode;

                    NodeList packageStatsList = findBugsSummaryElement.getElementsByTagName("PackageStats");
                    for (int i1 = 0; i1 < packageStatsList.getLength(); i1++) {
                        Node fileStatsNide = packageStatsList.item(i);
                        if (fileStatsNide.getNodeType() == Node.ELEMENT_NODE) {
                            Element fileStatsElement = (Element) findBugsSummaryNode;
                            String packagePath = fileStatsElement.getAttribute("package");
                            String totalBugs = fileStatsElement.getAttribute("total_bugs");
                            String totalTypes = fileStatsElement.getAttribute("total_types");

                            // 文件不存在bug
                            if ("0".equals(totalBugs)){
                                continue;
                            }
                            List<SpotbugsBugClassStats> classStatsArrayList = new ArrayList<>();

                            NodeList classStatsList = fileStatsElement.getElementsByTagName("ClassStats");
                            for (int i2 = 0; i2 < classStatsList.getLength(); i2++) {
                                Element classStatsElement = (Element)  classStatsList.item(i);
                                String aClass = classStatsElement.getAttribute("class");
                                String bugs = classStatsElement.getAttribute("bugs");
                                String idInterface = classStatsElement.getAttribute("interface");
                                // if ("0".equals(bugs)){
                                //     continue;
                                // }

                                List<SpotbugsBugInstance> scanBugsList = findScanBugs(xmlPath);
                                List<SpotbugsBugInstance> bugInstanceList = scanBugsList.stream()
                                        .filter(a -> !Objects.equals(a.getBugClass().getClassname(), aClass)).toList();

                                SpotbugsBugClassStats spotbugsBugClassStats = new SpotbugsBugClassStats()
                                        // .setBugNumber(scanBugsList.size())
                                        .setClassName(aClass)
                                        // .setInterface(Boolean.valueOf(idInterface))
                                        .setBugInstanceList(bugInstanceList);

                                classStatsArrayList.add(spotbugsBugClassStats);
                            }
                            SpotbugsBugPackageStats spotbugsBugPackageStats = new SpotbugsBugPackageStats()
                                    .setTotalBugs(classStatsArrayList.size())
                                    .setPackagePath(packagePath)
                                    // .setTotalTypes(Integer.parseInt(totalTypes))
                                    .setClassStatsList(classStatsArrayList);
                            list.add(spotbugsBugPackageStats);
                        }
                    }
                }
            }
            return list;
        }catch (Exception e){
            throw new SystemException("解析Xml文件失败，path:"+xmlPath+" ，message："+e.getMessage());
        }
    }


    public List<SpotbugsBugInstance> findScanBugs(String xmlPath){
        try {
            // 读取XML文件
            File xmlFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // 可选：根据需要对Document进行额外的配置
            doc.getDocumentElement().normalize();

            // 获取根元素
            Element rootElement = doc.getDocumentElement();

            List<SpotbugsBugInstance> list = new ArrayList<>();

            Map<String, SpotbugsBugCategory> categoryMap = analysisBugCategory(rootElement);

            Map<String, SpotbugsBugPattern> patternMap = analysisBugPattern(rootElement);

            Map<String, SpotbugsBugCode> bugCodeMap = analysisBugCode(rootElement);


            // 获取BugInstance元素的列表
            NodeList bugInstanceList = rootElement.getElementsByTagName("BugInstance");

            // 遍历BugInstance元素列表
            for (int i = 0; i < bugInstanceList.getLength(); i++) {
                Node bugInstanceNode = bugInstanceList.item(i);

                if (bugInstanceNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element bugInstanceElement = (Element) bugInstanceNode;

                    // 获取Bug 级别
                    String priority = bugInstanceElement.getAttribute("priority");

                    String bugDescription = bugInstanceElement.getElementsByTagName("LongMessage")
                            .item(0).getTextContent();

                    String shortMessage = bugInstanceElement.getElementsByTagName("ShortMessage")
                            .item(0).getTextContent();


                    String abbrev = bugInstanceElement.getAttribute("abbrev");
                    SpotbugsBugCode spotbugsBugCode = bugCodeMap.get(abbrev);

                    String category = bugInstanceElement.getAttribute("category");
                    SpotbugsBugCategory spotbugsBugCategory = categoryMap.get(category);

                    String bugType = bugInstanceElement.getAttribute("type");
                    SpotbugsBugPattern spotbugsBugPattern = patternMap.get(bugType);

                    // 解析Class
                    SpotbugsBugClass spotbugsBugClass = analysisClass(bugInstanceElement);

                    // 解析Method
                    SpotbugsBugMethod spotbugsBugMethod = analysisMethod(bugInstanceElement);

                    // 解析Field
                    SpotbugsBugField spotbugsBugField = analysisField(bugInstanceElement);

                    // 解析SourceLine
                    SpotbugsBugSourceLine spotbugsBugSourceLine = analysisSourceLine(bugInstanceElement);

                    SpotbugsBugInstance spotbugsBugInstance = new SpotbugsBugInstance()
                            .setBugClass(spotbugsBugClass)
                            .setBugCode(spotbugsBugCode)
                            .setBugField(spotbugsBugField)
                            .setBugMethod(spotbugsBugMethod)
                            .setBugField(spotbugsBugField)
                            .setCategory(spotbugsBugCategory)
                            .setBugPattern(spotbugsBugPattern)
                            .setBugPriority(priority)
                            .setBugSourceLine(spotbugsBugSourceLine)
                            .setBugType(bugType)
                            .setLongMessage(bugDescription)
                            .setShortMessage(shortMessage);
                    list.add(spotbugsBugInstance);
                }
            }
            return list.stream().sorted(Comparator.comparing(SpotbugsBugInstance::getBugPriority)).toList();

        } catch (Exception e) {
            throw new SystemException("解析Xml文件失败，path:"+xmlPath+" ，message："+e.getMessage());
        }
    }

    // 解析Class
    private SpotbugsBugClass analysisClass(Element element){
        Element classElement = (Element) element.getElementsByTagName("Class")
                .item(0);
        Element sourceLine = (Element) classElement.getElementsByTagName("SourceLine")
                .item(0);
        int beginLine = 0 ,endLine = 0;
        String start = sourceLine.getAttribute("start");
        if (!StringUtils.isEmpty(start)){
            beginLine = Integer.parseInt(sourceLine.getAttribute("start"));
        }
        String end = sourceLine.getAttribute("end");
        if (!StringUtils.isEmpty(end)){
            endLine = Integer.parseInt(sourceLine.getAttribute("end"));
        }
        String classname = classElement.getAttribute("classname");
        String message =  classElement.getElementsByTagName("Message")
                .item(0).getTextContent();
        return new SpotbugsBugClass()
                .setMessage(message)
                .setStartLine(beginLine)
                .setEndLine(endLine)
                .setClassname(classname);
    }

    // 解析Method
    private SpotbugsBugMethod analysisMethod(Element element){
        Element methodElement = (Element) element.getElementsByTagName("Method")
                .item(0);
        if (Objects.isNull(methodElement)){
            return null;
        }
        String name = methodElement.getAttribute("name");
        String signature = methodElement.getAttribute("signature");
        Element sourceLine = (Element) methodElement.getElementsByTagName("SourceLine")
                .item(0);
        int beginLine = 0 ,endLine = 0;
        String start = sourceLine.getAttribute("start");
        if (!StringUtils.isEmpty(start)){
            beginLine = Integer.parseInt(start);
        }
        String end = sourceLine.getAttribute("end");
        if (!StringUtils.isEmpty(end)){
            endLine = Integer.parseInt(end);
        }
        String message =  methodElement.getElementsByTagName("Message")
                .item(0).getTextContent();
        return new SpotbugsBugMethod()
                .setMethodName(name)
                .setSignature(signature)
                .setStartLine(beginLine)
                .setEndLine(endLine)
                .setMessage(message);
    }

    // 解析Field
    private SpotbugsBugField analysisField(Element element){
        Element fieldElement = (Element) element.getElementsByTagName("Field")
                .item(0);
        if (Objects.isNull(fieldElement)){
            return null;
        }
        String name = fieldElement.getAttribute("name");
        String signature = fieldElement.getAttribute("signature");
        String message =  fieldElement.getElementsByTagName("Message")
                .item(0).getTextContent();
        return new SpotbugsBugField()
                .setFieldName(name)
                .setSignature(signature)
                .setMessage(message);
    }

    // 解析SourceLine
    private SpotbugsBugSourceLine analysisSourceLine(Element element){
        Element sourceLineElement = (Element) element.getElementsByTagName("SourceLine").item(0);
        if (Objects.isNull(sourceLineElement)){
            return null;
        }
        String sourcePath = sourceLineElement.getAttribute("sourcepath");
        String sourceFile = sourceLineElement.getAttribute("sourcefile");

        int beginLine = 0 ,endLine = 0;
        String start = sourceLineElement.getAttribute("start");
        if (!StringUtils.isEmpty(start)){
            beginLine = Integer.parseInt(start);
        }
        String end = sourceLineElement.getAttribute("end");
        if (!StringUtils.isEmpty(end)){
            endLine = Integer.parseInt(end);
        }
        String message =  sourceLineElement.getElementsByTagName("Message").item(0).getTextContent();
        return new SpotbugsBugSourceLine()
                .setStartLine(beginLine)
                .setEndLine(endLine)
                .setMessage(message)
                .setSourceFile(sourceFile)
                .setSourcePath(sourcePath);
    }

    // 解析Category
    private Map<String, SpotbugsBugCategory> analysisBugCategory(Element element){
        NodeList bugInstanceList = element.getElementsByTagName("BugCategory");
        Map<String, SpotbugsBugCategory> hashMap = new HashMap<>();
        for (int i = 0; i < bugInstanceList.getLength(); i++) {
            Node bugInstanceNode = bugInstanceList.item(i);

            if (bugInstanceNode.getNodeType() == Node.ELEMENT_NODE) {
                Element categoryElement = (Element) bugInstanceNode;

                String category = categoryElement.getAttribute("category");
                String description =  categoryElement.getElementsByTagName("Description")
                        .item(0).getTextContent();
                SpotbugsBugCategory spotbugsBugCategory = new SpotbugsBugCategory()
                        .setCategory(category)
                        .setDescription(description);

                hashMap.put(category,spotbugsBugCategory);
            }
        }
        return hashMap;
    }

    private Map<String, SpotbugsBugPattern> analysisBugPattern(Element element){
        NodeList bugInstanceList = element.getElementsByTagName("BugPattern");
        Map<String, SpotbugsBugPattern> hashMap = new HashMap<>();
        for (int i = 0; i < bugInstanceList.getLength(); i++) {
            Node bugInstanceNode = bugInstanceList.item(i);

            if (bugInstanceNode.getNodeType() == Node.ELEMENT_NODE) {
                Element categoryElement = (Element) bugInstanceNode;

                String category = categoryElement.getAttribute("category");
                String type = categoryElement.getAttribute("type");
                String abbrev = categoryElement.getAttribute("abbrev");

                String shortDescription = categoryElement.getElementsByTagName("ShortDescription")
                        .item(0).getTextContent();

                String details = categoryElement.getElementsByTagName("Details")
                        .item(0).getTextContent();
                SpotbugsBugPattern spotbugsBugPattern = new SpotbugsBugPattern()
                        .setCategory(category)
                        .setType(type)
                        .setAbbrev(abbrev)
                        .setShortDescription(shortDescription)
                        .setDetails(details);

                hashMap.put(type, spotbugsBugPattern);
            }
        }
        return hashMap;
    }

    private Map<String, SpotbugsBugCode> analysisBugCode(Element element){
        NodeList bugInstanceList = element.getElementsByTagName("BugCode");
        Map<String, SpotbugsBugCode> hashMap = new HashMap<>();
        for (int i = 0; i < bugInstanceList.getLength(); i++) {
            Node bugInstanceNode = bugInstanceList.item(i);

            if (bugInstanceNode.getNodeType() == Node.ELEMENT_NODE) {
                Element categoryElement = (Element) bugInstanceNode;

                String cweid = categoryElement.getAttribute("cweid");
                String abbrev = categoryElement.getAttribute("abbrev");

                String description =  categoryElement.getElementsByTagName("Description")
                        .item(0).getTextContent();

                SpotbugsBugCode spotbugsBugCode = new SpotbugsBugCode()
                        .setAbbrev(abbrev)
                        .setCweid(cweid)
                        .setDescription(description);

                hashMap.put(abbrev,spotbugsBugCode);

            }
        }
        return hashMap;
    }



}
