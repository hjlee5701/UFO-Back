package jungle.spaceship.tmi.entity;

import jungle.spaceship.chat.controller.dto.ChatResponseDto;
import jungle.spaceship.tmi.controller.dto.TmiDto;
import jungle.spaceship.member.entity.Member;
import jungle.spaceship.member.entity.Timestamped;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
public class Tmi extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tmiId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Tmi(TmiDto dto, Member member) {
        this.content = dto.getContent();
        this.member = member;
    }

    public ChatResponseDto toDto() {
        return new ChatResponseDto(this.content, this.member.getNickname());
    }
}