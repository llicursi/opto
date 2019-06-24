package com.llicursi.opto.datatransferobject.mapper;

import com.llicursi.opto.datatransferobject.VoteDTO;
import com.llicursi.opto.domainobject.VoteDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class VoteMapper {

    public static VoteDTO convertToVoteDTO(VoteDO voteDO)
    {
       return new VoteDTO(voteDO.getVoteIdentity().getSubject().getId(), voteDO.getAgree());
    }

    public static List<VoteDTO> makeVoteDTOList(Collection<VoteDO> votes)
    {
        return votes.stream()
                .map(VoteMapper::convertToVoteDTO)
                .collect(Collectors.toList());
    }
}
