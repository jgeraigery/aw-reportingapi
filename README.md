# HCP Anywhere Reporting API Java SDK                                                                  
          
The HCP Anywhere Reporting API is an API for administrators to generate reports about: 
* User activity
* Profile activity
* System activity

# Types of reports
* Administrative reports: returns information on user configuration and system resource usage. You need to have the Administrator role to generate an administrative report.

* Audit reports: returns information on your HCP Anywhere users' file sync and share activity. You need to have the Audit role to generate an audit report. You can also audit users' activity and filesystems in the Management Console. For more information, please look at the [help documentation](http://tuilato.github.io/aw-reportingapi/Docs/welcomeAdmin.htm).

# Availability 
Release 2.1.0 of HCP Anywhere

# Reporting API Version
2.1.0
                                                                                                    
# Documentation 

Javadoc is available [via github](http://tuilato.github.io/aw-reportingapi/javadoc/).

Reporting API help documentation is available from the HCP Anywhere Management Console or [via github](http://tuilato.github.io/aw-reportingapi/Docs/welcomeAdmin.htm)

## Example                                                                                          
                                            
```
ReportingAPI reportingApi = new ReportingAPI(HOSTNAME, API_VERSION, AUDIT_ADMIN_USERNAME,
   PASSWORD,
   UNIQUE_ID, CLIENT_VERSION,
   CLIENT_NICKNAME);
```

A complete example is provided in the repository under /example.

# Questions?

Reach out to Hitachi Data Systems at our [HCP Anywhere community portal](https://community.hds.com/community/developer-network/hcpaw/pages/developer).

# Copyright and License

Code and documentation copyright by Hitachi Data Systems, 2017.  Release under [the Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0).
