package by.tut.shershnev_s.service.model;

public class DocumentDTO {

    private Long id;
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

    @Override
    public String toString() {
        return "DocumentDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", uniqueNumber='" + uniqueNumber + '\'' +
                '}';
    }
}
