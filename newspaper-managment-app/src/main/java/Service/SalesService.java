package Service;

import java.util.ArrayList;
import java.util.List;

public class SalesService {
    private InventoryService inventoryService;
    private List<Sale> sales = new ArrayList<>();

    public SalesService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Продажа товара.
     *
     * @param publicationId ID товара.
     * @param quantity      Количество.
     */
    public void sellProduct(int publicationId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество товара должно быть положительным");
        }
        inventoryService.sellProduct(publicationId, quantity);
        sales.add(new Sale(publicationId, quantity));
    }

    /**
     * Получение списка всех продаж.
     *
     * @return Список продаж.
     */
    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }

    /**
     * Внутренний класс для хранения информации о продаже.
     */
    private static class Sale {
        private int publicationId;
        private int quantity;

        public Sale(int publicationId, int quantity) {
            this.publicationId = publicationId;
            this.quantity = quantity;
        }

        public int getPublicationId() {
            return publicationId;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}