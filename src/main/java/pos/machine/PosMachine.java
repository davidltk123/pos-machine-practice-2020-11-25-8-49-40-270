package pos.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        receiptItems = generateItemDetails(ItemDataLoader.loadBarcodes());
        String receipt = generateReceipt(receiptItems);
        return receipt;
    }

    private String generateReceipt(List<ReceiptItem> receiptItems) {
        String receipt = "***<store earning no money>Receipt***\n";
        for(ReceiptItem receiptItem: receiptItems){
            receipt += receiptItem.generateReceipt();
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

    private List<ReceiptItem> generateItemDetails(List<String> loadBarcodes) {
        List<ReceiptItem> receiptItems = new ArrayList<>();

        //create unique barcordes list
        List<String> uniqueBarcodes = loadBarcodes.stream().distinct().collect(Collectors.toList());

        //calculate occurence and form itemDetails object
        for(String uniqueBarcode : uniqueBarcodes){
            long quantity = loadBarcodes.stream().filter(item -> item.equals(uniqueBarcode)).count();
            ItemInfo itemInfo = fetchItemInfoFromDatabase(uniqueBarcode);
            ReceiptItem receiptItem = new ReceiptItem(uniqueBarcode,itemInfo.getName(),itemInfo.getPrice(),(int)quantity);
            receiptItems.add(receiptItem);
        }
        return receiptItems;
    }

    private ItemInfo fetchItemInfoFromDatabase(String loadBarcode) {
        List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();
        return itemInfos.stream().filter(itemInfo -> itemInfo.getBarcode().equals(loadBarcode)).findAny().orElse(null);
    }
}