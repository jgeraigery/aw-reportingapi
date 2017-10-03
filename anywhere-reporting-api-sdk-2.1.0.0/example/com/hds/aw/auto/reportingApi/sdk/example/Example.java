/*
 * ========================================================================
 * 
 * Copyright (c) by Hitachi Data Systems, 2016. All rights reserved.
 * 
 * ========================================================================
 */

package com.hds.aw.auto.reportingApi.sdk.example;

import com.hds.aw.auto.reportingApi.sdk.ReportingAPI;
import com.hds.aw.auto.reportingApi.sdk.ReportingAPISDKException;
import com.hds.aw.auto.reportingApi.sdk.ReportingAPIVersion;
import com.hds.aw.auto.reportingApi.sdk.models.AdminUserLastAccessReportUserEntry;
import com.hds.aw.auto.reportingApi.sdk.models.AuditAccountActivityReportUserEntry;
import com.hds.aw.auto.reportingApi.sdk.models.UserReportPage;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Example of how to use the SDK.
 */
public class Example {
    public static final String HOSTNAME = "hostName";
    public static final ReportingAPIVersion API_VERSION = ReportingAPIVersion.LATEST;
    public static final String AUDIT_ADMIN_USERNAME = "auditAdminUser";
    public static final String PASSWORD = "password";
    public static final String UNIQUE_ID = "uniqueId";
    public static final String CLIENT_VERSION = "2.1.0.0";
    public static final String CLIENT_NICKNAME = "ReportingAPISDK";
    public static final String USER_TO_AUDIT = "userToAudit";
    public static final Integer MAX_RESULTS = 20;

    public static void main(String[] args) throws ReportingAPISDKException {
        // Instantiating ReportingAPI instance is always the first step. Use this instance to access
        // all kinds of reports.
        ReportingAPI reportingApi = new ReportingAPI(HOSTNAME, API_VERSION, AUDIT_ADMIN_USERNAME,
                PASSWORD,
                UNIQUE_ID, CLIENT_VERSION,
                CLIENT_NICKNAME);
        // Get audit report through ReportingAPI instance. Here is an example of getting account
        // activity report.
        Calendar startTime = new GregorianCalendar(2016, Calendar.JANUARY, 1);
        UserReportPage<AuditAccountActivityReportUserEntry> userAccountReport = reportingApi
                .getAuditReports().getAccountActivityReport()
                .getReportOnUser(USER_TO_AUDIT, startTime.getTimeInMillis(),
                                 System.currentTimeMillis(), null, null)
                .getResponseType();
        // The content of the report is encapsulated as Java object. They can be processed in
        // programmatic ways.
        for (AuditAccountActivityReportUserEntry userEntry : userAccountReport.getUsers()) {
            System.out.println(userEntry.getUser());
        }
        // Get admin report through ReportingAPI instance. Here is an example of getting admin user
        // last access report.
        UserReportPage<AdminUserLastAccessReportUserEntry> lastAccessReportFirstPage = reportingApi
                .getAdminReports().getUserLastAccessReport()
                .getSystemScopeReport(MAX_RESULTS, null, null)
                .getResponseType();
        // If the report is not finished, get the next page of the report with page token.
        String pageToken = lastAccessReportFirstPage.getPageToken();
        UserReportPage<AdminUserLastAccessReportUserEntry> lastAccessReportSecondPage = reportingApi
                .getAdminReports().getUserLastAccessReport()
                .getReportWithPageToken(pageToken, MAX_RESULTS, null)
                .getResponseType();
        System.out.println(lastAccessReportSecondPage);
    }
}
