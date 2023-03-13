package com.example.springecom.user;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString
@Table(name = "tbl_seller")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller extends User {
    private String shopName;
}
