package pos.machine;

public class ItemDetail extends ItemInfo{
    private final int quantity;
    private final int subTotal;

    public ItemDetail(String barcode, String name, int price, int quantity) {
        super(barcode,name,price);
        this.quantity = quantity;
        this.subTotal = quantity*price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSubTotal() {
        return subTotal;
    }
}