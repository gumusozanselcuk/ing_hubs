package com.inghubs.creditmodule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Auth request dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {

    /**
     * Username for auth
     */
    @NotNull
    public String username;

    /**
     * Password for auth
     */
    @NotNull
    public String password;

}
