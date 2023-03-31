package com.tsv.implementation.controller;

import com.tsv.implementation.Entity.Link;
import com.tsv.implementation.dao.LinkRepository;
import com.tsv.implementation.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("http://localhost:3000" )
@RequestMapping("/link")
public class LinkController
{

    @Autowired
    LinkService linkService;

    private Link link;

    @Autowired
    LinkRepository linkRepository;

   /* @ModelAttribute("user")
    public Link userLink()
    {
        return new Link();
    }*/
//    @GetMapping
//    public String showLinkGeneratorPage(Model model)
//    {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        UserDetails user = (UserDetails)securityContext.getAuthentication().getPrincipal();
//        Link lo = linkRepository.findByHostName(user.getUsername());
//        if(lo != null)
//        {
//            model.addAttribute("link" ,lo.getLink());
//        }
//
//        return "GenerateLink";
//    }


    @PostMapping("/generate")
    @ResponseBody
    public String generateLink() {
        linkService.generate();

        return link.getLink()+"";
    }
}
