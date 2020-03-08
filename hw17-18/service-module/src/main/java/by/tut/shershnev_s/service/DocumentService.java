package by.tut.shershnev_s.service;

import by.tut.shershnev_s.service.model.DocumentDTO;

import java.util.List;

public interface DocumentService {

    DocumentDTO add(DocumentDTO documentDTO);

    List<DocumentDTO> findAll();
}
