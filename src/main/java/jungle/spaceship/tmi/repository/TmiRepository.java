package jungle.spaceship.tmi.repository;

import jungle.spaceship.member.entity.Member;
import jungle.spaceship.tmi.entity.Tmi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TmiRepository extends JpaRepository<Tmi, Long> {
    List<Tmi> findByMember_Family_FamilyIdAndCreateAtIsAfterOrderByCreateAtDesc(Long familyId, LocalDateTime today);

    Long countByMemberAndCreateAtIsAfterAndCreateAtIsBefore(Member member, LocalDateTime startOfDay, LocalDateTime endOfDay);
}