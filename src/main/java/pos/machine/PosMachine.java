package pos.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ItemDetail> itemDetails = new ArrayList<>();
        itemDetails = generateItemDetails(ItemDataLoader.loadBarcodes());
        String receipt = generateReceipt(itemDetails);
        return receipt;
    }

    private String generateReceipt(List<ItemDetail> itemDetails) {
        String receipt = "***<store earning no money>Receipt***\n";
        for(ItemDetail itemDetail: itemDetails){
            receipt += generateReceiptByItem(itemDetail);
        }
        receipt += "----------------------\n";
        receipt += generateTotal(itemDetails);
        receipt += "**********************";

        return receipt;
    }

    private String generateTotal(List<ItemDetail> itemDetails) {
        int total = itemDetails.stream().mapToInt(item -> item.getSubTotal()).reduce(0,Integer::sum);
        return "Total: "+total+" (yuan)\n";
    }

    private String generateReceiptByItem(ItemDetail itemDetail) {
        return String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",itemDetail.getName(),itemDetail.getQuantity(),itemDetail.getPrice(),itemDetail.getSubTotal());
    }

    private List<ItemDetail> generateItemDetails(List<String> loadBarcodes) {
        List<ItemDetail> itemDetails = new ArrayList<>();

        //create unique barcordes list
        List<String> uniqueBarcodes = loadBarcodes.stream().distinct().collect(Collectors.toList());

        //calculate occurence and form itemDetails object
        for(String uniqueBarcode : uniqueBarcodes){
            int occurences = Collections.frequency(loadBarcodes,uniqueBarcode);
            ItemInfo itemInfo = fetchItemInfoFromDatabase(uniqueBarcode);
            ItemDetail itemDetail = new ItemDetail(itemInfo.getName(), occurences, itemInfo.getPrice());
            itemDetails.add(itemDetail);
        }
        return itemDetails;
    }

    private ItemInfo fetchItemInfoFromDatabase(String loadBarcode) {
        List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();
        return itemInfos.stream().filter(itemInfo -> itemInfo.getBarcode().equals(loadBarcode)).findAny().orElse(null);
    }
}