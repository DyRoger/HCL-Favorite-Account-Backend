package org.bank.hcl.model;

import lombok.Data;

@Data
public class LoginData {

    private String hash;
    private String customerId;
}
