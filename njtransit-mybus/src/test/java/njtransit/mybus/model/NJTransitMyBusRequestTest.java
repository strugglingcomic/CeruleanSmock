package njtransit.mybus.model;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientProperties;
import org.junit.Before;
import org.junit.Test;

import njtransit.mybus.MyBusSpeechlet;

public class NJTransitMyBusRequestTest {
  private Client testClient;
  private NJTransitMyBusRequest test156Request;
  private NJTransitMyBusRequest test159Request;
  
  @Before
  public void setup() {
    this.testClient = ClientBuilder.newClient();
    this.testClient.property(ClientProperties.CONNECT_TIMEOUT, 500);
    this.testClient.property(ClientProperties.READ_TIMEOUT, 500);
    this.test156Request = new NJTransitMyBusRequest("156", "New York", "21880", false);
    this.test159Request = new NJTransitMyBusRequest("159", "New York", "21880", false);
  }
  
  @Test
  public void testDefaultRouteETARequests() {
    List<BusRouteETA> results = new ArrayList<BusRouteETA>();
    results.addAll(this.test156Request.getBusRouteETA(this.testClient, MyBusSpeechlet.NJTRANSIT_MYBUS_URL_ROOT));
    results.addAll(this.test159Request.getBusRouteETA(this.testClient, MyBusSpeechlet.NJTRANSIT_MYBUS_URL_ROOT));
    for(BusRouteETA eta : results) {
      System.out.println(eta);
    }
  }

}
