package njtransit.mybus.model;

import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * A bus route + stop ETA POJO, which is only valid/accurate for brief moment (~1 minute granularity)
 *
 * @author codywang
 */
public class BusRouteETA implements Comparable<BusRouteETA> {

  private static final PeriodFormatter FORMATTER = new PeriodFormatterBuilder().appendMinutes().appendSuffix("min").toFormatter();
  private static final int UNKNOWN = -1;

  private final BusStop stop;
  private final Integer routeNum;
  private final Period eta;
  private final Integer busId;
  private final boolean isLessThan;

  public BusRouteETA(final BusStop stop, final int routeNum, final Period eta, final int busId, final boolean isLessThan) {
    this.stop = stop;
    this.routeNum = routeNum;
    this.eta = eta;
    this.busId = busId;
    this.isLessThan = isLessThan;
  }

  /**
   * factory for parsing route and eta strings into object
   * TODO: add support for bus id's (maybe)
   * 
   * @param routeStr
   * @param etaString
   * @return
   */
  public static BusRouteETA createParsedBusRouteETA(final int stopId, final String stopName, final String routeStr, final String etaString) {
    final BusStop stop = new BusStop(stopId, stopName);
    final Integer routeNum = NumberUtils.toInt(routeStr.replaceAll("[^\\d]", ""), UNKNOWN);
    final boolean isLessThan = etaString.contains("<");
    final Period eta = FORMATTER.parsePeriod(etaString.replaceAll("[^a-zA-Z0-9]", ""));
    final Integer busId = UNKNOWN;

    return new BusRouteETA(stop, routeNum, eta, busId, isLessThan);
  }

  // TODO: add constructor for saving bus-vehicle ID too

  public BusStop getStop() {
    return this.stop;
  }

  public Integer getRouteNum() {
    return this.routeNum;
  }

  public Period getEta() {
    return this.eta;
  }

  public Integer getBusId() {
    return this.busId;
  }

  @Override
  public String toString() {
    String operatorStr = " about ";
    String unitStr = " minutes ";
    if(this.isLessThan) {
      operatorStr = " less than ";
    }
    if(this.eta.getMinutes() == 1) {
      unitStr = " minute ";
    }
    return "Another route " + integerToDigits(this.routeNum) + " bus is " + operatorStr + this.eta.getMinutes() + unitStr + " away";
  }

  @Override
  public int compareTo(final BusRouteETA routeEta) {
    final int primaryCompare = Integer.compare(this.eta.getMinutes(), routeEta.eta.getMinutes());
    if (primaryCompare == 0) {
      return Integer.compare(this.routeNum, routeEta.routeNum);
    }
    return primaryCompare;
  }

  private static String integerToDigits(final int number) {
    return Integer.toString(number).replaceAll(".(?!$)", "$0 ");
  }
}
