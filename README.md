# HCP Anywhere Reporting API Java SDK                                                                  
          
The HCP Anywhere Reporting API is an API for administrators to generate reports about: 
* User activity
* Profile activity
* System activity

# Types of reports
* Administrative reports: returns information on user configuration and system resource usage. You need to have the Administrator role to generate an administrative report.

* Audit reports: returns information on your HCP Anywhere users' file sync and share activity. You need to have the Audit role to generate an audit report. You can also audit users' activity and filesystems in the Management Console. For more information, please look at the [help documentation](http://hitachidatasystems.github.io/aw-reportingapi/reporting-api-doc).

# Documentation 

Javadoc is available [via github](http://hitachi-data-systems.github.io/aw-reportingapi/javadoc/).

Reporting API help documentation is available from the HCP Anywhere Management Console or [via github](http://hitachi-data-systems.github.io/aw-reportingapi/reporting-api-doc)

## Example                                                                                          
                                            
```
ReportingAPI reportingApi = new ReportingAPI(HOSTNAME, API_VERSION, AUDIT_ADMIN_USERNAME,
   PASSWORD,
   UNIQUE_ID, CLIENT_VERSION,
   CLIENT_NICKNAME);
```

A complete example is provided in the repository under /example.

## Using the HCP Anywhere File Sync and Share SDK                                                       

The SDK is availble at [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.hds.hcpaw%22%20AND%20a%3A%22anywhere-reporting-sdk%22).

### Maven Pom
```
<dependency>
  <groupId>com.hds.hcpaw</groupId>
  <artifactId>anywhere-reporting-sdk</artifactId>
  <version>4.6.0.38</version>
</dependency>
```

### Gradle
```
dependencies {
    compile 'com.hds.hcpaw:anywhere-reporting-sdk:4.6.0.38'
}
```

# Questions?

Reach out to Hitachi Vantara at our community portal http://community.hitachivantara.com.

# Copyright and License

Code and documentation copyright by Hitachi Vantara LLC 2020. All Rights Reserved.  Release under [the Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0).
