package com.projedata.sms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Raw Material not found!", value = HttpStatus.NOT_FOUND)

public class RawMaterialNotFoundExeception extends RuntimeException {
}
