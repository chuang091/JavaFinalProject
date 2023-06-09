public class Place {
    public double lat;
    public double lng;
    public String name;
    public int pHLimit;
    public int pLLimit;
    public double rate;
    

    public Place(double lat, double lng, String name, int phl, int pll,double rate) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.pHLimit = phl;
        this.pLLimit = pll;
        this.rate = rate;
    }
}
