package dong.compactEShop.service.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.joda.time.DateTime;
import org.joda.time.base.BaseDateTime;
public class PromoModel {
    private Integer id;

    //campaign name
    private String promoName;

    //campaign status 1:Ready, 2:In progress, 3:End
    private Integer status;

    // campaign start time
    private DateTime StartDate;

    //campaihn end time
    private DateTime endDate;

    //campaign item
    private Integer itemId;

    //item price in campaign
    private BigDecimal promoItemPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartDate() {
        return StartDate;
    }

    public void setStartDate(DateTime startDate) {
        StartDate = startDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
