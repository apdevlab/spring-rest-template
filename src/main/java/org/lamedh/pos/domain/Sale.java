package org.lamedh.pos.domain;

import org.lamedh.pos.common.EntityBase;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Sale extends EntityBase {
    private String code;
    private LocalDateTime time;

    @ManyToOne
    private Employee cashier;

    @OneToMany
    private List<SaleLineItem> lineItems;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Employee getCashier() {
        return cashier;
    }

    public void setCashier(Employee cashier) {
        this.cashier = cashier;
    }

    public Collection<SaleLineItem> getLineItems() {
        return lineItems;
    }

    public Sale() {
        lineItems = new ArrayList<>();
        time = LocalDateTime.now();
    }

    public void addLineItem(Product product, int quantity) {
        SaleLineItem sli = getByProduct(product).orElseGet(() -> addLineItem(new SaleLineItem(product, 0)));
        sli.addQuantity(quantity);
    }

    private SaleLineItem addLineItem(SaleLineItem saleLineItem) {
        lineItems.add(saleLineItem);
        return saleLineItem;
    }

    public Optional<SaleLineItem> getByProduct(Product product) {
        Optional<SaleLineItem> salel = lineItems.stream().filter(sli -> sli.getProduct().equals(product)).findFirst();
        return salel;
    }

    public BigDecimal getTotal() {
        return lineItems.stream().map(sli -> sli.getSubTotal()).reduce(BigDecimal.ZERO,  (a, b) -> a.add(b));
    }
}