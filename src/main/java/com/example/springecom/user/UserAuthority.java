package com.example.springecom.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "tbl_user_authority")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthority implements GrantedAuthority {
    @Id
    private Long id;

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
