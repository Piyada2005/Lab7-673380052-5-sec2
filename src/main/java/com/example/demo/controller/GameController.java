package com.example.demo.controller;

import com.example.demo.model.Game;
import com.example.demo.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // READ - แสดงเกมทั้งหมด
    @GetMapping
    public String listGames(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "games/list";
    }

    // CREATE - แสดงหน้าเพิ่มเกม
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("game", new Game());
        return "games/add";
    }

    // CREATE - บันทึกเกมใหม่
    @PostMapping("/save")
    public String saveGame(@ModelAttribute("game") Game game, RedirectAttributes redirectAttributes) {
        gameService.saveGame(game);
        redirectAttributes.addFlashAttribute("message", "เพิ่มเกมสำเร็จ");
        return "redirect:/games";
    }

    // UPDATE - แสดงหน้าแก้ไข
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Game game = gameService.getGameById(id)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบข้อมูลเกม ID: " + id));
        model.addAttribute("game", game);
        return "games/edit";
    }

    // UPDATE - บันทึกข้อมูลที่แก้ไข
    @PostMapping("/update/{id}")
    public String updateGame(@PathVariable Long id, @ModelAttribute("game") Game game, RedirectAttributes redirectAttributes) {
        gameService.updateGame(id, game);
        redirectAttributes.addFlashAttribute("message", "อัปเดตข้อมูลเกมสำเร็จ");
        return "redirect:/games";
    }

    // DELETE - แสดงหน้ายืนยันการลบ
    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model) {
        Game game = gameService.getGameById(id)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบข้อมูลเกม ID: " + id));
        model.addAttribute("game", game);
        return "games/delete";
    }

    // DELETE - ดำเนินการลบเกม
    @PostMapping("/delete/{id}")
    public String deleteGame(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        gameService.deleteGame(id);
        redirectAttributes.addFlashAttribute("message", "ลบเกมสำเร็จ");
        return "redirect:/games";
    }
}