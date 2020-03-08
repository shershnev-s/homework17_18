package by.tut.shershnev_s.controller;

import by.tut.shershnev_s.service.DocumentService;
import by.tut.shershnev_s.service.model.DocumentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Controller
public class DocumentController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/add_doc")
    public String getDocs(Model model) {
        return "add_doc";
    }

    @PostMapping("/add_doc")
    public String addDocs(@RequestParam String description, Model model){
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription(description);
        documentDTO=documentService.add(documentDTO);
        //model.addAttribute(documentDTO);
        return "redirect:/documents";
    }

    @GetMapping("/documents")
    public String getAddedDoc(Model model){
        List<DocumentDTO> documentDTOS = documentService.findAll();
        model.addAttribute("docs", documentDTOS);
        return "documents";
    }

    @GetMapping("/doc/{id}")
    public String getDocById(@PathVariable Long id, Model model){

        return "doc";
    }
}
