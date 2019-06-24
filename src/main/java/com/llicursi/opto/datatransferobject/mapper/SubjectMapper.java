package com.llicursi.opto.datatransferobject.mapper;

import com.llicursi.opto.datatransferobject.SubjectDTO;
import com.llicursi.opto.domainobject.SubjectDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SubjectMapper {

    public static SubjectDTO convertToSubjectDTO(SubjectDO subjectDO)
    {
        SubjectDTO.SubjectDTOBuilder subjectDTOBuilder = SubjectDTO.newBuilder()
                .setId(subjectDO.getId())
                .setTitle(subjectDO.getTitle())
                .setDescription(subjectDO.getDescription())
                .setStart(subjectDO.getStart())
                .setVotes(subjectDO.getVotes())
                .setDue(subjectDO.getDue())
                .setUser(UserMapper.convertToUserDTO(subjectDO.getUser()));

        return subjectDTOBuilder.createSubjectDTO();
    }

    public static SubjectDO convertToSubjectDO(SubjectDTO subjectDTO)
    {
        return new SubjectDO(subjectDTO.getTitle(), subjectDTO.getDescription(), subjectDTO.getStart(), subjectDTO.getDue());
    }

    public static List<SubjectDTO> makeSubjectDTOList(Collection<SubjectDO> subjects)
    {
        return subjects.stream()
                .map(SubjectMapper::convertToSubjectDTO)
                .collect(Collectors.toList());
    }
}
