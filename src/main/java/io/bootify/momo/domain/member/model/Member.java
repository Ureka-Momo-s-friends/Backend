package io.bootify.momo.domain.member.model;

import io.bootify.momo.domain.order.model.Order;
import io.bootify.momo.domain.cat.model.Pet;
import io.bootify.momo.domain.cat.model.StrayCat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.*;

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

    @Column(name = "google_id", length = 255, nullable = false)
    private String googleId;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Pet> memberPets;

    @Column(name = "profile_img_url", length = 255) // 새로운 필드 추가
    private String profileImgUrl;
    @OneToMany(mappedBy = "member")
    private Set<Cart> memberCarts;

    @OneToMany(mappedBy = "member")
    private Set<Order> memberOrders;

    @OneToMany(mappedBy = "member")
    private Set<StrayCat> memberStrayCats;

    @OneToMany(mappedBy = "member")
    private Set<MemberAddress> memberAddresses;
}

