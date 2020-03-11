package by.tut.shershnev_s.service.model;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DocumentDTO {

    private Long id;
    @NotEmpty
    @Size(max = 100)
    private String description;
    private String uniqueNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }
}
