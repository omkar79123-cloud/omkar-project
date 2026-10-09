package com.smartcanteen.model;
import javax.persistence.*; import java.math.BigDecimal;
@Entity @Table(name="order_items")
public class OrderItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="order_id") private Order order;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="product_id") private Product product;
 @Column(nullable=false) private String productName; @Column(nullable=false,precision=12,scale=2) private BigDecimal unitPrice; @Column(nullable=false) private int quantity;
 protected OrderItem() {} public OrderItem(Order o,Product p,int q){order=o;product=p;productName=p.getName();unitPrice=p.getPrice();quantity=q;}
 public String getProductName(){return productName;} public BigDecimal getUnitPrice(){return unitPrice;} public int getQuantity(){return quantity;}
}
