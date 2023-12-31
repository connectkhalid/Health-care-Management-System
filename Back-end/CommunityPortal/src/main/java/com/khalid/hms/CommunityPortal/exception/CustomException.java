package com.khalid.hms.CommunityPortal.exception;

import com.khalid.hms.CommunityPortal.response.ActionResponse;
import lombok.*;
import org.springframework.http.HttpStatus;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends Exception{
    private ActionResponse errorResponse;
    public CustomException(String message) {
        super(message);
        this.errorResponse = new ActionResponse(message);
    }

    public HttpStatus getHttpStatus() {
        // Assuming HttpStatus.BAD_REQUEST as a default status for the example
        return HttpStatus.BAD_REQUEST;
    }

    public ActionResponse getErrorResponse() {
        return errorResponse;
    }
}
