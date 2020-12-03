package pl.coderslab.blinddate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Konto z takim adresem email już istnieje")
public class DuplicateEmailException extends RuntimeException {
}

