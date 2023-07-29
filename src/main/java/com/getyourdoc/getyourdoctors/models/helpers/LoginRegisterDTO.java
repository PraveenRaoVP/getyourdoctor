package com.getyourdoc.getyourdoctors.models.helpers;

import com.getyourdoc.getyourdoctors.models.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRegisterDTO {
    private String email;
    private String password;
}
