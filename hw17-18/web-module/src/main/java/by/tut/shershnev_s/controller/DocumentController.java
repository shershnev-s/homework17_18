package by.tut.shershnev_s.controller;

import by.tut.shershnev_s.service.DocumentService;
import by.tut.shershnev_s.service.model.DocumentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/add_doc")
    public String addDocs(@Valid @ModelAttribute(name = "document") DocumentDTO document, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("document", document);
            return "/add_doc";
        } else {
            model.addAttribute("document", document);
            String description = document.getDescription();
            document.setDescription(description);
            documentService.add(document);
        }
        return "redirect:/documents";
    }

    @GetMapping("/add_doc")
    public String getAddDoc(Model model) {
        model.addAttribute("document", new DocumentDTO());
        return "add_doc";
    }

    @GetMapping("/documents")
    public String getAddedDoc(Model model) {
        List<DocumentDTO> documentDTOS = documentService.findAll();
        model.addAttribute("docs", documentDTOS);
        return "documents";
    }

    @GetMapping("/doc/{id}")
    public String getDocById(@PathVariable Long id, Model model) {
        DocumentDTO documentDTO = documentService.findById(id);
        model.addAttribute("document", documentDTO);
        return "doc_info";
    }

    @GetMapping("/delete/{id}")
    public String deleteDocById(@PathVariable Long id, Model model) {
        DocumentDTO documentDTO = documentService.findById(id);
        model.addAttribute("document", documentDTO);
        documentService.deleteByID(documentDTO);
        return "delete";
    }

}
