package pos.machine;

import java.util.List;
import java.util.stream.Collectors;

public class BarcodeHandler {
    public static List<String> getUniqueBarcodes(List<String> loadBarcodes){
        return loadBarcodes.stream().distinct().collect(Collectors.toList());
    }

    public static int getBarcodeOccurences(List<String> loadBarcodes, String targetBarcode){
        return (int)loadBarcodes.stream().filter(item -> item.equals(targetBarcode)).count();
    }
}
