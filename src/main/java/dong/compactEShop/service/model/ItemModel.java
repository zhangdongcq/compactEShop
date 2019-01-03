package dong.compactEShop.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ItemModel {
    private Integer id;

    @NotBlank(message = "title should not be blank.")
    private String title;

    @NotNull(message = "price should not be empty.")
    @Min(value=0, message = "price should greater than 0.")
    private BigDecimal price;

    @NotNull(message = "stock is required.")
    private Integer stock;

    @NotBlank(message = "item description is required.")
    private String description;

    private Integer sales;

    @NotBlank(message = "item image is required.")
    private String imgUrl;

    /**
     * Implementing aggregation model. If promoModel is not null;
     * it participates promotion campaign.
     * @return  PromoModel
     */
    private PromoModel promoModel;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public PromoModel getPromoModel() {
        return promoModel;
    }

    public void setPromoModel(PromoModel promoModel) {
        this.promoModel = promoModel;
    }
}
