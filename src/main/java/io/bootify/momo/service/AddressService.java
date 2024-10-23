package io.bootify.momo.service;

import io.bootify.momo.domain.Address;
import io.bootify.momo.domain.Member;
import io.bootify.momo.domain.Order;
import io.bootify.momo.model.AddressDTO;
import io.bootify.momo.repos.AddressRepository;
import io.bootify.momo.repos.MemberRepository;
import io.bootify.momo.repos.OrderRepository;
import io.bootify.momo.util.NotFoundException;
import io.bootify.momo.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public AddressService(final AddressRepository addressRepository,
            final MemberRepository memberRepository, final OrderRepository orderRepository) {
        this.addressRepository = addressRepository;
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
    }

    public List<AddressDTO> findAll() {
        final List<Address> addresses = addressRepository.findAll(Sort.by("id"));
        return addresses.stream()
                .map(address -> mapToDTO(address, new AddressDTO()))
                .toList();
    }

    public AddressDTO get(final Long id) {
        return addressRepository.findById(id)
                .map(address -> mapToDTO(address, new AddressDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AddressDTO addressDTO) {
        final Address address = new Address();
        mapToEntity(addressDTO, address);
        return addressRepository.save(address).getId();
    }

    public void update(final Long id, final AddressDTO addressDTO) {
        final Address address = addressRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(addressDTO, address);
        addressRepository.save(address);
    }

    public void delete(final Long id) {
        addressRepository.deleteById(id);
    }

    private AddressDTO mapToDTO(final Address address, final AddressDTO addressDTO) {
        addressDTO.setId(address.getId());
        addressDTO.setZonecode(address.getZonecode());
        addressDTO.setAddresss(address.getAddresss());
        addressDTO.setAddressDetail(address.getAddressDetail());
        addressDTO.setAddressName(address.getAddressName());
        addressDTO.setAddressContact(address.getAddressContact());
        addressDTO.setMember(address.getMember() == null ? null : address.getMember().getId());
        return addressDTO;
    }

    private Address mapToEntity(final AddressDTO addressDTO, final Address address) {
        address.setZonecode(addressDTO.getZonecode());
        address.setAddresss(addressDTO.getAddresss());
        address.setAddressDetail(addressDTO.getAddressDetail());
        address.setAddressName(addressDTO.getAddressName());
        address.setAddressContact(addressDTO.getAddressContact());
        final Member member = addressDTO.getMember() == null ? null : memberRepository.findById(addressDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        address.setMember(member);
        return address;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Address address = addressRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Order addressOrder = orderRepository.findFirstByAddress(address);
        if (addressOrder != null) {
            referencedWarning.setKey("address.order.address.referenced");
            referencedWarning.addParam(addressOrder.getId());
            return referencedWarning;
        }
        return null;
    }

}
