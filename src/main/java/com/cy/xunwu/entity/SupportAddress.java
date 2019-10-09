package com.cy.xunwu.entity;

import javax.persistence.*;

@Entity
@Table(name = "support_address")
public class SupportAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //上一级行政单位
    @Column(name = "belong_to")
    private String belongTo;

    @Column(name = "en_name")
    private String enName;

    @Column(name = "cn_name")
    private String cnName;

    private String level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    /**
     *
     * 行政级别定义
     *
     * 每个枚举类型变量都是该类的一个实例。
     * 相当于CITY = new Level("city")
     */
    public enum Level{

        CITY("city"),
        REGION("region");

        private String value;

        Level(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Level of(String value){

            //Level.values()获取到CITY和REGION
            for (Level level: Level.values()) {

                //level.getValue()就是相当于CITY.getValue(),得到city
                if (level.getValue().equals(value)){

                    //返回的是枚举变量，CITY或者REGION
                    return level;
                }

            }
            throw new IllegalArgumentException();
        }
    }
}
