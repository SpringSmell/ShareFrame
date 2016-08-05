package com.future.shareframe.model;

import java.util.List;

/**
 * Created by chris Zou on 2016/7/8.
 */
public class TestBean {


    /**
     * station : Z9280
     * name : 成都
     * 仰角 : ["0.5","1.5","2.4"]
     * 范围 : ["230","460"]
     */

    private String station;
    private String name;

    public void set仰角(String[] 仰角) {
        this.仰角 = 仰角;
    }

    public void set范围(String[] 范围) {
        this.范围 = 范围;
    }

    private String[] 仰角;
    private String[] 范围;

    public String[] get范围() {
        return 范围;
    }

    public String[] get仰角() {
        return 仰角;
    }



    public void setStation(String station) {
        this.station = station;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getStation() {
        return station;
    }

    public String getName() {
        return name;
    }

}
