package claudiu.exchangerates;

/**
 * Created by Raul B. on 21.12.2015
 */
public class DataProvider {

    private String name;
    private String value;

    public DataProvider(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getExchangeName() {
        return name;
    }

    public String getExchangeValue() {
        return value;
    }


}
