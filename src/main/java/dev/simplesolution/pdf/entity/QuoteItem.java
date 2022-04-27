package dev.simplesolution.pdf.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class QuoteItem {
    private String description;
    private Integer quantity;
    private Double unitPrice;
    private Double total;
}