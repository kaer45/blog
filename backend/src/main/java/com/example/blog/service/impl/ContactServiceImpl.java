package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Contact;
import com.example.blog.mapper.ContactMapper;
import com.example.blog.service.ContactService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {

    @Override
    public Contact create(Contact contact) {
        contact.setReplied(false);
        contact.setCreatedAt(LocalDateTime.now());
        contact.setUpdatedAt(LocalDateTime.now());
        save(contact);
        return contact;
    }

    @Override
    public List<Contact> findAll() {
        return list(new LambdaQueryWrapper<Contact>()
                .orderByDesc(Contact::getCreatedAt));
    }

    @Override
    public Contact findById(Long id) {
        return getById(id);
    }

    @Override
    public Contact reply(Long id, String replyContent) {
        Contact contact = getById(id);
        if (contact == null) {
            throw new RuntimeException("留言不存在");
        }
        contact.setReplied(true);
        contact.setReplyContent(replyContent);
        contact.setUpdatedAt(LocalDateTime.now());
        updateById(contact);
        return contact;
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }
}