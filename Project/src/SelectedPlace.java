import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class SelectedPlace {
    private SimpleStringProperty name;
    private SimpleDoubleProperty rate;
    private SimpleDoubleProperty avgPrice;

    public SelectedPlace(String name, double rate, double avgPrice) {
        this.name = new SimpleStringProperty(name);
        this.rate = new SimpleDoubleProperty(rate);
        this.avgPrice = new SimpleDoubleProperty(avgPrice);
    }

    public double getAvgPrice() {
        return avgPrice.get();
    }

    public String getName() {
        return name.get();
    }


    public double getRate() {
        return rate.get();
    }
}
