package io.tiklab.matflow.task.code.model;

public class XcodeRepository {


    private String id;


    private String name;



    private String address;


    public String getId() {
        return id;
    }

    public XcodeRepository setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public XcodeRepository setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public XcodeRepository setAddress(String address) {
        this.address = address;
        return this;
    }
}
