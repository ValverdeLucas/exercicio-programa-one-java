import java.util.ArrayList;
import java.util.List;

public class CreditCard {
    private double accountAmount;
    private double accountLimit;
    private List<Product> cart;

    public CreditCard() {
        this.cart = new ArrayList<>();
    }

    public double setAccountLimit(double accountLimit) {
        this.accountLimit += accountLimit;
        return accountLimit;
    }

    public double setAccountAmount(double accountAmount) {
        this.accountAmount += accountAmount;
        return accountAmount;
    }

    public boolean insertOnCart(Product product) {
        if (this.accountAmount >= product.getPrice()) {
            this.accountAmount -= product.getPrice();
            this.cart.add(product);
            return true;
        }
        return false;
    }

    public boolean removeFromCart(Product product) {
        if (this.cart.contains(product)) {
            this.accountAmount += product.getPrice();
            this.cart.remove(product);
            return true;
        }
        return false;
    }

    public double getAccountAmount() {
        return accountAmount;
    }

    public double getAccountLimit() {
        return accountLimit;
    }

    public List<Product> getCart() {
        return cart;
    }

}
