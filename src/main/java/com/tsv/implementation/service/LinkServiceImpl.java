package com.tsv.implementation.service;

import com.tsv.implementation.Entity.Link;
import com.tsv.implementation.Entity.User;
import com.tsv.implementation.dao.LinkRepository;
import com.tsv.implementation.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class LinkServiceImpl implements LinkService{


   @Autowired
   private UserRepository userRepo;
    @Autowired
    private LinkRepository linkRepository;
    @Override
    public void generate() {
//        int pin = (int) (Math.random() * 9000) + 1000;
//        Link link = new Link();
//        link.setLink(pin);
//
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        UserDetails user = (UserDetails)securityContext.getAuthentication().getPrincipal();
//        User users = userRepo.findByEmail(user.getUsername());
//        link.setHostName(users.getEmail());
//        linkRepository.save(link);
//
//        return link;
        int pin = (int) (Math.random() * 9000) + 1000;
        Link link = new Link();
        link.setLink(pin);

//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();

//        if (authentication != null && ((Authentication) authentication).isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) principal;
//                User user = userRepo.findByEmail(userDetails.getUsername());
//                link.setHostName(user.getEmail());
//            } else if (principal instanceof String) {
//                String username = (String) principal;
//                User user = userRepo.findByEmail(username);
//                link.setHostName(user.getEmail());
//            }
//        }

        linkRepository.save(link);
    }

}
