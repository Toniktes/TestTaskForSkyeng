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
    private TypeOfPostalItem type;
    @Column
    private Long addresseeIndex;
    @Column
    private String addresseeAddress;
    @Column
    private String addresseeName;

}
