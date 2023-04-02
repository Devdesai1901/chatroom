package com.tsv.implementation.controller;

import com.tsv.implementation.Entity.Link;
import com.tsv.implementation.service.LinkVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/verifyLink")
public class LinkVerfiController {

    @Autowired
    LinkVerifyService linkVerifyService;
    @ModelAttribute("user")
    public Link verifier()
    {
        return new Link();
    }
    @GetMapping
    public String verifyLinkPage()
    {
        return "verifyLink";
    }


    @PostMapping("/verify")
    public String check(@ModelAttribute("user") Link linkData,  BindingResult result, RedirectAttributes redirectAttributes)
    {
         int data = linkData.getLink();
         String messg = linkVerifyService.checking(data);
        if(messg == "success")
        {
            redirectAttributes.addFlashAttribute("message", "User Authorized Successfully");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            return "redirect:/api";
        }
        else if(messg == "linkerror")
        {
            redirectAttributes.addFlashAttribute("message", "Entered Link is not correct");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        } else if (messg == "usererror")
        {
            redirectAttributes.addFlashAttribute("message", "User is not invited to meet");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/verifyLink";
    }


}
