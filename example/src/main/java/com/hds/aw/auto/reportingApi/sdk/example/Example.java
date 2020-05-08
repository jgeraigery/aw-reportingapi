/*
 * ========================================================================
 * 
 * Copyright (c) by Hitachi Vantara, 2018. All rights reserved.
 * 
 * ========================================================================
 */

package com.hds.aw.auto.reportingApi.sdk.example;

import com.hds.aw.auto.reportingApi.sdk.ReportingAPI;
import com.hds.aw.auto.reportingApi.sdk.exception.ReportingAPISDKException;
import com.hds.aw.auto.reportingApi.sdk.ReportingAPIVersion;
import com.hds.aw.auto.reportingApi.sdk.models.AdminUserLastAccessReportUserEntry;
import com.hds.aw.auto.reportingApi.sdk.models.AuditAccountActivityReportUserEntry;
import com.hds.aw.auto.reportingApi.sdk.models.UserReportPage;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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
    public static final String CLIENT_VERSION = "4.3.2.0";
    public static final String CLIENT_NICKNAME = "ReportingAPISDK";
    public static final String USER_TO_AUDIT = "userToAudit";
    public static final String CERTIFICATE_PATH = null;
    public static final String CERTIFICATE_TYPE = "PKCS12";
    public static final Integer MAX_RESULTS = 20;

    public static void main(String[] args) throws ReportingAPISDKException, GeneralSecurityException {
        SSLContext sslContext = null;
        // Create an SSL context for a P12 certificate
        if (CERTIFICATE_PATH != null) {
            sslContext = createInsecureSSLContext(getKeyManagerFactory(CERTIFICATE_PATH, PASSWORD,
                    CERTIFICATE_TYPE), "TLS");
        }
        // Instantiating ReportingAPI instance is always the first step. Use this instance to access
        // all kinds of reports.
        ReportingAPI reportingApi = new ReportingAPI(HOSTNAME, API_VERSION, AUDIT_ADMIN_USERNAME,
                PASSWORD,
                UNIQUE_ID, CLIENT_VERSION,
                CLIENT_NICKNAME,
                sslContext);
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
        System.out.println(pageToken);
        UserReportPage<AdminUserLastAccessReportUserEntry> lastAccessReportSecondPage = reportingApi
                .getAdminReports().getUserLastAccessReport()
                .getReportWithPageToken(pageToken, MAX_RESULTS, null)
                .getResponseType();
        System.out.println(lastAccessReportSecondPage);
    }

    /**
     * Creates an SSL context
     *
     * @param keyManagerFactory Key manager factory
     * @param sslProtocol       SSL protocol
     * @return SSLContext
     * @throws GeneralSecurityException
     */
    public static SSLContext createInsecureSSLContext(KeyManagerFactory keyManagerFactory,
                                                      String sslProtocol)
            throws GeneralSecurityException {
        SSLContext sslContext = SSLContext.getInstance(sslProtocol);

        sslContext.init(keyManagerFactory.getKeyManagers(), new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }}, new SecureRandom());

        return sslContext;
    }

    /**
     * Get a KeyManagerFactory
     *
     * @param certificatePath
     * @param certificatePassword
     * @param certificateType
     * @return KeyManagerFactory
     */
    public static KeyManagerFactory getKeyManagerFactory(String certificatePath, String certificatePassword,
                                                         String certificateType) {
        KeyStore keyStore = null;
        TrustManagerFactory trustManagerFactory = null;
        KeyManagerFactory keyManagerFactory = null;

        try {
            keyStore = KeyStore.getInstance(certificateType);
            keyStore.load(new FileInputStream(certificatePath), certificatePassword.toCharArray());
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, certificatePassword.toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException |
                CertificateException | UnrecoverableKeyException e) {
            throw new RuntimeException("Could not register ssl.", e);
        }

        return keyManagerFactory;
    }
}
