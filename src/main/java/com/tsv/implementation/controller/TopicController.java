package com.tsv.implementation.controller;

import com.tsv.implementation.Entity.Link;
import com.tsv.implementation.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/topic")
public class TopicController
{
    @Autowired
    TopicService topicService;

    @ModelAttribute("user")
    public Link userLink()
    {
        return new Link();
    }

    @GetMapping
    public String getTopicPage()
    {
        return "SetDiscussion";
    }

    @PostMapping("/set")
    public String setTopic(@ModelAttribute("user") Link  linkData , BindingResult result, RedirectAttributes redirectAttributes)
    {
        String topic= linkData.getTopic();
       String messg =  topicService.addTopic(topic);
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
        return "redirect:/topic";
    }


}
