package com.tsv.implementation.controller;

//import com.chat.app.chatroomapp.App.Entity.ChatMessage;
//import com.chat.app.chatroomapp.App.Service.ChatMessageService;
import com.tsv.implementation.Entity.ChatMessage;
import com.tsv.implementation.Entity.MessageCount;
import com.tsv.implementation.Entity.User;
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

@Controller
@RequestMapping("/api")
public class ChatMessageController {
    @Autowired
    private  ChatMessageService chatMessageService;

    @Autowired
    private MessageCountController messageCountController;
    @Autowired
    UserRepository userRepo;
    @GetMapping
   public String indexPage(Model model)
    {

        // model.addAttribute("userDetails",userLoginDTO.getUsername());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails)securityContext.getAuthentication().getPrincipal();
        User users = userRepo.findByEmail(user.getUsername());
        model.addAttribute("userDetails", users.getEmail());
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
    @PostMapping("/groups/{groupId}/messages")
    public void saveMessage(@RequestParam("chat_message") String message,@RequestParam("username") String mail, @PathVariable long groupId) {
       ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setGroupId(groupId);
        chatMessage.setTimestamp(LocalDateTime.now());
        System.out.println(message);
        System.out.println(groupId);
       // System.out.println(mail);
         chatMessageService.saveMessage(chatMessage);
         messageCountController.addCount(mail);
        // return "redirect:/count/addon";
        /*ChatMessage error_mesg = new ChatMessage();
        error_mesg.setMessage("error");
        chatMessage.setGroupId(groupId);
        chatMessage.setTimestamp(LocalDateTime.now());
        return error_mesg;*/
    }
}
