package com.mahait.gov.in.nps.webservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by Apache CXF 3.0.1
 * 2016-03-15T19:54:18.065+05:30
 * Generated source version: 3.0.1
 * 
 */
@WebServiceClient(name = "STPWebServicePOJOService", 
                  wsdlLocation = "/data/contribution///STPWebServicePOJOPort.wsdl",
                  targetNamespace = "http://webservice.core.stp.cra.com/") 
public class STPWebServicePOJOService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://webservice.core.stp.cra.com/", "STPWebServicePOJOService");
    public final static QName STPWebServicePOJOPort = new QName("http://webservice.core.stp.cra.com/", "STPWebServicePOJOPort");
    static {
        URL url = null;
        try {
            url = new URL("https://www.npscan-cra.com/STPWeb/STPWebServicePOJOPort?wsdl");
          //  url = new URL("http://121.240.64.236/STPWeb/STPWebServicePOJOPort");
        	
        	
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(STPWebServicePOJOService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/D:/Nilesh UserData/BO Dev Workspace/CRASOTAppServer/src/main/resources/STPWebServicePOJOPort.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public STPWebServicePOJOService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public STPWebServicePOJOService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public STPWebServicePOJOService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public STPWebServicePOJOService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public STPWebServicePOJOService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public STPWebServicePOJOService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    

    /**
     *
     * @return
     *     returns STPWebServicePOJO
     */
    @WebEndpoint(name = "STPWebServicePOJOPort")
    public STPWebServicePOJO getSTPWebServicePOJOPort() {
        return super.getPort(STPWebServicePOJOPort, STPWebServicePOJO.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns STPWebServicePOJO
     */
    @WebEndpoint(name = "STPWebServicePOJOPort")
    public STPWebServicePOJO getSTPWebServicePOJOPort(WebServiceFeature... features) {
        return super.getPort(STPWebServicePOJOPort, STPWebServicePOJO.class, features);
    }

}
