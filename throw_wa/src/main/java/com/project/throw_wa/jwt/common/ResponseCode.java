package com.project.throw_wa.jwt.common;

public interface ResponseCode {

    String SUCCESS = "SU";

    String VALIDATE_FAILED = "VF";
    String DUPLICATE_ID = "DI";

    String SIGN_IN_FAIL = "SF";
    String CERTIFICATION_FAIL = "CF";

    String DATABASE_ERROR = "DBE";
}
