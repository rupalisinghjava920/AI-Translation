package com.ai.translation.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Translation_Table")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "input_text", length = 1000)
    private String inputText;

    @Column(name = "translated_text", length = 1000)
    private String translatedText;

    @Column(name = "source_lang")
    private String sourceLang;

    @Column(name = "target_lang")
    private String targetLang;

}
