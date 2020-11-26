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
        List<String> uniqueBarcodes = loadBarcodes.stream().distinct().collect(Collectors.toList());

        //calculate occurence and form itemDetails object
        uniqueBarcodes.forEach(uniqueBarcode ->{
            long quantity = loadBarcodes.stream().filter(item -> item.equals(uniqueBarcode)).count();
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