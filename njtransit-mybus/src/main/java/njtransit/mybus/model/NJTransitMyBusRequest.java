package njtransit.mybus.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * a request POJO for making queries to NJTRansit MyBus site
 * TODO: generalize a super class
 *
 * @author codywang
 */
public class NJTransitMyBusRequest {

  private final String route;
  private final String direction;
  private final String stopId;
  private final String displayStopName;
  private final boolean showAllBuses;

  /**
   * construct a request for NJTransitMyBus;
   * if no stop name is given, use StringUtils.EMPTY
   * 
   * @param route
   * @param direction
   * @param stopId
   * @param showAllBuses
   */
  public NJTransitMyBusRequest(final String route, final String direction, final String stopId, final boolean showAllBuses) {
    this(route, direction, stopId, StringUtils.EMPTY, showAllBuses);
  }

  /**
   * construct a request for NJTransitMyBus
   * 
   * @param route
   * @param direction
   * @param stopId
   * @param displayStopName
   * @param showAllBuses
   */
  public NJTransitMyBusRequest(final String route, final String direction, final String stopId, final String displayStopName, final boolean showAllBuses) {
    Validate.notBlank(stopId, "stopId is required");

    this.route = route;
    this.direction = direction;
    this.stopId = stopId;
    this.displayStopName = displayStopName;
    this.showAllBuses = showAllBuses;
  }

  public String getRoute() {
    return this.route;
  }

  public String getDirection() {
    return this.direction;
  }

  public String getStopId() {
    return this.stopId;
  }

  public String getDisplayStopName() {
    return this.displayStopName;
  }

  public boolean isShowAllBuses() {
    return this.showAllBuses;
  }

  public List<BusRouteETA> getBusRouteETA(final Client client, final String url) {
    final List<BusRouteETA> etaResults = new ArrayList<BusRouteETA>();

    final String showAllBusses = this.showAllBuses ? "on" : "off";
    final WebTarget target = client.target(url)
        .queryParam("route", this.route)
        .queryParam("direction", this.direction)
        .queryParam("id", this.stopId)
        .queryParam("showAllBusses", showAllBusses);

    final Response response = target.request(MediaType.TEXT_PLAIN).get();
    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
      final Document document = Jsoup.parse(response.readEntity(String.class));
      final Element root = document.body().select("hr").first();
      final Elements routeInfoElements = root.siblingElements().select("b");
      String currentRoute = StringUtils.EMPTY;
      for (final Element child : routeInfoElements) {
        final String val = child.text().toLowerCase();
        if (val.contains("#")) {
          currentRoute = val;
        } else if (val.contains("min")) {
          etaResults.add(BusRouteETA.createParsedBusRouteETA(Integer.parseInt(this.stopId), this.displayStopName, currentRoute, val));
        }
      }
    }
    return etaResults;
  }
}
