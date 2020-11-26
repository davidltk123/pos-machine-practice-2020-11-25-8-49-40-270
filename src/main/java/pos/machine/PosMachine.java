package pos.machine;

import java.util.ArrayList;
import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        receiptItems = generateReceiptItems(ItemDataLoader.loadBarcodes());
        Receipt receipt = new Receipt();
        return receipt.generateReceipt(receiptItems);
    }

    private List<ReceiptItem> generateReceiptItems(List<String> loadBarcodes) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        List<String> uniqueBarcodes = BarcodeHandler.getUniqueBarcodes(loadBarcodes);
        addReceiptItem(loadBarcodes,uniqueBarcodes,receiptItems);
        return receiptItems;
    }

    private void addReceiptItem(List<String> loadBarcodes, List<String> uniqueBarcodes,List<ReceiptItem> receiptItems ){
        uniqueBarcodes.forEach(uniqueBarcode ->{
            int quantity = BarcodeHandler.getBarcodeOccurences(loadBarcodes,uniqueBarcode);
            ItemInfo itemInfo = fetchItemInfoFromDatabase(uniqueBarcode);
            ReceiptItem receiptItem = new ReceiptItem(uniqueBarcode,itemInfo.getName(),itemInfo.getPrice(),(int)quantity);
            receiptItems.add(receiptItem);
        });
    }

    private ItemInfo fetchItemInfoFromDatabase(String loadBarcode) {
        List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();
        return itemInfos.stream().filter(itemInfo -> itemInfo.getBarcode().equals(loadBarcode)).findAny().orElse(null);
    }
}