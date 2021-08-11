package com.easipass.util.entity.po;

import com.zj0724.common.component.jdbc.AccessDatabaseJdbc;
import java.util.Date;

/**
 * ApiTestCase
 *
 * @author ZJ
 * */
@Table(name = "API_TEST_CASE")
public final class ApiTestCasePO extends AbstractPO {

    @Column(name = "NAME", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String name;

    @Column(name = "TEST_POINT", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String testPoint;

    @Column(name = "REQUEST_HEADER", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String requestHeader;

    @Column(name = "REQUEST_BODY_DATA", type = AccessDatabaseJdbc.FieldType.LONG_VARCHAR)
    private String requestBodyData;

    @Column(name = "EXPECTED_RESULT", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String expectedResult;

    @Column(name = "ACTUAL_RESULT", type = AccessDatabaseJdbc.FieldType.VARCHAR)
    private String actualResult;

    @Column(name = "EXECUTE_TIME", type = AccessDatabaseJdbc.FieldType.DATE)
    private Date executeTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestPoint() {
        return testPoint;
    }

    public void setTestPoint(String testPoint) {
        this.testPoint = testPoint;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestBodyData() {
        return requestBodyData;
    }

    public void setRequestBodyData(String requestBodyData) {
        this.requestBodyData = requestBodyData;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

}