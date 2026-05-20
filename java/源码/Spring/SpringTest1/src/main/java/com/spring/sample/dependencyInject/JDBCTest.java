package com.spring.sample.dependencyInject;

public class JDBCTest {
    private String url;
    private String userName;
    private String password;
    private String driverPath;
    private int initialSize;
    private int maxActive;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "JDBCTest{" +
            "url='" + url + '\'' +
            ", userName='" + userName + '\'' +
            ", password='" + password + '\'' +
            ", driverPath='" + driverPath + '\'' +
            ", initialSize=" + initialSize +
            ", maxActive=" + maxActive +
            '}';
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverPath() {
        return driverPath;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }
}
