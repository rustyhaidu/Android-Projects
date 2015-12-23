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

    public void setExchangeName(String name) {
        this.name = name;
    }

    public String getExchangeValue() {
        return value;
    }

    public void setExchangeValue(String surname) {
        this.value = surname;
    }


}
