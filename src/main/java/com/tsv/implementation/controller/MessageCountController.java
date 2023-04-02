package com.tsv.implementation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mysql.cj.xdevapi.JsonArray;
import com.tsv.implementation.Entity.MessageCount;
import com.tsv.implementation.dao.MessageCountRepository;
import com.tsv.implementation.dto.UserLoginDTO;
import com.tsv.implementation.service.MessageCountServie;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

@Controller
@RequestMapping("/count")
public class MessageCountController
{
    @Autowired
    private MessageCountServie messageCountServie;

    @Autowired
    private MessageCountRepository messageCountRepository;

    @ModelAttribute("user")
    public MessageCount messageCount() {return new MessageCount();}





    //@PostMapping("/rank")
    @GetMapping("/rank")
    @ResponseBody
    public List<MessageCount> getCount() throws JsonProcessingException {

       List<MessageCount> data =  messageCountServie.getRank();

        return data;
    }

    //@PostMapping("/addon")
    //@RequestParam("username")
    public void addCount(String email)
    {
       // System.out.println("hii");
       // System.out.println(email);
        MessageCount user = messageCountRepository.findByUserName(email);


            int count =  user.getMessageCount();
            count = count + 1;
            user.setMessageCount(count);
            messageCountRepository.save(user);

    }
    @GetMapping
    public String showAddUserPage()
    {
        return "addUser";
    }
   @PostMapping
    public String  validateUser(@ModelAttribute("user") MessageCount messageCount , BindingResult result, RedirectAttributes redirectAttributes)
    {

        String messg = messageCountServie.validateFromDatabase(messageCount);
        if(messg == "success")
        {

            redirectAttributes.addFlashAttribute("message", "User added Successfully");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        }
        else
        {
            redirectAttributes.addFlashAttribute("message", "User is not registered");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return "redirect:/count";
    }
}
