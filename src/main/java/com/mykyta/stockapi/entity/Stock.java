package com.mykyta.stockapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Table(name = "stocks")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "company_name")
    private String companyName;
    @Column(name = "currency")
    private String currency;
    @Column(name = "price")
    private double price;
    @Column(name = "stock_change")
    private double change;
    @Column(name = "volume")
    private Long volume;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "stocks")
    @JsonIgnore
    private List<User> users;

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", companyName='" + companyName + '\'' +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", change=" + change +
                ", volume=" + volume +
                '}';
    }
}
