package io.tiklab.matflow.task.code.model;

/**
 * @author Spotbugs代码扫描，Bug实例
 */
public class SpotbugsBugInstance {

    // bug类型
    private String bugType;

    // bug级别
    private String bugPriority;

    // 简略描述
    private String shortMessage;

    // 详细描述
    private String longMessage;

    //  bug类描述
    private SpotbugsBugClass bugClass;

    //  bug字段描述
    private SpotbugsBugField bugField;

    //  bug方法描述
    private SpotbugsBugMethod bugMethod;

    //  bug源码描述
    private SpotbugsBugSourceLine bugSourceLine;

    // 表示一个Bug的类别
    private SpotbugsBugCategory category;

    // 代码缺陷模式（BugPattern）的信息
    private SpotbugsBugPattern bugPattern;

    // 表示一个Bug的类型
    private SpotbugsBugCode bugCode;


    public String getBugType() {
        return bugType;
    }

    public SpotbugsBugInstance setBugType(String bugType) {
        this.bugType = bugType;
        return this;
    }

    public String getBugPriority() {
        return bugPriority;
    }

    public SpotbugsBugInstance setBugPriority(String bugPriority) {
        this.bugPriority = bugPriority;
        return this;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public SpotbugsBugInstance setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
        return this;
    }

    public String getLongMessage() {
        return longMessage;
    }

    public SpotbugsBugInstance setLongMessage(String longMessage) {
        this.longMessage = longMessage;
        return this;
    }

    public SpotbugsBugClass getBugClass() {
        return bugClass;
    }

    public SpotbugsBugInstance setBugClass(SpotbugsBugClass bugClass) {
        this.bugClass = bugClass;
        return this;
    }

    public SpotbugsBugField getBugField() {
        return bugField;
    }

    public SpotbugsBugInstance setBugField(SpotbugsBugField bugField) {
        this.bugField = bugField;
        return this;
    }

    public SpotbugsBugMethod getBugMethod() {
        return bugMethod;
    }

    public SpotbugsBugInstance setBugMethod(SpotbugsBugMethod bugMethod) {
        this.bugMethod = bugMethod;
        return this;
    }

    public SpotbugsBugSourceLine getBugSourceLine() {
        return bugSourceLine;
    }

    public SpotbugsBugInstance setBugSourceLine(SpotbugsBugSourceLine bugSourceLine) {
        this.bugSourceLine = bugSourceLine;
        return this;
    }

    public SpotbugsBugCategory getCategory() {
        return category;
    }

    public SpotbugsBugInstance setCategory(SpotbugsBugCategory category) {
        this.category = category;
        return this;
    }

    public SpotbugsBugPattern getBugPattern() {
        return bugPattern;
    }

    public SpotbugsBugInstance setBugPattern(SpotbugsBugPattern bugPattern) {
        this.bugPattern = bugPattern;
        return this;
    }

    public SpotbugsBugCode getBugCode() {
        return bugCode;
    }

    public SpotbugsBugInstance setBugCode(SpotbugsBugCode bugCode) {
        this.bugCode = bugCode;
        return this;
    }
}
