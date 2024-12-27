package Service;

import DataModel.Publication;

public class InventoryService {
    private ProductService productService;

    public InventoryService(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Приемка товара (увеличение количества).
     *
     * @param publicationId ID товара.
     * @param quantity      Количество.
     */
    public void receiveProduct(int publicationId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество товара должно быть положительным");
        }
        Publication publication = productService.getProductById(publicationId);
        if (publication == null) {
            throw new IllegalArgumentException("Товар с ID " + publicationId + " не найден");
        }
        publication.setQuantity(publication.getQuantity() + quantity);
        productService.updateProduct(publication);
    }

    /**
     * Продажа товара (уменьшение количества).
     *
     * @param publicationId ID товара.
     * @param quantity      Количество.
     */
    public void sellProduct(int publicationId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество товара должно быть положительным");
        }
        Publication publication = productService.getProductById(publicationId);
        if (publication == null) {
            throw new IllegalArgumentException("Товар с ID " + publicationId + " не найден");
        }
        if (publication.getQuantity() < quantity) {
            throw new IllegalArgumentException("Недостаточно товара на складе");
        }
        publication.setQuantity(publication.getQuantity() - quantity);
        productService.updateProduct(publication);
    }

    /**
     * Получение количества товара на складе.
     *
     * @param publicationId ID товара.
     * @return Количество товара.
     */
    public int getInventoryQuantity(int publicationId) {
        Publication publication = productService.getProductById(publicationId);
        if (publication == null) {
            throw new IllegalArgumentException("Товар с ID " + publicationId + " не найден");
        }
        return publication.getQuantity();
    }
}