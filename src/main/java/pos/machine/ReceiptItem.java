package pos.machine;

public class ReceiptItem extends ItemInfo{
    private final int quantity;
    private final int subTotal;

    public ReceiptItem(String barcode, String name, int price, int quantity) {
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

    public String generateReceiptItem() {
        return String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",this.getName(),this.getQuantity(),this.getPrice(),this.getSubTotal());
    }
}