package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Currencies {

    private Long id;
    private String code;
    private String fullName;
    private String sign;

    public Currencies() {
    }
}
