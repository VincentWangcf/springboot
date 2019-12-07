package com.vincent.springboot.service.impl;


import com.vincent.springboot.dao.ProductDao;
import com.vincent.springboot.dao.PurchaseRecordDao;
import com.vincent.springboot.pojo.ProductPo;
import com.vincent.springboot.pojo.PurchaseRecordPo;
import com.vincent.springboot.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private PurchaseRecordDao purchaseRecordDao;

    @Override
    @Transactional //启动spring数据库机制
    public boolean purchase(Long userId, Long productId, int quantity) {
        //获取产品
        ProductPo product = productDao.getProduct(productId);
        //比较库存和购买数量
        if (product.getStock() < quantity) {
            //库存不足
            return false;
        }
        //减库存
        productDao.decreaseProduct(productId, quantity);
        //初始化购买记录
        PurchaseRecordPo pr = initPurchaseRecord(userId, product, quantity);
        purchaseRecordDao.inserPurchaseRecord(pr);
        return true;


    }

    private PurchaseRecordPo initPurchaseRecord(Long userId, ProductPo product, int quantity) {
        PurchaseRecordPo pr = new PurchaseRecordPo();
        pr.setNote("购买记录时间" + System.currentTimeMillis());
        pr.setPrice(product.getPrice());
        pr.setProductId(product.getId());
        pr.setQuantity(quantity);
        double sum = product.getPrice() * quantity;
        pr.setSum(sum);
        pr.setId(userId
        );
        return pr;
    }
}
