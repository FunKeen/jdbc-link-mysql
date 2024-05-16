package pojo;

public class Account {

    private int id;
    private String meal_name;
    private Float price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", meal_name='" + meal_name + '\'' +
                ", price=" + price +
                '}';
    }
}
