package io.bootify.momo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Member {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String username;

    @Column(nullable = false, length = 11)
    private String contact;

    @OneToMany(mappedBy = "member")
    private Set<Pet> memberPets;

    @OneToMany(mappedBy = "member")
    private Set<Cart> memberCarts;

    @OneToMany(mappedBy = "member")
    private Set<Order> memberOrders;

    @OneToMany(mappedBy = "member")
    private Set<StrayCat> memberStrayCats;

    @OneToMany(mappedBy = "member")
    private Set<Address> memberAddresses;

}
