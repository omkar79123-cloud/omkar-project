package com.smartcanteen.model;
import javax.persistence.*; import java.math.BigDecimal;
@Entity @Table(name="products")
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String name;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal price;
 @Column(nullable=false) private boolean active=true;
 protected Product() {} public Product(String n,BigDecimal p){name=n;price=p;}
 public Long getId(){return id;} public String getName(){return name;} public BigDecimal getPrice(){return price;} public boolean isActive(){return active;}
 public void update(String n,BigDecimal p,boolean a){name=n;price=p;active=a;}
}
