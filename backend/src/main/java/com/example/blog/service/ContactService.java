package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Contact;

import java.util.List;

public interface ContactService extends IService<Contact> {
    Contact create(Contact contact);
    List<Contact> findAll();
    Contact findById(Long id);
    Contact reply(Long id, String replyContent);
    void delete(Long id);
}