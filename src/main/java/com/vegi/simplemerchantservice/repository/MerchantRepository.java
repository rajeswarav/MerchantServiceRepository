package com.vegi.simplemerchantservice.repository;


import com.vegi.simplemerchantservice.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, String> {

}
