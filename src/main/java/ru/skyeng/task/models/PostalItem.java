package ru.skyeng.task.models;

import lombok.Getter;
import lombok.Setter;
import ru.skyeng.task.enums.TypeOfPostalItem;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PostalItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    TypeOfPostalItem type;
    @Column
    Long addresseeIndex;
    @Column
    String addresseeAddress;
    @Column
    String name;

}
