public class Place {
double lat;
double lng;
String name;
double pLLimit;
double pHLimit;
double rate;
int id;
int veg;
String type;
String diningTime;
String position;
double avgprice;
    public Place(int id, String name, double pLLimit, double pHLimit, int veg, String type, 
                 String diningTime, String position, double rate, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.pLLimit = pLLimit;
        this.pHLimit = pHLimit;
        this.veg = veg;
        this.type = type;
        this.diningTime = diningTime;
        this.position = position;
        this.rate = rate;
        this.lat = lat;
        this.lng = lng;
        avgprice =  pLLimit+ pHLimit /2.0;
    }
}
