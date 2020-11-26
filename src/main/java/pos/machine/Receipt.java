package pos.machine;

import java.util.List;

public class Receipt {
    public String generateReceipt(List<ReceiptItem> receiptItems) {
        String receipt = "***<store earning no money>Receipt***\n";
        for(ReceiptItem receiptItem: receiptItems){
            receipt += receiptItem.generateReceiptItem();
        }
        receipt += "----------------------\n";
        receipt += generateTotal(receiptItems);
        receipt += "**********************";

        return receipt;
    }

    private String generateTotal(List<ReceiptItem> receiptItems) {
        int total = receiptItems.stream().mapToInt(item -> item.getSubTotal()).sum();
        return String.format("Total: %d (yuan)\n",total);
    }
}
