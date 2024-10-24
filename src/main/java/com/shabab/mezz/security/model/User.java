package com.shabab.mezz.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Project: Mezz-SpringBoot
 * Author: Shabab-1281539
 * Created on: 20/10/2024
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sec_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Max 100 Characters")
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank(message = "Cell number is required")
    @Pattern(regexp = "^\\d{11}$", message = "Cell number must be 11 digits")
    @Column(length = 11, nullable = false, unique = true)
    private String cell;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Max 100 Characters")
    @Column(length = 100, unique = true)
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "balance", nullable = false, columnDefinition = "double default 0.0")
    private Double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mess_id", nullable = false)
    private Mess mess;

    public User(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return cell;
    }

    public enum Role {
        ROLE_ADMIN,
        ROLE_MANAGER,
        ROLE_MEMBER
    }

}
