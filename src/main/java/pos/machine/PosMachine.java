package pos.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        receiptItems = generateItemDetails(ItemDataLoader.loadBarcodes());
        Receipt receipt = new Receipt();
        return receipt.generateReceipt(receiptItems);
    }

    private List<ReceiptItem> generateItemDetails(List<String> loadBarcodes) {
        List<ReceiptItem> receiptItems = new ArrayList<>();

        //create unique barcordes list
        List<String> uniqueBarcodes = BarcodeHandler.getUniqueBarcodes(loadBarcodes);

        //calculate occurence and form itemDetails object
        uniqueBarcodes.forEach(uniqueBarcode ->{
            int quantity = BarcodeHandler.getBarcodeOccurences(loadBarcodes,uniqueBarcode);
            ItemInfo itemInfo = fetchItemInfoFromDatabase(uniqueBarcode);
            ReceiptItem receiptItem = new ReceiptItem(uniqueBarcode,itemInfo.getName(),itemInfo.getPrice(),(int)quantity);
            receiptItems.add(receiptItem);
        });
        return receiptItems;
    }

    private ItemInfo fetchItemInfoFromDatabase(String loadBarcode) {
        List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();
        return itemInfos.stream().filter(itemInfo -> itemInfo.getBarcode().equals(loadBarcode)).findAny().orElse(null);
    }
}