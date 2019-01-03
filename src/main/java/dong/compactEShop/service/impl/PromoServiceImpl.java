package dong.compactEShop.service.impl;

import dong.compactEShop.dao.PromoDOMapper;
import dong.compactEShop.dataobject.PromoDO;
import dong.compactEShop.service.PromoService;
import dong.compactEShop.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //Get flash item detail
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);
        //Data Object --> Data Model
        PromoModel promoModel = this.convertFromDataObject(promoDO);
        //Currently there is no promotion campaign
        if(promoModel == null) return null;
        //Check the datetime for campaign, started or about to start.
        if(promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }
    private PromoModel convertFromDataObject(PromoDO promoDO){
        if(promoDO == null) return null;
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO, promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;

    }
}
