package com.example.blog.controller;

import com.example.blog.dto.ContactDTO;
import com.example.blog.dto.Result;
import com.example.blog.entity.Contact;
import com.example.blog.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public Result<Contact> create(@RequestBody ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setSubject(contactDTO.getSubject());
        contact.setMessage(contactDTO.getMessage());
        Contact created = contactService.create(contact);
        return Result.success(created);
    }

    @GetMapping
    public Result<List<Contact>> list() {
        List<Contact> contacts = contactService.findAll();
        return Result.success(contacts);
    }

    @GetMapping("/{id}")
    public Result<Contact> getById(@PathVariable Long id) {
        Contact contact = contactService.findById(id);
        if (contact == null) {
            throw new RuntimeException("留言不存在");
        }
        return Result.success(contact);
    }

    @PutMapping("/{id}/reply")
    public Result<Contact> reply(@PathVariable Long id, @RequestBody ReplyDTO replyDTO) {
        Contact contact = contactService.reply(id, replyDTO.getReplyContent());
        return Result.success(contact);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        Contact contact = contactService.findById(id);
        if (contact == null) {
            throw new RuntimeException("留言不存在");
        }
        contactService.delete(id);
        return Result.success(null);
    }

    public static class ReplyDTO {
        private String replyContent;
        
        public String getReplyContent() {
            return replyContent;
        }
        
        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }
    }
}