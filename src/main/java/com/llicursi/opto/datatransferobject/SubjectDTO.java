package com.llicursi.opto.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.Date;

@Getter
public class SubjectDTO {

    private Long id;

    @JsonIgnoreProperties(value = { "role", "id" })
    private UserDTO user;

    private Date start;

    private Date due;

    private String title;

    private String description;

    public SubjectDTO(){}

    public SubjectDTO(Long id, UserDTO user, Date start, Date due, String title, String description) {
        this.id = id;
        this.user = user;
        this.start = start;
        this.due = due;
        this.title = title;
        this.description = description;
    }

    public static SubjectDTO.SubjectDTOBuilder newBuilder()
    {
        return new SubjectDTO.SubjectDTOBuilder();
    }

    public static class SubjectDTOBuilder
    {
        private Long id;
        private UserDTO user;
        private Date start;
        private Date due;
        private String title;
        private String description;

        public SubjectDTO.SubjectDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }

        public SubjectDTO.SubjectDTOBuilder setUser(UserDTO user) {
            this.user = user;
            return this;
        }

        public SubjectDTO.SubjectDTOBuilder setStart(Date start) {
            this.start = start;
            return this;
        }

        public SubjectDTO.SubjectDTOBuilder setDue(Date due) {
            this.due = due;
            return this;
        }

        public SubjectDTO.SubjectDTOBuilder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public SubjectDTO.SubjectDTOBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public SubjectDTO createSubjectDTO()
        {
            return new SubjectDTO(id, user, start, due, title, description);
        }
    }
}