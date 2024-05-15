package org.geely.domain.core;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.core.data.CartData;
import org.geely.infrastructure.db.repository.CartRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 购物车
 *
 * @author cong huang
 */
public class Cart {
    private CartData data;
    private static final Integer MAX_QUANTITY = 99999;

    private Cart(CartData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static Cart add(CartData data) {
        Cart cart = instanceOf(data);
        if (cart == null) {
            cart = new Cart(data);
            cart.data.setId(null);
            cart.data.setVersion(0);
        } else {
            cart.data.setQuantity(cart.data.getQuantity() + data.getQuantity());
        }
        cart.fixQuantity();
        cart.selected(true);
        cart.save();
        return cart;
    }

    public static Cart instanceOf(Integer id) {
        CartData data = SpringUtil.getBean(CartRepository.class).getById(id);
        return new Cart(data);
    }

    public static Cart instanceOf(CartData data) {
        CartData dbData = SpringUtil.getBean(CartRepository.class).get(data);
        if (dbData == null) {
            return null;
        } else {
            return new Cart(dbData);
        }
    }

    public static Long count(Integer mallId, Integer customerId, Integer accountId) {
        return SpringUtil.getBean(CartRepository.class).count(mallId, customerId, accountId);
    }

    public static List<Cart> instancesOf(Set<Integer> ids) {
        return SpringUtil.getBean(CartRepository.class).listByIds(ids).stream().map(Cart::new).collect(Collectors.toList());
    }

    public static List<Cart> instancesOf(Integer mallId, Integer customerId, Integer accountId, Set<Integer> skuSet) {
        return SpringUtil.getBean(CartRepository.class).listBySkuIds(mallId, customerId, accountId, skuSet).stream().map(Cart::new).collect(Collectors.toList());
    }

    public void save() {
        this.data = SpringUtil.getBean(CartRepository.class).save(this.data);
    }

    public void delete() {
        SpringUtil.getBean(CartRepository.class).delete(this.data);
        this.data = null;
    }

    public Cart selected(boolean selected) {
        this.data.setSelected(selected);
        return this;
    }

    public CartData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Integer getId() {
        return data.getId();
    }


    private void fixQuantity() {
        if (data.getQuantity() < 1) {
            data.setQuantity(1);
        } else if (data.getQuantity() > MAX_QUANTITY) {
            data.setQuantity(MAX_QUANTITY);
        }
    }

    public Cart updateQuantity(Integer quantity) {
        data.setQuantity(quantity);
        fixQuantity();
        return this;
    }

    public void validCheck(Integer mallId, Integer customerId, Integer accountId) {
        Assert.isTrue(data.getMallId().equals(mallId) && data.getCustomerId().equals(customerId) && data.getAccountId().equals(accountId), "无效的购物车id");
    }
}
