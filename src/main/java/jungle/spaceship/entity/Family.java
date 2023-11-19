package jungle.spaceship.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jungle.spaceship.controller.dto.FamilyDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Family extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long familyId;

    private String ufoName;
    private String plantName;

    @OneToMany(mappedBy = "family")
    @JsonManagedReference
    private List<Member> members = new ArrayList<>();

    public Family(FamilyDto dto) {
        this.ufoName = dto.getUfoName();
        this.plantName = dto.getPlantName();
    }

}