package com.tsv.implementation.controller;

//import com.chat.app.chatroomapp.App.Entity.ChatMessage;
//import com.chat.app.chatroomapp.App.Service.ChatMessageService;
import com.tsv.implementation.Entity.*;
import com.tsv.implementation.Security.JwtTokenHelper;
import com.tsv.implementation.dao.LinkRepository;
import com.tsv.implementation.dao.MessageCountRepository;
import com.tsv.implementation.dao.RoleRepository;
import com.tsv.implementation.dao.UserRepository;
import com.tsv.implementation.dto.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import  com.tsv.implementation.service.ChatMessageService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/api")
public class ChatMessageController {
    @Autowired
    private  ChatMessageService chatMessageService;

    @Autowired
    private MessageCountRepository messageCountRepository;
    @Autowired
    private MessageCountController messageCountController;

    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    UserRepository userRepo;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    RoleRepository roleRepository;


    @GetMapping
   public String indexPage(Model model)
    {

        // model.addAttribute("userDetails",userLoginDTO.getUsername());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails)securityContext.getAuthentication().getPrincipal();
        //jwtTokenHelper.getUsernameFromToken()
        User users = userRepo.findByEmail(user.getUsername());
        String email = users.getEmail();
        Optional<Role> role = roleRepository.findById(users.getId());
      //  Role role =roleRepository.findByE(users.getEmail());

           String mainRole = null;
           String rolePage = null;
            if(!(role.isEmpty()))
            {
                Role r = role.get();
                 mainRole= r.getRole();
                System.out.println(mainRole);
                Link linkData = linkRepository.findByHostName(email);
                rolePage = "HOST";
                model.addAttribute("link",linkData.getLink());
                model.addAttribute("role",rolePage);
            }




        if(mainRole != "HOST" && mainRole == null)
        {
            MessageCount mc = messageCountRepository.findByUserName(email);
            int linkdata =  mc.getLink();
            Link linkUser = linkRepository.findByLink(linkdata);
            if(linkUser != null && mc != null)
            {
                model.addAttribute("link" ,linkdata);
                model.addAttribute("topic",linkUser.getTopic());
                rolePage = "USER";
                model.addAttribute("role",rolePage);
            }
        }

        model.addAttribute("userDetails", email);
        return "index";
    }


   public ChatMessageController(ChatMessageService chatMessageService) {
        super();
        this.chatMessageService = chatMessageService;
    }


    @GetMapping("/groups/{groupId}/messages")
    public List<ChatMessage> getMessagesForGroup(@PathVariable long groupId) {
        return chatMessageService.getMessagesForGroup(groupId);
    }
    //
    @PutMapping("/groups/{groupId}/messages")
    public void saveMessage(@RequestParam("chat_message") String message,@RequestParam("username") String mail,@RequestParam("role") String roleAtt,@PathVariable long groupId) {
       ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setGroupId(groupId);
        chatMessage.setSenderId(mail);
        chatMessage.setTimestamp(LocalDateTime.now());
        System.out.println(message);
        System.out.println(groupId);
       // System.out.println(mail);
         chatMessageService.saveMessage(chatMessage);
         System.out.println(roleAtt);
         if(roleAtt.equals("USER"))
         {
             messageCountController.addCount(mail);
         }
        // return "redirect:/count/addon";
        /*ChatMessage error_mesg = new ChatMessage();
        error_mesg.setMessage("error");
        chatMessage.setGroupId(groupId);
        chatMessage.setTimestamp(LocalDateTime.now());
        return error_mesg;*/
    }
}

