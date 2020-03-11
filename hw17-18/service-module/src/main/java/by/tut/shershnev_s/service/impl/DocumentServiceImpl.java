package by.tut.shershnev_s.service.impl;

import by.tut.shershnev_s.repository.ConnectionRepository;
import by.tut.shershnev_s.repository.DocumentRepository;
import by.tut.shershnev_s.repository.model.Document;
import by.tut.shershnev_s.service.DocumentService;
import by.tut.shershnev_s.service.model.DocumentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ConnectionRepository connectionRepository;
    private DocumentRepository documentRepository;

    public DocumentServiceImpl(ConnectionRepository connectionRepository, DocumentRepository documentRepository) {
        this.connectionRepository = connectionRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public DocumentDTO add(DocumentDTO documentDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Document document = convertDTOToDocument(documentDTO);
                document.setUniqueNumber(UUID.randomUUID().toString());
                document = documentRepository.add(connection, document);
                connection.commit();
                return convertDocumentToDTO(document);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Can't add document");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Can't create connection");
        }
        return null;
    }

    @Override
    public List<DocumentDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Document> docs = documentRepository.findAll(connection);
                List<DocumentDTO> documentDTOS = docs.stream()
                        .map(this::convertDocumentToDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return documentDTOS;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Can't find documents");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Can't create connection");
        }
        return Collections.emptyList();
    }

    @Override
    public DocumentDTO findById(Long id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Document document = documentRepository.findById(connection, id);
                DocumentDTO documentDTO = convertDocumentToDTO(document);
                connection.commit();
                return documentDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Can't find documents");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Can't create connection");
        }
        return null;
    }

    @Override
    public void deleteByID(DocumentDTO documentDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Long id = documentDTO.getId();
                documentRepository.deleteByID(connection, id);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Can't delete document");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Can't create connection");
        }
    }

    private DocumentDTO convertDocumentToDTO(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription(document.getDescription());
        documentDTO.setId(document.getId());
        documentDTO.setUniqueNumber(document.getUniqueNumber());
        return documentDTO;
    }

    private Document convertDTOToDocument(DocumentDTO documentDTO) {
        Document document = new Document();
        document.setDescription(documentDTO.getDescription());
        return document;
    }

}
