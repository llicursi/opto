package com.llicursi.opto.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Integer votes;

    public String getExpires(){
        Duration between = Duration.between(LocalDateTime.now(), due.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());

        if (between.toDays() >= 2){
            return String.format("%d days", between.toDays());
        } else if (between.toHours() >= 1){
            return String.format("%d hours", between.toHours());
        }
        return String.format("%d min", between.toMinutes());
    }

    public SubjectDTO(){}

    public SubjectDTO(Long id, UserDTO user, Date start, Date due, String title, String description, Integer votes) {
        this.id = id;
        this.user = user;
        this.start = start;
        this.due = due;
        this.title = title;
        this.description = description;
        this.votes = votes;
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
        private Integer votes;

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


        public SubjectDTO.SubjectDTOBuilder setVotes(Integer votes) {
            this.votes = votes;
            return this;
        }

        public SubjectDTO createSubjectDTO()
        {
            return new SubjectDTO(id, user, start, due, title, description,votes);
        }
    }
}