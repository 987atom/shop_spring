package com.example.shop_spring.Services;

import com.example.shop_spring.Entitys.UsersEntity;
import com.example.shop_spring.Repositorys.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServices {
    @Autowired
    private UsersRepository usersRepository;

    public UsersEntity save(UsersEntity usersEntity) {
        return usersRepository.save(usersEntity);
    }

    public UsersEntity findeByID(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public List<UsersEntity> findeAll() {
        return usersRepository.findAll();
    }

    public void deleteByID(Long id) {
        usersRepository.deleteById(id);
    }

    public UsersEntity update(Long id, UsersEntity updateUsers) {
        UsersEntity usersEntity = usersRepository.findById(id).orElse(null);

        if (usersEntity != null) {
            usersEntity.setCarts(updateUsers.getCarts());
            usersEntity.setName(updateUsers.getName());
            usersEntity.setRole(updateUsers.getRole());
            usersEntity.setSurename(updateUsers.getSurename());
        }

        return usersRepository.save(usersEntity);
    }
}
